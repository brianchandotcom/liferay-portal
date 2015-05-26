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

package com.liferay.portlet.configuration.icon.close;

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
public class ClosePortletConfigurationIconFactory
	implements PortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(HttpServletRequest request) {
		return new ClosePortletConfigurationIcon(request);
	}

	@Override
	public double getWeight() {
		return 1.0;
	}

	private class ClosePortletConfigurationIcon
		extends BasePortletConfigurationIcon {

		public ClosePortletConfigurationIcon(HttpServletRequest request) {
			_themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);
		}

		@Override
		public String getCssClass() {
			return "item-remove portlet-close portlet-close-icon";
		}

		@Override
		public String getImage() {
			return "../aui/remove";
		}

		@Override
		public String getMessage() {
			return "remove";
		}

		@Override
		public String getOnClick() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return "Liferay.Portlet.close('#p_p_id_".concat(
				portletDisplay.getId()).concat("_'); return false;");
		}

		@Override
		public String getURL() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return portletDisplay.getURLClose();
		}

		@Override
		public boolean isShow() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return portletDisplay.isShowCloseIcon();
		}

		@Override
		public boolean isToolTip() {
			return false;
		}

		private final ThemeDisplay _themeDisplay;

	}

}