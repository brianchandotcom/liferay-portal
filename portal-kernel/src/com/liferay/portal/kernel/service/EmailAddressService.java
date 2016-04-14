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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

/**
 * Provides the remote service interface for EmailAddress. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddressServiceUtil
 * @see com.liferay.portal.service.base.EmailAddressServiceBaseImpl
 * @see com.liferay.portal.service.impl.EmailAddressServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface EmailAddressService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link EmailAddressServiceUtil} to access the email address remote service. Add custom service methods to {@link com.liferay.portal.service.impl.EmailAddressServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public EmailAddress addEmailAddress(java.lang.String className,
		long classPK, java.lang.String address, long typeId, boolean primary,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException;

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

	public EmailAddress updateEmailAddress(long emailAddressId,
		java.lang.String address, long typeId, boolean primary)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<EmailAddress> getEmailAddresses(java.lang.String className,
		long classPK) throws PortalException;

	public void deleteEmailAddress(long emailAddressId)
		throws PortalException;
}