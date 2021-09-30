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

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.ContactBirthdayException;
import com.liferay.portal.kernel.exception.ContactClassNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.ContactLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ContactLocalServiceImpl extends ContactLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Contact addContact(Contact contact) {
		try {
			validateBirthday(contact.getBirthday());
		}
		catch (ContactBirthdayException contactBirthdayException) {
			throw new SystemException(contactBirthdayException);
		}

		return super.addContact(contact);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Contact addContact(
			long userId, String className, long classPK, String emailAddress,
			String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String smsSn, String facebookSn, String jabberSn,
			String skypeSn, String twitterSn, String jobTitle)
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			ContactBirthdayException.class);

		validate(className, classPK);
		validateBirthday(birthday);

		long contactId = counterLocalService.increment();

		Contact contact = contactPersistence.create(contactId);

		contact.setCompanyId(user.getCompanyId());
		contact.setUserId(user.getUserId());
		contact.setUserName(user.getFullName());
		contact.setClassName(className);
		contact.setClassPK(classPK);
		contact.setEmailAddress(emailAddress);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setSmsSn(smsSn);
		contact.setFacebookSn(facebookSn);
		contact.setJabberSn(jabberSn);
		contact.setSkypeSn(skypeSn);
		contact.setTwitterSn(twitterSn);
		contact.setJobTitle(jobTitle);

		return contactPersistence.update(contact);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public Contact deleteContact(Contact contact) {

		// Contact

		contact = contactPersistence.remove(contact);

		// Addresses

		_addressLocalService.deleteAddresses(
			contact.getCompanyId(), Contact.class.getName(),
			contact.getContactId());

		// Email addresses

		_emailAddressLocalService.deleteEmailAddresses(
			contact.getCompanyId(), Contact.class.getName(),
			contact.getContactId());

		// Phone

		_phoneLocalService.deletePhones(
			contact.getCompanyId(), Contact.class.getName(),
			contact.getContactId());

		// Website

		_websiteLocalService.deleteWebsites(
			contact.getCompanyId(), Contact.class.getName(),
			contact.getContactId());

		return contact;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public Contact deleteContact(long contactId) {
		Contact contact = contactPersistence.fetchByPrimaryKey(contactId);

		if (contact != null) {
			contact = deleteContact(contact);
		}

		return contact;
	}

	@Override
	public List<Contact> getCompanyContacts(
		long companyId, int start, int end) {

		return contactPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyContactsCount(long companyId) {
		return contactPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<Contact> getContacts(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<Contact> orderByComparator) {

		return contactPersistence.findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getContactsCount(long classNameId, long classPK) {
		return contactPersistence.countByC_C(classNameId, classPK);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Contact updateContact(Contact contact) {
		try {
			validateBirthday(contact.getBirthday());
		}
		catch (ContactBirthdayException contactBirthdayException) {
			throw new SystemException(contactBirthdayException);
		}

		return super.updateContact(contact);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Contact updateContact(
			long contactId, String emailAddress, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String facebookSn, String jabberSn, String skypeSn,
			String twitterSn, String jobTitle)
		throws PortalException {

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			ContactBirthdayException.class);

		validateBirthday(birthday);

		Contact contact = contactPersistence.findByPrimaryKey(contactId);

		contact.setEmailAddress(emailAddress);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setSmsSn(smsSn);
		contact.setFacebookSn(facebookSn);
		contact.setJabberSn(jabberSn);
		contact.setSkypeSn(skypeSn);
		contact.setTwitterSn(twitterSn);
		contact.setJobTitle(jobTitle);

		return contactPersistence.update(contact);
	}

	protected void validate(String className, long classPK)
		throws PortalException {

		if (Validator.isNull(className) ||
			className.equals(User.class.getName()) || (classPK <= 0)) {

			throw new ContactClassNameException();
		}
	}

	protected void validateBirthday(Date birthday)
		throws ContactBirthdayException {

		if ((birthday != null) && birthday.after(new Date())) {
			throw new ContactBirthdayException("Birthday is in the future");
		}
	}

	@BeanReference(type = AddressLocalService.class)
	private AddressLocalService _addressLocalService;

	@BeanReference(type = EmailAddressLocalService.class)
	private EmailAddressLocalService _emailAddressLocalService;

	@BeanReference(type = PhoneLocalService.class)
	private PhoneLocalService _phoneLocalService;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

	@BeanReference(type = WebsiteLocalService.class)
	private WebsiteLocalService _websiteLocalService;

}