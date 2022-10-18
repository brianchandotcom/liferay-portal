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

package com.liferay.account.admin.web.internal.portlet.action.util;

import com.liferay.account.constants.AccountTicketConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;

/**
 * @author Pei-Jung Lan
 */
public class TicketUtil {

	public static Ticket getTicket(
		PortletRequest portletRequest, TicketLocalService ticketLocalService) {

		String ticketKey = ParamUtil.getString(portletRequest, "ticketKey");

		if (Validator.isNull(ticketKey)) {
			return null;
		}

		try {
			Ticket ticket = ticketLocalService.fetchTicket(ticketKey);

			if ((ticket == null) ||
				(ticket.getType() !=
					AccountTicketConstants.TYPE_USER_INVITATION)) {

				return null;
			}

			if (!ticket.isExpired()) {
				return ticket;
			}

			ticketLocalService.deleteTicket(ticket);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(TicketUtil.class);

}