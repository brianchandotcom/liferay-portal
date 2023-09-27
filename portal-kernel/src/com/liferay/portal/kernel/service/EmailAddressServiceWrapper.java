/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.model.EmailAddress;

/**
 * Provides a wrapper for {@link EmailAddressService}.
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddressService
 * @generated
 */
public class EmailAddressServiceWrapper
	implements EmailAddressService, ServiceWrapper<EmailAddressService> {

	public EmailAddressServiceWrapper() {
		this(null);
	}

	public EmailAddressServiceWrapper(EmailAddressService emailAddressService) {
		_emailAddressService = emailAddressService;
	}

	@Override
	public EmailAddress addEmailAddress(
			String className, long classPK, String address, long typeId,
			boolean primary, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _emailAddressService.addEmailAddress(
			className, classPK, address, typeId, primary, serviceContext);
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
	public EmailAddress fetchEmailAddress(long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _emailAddressService.fetchEmailAddress(emailAddressId);
	}

	@Override
	public EmailAddress getEmailAddress(long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _emailAddressService.getEmailAddress(emailAddressId);
	}

	@Override
	public java.util.List<EmailAddress> getEmailAddresses(
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
	public EmailAddress updateEmailAddress(
			long emailAddressId, String address, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _emailAddressService.updateEmailAddress(
			emailAddressId, address, typeId, primary);
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