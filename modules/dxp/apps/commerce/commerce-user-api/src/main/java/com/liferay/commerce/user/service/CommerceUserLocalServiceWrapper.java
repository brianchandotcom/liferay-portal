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

package com.liferay.commerce.user.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceUserLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceUserLocalService
 * @generated
 */
@ProviderType
public class CommerceUserLocalServiceWrapper implements CommerceUserLocalService,
	ServiceWrapper<CommerceUserLocalService> {
	public CommerceUserLocalServiceWrapper(
		CommerceUserLocalService commerceUserLocalService) {
		_commerceUserLocalService = commerceUserLocalService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.User updatePassword(long userId,
		String password1, String password2, boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserLocalService.updatePassword(userId, password1,
			password2, passwordReset);
	}

	@Override
	public com.liferay.portal.kernel.model.User updatePasswordReset(
		long userId, boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserLocalService.updatePasswordReset(userId,
			passwordReset);
	}

	@Override
	public com.liferay.portal.kernel.model.User updateReminderQuery(
		long userId, String question, String answer)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserLocalService.updateReminderQuery(userId, question,
			answer);
	}

	@Override
	public com.liferay.portal.kernel.model.User updateUser(long userId,
		String screenName, String emailAddress, boolean portrait,
		byte[] portraitBytes, String languageId, String firstName,
		String middleName, String lastName, long prefixId, long suffixId,
		boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
		String jobTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserLocalService.updateUser(userId, screenName,
			emailAddress, portrait, portraitBytes, languageId, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, serviceContext);
	}

	@Override
	public void updateUserRoles(long userId, long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceUserLocalService.updateUserRoles(userId, groupId, roleIds);
	}

	@Override
	public CommerceUserLocalService getWrappedService() {
		return _commerceUserLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceUserLocalService commerceUserLocalService) {
		_commerceUserLocalService = commerceUserLocalService;
	}

	private CommerceUserLocalService _commerceUserLocalService;
}