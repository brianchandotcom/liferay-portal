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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ExternalReferenceUser. This utility wraps
 * {@link com.liferay.external.reference.service.impl.ExternalReferenceUserLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalReferenceUserLocalService
 * @see com.liferay.external.reference.service.base.ExternalReferenceUserLocalServiceBaseImpl
 * @see com.liferay.external.reference.service.impl.ExternalReferenceUserLocalServiceImpl
 * @generated
 */
@ProviderType
public class ExternalReferenceUserLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.external.reference.service.impl.ExternalReferenceUserLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	public static com.liferay.portal.kernel.model.User upsertUser(
		long creatorUserId, long companyId, boolean autoPassword,
		String password1, String password2, boolean autoScreenName,
		String screenName, String emailAddress, java.util.Locale locale,
		String firstName, String middleName, String lastName, long prefixId,
		long suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds,
		java.util.List<com.liferay.portal.kernel.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds, boolean sendEmail, String externalReferenceCode,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .upsertUser(creatorUserId, companyId, autoPassword,
			password1, password2, autoScreenName, screenName, emailAddress,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupRoles, userGroupIds, sendEmail,
			externalReferenceCode, serviceContext);
	}

	public static ExternalReferenceUserLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ExternalReferenceUserLocalService, ExternalReferenceUserLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ExternalReferenceUserLocalService.class);

		ServiceTracker<ExternalReferenceUserLocalService, ExternalReferenceUserLocalService> serviceTracker =
			new ServiceTracker<ExternalReferenceUserLocalService, ExternalReferenceUserLocalService>(bundle.getBundleContext(),
				ExternalReferenceUserLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}