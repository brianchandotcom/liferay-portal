/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 * <p>
 * This class is a wrapper for {@link EmailAddressService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       EmailAddressService
 * @generated
 */
public class EmailAddressServiceWrapper implements EmailAddressService,
	ServiceWrapper<EmailAddressService> {
	public EmailAddressServiceWrapper(EmailAddressService emailAddressService) {
		_emailAddressService = emailAddressService;
	}

	public com.liferay.portal.model.EmailAddress addEmailAddress(
		java.lang.String className, long classPK, java.lang.String address,
		int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressService.addEmailAddress(className, classPK,
			address, typeId, primary);
	}

	public void deleteEmailAddress(long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_emailAddressService.deleteEmailAddress(emailAddressId);
	}

	public com.liferay.portal.model.EmailAddress getEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressService.getEmailAddress(emailAddressId);
	}

	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressService.getEmailAddresses(className, classPK);
	}

	public com.liferay.portal.model.EmailAddress updateEmailAddress(
		long emailAddressId, java.lang.String address, int typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressService.updateEmailAddress(emailAddressId, address,
			typeId, primary);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public EmailAddressService getWrappedEmailAddressService() {
		return _emailAddressService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedEmailAddressService(
		EmailAddressService emailAddressService) {
		_emailAddressService = emailAddressService;
	}

	public EmailAddressService getWrappedService() {
		return _emailAddressService;
	}

	public void setWrappedService(EmailAddressService emailAddressService) {
		_emailAddressService = emailAddressService;
	}

	private EmailAddressService _emailAddressService;
}