/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Tina Tian
 */
public class TemplateResourceCacheListener
	implements CacheListener<String, TemplateResource> {

	public TemplateResourceCacheListener(String templateResourceLoaderName) {
		String cacheName = TemplateResource.class.getName();

		cacheName = cacheName.concat(StringPool.POUND).concat(
			templateResourceLoaderName);

		_portalCache = SingleVMPoolUtil.getCache(cacheName);
	}

	public void notifyEntryEvicted(
			PortalCache<String, TemplateResource> portalCache, String key,
			TemplateResource templateResource)
		throws PortalCacheException {

		if (templateResource != null) {
			_portalCache.remove(templateResource);
		}
	}

	public void notifyEntryExpired(
			PortalCache<String, TemplateResource> portalCache, String key,
			TemplateResource templateResource)
		throws PortalCacheException {

		if (templateResource != null) {
			_portalCache.remove(templateResource);
		}
	}

	public void notifyEntryPut(
			PortalCache<String, TemplateResource> portalCache, String key,
			TemplateResource templateResource)
		throws PortalCacheException {
	}

	public void notifyEntryRemoved(
			PortalCache<String, TemplateResource> portalCache, String key,
			TemplateResource templateResource)
		throws PortalCacheException {

		if (templateResource != null) {
			_portalCache.remove(templateResource);
		}
	}

	public void notifyEntryUpdated(
			PortalCache<String, TemplateResource> portalCache, String key,
			TemplateResource templateResource)
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

	private PortalCache<TemplateResource, ?> _portalCache;

}