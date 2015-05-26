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

package com.liferay.portlet.configuration.icon.print;

import com.liferay.portal.kernel.portlet.configuration.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.PortletConfigurationIconFactory;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = PortletConfigurationIconFactory.class)
public class PrintPortletConfigurationIconFactory
	implements PortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(HttpServletRequest request) {
		return new PrintPortletConfigurationIcon(request);
	}

	@Override
	public double getWeight() {
		return 8.0;
	}

	private class PrintPortletConfigurationIcon
		extends BasePortletConfigurationIcon {

		public PrintPortletConfigurationIcon(HttpServletRequest request) {
			_themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);
		}

		@Override
		public String getCssClass() {
			return "portlet-print portlet-print-icon";
		}

		@Override
		public String getImage() {
			return "../aui/print";
		}

		@Override
		public String getMessage() {
			return "print";
		}

		@Override
		public String getOnClick() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return "location.href = '".concat(
				portletDisplay.getURLPrint()).concat("'; return false;");
		}

		@Override
		public String getTarget() {
			return "_blank";
		}

		@Override
		public String getURL() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return portletDisplay.getURLPrint();
		}

		@Override
		public boolean isShow() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return portletDisplay.isShowPrintIcon();
		}

		@Override
		public boolean isToolTip() {
			return false;
		}

		private final ThemeDisplay _themeDisplay;

	}

}