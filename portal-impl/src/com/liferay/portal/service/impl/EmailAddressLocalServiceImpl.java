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

import com.liferay.portal.EmailAddressException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.EmailAddressLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class EmailAddressLocalServiceImpl
	extends EmailAddressLocalServiceBaseImpl {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addEmailAddress(long,
	 *             String, long, String, int, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public EmailAddress addEmailAddress(
			long userId, String className, long classPK, String address,
			int typeId, boolean primary)
		throws PortalException {

		return addEmailAddress(
			userId, className, classPK, address, typeId, primary,
			new ServiceContext());
	}

	@Override
	public EmailAddress addEmailAddress(
			long userId, String className, long classPK, String address,
			int typeId, boolean primary, ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = classNameLocalService.getClassNameId(className);
		Date now = new Date();

		validate(
			0, user.getCompanyId(), classNameId, classPK, address, typeId,
			primary);

		long emailAddressId = counterLocalService.increment();

		EmailAddress emailAddress = emailAddressPersistence.create(
			emailAddressId);

		emailAddress.setUuid(serviceContext.getUuid());
		emailAddress.setCompanyId(user.getCompanyId());
		emailAddress.setUserId(user.getUserId());
		emailAddress.setUserName(user.getFullName());
		emailAddress.setCreateDate(serviceContext.getCreateDate(now));
		emailAddress.setModifiedDate(serviceContext.getModifiedDate(now));
		emailAddress.setClassNameId(classNameId);
		emailAddress.setClassPK(classPK);
		emailAddress.setAddress(address);
		emailAddress.setTypeId(typeId);
		emailAddress.setPrimary(primary);

		emailAddressPersistence.update(emailAddress);

		return emailAddress;
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public EmailAddress deleteEmailAddress(EmailAddress emailAddress) {
		emailAddressPersistence.remove(emailAddress);

		return emailAddress;
	}

	@Override
	public EmailAddress deleteEmailAddress(long emailAddressId)
		throws PortalException {

		EmailAddress emailAddress = emailAddressPersistence.findByPrimaryKey(
			emailAddressId);

		return emailAddressLocalService.deleteEmailAddress(emailAddress);
	}

	@Override
	public void deleteEmailAddresses(
		long companyId, String className, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		List<EmailAddress> emailAddresses = emailAddressPersistence.findByC_C_C(
			companyId, classNameId, classPK);

		for (EmailAddress emailAddress : emailAddresses) {
			emailAddressLocalService.deleteEmailAddress(emailAddress);
		}
	}

	@Override
	public List<EmailAddress> getEmailAddresses() {
		return emailAddressPersistence.findAll();
	}

	@Override
	public List<EmailAddress> getEmailAddresses(
		long companyId, String className, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return emailAddressPersistence.findByC_C_C(
			companyId, classNameId, classPK);
	}

	@Override
	public EmailAddress updateEmailAddress(
			long emailAddressId, String address, int typeId, boolean primary)
		throws PortalException {

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
		boolean primary) {

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
		throws PortalException {

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