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

package com.liferay.commerce.product.content.web.internal.display.context;

import com.liferay.commerce.product.content.web.configuration.CPContentConfigurationHelper;
import com.liferay.commerce.product.content.web.configuration.CPContentPortletInstanceConfiguration;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPContentConfigurationDisplayContext {

	public CPContentConfigurationDisplayContext(
			CPContentConfigurationHelper cpContentConfigurationHelper,
			CPTypeServicesTracker cpTypeServicesTracker,
			HttpServletRequest httpServletRequest)
		throws Exception {

		_cpContentConfigurationHelper = cpContentConfigurationHelper;
		_cpTypeServicesTracker = cpTypeServicesTracker;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpContentPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPContentPortletInstanceConfiguration.class);
	}

	public List<CPType> getCPTypes() {
		return _cpTypeServicesTracker.getCPTypes();
	}

	public String getDisplayStyle(String cpTypeName) {
		return _cpContentConfigurationHelper.getCPTypeDisplayStyle(
			_cpContentPortletInstanceConfiguration, cpTypeName);
	}

	public long getDisplayStyleGroupId(String cpTypeName) {
		return _cpContentConfigurationHelper.getCPTypeDisplayStyleGroupId(
			_cpContentPortletInstanceConfiguration, cpTypeName);
	}

	private final CPContentConfigurationHelper _cpContentConfigurationHelper;
	private final CPContentPortletInstanceConfiguration
		_cpContentPortletInstanceConfiguration;
	private final CPTypeServicesTracker _cpTypeServicesTracker;

}