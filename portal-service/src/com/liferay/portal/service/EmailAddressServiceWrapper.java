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

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _emailAddressService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_emailAddressService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addEmailAddress(String,
	long, String, int, boolean, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.EmailAddress addEmailAddress(
		java.lang.String className, long classPK, java.lang.String address,
		int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddressService.addEmailAddress(className, classPK,
			address, typeId, primary);
	}

	@Override
	public com.liferay.portal.model.EmailAddress addEmailAddress(
		java.lang.String className, long classPK, java.lang.String address,
		int typeId, boolean primary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddressService.addEmailAddress(className, classPK,
			address, typeId, primary, serviceContext);
	}

	@Override
	public void deleteEmailAddress(long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_emailAddressService.deleteEmailAddress(emailAddressId);
	}

	@Override
	public com.liferay.portal.model.EmailAddress getEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddressService.getEmailAddress(emailAddressId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddressService.getEmailAddresses(className, classPK);
	}

	@Override
	public com.liferay.portal.model.EmailAddress updateEmailAddress(
		long emailAddressId, java.lang.String address, int typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddressService.updateEmailAddress(emailAddressId, address,
			typeId, primary);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public EmailAddressService getWrappedEmailAddressService() {
		return _emailAddressService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedEmailAddressService(
		EmailAddressService emailAddressService) {
		_emailAddressService = emailAddressService;
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