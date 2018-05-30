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
	public String getOSGiServiceIdentifier();

	public User updatePassword(long userId, String password1, String password2,
		boolean passwordReset) throws PortalException;

	public User updatePasswordReset(long userId, boolean passwordReset)
		throws PortalException;

	public User updateReminderQuery(long userId, String question, String answer)
		throws PortalException;

	public User updateUser(long userId, String screenName, String emailAddress,
		boolean portrait, byte[] portraitBytes, String languageId,
		String firstName, String middleName, String lastName, long prefixId,
		long suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, String jobTitle, ServiceContext serviceContext)
		throws PortalException;

	public void updateUserRoles(long userId, long groupId, long[] roleIds)
		throws PortalException;
}