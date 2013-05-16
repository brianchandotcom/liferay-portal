/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.EmailAddressException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.EmailAddressLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class EmailAddressLocalServiceImpl
	extends EmailAddressLocalServiceBaseImpl {

	@Deprecated
	public EmailAddress addEmailAddress(
			long userId, String className, long classPK, String address,
			int typeId, boolean primary)
		throws PortalException, SystemException {

		return addEmailAddress(
			userId, className, classPK, address, typeId, primary, null);
	}

	public EmailAddress addEmailAddress(
			long userId, String className, long classPK, String address,
			int typeId, boolean primary, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		validate(
			0, user.getCompanyId(), classNameId, classPK, address, typeId,
			primary);

		long emailAddressId = counterLocalService.increment();

		EmailAddress emailAddress = emailAddressPersistence.create(
			emailAddressId);

		if (serviceContext != null) {
			emailAddress.setUuid(serviceContext.getUuid());
		}

		emailAddress.setCompanyId(user.getCompanyId());
		emailAddress.setUserId(user.getUserId());
		emailAddress.setUserName(user.getFullName());
		emailAddress.setCreateDate(now);
		emailAddress.setModifiedDate(now);
		emailAddress.setClassNameId(classNameId);
		emailAddress.setClassPK(classPK);
		emailAddress.setAddress(address);
		emailAddress.setTypeId(typeId);
		emailAddress.setPrimary(primary);

		emailAddressPersistence.update(emailAddress);

		return emailAddress;
	}

	public void deleteEmailAddresses(
			long companyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<EmailAddress> emailAddresses = emailAddressPersistence.findByC_C_C(
			companyId, classNameId, classPK);

		for (EmailAddress emailAddress : emailAddresses) {
			deleteEmailAddress(emailAddress);
		}
	}

	public EmailAddress fetchEmailAddressByUuidAndCompanyId(
			String uuid, long companyId)
		throws SystemException {

		return emailAddressPersistence.fetchByUuid_C_First(
			uuid, companyId, null);
	}

	public List<EmailAddress> getEmailAddresses() throws SystemException {
		return emailAddressPersistence.findAll();
	}

	public List<EmailAddress> getEmailAddresses(
			long companyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return emailAddressPersistence.findByC_C_C(
			companyId, classNameId, classPK);
	}

	public EmailAddress updateEmailAddress(
			long emailAddressId, String address, int typeId, boolean primary)
		throws PortalException, SystemException {

		validate(emailAddressId, 0, 0, 0, address, typeId, primary);

		EmailAddress emailAddress = emailAddressPersistence.findByPrimaryKey(
			emailAddressId);

		emailAddress.setModifiedDate(new Date());
		emailAddress.setAddress(address);
		emailAddress.setTypeId(typeId);
		emailAddress.setPrimary(primary);

		emailAddressPersistence.update(emailAddress);

		return emailAddress;
	}

	protected void validate(
			long emailAddressId, long companyId, long classNameId, long classPK,
			boolean primary)
		throws SystemException {

		// Check to make sure there isn't another emailAddress with the same
		// company id, class name, and class pk that also has primary set to
		// true

		if (primary) {
			List<EmailAddress> emailAddresses =
				emailAddressPersistence.findByC_C_C_P(
					companyId, classNameId, classPK, primary);

			for (EmailAddress emailAddress : emailAddresses) {
				if ((emailAddressId <= 0) ||
					(emailAddress.getEmailAddressId() != emailAddressId)) {

					emailAddress.setPrimary(false);

					emailAddressPersistence.update(emailAddress);
				}
			}
		}
	}

	protected void validate(
			long emailAddressId, long companyId, long classNameId, long classPK,
			String address, int typeId, boolean primary)
		throws PortalException, SystemException {

		if (!Validator.isEmailAddress(address)) {
			throw new EmailAddressException();
		}

		if (emailAddressId > 0) {
			EmailAddress emailAddress =
				emailAddressPersistence.findByPrimaryKey(emailAddressId);

			companyId = emailAddress.getCompanyId();
			classNameId = emailAddress.getClassNameId();
			classPK = emailAddress.getClassPK();
		}

		listTypeService.validate(
			typeId, classNameId, ListTypeConstants.EMAIL_ADDRESS);

		validate(emailAddressId, companyId, classNameId, classPK, primary);
	}

}