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

package com.liferay.users.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portlet.LiferayPortletUtil;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ACCOUNT,
		"mvc.command.name=/", "mvc.command.name=/my_account/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMyAccountMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		if (renderRequest.getRemoteUser() == null) {
			return "/null.jsp";
		}

		try {
			User user = _portal.getUser(renderRequest);

			LiferayPortletRequest liferayPortletRequest =
				LiferayPortletUtil.getLiferayPortletRequest(renderRequest);

			DynamicServletRequest dynamicRequest =
				(DynamicServletRequest)
					liferayPortletRequest.getHttpServletRequest();

			dynamicRequest.setParameter(
				"p_u_i_d", String.valueOf(user.getUserId()));

			return "/edit_user.jsp";
		}
		catch (PortalException pe) {
			throw new PortletException(pe);
		}
	}

	@Reference
	private Portal _portal;

}