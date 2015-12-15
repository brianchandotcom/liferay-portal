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

package com.liferay.portlet.css.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.css.web.constants.PortletCSSPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class PortletCSSPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PortletCSSPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getCssClass() {
		return "lfr-js-required portlet-css portlet-css-icon";
	}

	@Override
	public String getMessage() {
		return "look-and-feel";
	}

	@Override
	public String getOnClick() {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL baseActionURL = PortletURLFactoryUtil.create(
			portletRequest, PortletCSSPortletKeys.PORTLET_CSS,
			themeDisplay.getPlid(), PortletRequest.ACTION_PHASE);

		PortletURL baseRenderURL = PortletURLFactoryUtil.create(
			portletRequest, PortletCSSPortletKeys.PORTLET_CSS,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		PortletURL baseResourceURL = PortletURLFactoryUtil.create(
			portletRequest, PortletCSSPortletKeys.PORTLET_CSS,
			themeDisplay.getPlid(), PortletRequest.RESOURCE_PHASE);

		StringBundler sb = new StringBundler(9);

		sb.append("Liferay.Portlet.loadCSSEditor('");
		sb.append(portletDisplay.getId());
		sb.append("', '");
		sb.append(baseActionURL);
		sb.append("', '");
		sb.append(baseRenderURL);
		sb.append("', '");
		sb.append(baseResourceURL);
		sb.append("'); return false;");

		return sb.toString();
	}

	@Override
	public String getURL() {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getURLPortletCss();
	}

	@Override
	public boolean isShow() {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.isShowPortletCssIcon();
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}