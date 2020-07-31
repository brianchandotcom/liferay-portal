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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;

import java.util.Collection;
import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for SharingEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface SharingEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.sharing.service.impl.SharingEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the sharing entry remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link SharingEntryServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds a new sharing entry in the database or updates an existing one.
	 *
	 * @param toUserId the ID of the user the resource is shared with
	 * @param classNameId the resource's class name ID
	 * @param classPK the primary key of the resource
	 * @param groupId the primary key of the resource's group
	 * @param shareable whether the user specified by {@code toUserId} can
	 share the resource
	 * @param sharingEntryActions the sharing entry actions
	 * @param expirationDate the date when the sharing entry expires
	 * @param serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the user does not have permission to share the
	 resource, if the sharing entry actions are invalid (e.g., empty
	 don't contain {@code SharingEntryAction#VIEW}, or contain a
	 {@code null} value), if the to/from user IDs are the same, or if
	 the expiration date is a past value
	 */
	public SharingEntry addOrUpdateSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Adds a new sharing entry in the database.
	 *
	 * @param toUserId the ID of the user the resource is shared with
	 * @param classNameId the resource's class name ID
	 * @param classPK the primary key of the resource
	 * @param groupId the primary key of the resource's group
	 * @param shareable whether the user specified by {@code toUserId} can
	 share the resource
	 * @param sharingEntryActions the sharing entry actions
	 * @param expirationDate the date when the sharing entry expires
	 * @param serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the user does not have permission to share the
	 resource, if a sharing entry already exists for the to/from user
	 IDs, if the sharing entry actions are invalid (e.g., empty, do
	 not contain {@code SharingEntryAction#VIEW}, or contain a {@code
	 null} value), if the to/from user IDs are the same, or if the
	 expiration date is a past value
	 */
	public SharingEntry addSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException;

	public SharingEntry deleteSharingEntry(
			long sharingEntryId, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * Updates the sharing entry in the database.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @param sharingEntryActions the sharing entry actions
	 * @param shareable whether the user the resource is shared with can also
	 share it
	 * @param expirationDate the date when the sharing entry expires
	 * @param serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the sharing entry does not exist, if the
	 sharing entry actions are invalid (e.g., empty, don't contain
	 {@code SharingEntryAction#VIEW}, or contain a {@code null}
	 value), or if the expiration date is a past value
	 */
	public SharingEntry updateSharingEntry(
			long sharingEntryId,
			Collection<SharingEntryAction> sharingEntryActions,
			boolean shareable, Date expirationDate,
			ServiceContext serviceContext)
		throws PortalException;

}