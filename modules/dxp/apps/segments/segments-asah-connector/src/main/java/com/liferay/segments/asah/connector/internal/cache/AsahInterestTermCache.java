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
 * @author Sarai Díaz
 */
@Component(
	configurationPid = "com.liferay.segments.asah.connector.internal.configuration.SegmentsAsahConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = AsahInterestTermCache.class
)
public class AsahInterestTermCache {

	public String[] getInterestTerms(String userId) {
		return _portalCache.get(_generateCacheKey(userId));
	}

	public void putInterestTerms(String userId, String[] terms) {
		_portalCache.put(
			_generateCacheKey(userId), terms,
			_interestTermsTimeToLiveInSeconds);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		SegmentsAsahConfiguration segmentsAsahConfiguration =
			ConfigurableUtil.createConfigurable(
				SegmentsAsahConfiguration.class, properties);

		_interestTermsTimeToLiveInSeconds =
			segmentsAsahConfiguration.interestTermsCacheExpirationTime();
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache =
			(PortalCache<String, String[]>)multiVMPool.getPortalCache(
				AsahInterestTermCache.class.getName());
	}

	private String _generateCacheKey(String userId) {
		return _CACHE_PREFIX + userId;
	}

	private static final String _CACHE_PREFIX = "segments-";

	private int _interestTermsTimeToLiveInSeconds =
		PortalCache.DEFAULT_TIME_TO_LIVE;
	private PortalCache<String, String[]> _portalCache;

}