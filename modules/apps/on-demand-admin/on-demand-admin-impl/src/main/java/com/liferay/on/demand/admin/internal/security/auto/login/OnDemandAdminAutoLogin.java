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

package com.liferay.on.demand.admin.internal.security.auto.login;

import com.liferay.on.demand.admin.constants.OnDemandAdminConstants;
import com.liferay.on.demand.admin.manager.OnDemandAdminManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = AutoLogin.class)
public class OnDemandAdminAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		Ticket ticket = _getTicket(httpServletRequest);

		if (ticket == null) {
			return null;
		}

		try {
			User user = _userLocalService.getUser(ticket.getClassPK());

			if (!_onDemandAdminManager.isOnDemandAdminUser(user)) {
				return null;
			}

			String[] credentials = new String[3];

			credentials[0] = String.valueOf(user.getUserId());
			credentials[1] = user.getPassword();
			credentials[2] = Boolean.TRUE.toString();

			return credentials;
		}
		finally {
			_ticketLocalService.deleteTicket(ticket);
		}
	}

	private Ticket _getTicket(HttpServletRequest httpServletRequest) {
		String ticketKey = ParamUtil.getString(httpServletRequest, "ticketKey");

		if (Validator.isNull(ticketKey)) {
			return null;
		}

		try {
			Ticket ticket = _ticketLocalService.fetchTicket(ticketKey);

			if ((ticket == null) ||
				(ticket.getType() !=
					OnDemandAdminConstants.TICKET_TYPE_ON_DEMAND_ADMIN_LOGIN)) {

				return null;
			}

			if (!ticket.isExpired()) {
				return ticket;
			}

			_ticketLocalService.deleteTicket(ticket);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OnDemandAdminAutoLogin.class);

	@Reference
	private OnDemandAdminManager _onDemandAdminManager;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}