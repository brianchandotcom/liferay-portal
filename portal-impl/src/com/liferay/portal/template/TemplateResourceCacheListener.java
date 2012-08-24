/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class TemplateResourceCacheListener implements CacheListener {

	public TemplateResourceCacheListener(String templateResourceLoaderName) {
		String cacheName = TemplateResource.class.getName();

		cacheName = cacheName.concat(StringPool.POUND).concat(
			templateResourceLoaderName);

		_portalCache = SingleVMPoolUtil.getCache(cacheName);
	}

	public void notifyEntryEvicted(
			PortalCache portalCache, Serializable key, Object value)
		throws PortalCacheException {

		if (value != null) {
			TemplateResource templateResource = (TemplateResource)value;

			_portalCache.remove(templateResource);
		}
	}

	public void notifyEntryExpired(
			PortalCache portalCache, Serializable key, Object value)
		throws PortalCacheException {

		if (value != null) {
			TemplateResource templateResource = (TemplateResource)value;

			_portalCache.remove(templateResource);
		}
	}

	public void notifyEntryPut(
			PortalCache portalCache, Serializable key, Object value)
		throws PortalCacheException {
	}

	public void notifyEntryRemoved(
			PortalCache portalCache, Serializable key, Object value)
		throws PortalCacheException {

		if (value != null) {
			TemplateResource templateResource = (TemplateResource)value;

			_portalCache.remove(templateResource);
		}
	}

	public void notifyEntryUpdated(
			PortalCache portalCache, Serializable key, Object value)
		throws PortalCacheException {

		if (value != null) {
			TemplateResource templateResource = (TemplateResource)value;

			_portalCache.remove(templateResource);
		}
	}

	public void notifyRemoveAll(PortalCache portalCache)
		throws PortalCacheException {

		_portalCache.removeAll();
	}

	private PortalCache _portalCache;

}