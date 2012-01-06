/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Contact;
import com.liferay.portal.service.base.ContactLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ContactLocalServiceImpl extends ContactLocalServiceBaseImpl {

	@Override
	public void deleteContact(long contactId) throws SystemException {
		Contact contact = contactPersistence.fetchByPrimaryKey(contactId);

		if (contact != null) {
			deleteContact(contact);
		}
	}

	@Override
	public void deleteContact(Contact contact) throws SystemException {

		// Addresses

		addressLocalService.deleteAddresses(
			contact.getCompanyId(), Contact.class.getName(),
			contact.getContactId());

		// Email addresses

		emailAddressLocalService.deleteEmailAddresses(
			contact.getCompanyId(), Contact.class.getName(),
			contact.getContactId());

		// Phone

		phoneLocalService.deletePhones(
			contact.getCompanyId(), Contact.class.getName(),
			contact.getContactId());

		// Website

		websiteLocalService.deleteWebsites(
			contact.getCompanyId(), Contact.class.getName(),
			contact.getContactId());

		// Contact

		contactPersistence.remove(contact);
	}

	@Override
	public Contact getContact(long contactId)
		throws PortalException, SystemException {

		return contactPersistence.findByPrimaryKey(contactId);
	}

}