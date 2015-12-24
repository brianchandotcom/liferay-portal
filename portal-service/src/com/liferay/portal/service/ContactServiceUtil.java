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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for Contact. This utility wraps
 * {@link com.liferay.portal.service.impl.ContactServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ContactService
 * @see com.liferay.portal.service.base.ContactServiceBaseImpl
 * @see com.liferay.portal.service.impl.ContactServiceImpl
 * @generated
 */
@ProviderType
public class ContactServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ContactServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.model.Contact getContact(long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getContact(contactId);
	}

	public static java.util.List<com.liferay.portal.model.Contact> getContacts(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Contact> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getContacts(classNameId, classPK, start, end,
			orderByComparator);
	}

	public static int getContactsCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getContactsCount(classNameId, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ContactService getService() {
		if (_service == null) {
			_service = (ContactService)PortalBeanLocatorUtil.locate(ContactService.class.getName());

			ReferenceRegistry.registerReference(ContactServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static ContactService _service;
}