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

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.admin.web.internal.portlet.action.util.TicketUtil;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.portal.kernel.exception.NoSuchTicketException;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_USERS_REGISTRATION,
		"mvc.command.name=/account_admin/create_account_user",
		"portlet.add.default.resource.check.whitelist.mvc.action=true"
	},
	service = MVCRenderCommand.class
)
public class CreateAccountUserMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		Ticket ticket = TicketUtil.getTicket(
			renderRequest, _ticketLocalService);

		if (ticket == null) {
			SessionErrors.add(renderRequest, NoSuchTicketException.class);

			return "/account_user_registration/error.jsp";
		}

		return "/account_user_registration/create_account_user.jsp";
	}

	@Reference
	private TicketLocalService _ticketLocalService;

}