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
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
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
		long userId, java.lang.String question, java.lang.String answer)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserLocalService.updateReminderQuery(userId, question,
			answer);
	}

	@Override
	public com.liferay.portal.kernel.model.User updateUser(long userId,
		java.lang.String screenName, java.lang.String emailAddress,
		boolean portrait, byte[] portraitBytes, java.lang.String languageId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle,
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