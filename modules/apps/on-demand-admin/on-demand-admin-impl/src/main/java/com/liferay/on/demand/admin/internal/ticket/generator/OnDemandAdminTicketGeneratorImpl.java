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

package com.liferay.on.demand.admin.internal.ticket.generator;

import com.liferay.on.demand.admin.constants.OnDemandAdminConstants;
import com.liferay.on.demand.admin.ticket.generator.OnDemandAdminTicketGenerator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = OnDemandAdminTicketGenerator.class)
public class OnDemandAdminTicketGeneratorImpl
	implements OnDemandAdminTicketGenerator {

	@Override
	public Ticket generate(Company company, long requestorUserId)
		throws PortalException {

		User user = _addOnDemandAdminUser(company, requestorUserId);

		return _ticketLocalService.addDistinctTicket(
			user.getCompanyId(), User.class.getName(), user.getUserId(),
			OnDemandAdminConstants.TICKET_TYPE_ON_DEMAND_ADMIN_LOGIN, null,
			new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)),
			null);
	}

	private User _addOnDemandAdminUser(Company company, long userId)
		throws PortalException {

		User requestorUser = _userLocalService.getUser(userId);

		Date date = requestorUser.getBirthday();

		Role role = _roleLocalService.getRole(
			company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		boolean autoPassword = false;
		boolean sendEmail = false;

		String password = PropsValues.DEFAULT_ADMIN_PASSWORD;

		if (Validator.isNull(password)) {
			autoPassword = true;
			sendEmail = true;
		}

		User user = _userLocalService.addUser(
			requestorUser.getUserId(), company.getCompanyId(), autoPassword,
			password, password, true, null, requestorUser.getEmailAddress(),
			requestorUser.getLocale(), requestorUser.getFirstName(),
			requestorUser.getMiddleName(), requestorUser.getLastName(), 0, 0,
			requestorUser.getMale(), date.getMonth(), date.getDay(),
			date.getYear(), null, null, null, new long[] {role.getRoleId()},
			null, sendEmail, new ServiceContext());

		String screenName = _getScreenName(
			requestorUser.getUserId(), user.getUserId());

		user.setScreenName(screenName);
		user.setEmailAddress(screenName + StringPool.AT + company.getMx());

		user.setEmailAddressVerified(true);

		return _userLocalService.updateUser(user);
	}

	private String _getScreenName(long requestorUserId, long userId)
		throws PortalException {

		return StringBundler.concat(
			OnDemandAdminConstants.SCREEN_NAME_PREFIX_ON_DEMAND_ADMIN,
			StringPool.UNDERLINE, requestorUserId, StringPool.UNDERLINE,
			userId);
	}

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}