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

package com.liferay.osb.faro.web.internal.servlet.filter;

import com.liferay.ip.geocoder.IPGeocoder;
import com.liferay.ip.geocoder.IPInfo;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;

import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 */
@Component(
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=Blocked Countries Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class BlockedCountriesServletFilter extends BaseFilter {

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		IPGeocoder ipGeocoder = _ipGeocoderSnapshot.get();

		if ((ipGeocoder != null) && _isBlockedCountry(httpServletRequest)) {
			httpServletResponse.sendError(
				HttpServletResponse.SC_FORBIDDEN,
				"This content is not available in your country");

			return;
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private boolean _isBlockedCountry(HttpServletRequest httpServletRequest) {
		IPGeocoder ipGeocoder = _ipGeocoderSnapshot.get();

		IPInfo ipInfo = ipGeocoder.getIPInfo(httpServletRequest);

		return _blockedCountryCodes.contains(ipInfo.getCountryCode());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlockedCountriesServletFilter.class);

	private static final List<String> _blockedCountryCodes = Arrays.asList(
		"CU", "IR", "KP", "SY");
	private static final Snapshot<IPGeocoder> _ipGeocoderSnapshot =
		new Snapshot<>(BlockedCountriesServletFilter.class, IPGeocoder.class);

}