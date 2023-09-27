/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for Contact. This utility wraps
 * <code>com.liferay.portal.service.impl.ContactServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ContactService
 * @generated
 */
public class ContactServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.ContactServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<Contact> getCompanyContacts(
			long companyId, int start, int end)
		throws PortalException {

		return getService().getCompanyContacts(companyId, start, end);
	}

	public static int getCompanyContactsCount(long companyId) {
		return getService().getCompanyContactsCount(companyId);
	}

	public static Contact getContact(long contactId) throws PortalException {
		return getService().getContact(contactId);
	}

	public static List<Contact> getContacts(
			long classNameId, long classPK, int start, int end,
			OrderByComparator<Contact> orderByComparator)
		throws PortalException {

		return getService().getContacts(
			classNameId, classPK, start, end, orderByComparator);
	}

	public static int getContactsCount(long classNameId, long classPK)
		throws PortalException {

		return getService().getContactsCount(classNameId, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ContactService getService() {
		return _service;
	}

	public static void setService(ContactService service) {
		_service = service;
	}

	private static volatile ContactService _service;

}