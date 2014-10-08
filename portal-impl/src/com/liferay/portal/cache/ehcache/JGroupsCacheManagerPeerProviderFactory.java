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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CacheManagerPeerProviderFactory;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-11061.
 * </p>
 *
 * @author Tina Tian
 */
public class JGroupsCacheManagerPeerProviderFactory
	extends CacheManagerPeerProviderFactory {

	@Override
	public CacheManagerPeerProvider createCachePeerProvider(
		CacheManager cacheManager, Properties properties) {

		if (_log.isDebugEnabled()) {
			_log.debug("Creating JGroups peer provider");
		}

		String clusterName = properties.getProperty("clusterName");

		if (clusterName == null) {
			clusterName = _DEFAULT_CLUSTER_NAME;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Cluster name " + clusterName);
		}

		String channelProperties = properties.getProperty("channelProperties");

		if (channelProperties != null) {
			channelProperties = StringUtil.replace(
				channelProperties, StringPool.SPACE, StringPool.BLANK);

			if (Validator.isNull(channelProperties)) {
				channelProperties = null;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Channel properties " + channelProperties);
		}

		return new JGroupsManager(cacheManager, clusterName, channelProperties);
	}

	private static final String _DEFAULT_CLUSTER_NAME = "Ehcache";

	private static final Log _log = LogFactoryUtil.getLog(
		JGroupsCacheManagerPeerProviderFactory.class);

}