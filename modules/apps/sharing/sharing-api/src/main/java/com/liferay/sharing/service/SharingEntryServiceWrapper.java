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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SharingEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryService
 * @generated
 */
public class SharingEntryServiceWrapper
	implements ServiceWrapper<SharingEntryService>, SharingEntryService {

	public SharingEntryServiceWrapper(SharingEntryService sharingEntryService) {
		_sharingEntryService = sharingEntryService;
	}

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
	@Override
	public com.liferay.sharing.model.SharingEntry addOrUpdateSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			java.util.Collection
				<com.liferay.sharing.security.permission.SharingEntryAction>
					sharingEntryActions,
			java.util.Date expirationDate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sharingEntryService.addOrUpdateSharingEntry(
			toUserId, classNameId, classPK, groupId, shareable,
			sharingEntryActions, expirationDate, serviceContext);
	}

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
	@Override
	public com.liferay.sharing.model.SharingEntry addSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			java.util.Collection
				<com.liferay.sharing.security.permission.SharingEntryAction>
					sharingEntryActions,
			java.util.Date expirationDate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sharingEntryService.addSharingEntry(
			toUserId, classNameId, classPK, groupId, shareable,
			sharingEntryActions, expirationDate, serviceContext);
	}

	@Override
	public com.liferay.sharing.model.SharingEntry deleteSharingEntry(
			long sharingEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sharingEntryService.deleteSharingEntry(
			sharingEntryId, serviceContext);
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
	@Override
	public com.liferay.sharing.model.SharingEntry updateSharingEntry(
			long sharingEntryId,
			java.util.Collection
				<com.liferay.sharing.security.permission.SharingEntryAction>
					sharingEntryActions,
			boolean shareable, java.util.Date expirationDate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sharingEntryService.updateSharingEntry(
			sharingEntryId, sharingEntryActions, shareable, expirationDate,
			serviceContext);
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