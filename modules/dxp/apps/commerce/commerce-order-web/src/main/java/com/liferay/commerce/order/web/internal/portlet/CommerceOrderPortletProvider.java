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

package com.liferay.commerce.order.web.internal.portlet;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.ViewPortletProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.model.CommerceOrder",
	service = {ViewPortletProvider.class}
)
public class CommerceOrderPortletProvider
	extends BasePortletProvider implements ViewPortletProvider {

	@Override
	public String getPortletName() {
		return CommercePortletKeys.COMMERCE_ORDER;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest request, Group group)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long plid = _portal.getPlidFromPortletId(
			group.getGroupId(), getPortletName());

		long controlPanelPlid = _portal.getControlPanelPlid(
			themeDisplay.getCompanyId());

		if ((plid == controlPanelPlid) ||
			(plid == LayoutConstants.DEFAULT_PLID)) {

			return _portal.getControlPanelPortletURL(
				request, group, getPortletName(), 0, 0,
				PortletRequest.RENDER_PHASE);
		}
		else {
			return PortletURLFactoryUtil.create(
				request, getPortletName(), plid, PortletRequest.RENDER_PHASE);
		}
	}

	@Reference
	private Portal _portal;

}