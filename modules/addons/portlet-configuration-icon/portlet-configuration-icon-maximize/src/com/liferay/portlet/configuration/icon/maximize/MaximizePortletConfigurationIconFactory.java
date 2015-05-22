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

package com.liferay.portlet.configuration.icon.maximize;

import com.liferay.portal.kernel.portlet.configuration.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.PortletConfigurationIconFactory;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = PortletConfigurationIconFactory.class)
public class MaximizePortletConfigurationIconFactory
	implements PortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(HttpServletRequest request) {
		final ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new BasePortletConfigurationIcon() {

			@Override
			public String getCssClass() {
				return "portlet-maximize portlet-maximize-icon";
			}

			public String getImage() {
				return "../aui/plus";
			}

			@Override
			public String getMessage() {
				return "maximize";
			}

			@Override
			public String getMethod() {
				return "get";
			}

			@Override
			public String getOnClick() {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				return "submitForm(document.hrefFm, '".concat(
					HtmlUtil.escapeJS(portletDisplay.getURLMax())).concat(
						"'); return false;");
			}

			@Override
			public String getURL() {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				return portletDisplay.getURLMax();
			}

			@Override
			public boolean isShow() {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				return portletDisplay.isShowMaxIcon();
			}

			@Override
			public boolean isToolTip() {
				return false;
			}

		};
	}

	@Override
	public double getWeight() {
		return 7.0;
	}

}