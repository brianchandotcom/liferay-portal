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

package com.liferay.layout.admin.web.portlet.configuration.icon;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class EmbeddedPortletsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public EmbeddedPortletsPortletConfigurationIcon(
		PortletRequest portletRequest, LayoutLocalService layoutLocalService) {

		super(portletRequest);

		_layoutLocalService = layoutLocalService;
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return "embedded-portlets";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		try {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mvcPath", "/embedded_portlets.jsp");
			portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
			portletURL.setParameter(
				"selPlid", String.valueOf(getSelPlid(portletRequest)));
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			Layout layout = getLayout(portletRequest);

			if (layout == null) {
				return false;
			}

			if (!layout.isSupportsEmbeddedPortlets()) {
				return false;
			}

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			List<Portlet> embeddedPortlets =
				layoutTypePortlet.getEmbeddedPortlets();

			if (!embeddedPortlets.isEmpty()) {
				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	protected Layout getLayout(PortletRequest portletRequest) throws Exception {
		long selPlid = getSelPlid(portletRequest);

		return _layoutLocalService.fetchLayout(selPlid);
	}

	protected long getSelPlid(PortletRequest portletRequest) {
		return ParamUtil.getLong(
			portletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);
	}

	private final LayoutLocalService _layoutLocalService;

}