/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link ContactService}.
 *
 * @author Brian Wing Shun Chan
 * @see ContactService
 * @generated
 */
public class ContactServiceWrapper
	implements ContactService, ServiceWrapper<ContactService> {

	public ContactServiceWrapper() {
		this(null);
	}

	public ContactServiceWrapper(ContactService contactService) {
		_contactService = contactService;
	}

	@Override
	public com.liferay.portal.kernel.model.Contact addContact(
			long userId, java.lang.String className, long classPK,
			java.lang.String emailAddress, java.lang.String firstName,
			java.lang.String middleName, java.lang.String lastName,
			long prefixListTypeId, long suffixListTypeId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			java.lang.String smsSn, java.lang.String facebookSn,
			java.lang.String jabberSn, java.lang.String skypeSn,
			java.lang.String twitterSn, java.lang.String jobTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.addContact(
			userId, className, classPK, emailAddress, firstName, middleName,
			lastName, prefixListTypeId, suffixListTypeId, male, birthdayMonth,
			birthdayDay, birthdayYear, smsSn, facebookSn, jabberSn, skypeSn,
			twitterSn, jobTitle);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Contact>
			getCompanyContacts(long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getCompanyContacts(companyId, start, end);
	}

	@Override
	public int getCompanyContactsCount(long companyId) {
		return _contactService.getCompanyContactsCount(companyId);
	}

	@Override
	public com.liferay.portal.kernel.model.Contact getContact(long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getContact(contactId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Contact> getContacts(
			long classNameId, long classPK, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Contact> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getContacts(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getContactsCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getContactsCount(classNameId, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _contactService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.Contact updateContact(
			long contactId, java.lang.String emailAddress,
			java.lang.String firstName, java.lang.String middleName,
			java.lang.String lastName, long prefixListTypeId,
			long suffixListTypeId, boolean male, int birthdayMonth,
			int birthdayDay, int birthdayYear, java.lang.String smsSn,
			java.lang.String facebookSn, java.lang.String jabberSn,
			java.lang.String skypeSn, java.lang.String twitterSn,
			java.lang.String jobTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.updateContact(
			contactId, emailAddress, firstName, middleName, lastName,
			prefixListTypeId, suffixListTypeId, male, birthdayMonth,
			birthdayDay, birthdayYear, smsSn, facebookSn, jabberSn, skypeSn,
			twitterSn, jobTitle);
	}

	@Override
	public ContactService getWrappedService() {
		return _contactService;
	}

	@Override
	public void setWrappedService(ContactService contactService) {
		_contactService = contactService;
	}

	private ContactService _contactService;

}