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

package com.liferay.portlet.configuration.icon.refresh;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.theme.PortletDisplay;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class RefreshPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public RefreshPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getCssClass() {
		return "portlet-refresh portlet-refresh-icon";
	}

	@Override
	public String getMessage() {
		return "refresh";
	}

	@Override
	public String getOnClick() {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return "Liferay.Portlet.refresh('#p_p_id_".concat(
			portletDisplay.getId()).concat("_'); return false;");
	}

	@Override
	public String getURL() {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getURLRefresh();
	}

	@Override
	public boolean isShow() {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.isShowRefreshIcon();
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}