/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectFolderService}.
 *
 * @author Marco Leo
 * @see ObjectFolderService
 * @generated
 */
public class ObjectFolderServiceWrapper
	implements ObjectFolderService, ServiceWrapper<ObjectFolderService> {

	public ObjectFolderServiceWrapper() {
		this(null);
	}

	public ObjectFolderServiceWrapper(ObjectFolderService objectFolderService) {
		_objectFolderService = objectFolderService;
	}

	@Override
	public com.liferay.object.model.ObjectFolder addObjectFolder(
			String externalReferenceCode,
			java.util.Map<java.util.Locale, String> labelMap, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFolderService.addObjectFolder(
			externalReferenceCode, labelMap, name);
	}

	@Override
	public com.liferay.object.model.ObjectFolder deleteObjectFolder(
			long objectFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFolderService.deleteObjectFolder(objectFolderId);
	}

	@Override
	public com.liferay.object.model.ObjectFolder getObjectFolder(
			long objectFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFolderService.getObjectFolder(objectFolderId);
	}

	@Override
	public com.liferay.object.model.ObjectFolder
			getObjectFolderByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFolderService.getObjectFolderByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectFolderService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectFolder updateObjectFolder(
			String externalReferenceCode, long objectFolderId,
			java.util.Map<java.util.Locale, String> labelMap,
			java.util.List<com.liferay.object.model.ObjectFolderItem>
				objectFolderItems)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFolderService.updateObjectFolder(
			externalReferenceCode, objectFolderId, labelMap, objectFolderItems);
	}

	@Override
	public ObjectFolderService getWrappedService() {
		return _objectFolderService;
	}

	@Override
	public void setWrappedService(ObjectFolderService objectFolderService) {
		_objectFolderService = objectFolderService;
	}

	private ObjectFolderService _objectFolderService;

}