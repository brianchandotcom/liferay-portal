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

package com.liferay.commerce.user.service.impl;

import com.liferay.commerce.user.service.base.CommerceUserLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceUserLocalServiceImpl
	extends CommerceUserLocalServiceBaseImpl {

	@Override
	public void setUserRoles(long userId, long[] roleIds)
		throws PortalException {

		roleLocalService.setUserRoles(userId, roleIds);
	}

	@Override
	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException {

		return userLocalService.updatePassword(
			userId, password1, password2, passwordReset);
	}

	@Override
	public User updatePasswordReset(long userId, boolean passwordReset)
		throws PortalException {

		return userLocalService.updatePasswordReset(userId, passwordReset);
	}

	@Override
	public User updateReminderQuery(long userId, String question, String answer)
		throws PortalException {

		return userLocalService.updateReminderQuery(userId, question, answer);
	}

	@Override
	public User updateUser(
			long userId, String screenName, String emailAddress,
			boolean portrait, byte[] portraitBytes, String languageId,
			String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		return userLocalService.updateUser(
			userId, user.getPassword(), null, null, false,
			user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
			screenName, emailAddress, user.getFacebookId(), user.getOpenId(),
			portrait, portraitBytes, languageId, user.getTimeZoneId(),
			user.getGreeting(), user.getComments(), firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, null, null, null, null, null, jobTitle,
			user.getGroupIds(), user.getOrganizationIds(), user.getRoleIds(),
			null, user.getUserGroupIds(), serviceContext);
	}

}