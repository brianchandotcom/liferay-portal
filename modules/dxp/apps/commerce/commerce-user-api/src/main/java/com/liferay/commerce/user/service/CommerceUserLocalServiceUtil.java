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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceUser. This utility wraps
 * {@link com.liferay.commerce.user.service.impl.CommerceUserLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceUserLocalService
 * @see com.liferay.commerce.user.service.base.CommerceUserLocalServiceBaseImpl
 * @see com.liferay.commerce.user.service.impl.CommerceUserLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceUserLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.user.service.impl.CommerceUserLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.User updatePassword(
		long userId, java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePassword(userId, password1, password2, passwordReset);
	}

	public static com.liferay.portal.kernel.model.User updatePasswordReset(
		long userId, boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updatePasswordReset(userId, passwordReset);
	}

	public static com.liferay.portal.kernel.model.User updateReminderQuery(
		long userId, java.lang.String question, java.lang.String answer)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateReminderQuery(userId, question, answer);
	}

	public static com.liferay.portal.kernel.model.User updateUser(long userId,
		java.lang.String screenName, java.lang.String emailAddress,
		boolean portrait, byte[] portraitBytes, java.lang.String languageId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateUser(userId, screenName, emailAddress, portrait,
			portraitBytes, languageId, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, serviceContext);
	}

	public static void updateUserRoles(long userId, long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateUserRoles(userId, groupId, roleIds);
	}

	public static CommerceUserLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceUserLocalService, CommerceUserLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceUserLocalService.class);
}