/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;

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

		UserPermissionUtil.check(
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

}