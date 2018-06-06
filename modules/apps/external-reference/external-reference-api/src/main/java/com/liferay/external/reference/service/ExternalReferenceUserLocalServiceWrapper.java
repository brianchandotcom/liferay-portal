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

package com.liferay.external.reference.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExternalReferenceUserLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalReferenceUserLocalService
 * @generated
 */
@ProviderType
public class ExternalReferenceUserLocalServiceWrapper
	implements ExternalReferenceUserLocalService,
		ServiceWrapper<ExternalReferenceUserLocalService> {
	public ExternalReferenceUserLocalServiceWrapper(
		ExternalReferenceUserLocalService externalReferenceUserLocalService) {
		_externalReferenceUserLocalService = externalReferenceUserLocalService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _externalReferenceUserLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Add or update an user.
	*
	* @param creatorUserId the primary key of the creator
	* @param companyId the primary key of the user's company
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the user's roles
	* @param userGroupRoles the user user's group roles
	* @param userGroupIds the primary keys of the user's user groups
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param externalReferenceCode the user's external reference code
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user.
	* @review
	* @return the user
	*/
	@Override
	public com.liferay.portal.kernel.model.User upsertUser(long creatorUserId,
		long companyId, boolean autoPassword, String password1,
		String password2, boolean autoScreenName, String screenName,
		String emailAddress, java.util.Locale locale, String firstName,
		String middleName, String lastName, long prefixId, long suffixId,
		boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
		String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		java.util.List<com.liferay.portal.kernel.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds, boolean sendEmail, String externalReferenceCode,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _externalReferenceUserLocalService.upsertUser(creatorUserId,
			companyId, autoPassword, password1, password2, autoScreenName,
			screenName, emailAddress, locale, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, sendEmail, externalReferenceCode, serviceContext);
	}

	@Override
	public ExternalReferenceUserLocalService getWrappedService() {
		return _externalReferenceUserLocalService;
	}

	@Override
	public void setWrappedService(
		ExternalReferenceUserLocalService externalReferenceUserLocalService) {
		_externalReferenceUserLocalService = externalReferenceUserLocalService;
	}

	private ExternalReferenceUserLocalService _externalReferenceUserLocalService;
}