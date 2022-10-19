/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	service = AsahSegmentsEntryCache.class
)
public class AsahSegmentsEntryCache {

	public long[] getSegmentsEntryIds(String userId) {
		return _portalCache.get(_generateCacheKey(userId));
	}

	public void putSegmentsEntryIds(String userId, long[] segmentsEntryIds) {
		int anonymousTimeToLiveInSeconds = PortalCache.DEFAULT_TIME_TO_LIVE;

		try {
			anonymousTimeToLiveInSeconds =
				_segmentsAsahConfigurationProvider.
					getAnonymousUserSegmentsCacheExpirationTime(
						CompanyThreadLocal.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		_portalCache.put(
			_generateCacheKey(userId), segmentsEntryIds,
			anonymousTimeToLiveInSeconds);
	}

	@Activate
	protected void activate() {
		_portalCache = (PortalCache<String, long[]>)_multiVMPool.getPortalCache(
			AsahSegmentsEntryCache.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(AsahSegmentsEntryCache.class.getName());
	}

	private String _generateCacheKey(String userId) {
		return _CACHE_PREFIX + userId;
	}

	private static final String _CACHE_PREFIX = "segments-";

	private static final Log _log = LogFactoryUtil.getLog(
		AsahSegmentsEntryCache.class);

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<String, long[]> _portalCache;

	@Reference
	private SegmentsAsahConfigurationProvider
		_segmentsAsahConfigurationProvider;

}