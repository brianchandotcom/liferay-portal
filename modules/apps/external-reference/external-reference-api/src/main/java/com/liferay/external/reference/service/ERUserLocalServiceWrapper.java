/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.reference.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ERUserLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERUserLocalService
 * @generated
 */
public class ERUserLocalServiceWrapper
	implements ERUserLocalService, ServiceWrapper<ERUserLocalService> {

	public ERUserLocalServiceWrapper() {
		this(null);
	}

	public ERUserLocalServiceWrapper(ERUserLocalService erUserLocalService) {
		_erUserLocalService = erUserLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.User addOrUpdateUser(
			String externalReferenceCode, long creatorUserId, long companyId,
			boolean autoPassword, String password1, String password2,
			boolean autoScreenName, String screenName, String emailAddress,
			java.util.Locale locale, String firstName, String middleName,
			String lastName, long prefixListTypeId, long suffixListTypeId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, long[] groupIds, long[] organizationIds,
			long[] roleIds,
			java.util.List<com.liferay.portal.kernel.model.UserGroupRole>
				userGroupRoles,
			long[] userGroupIds, boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _erUserLocalService.addOrUpdateUser(
			externalReferenceCode, creatorUserId, companyId, autoPassword,
			password1, password2, autoScreenName, screenName, emailAddress,
			locale, firstName, middleName, lastName, prefixListTypeId,
			suffixListTypeId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, sendEmail, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _erUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public ERUserLocalService getWrappedService() {
		return _erUserLocalService;
	}

	@Override
	public void setWrappedService(ERUserLocalService erUserLocalService) {
		_erUserLocalService = erUserLocalService;
	}

	private ERUserLocalService _erUserLocalService;

}