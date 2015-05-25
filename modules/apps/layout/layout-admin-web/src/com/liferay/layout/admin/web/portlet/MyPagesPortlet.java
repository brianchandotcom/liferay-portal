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

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
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