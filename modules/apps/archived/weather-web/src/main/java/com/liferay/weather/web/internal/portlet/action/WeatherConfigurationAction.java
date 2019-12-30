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

package com.liferay.weather.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.weather.web.internal.constants.WeatherPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Samuel Kong
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + WeatherPortletKeys.WEATHER,
	service = ConfigurationAction.class
)
public class WeatherConfigurationAction extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String apiKey = getParameter(actionRequest, "apiKey");

		setPreference(actionRequest, "apiKey", apiKey);

		String[] zips = StringUtil.split(
			getParameter(actionRequest, "zips"), StringPool.NEW_LINE);

		setPreference(actionRequest, "zips", zips);

		try {
			super.processAction(portletConfig, actionRequest, actionResponse);
		}
		catch (ValidatorException ve) {
			SessionErrors.add(
				actionRequest, ValidatorException.class.getName(), ve);
		}
	}

}