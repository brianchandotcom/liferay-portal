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