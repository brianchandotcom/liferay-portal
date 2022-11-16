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