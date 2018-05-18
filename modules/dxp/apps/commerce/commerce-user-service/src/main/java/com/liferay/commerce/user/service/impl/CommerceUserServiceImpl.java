/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.service.impl;

import com.liferay.commerce.user.service.base.CommerceUserServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

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
	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException {

		return commerceUserLocalService.updatePassword(
			userId, password1, password2, passwordReset);
	}

	@Override
	public User updatePasswordReset(long userId, boolean passwordReset)
		throws PortalException {

		return commerceUserLocalService.updatePasswordReset(
			userId, passwordReset);
	}

	@Override
	public User updateReminderQuery(long userId, String question, String answer)
		throws PortalException {

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

		return commerceUserLocalService.updateUser(
			userId, screenName, emailAddress, portrait, portraitBytes,
			languageId, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			serviceContext);
	}

	@Override
	public void updateUserRoles(long userId, long groupId, long[] roleIds)
		throws PortalException {

		commerceUserLocalService.updateUserRoles(userId, groupId, roleIds);
	}

}