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
			java.util.Map<java.util.Locale, String> labelMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFolderService.updateObjectFolder(
			externalReferenceCode, objectFolderId, labelMap);
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