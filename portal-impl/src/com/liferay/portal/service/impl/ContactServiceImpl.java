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
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.CommonPermissionUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.base.ContactServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 */
public class ContactServiceImpl extends ContactServiceBaseImpl {

	@Override
	public Contact getContact(long contactId) throws PortalException {
		Contact contact = contactPersistence.findByPrimaryKey(contactId);

		CommonPermissionUtil.check(
			getPermissionChecker(), contact.getClassNameId(),
			contact.getClassPK(), ActionKeys.VIEW);

		return contact;
	}

	@Override
	public List<Contact> getContacts(
			long classNameId, long classPK, int start, int end,
			OrderByComparator<Contact> orderByComparator)
		throws PortalException {

		CommonPermissionUtil.check(
			getPermissionChecker(), classNameId, classPK, ActionKeys.VIEW);

		return contactPersistence.findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getContactsCount(long classNameId, long classPK)
		throws PortalException {

		CommonPermissionUtil.check(
			getPermissionChecker(), classNameId, classPK, ActionKeys.VIEW);

		return contactPersistence.countByC_C(classNameId, classPK);
	}

}