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

package com.liferay.document.library.change.tracking.service.internal.service;

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.document.library.change.tracking.service.CTDLFolderService;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.document.library.kernel.service.DLFolderServiceWrapper;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luiz Marins
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class CTDLFolderServiceWrapper extends DLFolderServiceWrapper {

	public CTDLFolderServiceWrapper() {
		super(null);
	}

	public CTDLFolderServiceWrapper(DLFolderService dlFolderService) {
		super(dlFolderService);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition<?> queryDefinition)
		throws PortalException {

		if (!_isChangeTrackingEnabled(groupId)) {
			return super.getFoldersAndFileEntriesAndFileShortcuts(
				groupId, folderId, mimeTypes, includeMountFolders,
				queryDefinition);
		}

		return _ctDLFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition<?> queryDefinition)
		throws PortalException {

		if (!_isChangeTrackingEnabled(groupId)) {
			return super.getFoldersAndFileEntriesAndFileShortcutsCount(
				groupId, folderId, mimeTypes, includeMountFolders,
				queryDefinition);
		}

		return _ctDLFolderLocalService.
			getFoldersAndFileEntriesAndFileShortcutsCount(
				groupId, folderId, mimeTypes, includeMountFolders,
				queryDefinition);
	}

	private boolean _isChangeTrackingEnabled(long groupId)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		if (_ctEngineManager.isChangeTrackingEnabled(group.getCompanyId()) &&
			_ctEngineManager.isChangeTrackingSupported(
				group.getCompanyId(), DLFileVersion.class)) {

			return true;
		}

		return false;
	}

	@Reference
	private CTDLFolderService _ctDLFolderLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private GroupLocalService _groupLocalService;

}