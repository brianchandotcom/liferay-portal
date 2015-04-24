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

package com.liferay.portlet.css.web.configuration;

import com.liferay.portal.kernel.portlet.configuration.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.PortletConfigurationIcon;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.css.web.constants.PortletCSSPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, service = PortletConfigurationIcon.class
)
public class PortletCSSPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getCssClass() {
		return "portlet-css portlet-css-icon lfr-js-required";
	}

	@Override
	public String getImage() {
		return "../aui/picture";
	}

	@Override
	public String getMessage() {
		return "look-and-feel";
	}

	@Override
	public String getOnClick() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		HttpServletRequest request = _themeDisplay.getRequest();

		PortletURL baseActionURL = PortletURLFactoryUtil.create(
			request, PortletCSSPortletKeys.PORTLET_CSS, _themeDisplay.getPlid(),
			PortletRequest.ACTION_PHASE);

		PortletURL baseRenderURL = PortletURLFactoryUtil.create(
			request, PortletCSSPortletKeys.PORTLET_CSS, _themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		PortletURL baseResourceURL = PortletURLFactoryUtil.create(
			request, PortletCSSPortletKeys.PORTLET_CSS, _themeDisplay.getPlid(),
			PortletRequest.RESOURCE_PHASE);

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
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.getURLPortletCss();
	}

	@Override
	public double getWeight() {
		return 16.0;
	}

	@Override
	public boolean isShow() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.isShowPortletCssIcon();
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}