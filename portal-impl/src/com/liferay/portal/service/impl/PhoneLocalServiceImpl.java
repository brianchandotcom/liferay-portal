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

import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.format.PhoneNumberFormatUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.PhoneLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PhoneLocalServiceImpl extends PhoneLocalServiceBaseImpl {

	@Deprecated
	public Phone addPhone(
			long userId, String className, long classPK, String number,
			String extension, int typeId, boolean primary)
		throws PortalException, SystemException {

		return addPhone(
			userId, className, classPK, number, extension, typeId, primary,
			null);
	}

	public Phone addPhone(
			long userId, String className, long classPK, String number,
			String extension, int typeId, boolean primary,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		validate(
			0, user.getCompanyId(), classNameId, classPK, number, extension,
			typeId, primary);

		long phoneId = counterLocalService.increment();

		Phone phone = phonePersistence.create(phoneId);

		if (serviceContext != null) {
			phone.setUuid(serviceContext.getUuid());
		}

		phone.setCompanyId(user.getCompanyId());
		phone.setUserId(user.getUserId());
		phone.setUserName(user.getFullName());
		phone.setCreateDate(now);
		phone.setModifiedDate(now);
		phone.setClassNameId(classNameId);
		phone.setClassPK(classPK);
		phone.setNumber(number);
		phone.setExtension(extension);
		phone.setTypeId(typeId);
		phone.setPrimary(primary);

		phonePersistence.update(phone);

		return phone;
	}

	public void deletePhones(long companyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<Phone> phones = phonePersistence.findByC_C_C(
			companyId, classNameId, classPK);

		for (Phone phone : phones) {
			deletePhone(phone);
		}
	}

	public Phone fetchPhoneByUuidAndCompanyId(String uuid, long companyId)
		throws SystemException {

		return phonePersistence.fetchByUuid_C_First(uuid, companyId, null);
	}

	public List<Phone> getPhones() throws SystemException {
		return phonePersistence.findAll();
	}

	public List<Phone> getPhones(long companyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return phonePersistence.findByC_C_C(companyId, classNameId, classPK);
	}

	public Phone updatePhone(
			long phoneId, String number, String extension, int typeId,
			boolean primary)
		throws PortalException, SystemException {

		validate(phoneId, 0, 0, 0, number, extension, typeId, primary);

		Phone phone = phonePersistence.findByPrimaryKey(phoneId);

		phone.setModifiedDate(new Date());
		phone.setNumber(number);
		phone.setExtension(extension);
		phone.setTypeId(typeId);
		phone.setPrimary(primary);

		phonePersistence.update(phone);

		return phone;
	}

	protected void validate(
			long phoneId, long companyId, long classNameId, long classPK,
			boolean primary)
		throws SystemException {

		// Check to make sure there isn't another phone with the same company
		// id, class name, and class pk that also has primary set to true

		if (primary) {
			List<Phone> phones = phonePersistence.findByC_C_C_P(
				companyId, classNameId, classPK, primary);

			for (Phone phone : phones) {
				if ((phoneId <= 0) || (phone.getPhoneId() != phoneId)) {
					phone.setPrimary(false);

					phonePersistence.update(phone);
				}
			}
		}
	}

	protected void validate(
			long phoneId, long companyId, long classNameId, long classPK,
			String number, String extension, int typeId, boolean primary)
		throws PortalException, SystemException {

		if (!PhoneNumberFormatUtil.validate(number)) {
			throw new PhoneNumberException();
		}

		if (Validator.isNotNull(extension)) {
			for (int i = 0; i < extension.length(); i++) {
				if (!Character.isDigit(extension.charAt(i))) {
					throw new PhoneNumberException();
				}
			}
		}

		if (phoneId > 0) {
			Phone phone = phonePersistence.findByPrimaryKey(phoneId);

			companyId = phone.getCompanyId();
			classNameId = phone.getClassNameId();
			classPK = phone.getClassPK();
		}

		if ((classNameId == PortalUtil.getClassNameId(Account.class)) ||
			(classNameId == PortalUtil.getClassNameId(Contact.class)) ||
			(classNameId == PortalUtil.getClassNameId(Organization.class))) {

			listTypeService.validate(
				typeId, classNameId, ListTypeConstants.PHONE);
		}

		validate(phoneId, companyId, classNameId, classPK, primary);
	}

}