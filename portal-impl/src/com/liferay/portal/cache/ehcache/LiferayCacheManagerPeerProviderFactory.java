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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CacheManagerPeerProviderFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayCacheManagerPeerProviderFactory
	extends CacheManagerPeerProviderFactory {

	public LiferayCacheManagerPeerProviderFactory() {
		String className =
			PropsValues.EHCACHE_CACHE_MANAGER_PEER_PROVIDER_FACTORY;

		if (_log.isDebugEnabled()) {
			_log.debug("Instantiating " + className + " " + hashCode());
		}

		try {
			_cacheManagerPeerProviderFactory =
				(CacheManagerPeerProviderFactory)InstanceFactory.newInstance(
					className);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CacheManagerPeerProvider createCachePeerProvider(
		CacheManager cacheManager, Properties properties) {

		String portalPropertyKey = properties.getProperty("portalPropertyKey");

		if (Validator.isNull(portalPropertyKey)) {
			throw new RuntimeException("portalPropertyKey is null");
		}

		String[] values = PropsUtil.getArray(portalPropertyKey);

		if (_log.isInfoEnabled()) {
			_log.info(
				"portalPropertyKey " + portalPropertyKey + " has value " +
					Arrays.toString(values));
		}

		Properties portalProperties = new Properties();

		for (String value : values) {
			String[] valueParts = StringUtil.split(value, CharPool.EQUAL);

			if (valueParts.length != 2) {
				if (_log.isWarnEnabled()) {
					_log.warn("Ignore malformed value " + value);
				}
			}

			portalProperties.put(
				valueParts[0], _htmlUtil.unescape(valueParts[1]));
		}

		if (_log.isDebugEnabled()) {
			_log.debug(PropertiesUtil.list(portalProperties));
		}

		return _cacheManagerPeerProviderFactory.createCachePeerProvider(
			cacheManager, portalProperties);
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayCacheManagerPeerProviderFactory.class);

	private static HtmlImpl _htmlUtil = new HtmlImpl();

	private CacheManagerPeerProviderFactory _cacheManagerPeerProviderFactory;

}