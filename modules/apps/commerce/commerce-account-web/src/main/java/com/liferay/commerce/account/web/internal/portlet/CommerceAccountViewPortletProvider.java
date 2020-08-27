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

package com.liferay.commerce.account.web.internal.portlet;

import com.liferay.commerce.account.constants.CommerceAccountPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.account.model.CommerceAccount",
	service = ViewPortletProvider.class
)
public class CommerceAccountViewPortletProvider
	extends BasePortletProvider implements ViewPortletProvider {

	@Override
	public String getPortletName() {
		return CommerceAccountPortletKeys.COMMERCE_ACCOUNT;
	}

	@Override
	public PortletURL getPortletURL(
			HttpServletRequest httpServletRequest, Group group)
		throws PortalException {

		if (group == null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		long plid = _portal.getPlidFromPortletId(
			group.getGroupId(), getPortletName());

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, getPortletName(), plid,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "viewCommerceAccount");

		return portletURL;
	}

	@Reference
	private Portal _portal;

}