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

package com.liferay.layout.admin.web.portlet;

import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.sites.action.ActionUtil;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-layouts-admin",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/my_pages.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.single-page-application=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=My Pages",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutAdminPortletKeys.MY_PAGES,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class}
)
public class MyPagesPortlet extends LayoutAdminPortlet {

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			User user = PortalUtil.getUser(renderRequest);

			RenderRequestImpl renderRequestImpl =
				(RenderRequestImpl)renderRequest;

			DynamicServletRequest dynamicRequest =
				(DynamicServletRequest)renderRequestImpl.
					getHttpServletRequest();

			dynamicRequest.setParameter(
				"p_u_i_d", String.valueOf(user.getUserId()));

			String tabs1 = ParamUtil.getString(
				dynamicRequest, "tabs1", "public-pages");

			boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(
				user.getUserId(), user.getCompanyId(), RoleConstants.POWER_USER,
				true);

			if (PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED &&
				!hasPowerUserRole) {

				tabs1 = "private-pages";
			}

			dynamicRequest.setParameter("tabs1", tabs1);

			Group group = user.getGroup();

			dynamicRequest.setParameter(
				"groupId", String.valueOf(group.getGroupId()));

			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		super.doDispatch(renderRequest, renderResponse);
	}

}