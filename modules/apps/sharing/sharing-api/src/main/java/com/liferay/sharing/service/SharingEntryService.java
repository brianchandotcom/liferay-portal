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

package com.liferay.sharing.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;

import java.util.Collection;
import java.util.Date;

/**
 * Provides the remote service interface for SharingEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryServiceUtil
 * @see com.liferay.sharing.service.base.SharingEntryServiceBaseImpl
 * @see com.liferay.sharing.service.impl.SharingEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=sharing", "json.web.service.context.path=SharingEntry"}, service = SharingEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SharingEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SharingEntryServiceUtil} to access the sharing entry remote service. Add custom service methods to {@link com.liferay.sharing.service.impl.SharingEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds a sharing entry in the database if it does not exist or it updates
	* it if it exists.
	*
	* @param toUserId the user id whose resource was shared
	* @param classNameId the class name ID of the resource being shared
	* @param classPK the primary key of the resource being shared
	* @param groupId the primary key of the group containing the resource
	being shared
	* @param shareable whether the to user id can share the resource as well
	* @param sharingEntryActions the sharing entry actions
	* @param expirationDate the date when the sharing entry expires
	* @return the sharing entry
	* @param serviceContext the service context to be applied
	* @throws PortalException if the user does not have permission to share the
	resource or sharing entry actions are invalid (it is empty, it
	doesn't contain {@link SharingEntryAction#VIEW,} or it
	contains a <code>null</code> value) or from user id and to user
	id are the same or the expiration date is a value in the past.
	*/
	public SharingEntry addOrUpdateSharingEntry(long toUserId,
		long classNameId, long classPK, long groupId, boolean shareable,
		Collection<SharingEntryAction> sharingEntryActions,
		Date expirationDate, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds a sharing entry in the database.
	*
	* @param toUserId the user id whose resource was shared
	* @param classNameId the class name ID of the resource being shared
	* @param classPK the primary key of the resource being shared
	* @param groupId the primary key of the group containing the resource
	being shared
	* @param shareable whether the to user id can share the resource as well
	* @param sharingEntryActions the sharing entry actions
	* @param expirationDate the date when the sharing entry expires
	* @return the sharing entry
	* @param serviceContext the service context to be applied
	* @throws PortalException if the user does not have permission to share the
	resource or there is already a sharing entry for the same from
	user id, to user id and resource or the sharing entry actions are
	invalid (it is empty, it doesn't contain
	{@link SharingEntryAction#VIEW,} or it contains a
	<code>null</code> value) or from user id and to user id are the
	same or the expiration date is a value in the past.
	*/
	public SharingEntry addSharingEntry(long toUserId, long classNameId,
		long classPK, long groupId, boolean shareable,
		Collection<SharingEntryAction> sharingEntryActions,
		Date expirationDate, ServiceContext serviceContext)
		throws PortalException;

	public SharingEntry deleteSharingEntry(long sharingEntryId,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	/**
	* Updates a sharing entry in the database.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @param sharingEntryActions the sharing entry actions
	* @param shareable whether the to user id can share the resource as well
	* @param expirationDate the date when the sharing entry expires
	* @return the sharing entry
	* @param serviceContext the service context to be applied
	* @throws PortalException if the sharing entry does not exist or sharing
	entry actions are invalid (it is empty, it doesn't contain
	{@link SharingEntryAction#VIEW,} or it contains a
	<code>null</code> value) or the expiration date is a value in the
	past.
	*/
	public SharingEntry updateSharingEntry(long sharingEntryId,
		Collection<SharingEntryAction> sharingEntryActions, boolean shareable,
		Date expirationDate, ServiceContext serviceContext)
		throws PortalException;
}