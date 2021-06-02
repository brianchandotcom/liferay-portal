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

package com.liferay.redirect.internal.configuration;

import com.liferay.portal.kernel.redirect.RedirectURLSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = RedirectURLSettings.class)
public class RedirectURLSettingsImpl implements RedirectURLSettings {

	@Override
	public String[] getAllowedDomains(long companyId) {
		RedirectURLConfiguration redirectURLConfiguration =
			_redirectURLConfigurationTrackerImpl.
				getCompanyRedirectURLConfiguration(companyId);

		return redirectURLConfiguration.allowedDomains();
	}

	@Override
	public String[] getAllowedIPs(long companyId) {
		RedirectURLConfiguration redirectURLConfiguration =
			_redirectURLConfigurationTrackerImpl.
				getCompanyRedirectURLConfiguration(companyId);

		return redirectURLConfiguration.allowedIPs();
	}

	@Override
	public String getSecurityMode(long companyId) {
		RedirectURLConfiguration redirectURLConfiguration =
			_redirectURLConfigurationTrackerImpl.
				getCompanyRedirectURLConfiguration(companyId);

		return redirectURLConfiguration.securityMode();
	}

	@Reference
	private RedirectURLConfigurationTrackerImpl
		_redirectURLConfigurationTrackerImpl;

}