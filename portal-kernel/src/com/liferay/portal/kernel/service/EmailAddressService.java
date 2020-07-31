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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for EmailAddress. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddressServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface EmailAddressService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.service.impl.EmailAddressServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the email address remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link EmailAddressServiceUtil} if injection and service tracking are not available.
	 */
	public EmailAddress addEmailAddress(
			String className, long classPK, String address, long typeId,
			boolean primary, ServiceContext serviceContext)
		throws PortalException;

	public void deleteEmailAddress(long emailAddressId) throws PortalException;

	/**
	 * Returns the email address with the primary key.
	 *
	 * @param emailAddressId the primary key of the email address
	 * @return the email address with the primary key, or <code>null</code> if
	 an email address with the primary key could not be found or if
	 the user did not have permission to view the email address
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public EmailAddress fetchEmailAddress(long emailAddressId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public EmailAddress getEmailAddress(long emailAddressId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<EmailAddress> getEmailAddresses(String className, long classPK)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public EmailAddress updateEmailAddress(
			long emailAddressId, String address, long typeId, boolean primary)
		throws PortalException;

}