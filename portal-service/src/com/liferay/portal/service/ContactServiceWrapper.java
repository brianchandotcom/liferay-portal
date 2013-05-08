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

package com.liferay.portal.service;

/**
 * Provides a wrapper for {@link ContactService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       ContactService
 * @generated
 */
public class ContactServiceWrapper implements ContactService,
	ServiceWrapper<ContactService> {
	public ContactServiceWrapper(ContactService contactService) {
		_contactService = contactService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _contactService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_contactService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Contact getContact(long contactId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _contactService.getContact(contactId);
	}

	public java.util.List<com.liferay.portal.model.Contact> getContacts(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _contactService.getContacts(classNameId, classPK, start, end,
			orderByComparator);
	}

	public int getContactsCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _contactService.getContactsCount(classNameId, classPK);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public ContactService getWrappedContactService() {
		return _contactService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedContactService(ContactService contactService) {
		_contactService = contactService;
	}

	public ContactService getWrappedService() {
		return _contactService;
	}

	public void setWrappedService(ContactService contactService) {
		_contactService = contactService;
	}

	private ContactService _contactService;
}