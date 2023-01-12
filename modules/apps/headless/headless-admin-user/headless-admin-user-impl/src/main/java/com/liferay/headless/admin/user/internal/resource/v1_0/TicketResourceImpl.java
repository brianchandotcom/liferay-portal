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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.Ticket;
import com.liferay.headless.admin.user.resource.v1_0.TicketResource;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.UserPermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/ticket.properties",
	scope = ServiceScope.PROTOTYPE, service = TicketResource.class
)
public class TicketResourceImpl extends BaseTicketResourceImpl {

	@Override
	public Ticket getUserAccountEmailVerificationTicket(Long userAccountId)
		throws Exception {

		return _getTicket(TicketConstants.TYPE_EMAIL_ADDRESS, userAccountId);
	}

	@Override
	public Ticket getUserAccountPasswordResetTicket(Long userAccountId)
		throws Exception {

		return _getTicket(TicketConstants.TYPE_PASSWORD, userAccountId);
	}

	private Ticket _getTicket(int type, Long userAccountId) throws Exception {
		_userLocalService.getUser(userAccountId);

		_userPermission.check(
			PermissionThreadLocal.getPermissionChecker(), userAccountId,
			ActionKeys.UPDATE);

		List<com.liferay.portal.kernel.model.Ticket> tickets =
			_ticketLocalService.getTickets(
				User.class.getName(), userAccountId, type);

		if (tickets.isEmpty()) {
			return null;
		}

		return _toTicket(tickets.get(0));
	}

	private Ticket _toTicket(com.liferay.portal.kernel.model.Ticket ticket)
		throws Exception {

		return new Ticket() {
			{
				expirationDate = ticket.getExpirationDate();
				extraInfo = ticket.getExtraInfo();
				id = ticket.getTicketId();
				key = ticket.getKey();
			}
		};
	}

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserPermission _userPermission;

}