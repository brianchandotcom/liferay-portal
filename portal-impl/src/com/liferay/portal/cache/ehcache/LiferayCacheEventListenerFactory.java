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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayCacheEventListenerFactory
	extends CacheEventListenerFactory {

	public LiferayCacheEventListenerFactory() {
		String className = PropsValues.EHCACHE_CACHE_EVENT_LISTENER_FACTORY;

		if (_log.isDebugEnabled()) {
			_log.debug("Instantiating " + className + " " + hashCode());
		}

		try {
			_cacheEventListenerFactory =
				(CacheEventListenerFactory)InstanceFactory.newInstance(
					PortalClassLoaderUtil.getClassLoader(), className);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CacheEventListener createCacheEventListener(Properties properties) {
		return _cacheEventListenerFactory.createCacheEventListener(properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayCacheEventListenerFactory.class);

	private final CacheEventListenerFactory _cacheEventListenerFactory;

}