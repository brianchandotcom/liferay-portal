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

package com.liferay.portal.cache.cluster;

import com.liferay.portal.cache.bootstrap.ClusterLinkBootstrapLoader;
import com.liferay.portal.kernel.cache.BootstrapLoader;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheManagerListener;
import com.liferay.portal.kernel.cache.CallbackFactory;

import java.io.Serializable;

import java.util.Properties;

/**
 * @author Tina Tian
 */
public class ClusterLinkCallbackFactory implements CallbackFactory {

	public static final CallbackFactory INSTANCE =
		new ClusterLinkCallbackFactory();

	@Override
	public BootstrapLoader createBootstrapLoader(Properties properties) {
		return new ClusterLinkBootstrapLoader(properties);
	}

	@Override
	public <K extends Serializable, V> CacheListener<K, V> createCacheListener(
		Properties properties) {

		return (CacheListener<K, V>)
			new ClusterLinkCacheReplicator<K, Serializable>(properties);
	}

	@Override
	public CacheManagerListener createCacheManagerListener(
		Properties properties) {

		throw new UnsupportedOperationException();
	}

	private ClusterLinkCallbackFactory() {
	}

}