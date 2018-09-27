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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SharingEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryService
 * @generated
 */
@ProviderType
public class SharingEntryServiceWrapper implements SharingEntryService,
	ServiceWrapper<SharingEntryService> {
	public SharingEntryServiceWrapper(SharingEntryService sharingEntryService) {
		_sharingEntryService = sharingEntryService;
	}

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
	@Override
	public com.liferay.sharing.model.SharingEntry addOrUpdateSharingEntry(
		long toUserId, long classNameId, long classPK, long groupId,
		boolean shareable,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryService.addOrUpdateSharingEntry(toUserId,
			classNameId, classPK, groupId, shareable, sharingEntryActions,
			expirationDate, serviceContext);
	}

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
	@Override
	public com.liferay.sharing.model.SharingEntry addSharingEntry(
		long toUserId, long classNameId, long classPK, long groupId,
		boolean shareable,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryService.addSharingEntry(toUserId, classNameId,
			classPK, groupId, shareable, sharingEntryActions, expirationDate,
			serviceContext);
	}

	@Override
	public com.liferay.sharing.model.SharingEntry deleteSharingEntry(
		long sharingEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryService.deleteSharingEntry(sharingEntryId,
			serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _sharingEntryService.getOSGiServiceIdentifier();
	}

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
	@Override
	public com.liferay.sharing.model.SharingEntry updateSharingEntry(
		long sharingEntryId,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		boolean shareable, java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryService.updateSharingEntry(sharingEntryId,
			sharingEntryActions, shareable, expirationDate, serviceContext);
	}

	@Override
	public SharingEntryService getWrappedService() {
		return _sharingEntryService;
	}

	@Override
	public void setWrappedService(SharingEntryService sharingEntryService) {
		_sharingEntryService = sharingEntryService;
	}

	private SharingEntryService _sharingEntryService;
}