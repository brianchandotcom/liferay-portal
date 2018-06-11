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

import com.liferay.commerce.user.service.base.CommerceUserServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CommerceUserServiceImpl extends CommerceUserServiceBaseImpl {

	@Override
	public User getUser(long userId) throws PortalException {
		return userLocalService.getUser(userId);
	}

	@Override
	public User updateActive(long userId, boolean active)
		throws PortalException {

		_checkUser(userId);

		return commerceUserLocalService.updateActive(userId, active);
	}

	@Override
	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException {

		_checkUser(userId);

		return commerceUserLocalService.updatePassword(
			userId, password1, password2, passwordReset);
	}

	@Override
	public User updatePasswordReset(long userId, boolean passwordReset)
		throws PortalException {

		_checkUser(userId);

		return commerceUserLocalService.updatePasswordReset(
			userId, passwordReset);
	}

	@Override
	public User updateReminderQuery(long userId, String question, String answer)
		throws PortalException {

		_checkUser(userId);

		return commerceUserLocalService.updateReminderQuery(
			userId, question, answer);
	}

	@Override
	public User updateUser(
			long userId, String screenName, String emailAddress,
			boolean portrait, byte[] portraitBytes, String languageId,
			String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, ServiceContext serviceContext)
		throws PortalException {

		_checkUser(userId);

		return commerceUserLocalService.updateUser(
			userId, screenName, emailAddress, portrait, portraitBytes,
			languageId, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			serviceContext);
	}

	@Override
	public void updateUserRoles(long userId, long groupId, long[] roleIds)
		throws PortalException {

		_checkUser(userId);

		commerceUserLocalService.updateUserRoles(userId, groupId, roleIds);
	}

	private void _checkUser(long userId) throws PortalException {
		User user = userLocalService.getUser(userId);

		for (long organizationId : user.getOrganizationIds()) {
			if (OrganizationPermissionUtil.contains(
					getPermissionChecker(), organizationId,
					ActionKeys.MANAGE_USERS)) {

				return;
			}
		}

		throw new PrincipalException();
	}

}