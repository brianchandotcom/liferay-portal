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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.template.TemplateResource;

/**
 * @author Tina Tian
 */
public class TemplateResourcePortalCacheListener
	implements PortalCacheListener<String, TemplateResource> {

	public TemplateResourcePortalCacheListener(
		String templateResourceLoaderName) {

		String portalCacheName = TemplateResource.class.getName();

		portalCacheName = portalCacheName.concat(StringPool.POUND).concat(
			templateResourceLoaderName);

		_portalCache = SingleVMPoolUtil.getPortalCache(portalCacheName);
	}

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

	private final PortalCache<TemplateResource, ?> _portalCache;

}