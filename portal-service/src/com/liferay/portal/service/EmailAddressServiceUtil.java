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
 * Provides the remote service utility for EmailAddress. This utility wraps
 * {@link com.liferay.portal.service.impl.EmailAddressServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddressService
 * @see com.liferay.portal.service.base.EmailAddressServiceBaseImpl
 * @see com.liferay.portal.service.impl.EmailAddressServiceImpl
 * @generated
 */
@ProviderType
public class EmailAddressServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.EmailAddressServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addEmailAddress(String,
	long, String, int, boolean, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.EmailAddress addEmailAddress(
		java.lang.String className, long classPK, java.lang.String address,
		long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEmailAddress(className, classPK, address, typeId, primary);
	}

	public static com.liferay.portal.model.EmailAddress addEmailAddress(
		java.lang.String className, long classPK, java.lang.String address,
		long typeId, boolean primary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEmailAddress(className, classPK, address, typeId,
			primary, serviceContext);
	}

	public static void deleteEmailAddress(long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteEmailAddress(emailAddressId);
	}

	/**
	* Returns the email address with the primary key.
	*
	* @param emailAddressId the primary key of the email address
	* @return the email address with the primary key, or <code>null</code> if
	an email address with the primary key could not be found or if
	the user did not have permission to view the email address
	*/
	public static com.liferay.portal.model.EmailAddress fetchEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchEmailAddress(emailAddressId);
	}

	public static com.liferay.portal.model.EmailAddress getEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEmailAddress(emailAddressId);
	}

	public static java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEmailAddresses(className, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.EmailAddress updateEmailAddress(
		long emailAddressId, java.lang.String address, long typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateEmailAddress(emailAddressId, address, typeId, primary);
	}

	public static EmailAddressService getService() {
		if (_service == null) {
			_service = (EmailAddressService)PortalBeanLocatorUtil.locate(EmailAddressService.class.getName());

			ReferenceRegistry.registerReference(EmailAddressServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static EmailAddressService _service;
}