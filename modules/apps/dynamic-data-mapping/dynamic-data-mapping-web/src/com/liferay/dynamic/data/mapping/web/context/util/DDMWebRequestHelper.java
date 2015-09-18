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

package com.liferay.dynamic.data.mapping.web.context.util;

import com.liferay.dynamic.data.mapping.configuration.DDMConfiguration;
import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lino Alves
 */

public class DDMWebRequestHelper {

	public DDMWebRequestHelper(HttpServletRequest request) {
		_request = request;
	}

	public DDMConfiguration
		getDDMConfiguration() {

		try {
			if (_ddmConfiguration == null) {
				ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
					WebKeys.THEME_DISPLAY);

				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				if (Validator.isNotNull(portletDisplay.getPortletResource())) {
					_ddmConfiguration =
						ConfigurationFactoryUtil.getConfiguration(
								DDMConfiguration.class,
						new ParameterMapSettingsLocator(
							_request.getParameterMap(),
							new GroupServiceSettingsLocator(
								themeDisplay.getSiteGroupId(),
								DDMConstants.SERVICE_NAME)));
				}
				else {
					_ddmConfiguration =
						ConfigurationFactoryUtil.getConfiguration(
								DDMConfiguration.class,
							new GroupServiceSettingsLocator(
								themeDisplay.getSiteGroupId(),
								DDMConstants.SERVICE_NAME));
				}
			}

			return _ddmConfiguration;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private DDMConfiguration _ddmConfiguration;
	private final HttpServletRequest _request;

}