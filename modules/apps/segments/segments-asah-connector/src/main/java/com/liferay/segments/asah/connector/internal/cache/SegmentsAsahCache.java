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

package com.liferay.segments.asah.connector.internal.cache;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.segments.asah.connector.internal.configuration.SegmentsAsahConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	configurationPid = "com.liferay.segments.asah.connector.internal.configuration.SegmentsAsahConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = SegmentsAsahCache.class
)
public class SegmentsAsahCache {

	public long[] getSegmentsEntryIds(String userId) {
		return _portalCache.get(_generateCacheKey(userId));
	}

	public void putSegmentsEntryIds(String userId, long[] segmentsEntryIds) {
		_portalCache.put(
			_generateCacheKey(userId), segmentsEntryIds, _timeToLiveInSeconds);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		SegmentsAsahConfiguration segmentsAsahConfiguration =
			ConfigurableUtil.createConfigurable(
				SegmentsAsahConfiguration.class, properties);

		_timeToLiveInSeconds =
			segmentsAsahConfiguration.anonymousUserSegmentsLifespan();
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache = (PortalCache<String, long[]>)multiVMPool.getPortalCache(
			SegmentsAsahCache.class.getName());
	}

	private String _generateCacheKey(String userId) {
		return _CACHE_PREFIX + userId;
	}

	private static final String _CACHE_PREFIX = "segments-";

	private PortalCache<String, long[]> _portalCache;
	private int _timeToLiveInSeconds = PortalCache.DEFAULT_TIME_TO_LIVE;

}