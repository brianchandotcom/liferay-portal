/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mypages.action;

import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.sites.action.ActionUtil;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 * @author Amos Fong
 */
public class ViewAction extends PortletAction {

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		if (renderRequest.getRemoteUser() == null) {
			return actionMapping.findForward("portlet.my_pages.view");
		}

		if (!renderRequest.getWindowState().equals(WindowState.MAXIMIZED)) {
			return actionMapping.findForward("portlet.my_pages.view");
		}

		User user = PortalUtil.getUser(renderRequest);

		RenderRequestImpl renderRequestImpl = (RenderRequestImpl)renderRequest;

		DynamicServletRequest dynamicRequest =
			(DynamicServletRequest)renderRequestImpl.getHttpServletRequest();

		dynamicRequest.setParameter(
			"p_u_i_d", String.valueOf(user.getUserId()));

		String tabs1 = "public-pages";

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

		return actionMapping.findForward("portlet.my_pages.edit_layouts");
	}

}