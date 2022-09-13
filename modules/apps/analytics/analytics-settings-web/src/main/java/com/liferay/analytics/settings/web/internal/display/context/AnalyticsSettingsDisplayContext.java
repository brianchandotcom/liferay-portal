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

package com.liferay.analytics.settings.web.internal.display.context;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.web.internal.constants.AnalyticsSettingsWebKeys;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Riccardo Ferrari
 */
public class AnalyticsSettingsDisplayContext extends BaseDisplayContext {

	public AnalyticsSettingsDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		super(httpServletRequest, httpServletResponse);

		_analyticsConfiguration =
			(AnalyticsConfiguration)httpServletRequest.getAttribute(
				AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION);
	}

	public String getLiferayAnalyticsURL() {
		return _analyticsConfiguration.liferayAnalyticsURL();
	}

	public String getToken() {
		return _analyticsConfiguration.token();
	}

	public boolean isConnected() {
		return !Validator.isBlank(_analyticsConfiguration.token());
	}

	private final AnalyticsConfiguration _analyticsConfiguration;

}