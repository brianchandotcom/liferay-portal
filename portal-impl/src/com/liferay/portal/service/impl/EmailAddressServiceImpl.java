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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.CommonPermissionUtil;
import com.liferay.portal.service.base.EmailAddressServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class EmailAddressServiceImpl extends EmailAddressServiceBaseImpl {

	@Override
	public EmailAddress addEmailAddress(
			String className, long classPK, String address, long typeId,
			boolean primary, ServiceContext serviceContext)
		throws PortalException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return emailAddressLocalService.addEmailAddress(
			getUserId(), className, classPK, address, typeId, primary,
			serviceContext);
	}

	@Override
	public void deleteEmailAddress(long emailAddressId) throws PortalException {
		EmailAddress emailAddress = emailAddressPersistence.findByPrimaryKey(
			emailAddressId);

		CommonPermissionUtil.check(
			getPermissionChecker(), emailAddress.getClassNameId(),
			emailAddress.getClassPK(), ActionKeys.UPDATE);

		emailAddressLocalService.deleteEmailAddress(emailAddress);
	}

	/**
	 * Returns the email address with the primary key.
	 *
	 * @param  emailAddressId the primary key of the email address
	 * @return the email address with the primary key, or <code>null</code> if
	 *         an email address with the primary key could not be found or if
	 *         the user did not have permission to view the email address
	 */
	@Override
	public EmailAddress fetchEmailAddress(long emailAddressId)
		throws PortalException {

		EmailAddress emailAddress = emailAddressPersistence.fetchByPrimaryKey(
			emailAddressId);

		if (emailAddress != null) {
			CommonPermissionUtil.check(
				getPermissionChecker(), emailAddress.getClassNameId(),
				emailAddress.getClassPK(), ActionKeys.VIEW);
		}

		return emailAddress;
	}

	@Override
	public EmailAddress getEmailAddress(long emailAddressId)
		throws PortalException {

		EmailAddress emailAddress = emailAddressPersistence.findByPrimaryKey(
			emailAddressId);

		CommonPermissionUtil.check(
			getPermissionChecker(), emailAddress.getClassNameId(),
			emailAddress.getClassPK(), ActionKeys.VIEW);

		return emailAddress;
	}

	@Override
	public List<EmailAddress> getEmailAddresses(String className, long classPK)
		throws PortalException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.VIEW);

		User user = getUser();

		return emailAddressLocalService.getEmailAddresses(
			user.getCompanyId(), className, classPK);
	}

	@Override
	public EmailAddress updateEmailAddress(
			long emailAddressId, String address, long typeId, boolean primary)
		throws PortalException {

		EmailAddress emailAddress = emailAddressPersistence.findByPrimaryKey(
			emailAddressId);

		CommonPermissionUtil.check(
			getPermissionChecker(), emailAddress.getClassNameId(),
			emailAddress.getClassPK(), ActionKeys.UPDATE);

		return emailAddressLocalService.updateEmailAddress(
			emailAddressId, address, typeId, primary);
	}

}