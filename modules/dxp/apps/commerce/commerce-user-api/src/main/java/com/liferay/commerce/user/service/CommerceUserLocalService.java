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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the local service interface for CommerceUser. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceUserLocalServiceUtil
 * @see com.liferay.commerce.user.service.base.CommerceUserLocalServiceBaseImpl
 * @see com.liferay.commerce.user.service.impl.CommerceUserLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceUserLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceUserLocalServiceUtil} to access the commerce user local service. Add custom service methods to {@link com.liferay.commerce.user.service.impl.CommerceUserLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public User updatePassword(long userId, java.lang.String password1,
		java.lang.String password2, boolean passwordReset)
		throws PortalException;

	public User updatePasswordReset(long userId, boolean passwordReset)
		throws PortalException;

	public User updateReminderQuery(long userId, java.lang.String question,
		java.lang.String answer) throws PortalException;

	public User updateUser(long userId, java.lang.String screenName,
		java.lang.String emailAddress, boolean portrait, byte[] portraitBytes,
		java.lang.String languageId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, long prefixId,
		long suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle,
		ServiceContext serviceContext) throws PortalException;

	public void updateUserRoles(long userId, long groupId, long[] roleIds)
		throws PortalException;
}