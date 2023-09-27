/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ip.geocoder.internal.segments.context.contributor;

import com.liferay.ip.geocoder.IPGeocoder;
import com.liferay.ip.geocoder.IPInfo;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.segments.context.Context;
import com.liferay.segments.context.contributor.RequestContextContributor;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Allen Ziegenfus
 */
@Component(
	property = {
		"request.context.contributor.key=" + IPGeocoderCountryRequestContextContributor.KEY,
		"request.context.contributor.type=string"
	},
	service = RequestContextContributor.class
)
public class IPGeocoderCountryRequestContextContributor
	implements RequestContextContributor {

	public static final String KEY = "ipGeocoderCountry";

	@Override
	public void contribute(
		Context context, HttpServletRequest httpServletRequest) {

		IPInfo ipInfo = _ipGeocoder.getIPInfo(httpServletRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(ipInfo);
		}

		if (ipInfo.getCountryCode() != null) {
			context.put(KEY, ipInfo.getCountryCode());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IPGeocoderCountryRequestContextContributor.class);

	@Reference
	private IPGeocoder _ipGeocoder;

}