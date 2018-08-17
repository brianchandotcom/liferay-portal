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

	@Override
	public com.liferay.sharing.model.SharingEntry addSharingEntry(
		long toUserId, long classNameId, long classPK, long groupId,
		java.util.Collection<com.liferay.sharing.constants.SharingEntryActionKey> sharingEntryActionKeys,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryService.addSharingEntry(toUserId, classNameId,
			classPK, groupId, sharingEntryActionKeys, serviceContext);
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

	@Override
	public com.liferay.sharing.model.SharingEntry updateSharingEntry(
		long sharingEntryId,
		java.util.Collection<com.liferay.sharing.constants.SharingEntryActionKey> sharingEntryActionKeys)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryService.updateSharingEntry(sharingEntryId,
			sharingEntryActionKeys);
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