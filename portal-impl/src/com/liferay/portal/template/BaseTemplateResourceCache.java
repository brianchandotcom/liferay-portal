/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.template;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceCache;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.ProxyFactory;

/**
 * @author Tina Tian
 */
public abstract class BaseTemplateResourceCache
	implements TemplateResourceCache {

	@Override
	public void clear() {
		if (!isEnabled()) {
			return;
		}

		_multiVMPortalCache.removeAll();
		_singleVMPortalCache.removeAll();
	}

	public <T> PortalCache<TemplateResource, T> getSecondLevelPortalCache() {
		return (PortalCache<TemplateResource, T>)_secondLevelPortalCache;
	}

	@Override
	public TemplateResource getTemplateResource(String templateId) {
		if (!isEnabled()) {
			return null;
		}

		TemplateResource templateResource = _singleVMPortalCache.get(
			templateId);

		if (templateResource == null) {
			templateResource = _multiVMPortalCache.get(templateId);
		}

		if ((templateResource != null) &&
			(templateResource != DUMMY_TEMPLATE_RESOURCE) &&
			(_modificationCheckInterval > 0)) {

			long expireTime =
				templateResource.getLastModified() + _modificationCheckInterval;

			if (System.currentTimeMillis() > expireTime) {
				remove(templateId);

				templateResource = null;

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Remove expired template resource " + templateId);
				}
			}
		}

		return templateResource;
	}

	@Override
	public boolean isEnabled() {
		if (_modificationCheckInterval == 0) {
			return false;
		}

		return true;
	}

	@Override
	public void put(String templateId, TemplateResource templateResource) {
		if (!isEnabled()) {
			return;
		}

		if (templateResource == null) {
			_singleVMPortalCache.put(templateId, DUMMY_TEMPLATE_RESOURCE);
		}
		else if (templateResource instanceof URLTemplateResource) {
			_singleVMPortalCache.put(
				templateId, new CacheTemplateResource(templateResource));
		}
		else if (templateResource instanceof CacheTemplateResource ||
				 templateResource instanceof StringTemplateResource) {

			_multiVMPortalCache.put(templateId, templateResource);
		}
		else {
			_multiVMPortalCache.put(
				templateId, new CacheTemplateResource(templateResource));
		}
	}

	@Override
	public void remove(String templateId) {
		if (!isEnabled()) {
			return;
		}

		_multiVMPortalCache.remove(templateId);
		_singleVMPortalCache.remove(templateId);
	}

	protected void destroy() {
		_multiVMPool.removePortalCache(
			_multiVMPortalCache.getPortalCacheName());

		_singleVMPool.removePortalCache(
			_singleVMPortalCache.getPortalCacheName());

		_singleVMPool.removePortalCache(
			_secondLevelPortalCache.getPortalCacheName());
	}

	protected void init(
		long modificationCheckInterval, MultiVMPool multiVMPool,
		SingleVMPool singleVMPool, String portalCacheName,
		String secondLevelPortalCacheName) {

		_modificationCheckInterval = modificationCheckInterval;
		_multiVMPool = multiVMPool;
		_singleVMPool = singleVMPool;

		_multiVMPortalCache =
			(PortalCache<String, TemplateResource>)multiVMPool.getPortalCache(
				portalCacheName);
		_singleVMPortalCache =
			(PortalCache<String, TemplateResource>)singleVMPool.getPortalCache(
				portalCacheName);

		_secondLevelPortalCache =
			(PortalCache<TemplateResource, ?>)_singleVMPool.getPortalCache(
				secondLevelPortalCacheName);

		_setSecondLevelPortalCache(_secondLevelPortalCache);
	}

	protected void setModificationCheckInterval(
		long modificationCheckInterval) {

		_modificationCheckInterval = modificationCheckInterval;
	}

	protected static final TemplateResource DUMMY_TEMPLATE_RESOURCE =
		ProxyFactory.newDummyInstance(TemplateResource.class);

	private void _setSecondLevelPortalCache(
		PortalCache<TemplateResource, ?> portalCache) {

		TemplateResourcePortalCacheListener
			templateResourcePortalCacheListener =
				new TemplateResourcePortalCacheListener(portalCache);

		_multiVMPortalCache.registerPortalCacheListener(
			templateResourcePortalCacheListener);
		_singleVMPortalCache.registerPortalCacheListener(
			templateResourcePortalCacheListener);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseTemplateResourceCache.class);

	private volatile long _modificationCheckInterval;
	private MultiVMPool _multiVMPool;
	private PortalCache<String, TemplateResource> _multiVMPortalCache;
	private PortalCache<TemplateResource, ?> _secondLevelPortalCache;
	private SingleVMPool _singleVMPool;
	private PortalCache<String, TemplateResource> _singleVMPortalCache;

	private class TemplateResourcePortalCacheListener
		implements PortalCacheListener<String, TemplateResource> {

		@Override
		public void dispose() {
		}

		@Override
		public void notifyEntryEvicted(
				PortalCache<String, TemplateResource> portalCache, String key,
				TemplateResource templateResource, int timeToLive)
			throws PortalCacheException {

			if (templateResource != null) {
				_portalCache.remove(templateResource);
			}
		}

		@Override
		public void notifyEntryExpired(
				PortalCache<String, TemplateResource> portalCache, String key,
				TemplateResource templateResource, int timeToLive)
			throws PortalCacheException {

			if (templateResource != null) {
				_portalCache.remove(templateResource);
			}
		}

		@Override
		public void notifyEntryPut(
				PortalCache<String, TemplateResource> portalCache, String key,
				TemplateResource templateResource, int timeToLive)
			throws PortalCacheException {
		}

		@Override
		public void notifyEntryRemoved(
				PortalCache<String, TemplateResource> portalCache, String key,
				TemplateResource templateResource, int timeToLive)
			throws PortalCacheException {

			if (templateResource != null) {
				_portalCache.remove(templateResource);
			}
		}

		@Override
		public void notifyEntryUpdated(
				PortalCache<String, TemplateResource> portalCache, String key,
				TemplateResource templateResource, int timeToLive)
			throws PortalCacheException {

			if (templateResource != null) {
				_portalCache.remove(templateResource);
			}
		}

		@Override
		public void notifyRemoveAll(
				PortalCache<String, TemplateResource> portalCache)
			throws PortalCacheException {

			_portalCache.removeAll();
		}

		private TemplateResourcePortalCacheListener(
			PortalCache<TemplateResource, ?> portalCache) {

			_portalCache = portalCache;
		}

		private final PortalCache<TemplateResource, ?> _portalCache;

	}

}