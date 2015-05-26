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

package com.liferay.portlet.configuration.icon.help;

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
public class HelpPortletConfigurationIconFactory
	implements PortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(HttpServletRequest request) {
		return new HelpPortletConfigurationIcon(request);
	}

	@Override
	public double getWeight() {
		return 9.0;
	}

	private class HelpPortletConfigurationIcon
		extends BasePortletConfigurationIcon {

		public HelpPortletConfigurationIcon(HttpServletRequest request) {
			_themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);
		}

		@Override
		public String getCssClass() {
			return "portlet-help portlet-help-icon";
		}

		@Override
		public String getImage() {
			return "../aui/question-sign";
		}

		@Override
		public String getMessage() {
			return "help";
		}

		@Override
		public String getURL() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return portletDisplay.getURLHelp();
		}

		@Override
		public boolean isShow() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return portletDisplay.isShowHelpIcon();
		}

		@Override
		public boolean isToolTip() {
			return false;
		}

		private final ThemeDisplay _themeDisplay;

	}

}