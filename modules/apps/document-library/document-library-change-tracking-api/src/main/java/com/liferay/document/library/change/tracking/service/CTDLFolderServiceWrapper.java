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

package com.liferay.document.library.change.tracking.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CTDLFolderService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTDLFolderService
 * @generated
 */
@ProviderType
public class CTDLFolderServiceWrapper implements CTDLFolderService,
	ServiceWrapper<CTDLFolderService> {
	public CTDLFolderServiceWrapper(CTDLFolderService ctdlFolderService) {
		_ctdlFolderService = ctdlFolderService;
	}

	@Override
	public java.util.List<Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctdlFolderService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this class directly. Always use <code>CTDLFolderServiceUtil</code> to access the ctdl folder remote service.
	*/
	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, String[] mimeTypes, boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctdlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctdlFolderService.getOSGiServiceIdentifier();
	}

	@Override
	public CTDLFolderService getWrappedService() {
		return _ctdlFolderService;
	}

	@Override
	public void setWrappedService(CTDLFolderService ctdlFolderService) {
		_ctdlFolderService = ctdlFolderService;
	}

	private CTDLFolderService _ctdlFolderService;
}