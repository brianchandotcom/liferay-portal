/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
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
	public List<Contact> getCompanyContacts(long companyId, int start, int end)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(companyId)) {
			throw new PrincipalException.MustBeOmniadmin(permissionChecker);
		}

		return contactPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyContactsCount(long companyId) {
		return contactPersistence.countByCompanyId(companyId);
	}

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