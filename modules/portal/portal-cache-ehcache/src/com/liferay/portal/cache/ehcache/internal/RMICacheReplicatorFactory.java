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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.kernel.cache.CacheReplicator;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Properties;

/**
 * @author Tina Tian
 */
public class RMICacheReplicatorFactory
	extends net.sf.ehcache.distribution.RMICacheReplicatorFactory {

	@Override
	protected boolean extractReplicatePuts(Properties properties) {
		return GetterUtil.getBoolean(
			properties.getProperty(CacheReplicator.REPLICATE_PUTS),
			CacheReplicator.DEFAULT_REPLICATE_PUTS);
	}

	@Override
	protected boolean extractReplicatePutsViaCopy(Properties properties) {
		return GetterUtil.getBoolean(
			properties.getProperty(CacheReplicator.REPLICATE_PUTS_VIA_COPY),
			CacheReplicator.DEFAULT_REPLICATE_PUTS_VIA_COPY);
	}

	@Override
	protected boolean extractReplicateRemovals(Properties properties) {
		return GetterUtil.getBoolean(
			properties.getProperty(CacheReplicator.REPLICATE_REMOVALS),
			CacheReplicator.DEFAULT_REPLICATE_REMOVALS);
	}

	@Override
	protected boolean extractReplicateUpdates(Properties properties) {
		return GetterUtil.getBoolean(
			properties.getProperty(CacheReplicator.REPLICATE_UPDATES),
			CacheReplicator.DEFAULT_REPLICATE_UPDATES);
	}

	@Override
	protected boolean extractReplicateUpdatesViaCopy(Properties properties) {
		return GetterUtil.getBoolean(
			properties.getProperty(CacheReplicator.REPLICATE_UPDATES_VIA_COPY),
			CacheReplicator.DEFAULT_REPLICATE_UPDATES_VIA_COPY);
	}

}