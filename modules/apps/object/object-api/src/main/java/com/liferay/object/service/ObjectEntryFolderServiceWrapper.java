/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectEntryFolderService}.
 *
 * @author Marco Leo
 * @see ObjectEntryFolderService
 * @generated
 */
public class ObjectEntryFolderServiceWrapper
	implements ObjectEntryFolderService,
			   ServiceWrapper<ObjectEntryFolderService> {

	public ObjectEntryFolderServiceWrapper() {
		this(null);
	}

	public ObjectEntryFolderServiceWrapper(
		ObjectEntryFolderService objectEntryFolderService) {

		_objectEntryFolderService = objectEntryFolderService;
	}

	@Override
	public com.liferay.object.model.ObjectEntryFolder addObjectEntryFolder(
			String externalReferenceCode, long groupId,
			java.util.Map<java.util.Locale, String> labelMap, String name,
			long parentObjectEntryFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectEntryFolderService.addObjectEntryFolder(
			externalReferenceCode, groupId, labelMap, name,
			parentObjectEntryFolderId, serviceContext);
	}

	@Override
	public com.liferay.object.model.ObjectEntryFolder deleteObjectEntryFolder(
			long objectEntryFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectEntryFolderService.deleteObjectEntryFolder(
			objectEntryFolderId);
	}

	@Override
	public com.liferay.object.model.ObjectEntryFolder getObjectEntryFolder(
			long objectEntryFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectEntryFolderService.getObjectEntryFolder(
			objectEntryFolderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectEntryFolderService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectEntryFolder updateObjectEntryFolder(
			long objectEntryFolderId,
			java.util.Map<java.util.Locale, String> labelMap, String name,
			long parentObjectEntryFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectEntryFolderService.updateObjectEntryFolder(
			objectEntryFolderId, labelMap, name, parentObjectEntryFolderId);
	}

	@Override
	public ObjectEntryFolderService getWrappedService() {
		return _objectEntryFolderService;
	}

	@Override
	public void setWrappedService(
		ObjectEntryFolderService objectEntryFolderService) {

		_objectEntryFolderService = objectEntryFolderService;
	}

	private ObjectEntryFolderService _objectEntryFolderService;

}