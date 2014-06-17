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

import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.format.PhoneNumberFormatUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.PhoneLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PhoneLocalServiceImpl extends PhoneLocalServiceBaseImpl {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addPhone(long, String, long,
	 *             String, String, int, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Phone addPhone(
			long userId, String className, long classPK, String number,
			String extension, int typeId, boolean primary)
		throws PortalException {

		return addPhone(
			userId, className, classPK, number, extension, typeId, primary,
			new ServiceContext());
	}

	@Override
	public Phone addPhone(
			long userId, String className, long classPK, String number,
			String extension, int typeId, boolean primary,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = classNameLocalService.getClassNameId(className);
		Date now = new Date();

		validate(
			0, user.getCompanyId(), classNameId, classPK, number, extension,
			typeId, primary);

		long phoneId = counterLocalService.increment();

		Phone phone = phonePersistence.create(phoneId);

		phone.setUuid(serviceContext.getUuid());
		phone.setCompanyId(user.getCompanyId());
		phone.setUserId(user.getUserId());
		phone.setUserName(user.getFullName());
		phone.setCreateDate(serviceContext.getCreateDate(now));
		phone.setModifiedDate(serviceContext.getModifiedDate(now));
		phone.setClassNameId(classNameId);
		phone.setClassPK(classPK);
		phone.setNumber(number);
		phone.setExtension(extension);
		phone.setTypeId(typeId);
		phone.setPrimary(primary);

		phonePersistence.update(phone);

		return phone;
	}

	@Override
	public Phone deletePhone(long phoneId) throws PortalException {
		Phone phone = phonePersistence.findByPrimaryKey(phoneId);

		return phoneLocalService.deletePhone(phone);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public Phone deletePhone(Phone phone) {
		phonePersistence.remove(phone);

		return phone;
	}

	@Override
	public void deletePhones(long companyId, String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		List<Phone> phones = phonePersistence.findByC_C_C(
			companyId, classNameId, classPK);

		for (Phone phone : phones) {
			phoneLocalService.deletePhone(phone);
		}
	}

	@Override
	public List<Phone> getPhones() {
		return phonePersistence.findAll();
	}

	@Override
	public List<Phone> getPhones(
		long companyId, String className, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return phonePersistence.findByC_C_C(companyId, classNameId, classPK);
	}

	@Override
	public Phone updatePhone(
			long phoneId, String number, String extension, int typeId,
			boolean primary)
		throws PortalException {

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
		boolean primary) {

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
		throws PortalException {

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

		if ((classNameId ==
				classNameLocalService.getClassNameId(Account.class)) ||
			(classNameId ==
				classNameLocalService.getClassNameId(Contact.class)) ||
			(classNameId ==
				classNameLocalService.getClassNameId(Organization.class))) {

			listTypeService.validate(
				typeId, classNameId, ListTypeConstants.PHONE);
		}

		validate(phoneId, companyId, classNameId, classPK, primary);
	}

}