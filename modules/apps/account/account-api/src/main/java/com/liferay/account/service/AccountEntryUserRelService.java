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

package com.liferay.account.service;

import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for AccountEntryUserRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRelServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface AccountEntryUserRelService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountEntryUserRelServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the account entry user rel remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link AccountEntryUserRelServiceUtil} if injection and service tracking are not available.
	 */
	public AccountEntryUserRel addAccountEntryUserRel(
			long accountEntryId, long creatorUserId, String screenName,
			String emailAddress, Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			String jobTitle, ServiceContext serviceContext)
		throws PortalException;

	public AccountEntryUserRel addAccountEntryUserRelByEmailAddress(
			long accountEntryId, String emailAddress, long[] accountRoleIds,
			String userExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	public void addAccountEntryUserRels(
			long accountEntryId, long[] accountUserIds)
		throws PortalException;

	public AccountEntryUserRel addPersonTypeAccountEntryUserRel(
			long accountEntryId, long creatorUserId, String screenName,
			String emailAddress, Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			String jobTitle, ServiceContext serviceContext)
		throws PortalException;

	public void deleteAccountEntryUserRelByEmailAddress(
			long accountEntryId, String emailAddress)
		throws PortalException;

	public void deleteAccountEntryUserRels(
			long accountEntryId, long[] accountUserIds)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * Invites users to the account by email address. If the email address is
	 * associated with a registered user, the user will be added to the account
	 * immediately. Otherwise, an email will be sent to the user to sign up.
	 * The user will be added to the account upon registration.
	 *
	 * @param accountEntryId the primary key of the account
	 * @param accountRoleIds the primary keys of the account roles that will be
	 assigned to the user
	 * @param emailAddress the invited user's email address
	 * @param inviter the user that makes the invitation request
	 * @param serviceContext the service context to be applied. Can set the
	 request used to send the invitation email
	 */
	public void inviteUser(
			long accountEntryId, long[] accountRoleIds, String emailAddress,
			User inviter, ServiceContext serviceContext)
		throws PortalException;

	public void setPersonTypeAccountEntryUser(long accountEntryId, long userId)
		throws PortalException;

}