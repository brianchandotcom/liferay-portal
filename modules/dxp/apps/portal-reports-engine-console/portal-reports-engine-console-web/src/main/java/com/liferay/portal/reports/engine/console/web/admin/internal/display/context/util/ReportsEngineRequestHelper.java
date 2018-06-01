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

package com.liferay.portal.reports.engine.console.web.admin.internal.display.context.util;

import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.reports.engine.console.configuration.ReportsGroupServiceEmailConfiguration;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsoleConstants;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class ReportsEngineRequestHelper extends BaseRequestHelper {

	public ReportsEngineRequestHelper(HttpServletRequest request) {
		super(request);

		_renderRequest = (RenderRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		_portletPreferences = _renderRequest.getPreferences();
	}

	public PortletPreferences getPortletPreferences() {
		return _portletPreferences;
	}

	public RenderRequest getRenderRequest() {
		return _renderRequest;
	}

	public ReportsGroupServiceEmailConfiguration
			getReportsGroupServiceEmailConfiguration()
		throws PortalException {

		if (_reportsGroupServiceEmailConfiguration != null) {
			return _reportsGroupServiceEmailConfiguration;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (Validator.isNotNull(portletDisplay.getPortletResource())) {
			_reportsGroupServiceEmailConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					ReportsGroupServiceEmailConfiguration.class,
					new ParameterMapSettingsLocator(
						_renderRequest.getParameterMap(),
						new GroupServiceSettingsLocator(
							themeDisplay.getSiteGroupId(),
							ReportsEngineConsoleConstants.SERVICE_NAME)));
		}
		else {
			_reportsGroupServiceEmailConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					ReportsGroupServiceEmailConfiguration.class,
					new GroupServiceSettingsLocator(
						themeDisplay.getSiteGroupId(),
						ReportsEngineConsoleConstants.SERVICE_NAME));
		}

		return _reportsGroupServiceEmailConfiguration;
	}

	private final PortletPreferences _portletPreferences;
	private final RenderRequest _renderRequest;
	private ReportsGroupServiceEmailConfiguration
		_reportsGroupServiceEmailConfiguration;

}