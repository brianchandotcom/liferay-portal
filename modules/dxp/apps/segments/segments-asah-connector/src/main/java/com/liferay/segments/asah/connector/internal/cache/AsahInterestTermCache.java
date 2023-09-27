/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.cache;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.segments.asah.connector.internal.configuration.provider.SegmentsAsahConfigurationProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(service = AsahInterestTermCache.class)
public class AsahInterestTermCache {

	public String[] getInterestTerms(String userId) {
		return _portalCache.get(_generateCacheKey(userId));
	}

	public void putInterestTerms(String userId, String[] terms) {
		int interestTermsTimeToLiveInSeconds = PortalCache.DEFAULT_TIME_TO_LIVE;

		try {
			interestTermsTimeToLiveInSeconds =
				_segmentsAsahConfigurationProvider.
					getInterestTermsCacheExpirationTime(
						CompanyThreadLocal.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		_portalCache.put(
			_generateCacheKey(userId), terms, interestTermsTimeToLiveInSeconds);
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<String, String[]>)_multiVMPool.getPortalCache(
				AsahInterestTermCache.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(AsahInterestTermCache.class.getName());
	}

	private String _generateCacheKey(String userId) {
		return _CACHE_PREFIX + userId;
	}

	private static final String _CACHE_PREFIX = "segments-";

	private static final Log _log = LogFactoryUtil.getLog(
		AsahInterestTermCache.class);

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<String, String[]> _portalCache;

	@Reference
	private SegmentsAsahConfigurationProvider
		_segmentsAsahConfigurationProvider;

}