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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link EmailAddressService}.
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddressService
 * @generated
 */
@ProviderType
public class EmailAddressServiceWrapper implements EmailAddressService,
	ServiceWrapper<EmailAddressService> {
	public EmailAddressServiceWrapper(EmailAddressService emailAddressService) {
		_emailAddressService = emailAddressService;
	}

	@Override
	public com.liferay.portal.kernel.model.EmailAddress addEmailAddress(
		String className, long classPK, String address, long typeId,
		boolean primary, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddressService.addEmailAddress(className, classPK,
			address, typeId, primary, serviceContext);
	}

	@Override
	public void deleteEmailAddress(long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_emailAddressService.deleteEmailAddress(emailAddressId);
	}

	/**
	* Returns the email address with the primary key.
	*
	* @param emailAddressId the primary key of the email address
	* @return the email address with the primary key, or <code>null</code> if
	an email address with the primary key could not be found or if
	the user did not have permission to view the email address
	*/
	@Override
	public com.liferay.portal.kernel.model.EmailAddress fetchEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddressService.fetchEmailAddress(emailAddressId);
	}

	@Override
	public com.liferay.portal.kernel.model.EmailAddress getEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddressService.getEmailAddress(emailAddressId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.EmailAddress> getEmailAddresses(
		String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddressService.getEmailAddresses(className, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _emailAddressService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.EmailAddress updateEmailAddress(
		long emailAddressId, String address, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddressService.updateEmailAddress(emailAddressId, address,
			typeId, primary);
	}

	@Override
	public EmailAddressService getWrappedService() {
		return _emailAddressService;
	}

	@Override
	public void setWrappedService(EmailAddressService emailAddressService) {
		_emailAddressService = emailAddressService;
	}

	private EmailAddressService _emailAddressService;
}