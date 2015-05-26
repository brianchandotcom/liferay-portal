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

package com.liferay.portlet.configuration.icon.minimize;

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
public class MinimizePortletConfigurationIconFactory
	implements PortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(HttpServletRequest request) {
		return new MinimizePortletConfigurationIcon(request);
	}

	@Override
	public double getWeight() {
		return 6.0;
	}

	private class MinimizePortletConfigurationIcon
		extends BasePortletConfigurationIcon {

		public MinimizePortletConfigurationIcon(HttpServletRequest request) {
			_themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);
		}

		@Override
		public String getCssClass() {
			return "portlet-minimize portlet-minimize-icon";
		}

		@Override
		public String getImage() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			if (portletDisplay.isStateMin()) {
				return "../aui/resize-vertical";
			}

			return "../aui/minus";
		}

		@Override
		public String getMessage() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			if (portletDisplay.isStateMin()) {
				return "restore";
			}

			return "minimize";
		}

		@Override
		public String getOnClick() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return "Liferay.Portlet.minimize('#p_p_id_".concat(
				portletDisplay.getId()).concat("_', this); return false;");
		}

		@Override
		public String getURL() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return portletDisplay.getURLMin();
		}

		@Override
		public boolean isShow() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return portletDisplay.isShowMinIcon();
		}

		@Override
		public boolean isToolTip() {
			return false;
		}

		private final ThemeDisplay _themeDisplay;

	}

}