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

package com.liferay.subscription.web.internal.util;

import com.liferay.portal.kernel.exception.NoSuchTicketException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.subscription.web.constants.SubscriptionConstants;

import javax.portlet.ActionRequest;

/**
 * @author Alejandro Tardín
 */
public class UnsubscribeUtil {

	public static void checkUser(long userId, ActionRequest actionRequest)
		throws PortalException {

		User user = PortalUtil.getUser(actionRequest);

		if ((user != null) && (userId != user.getUserId())) {
			throw new PrincipalException();
		}
	}

	public static void checkUser(long userId, Subscription subscription)
		throws PrincipalException {

		if ((subscription != null) && (subscription.getUserId() != userId)) {
			throw new PrincipalException();
		}
	}

	public static Ticket getTicket(TicketLocalService service, String key)
		throws PortalException {

		Ticket ticket = service.getTicket(key);

		if (ticket.getType() != SubscriptionConstants.TICKET_TYPE) {
			throw new NoSuchTicketException("Invalid type " + ticket.getType());
		}

		if (!Subscription.class.getName().equals(ticket.getClassName())) {
			throw new NoSuchTicketException(
				"Invalid className " + ticket.getClassName());
		}

		return ticket;
	}

}