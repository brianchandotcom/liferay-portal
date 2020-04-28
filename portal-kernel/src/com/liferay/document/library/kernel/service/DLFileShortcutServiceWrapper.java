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

package com.liferay.document.library.kernel.service;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFileShortcutService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutService
 * @generated
 */
public class DLFileShortcutServiceWrapper
	implements DLFileShortcutService, ServiceWrapper<DLFileShortcutService> {

	public DLFileShortcutServiceWrapper(
		DLFileShortcutService dlFileShortcutService) {

		_dlFileShortcutService = dlFileShortcutService;
	}

	@Override
	public DLFileShortcut addFileShortcut(
			long groupId, long repositoryId, long folderId, long toFileEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileShortcutService.addFileShortcut(
			groupId, repositoryId, folderId, toFileEntryId, serviceContext);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileShortcutService.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileShortcutService.getFileShortcut(fileShortcutId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dlFileShortcutService.getOSGiServiceIdentifier();
	}

	@Override
	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long repositoryId, long folderId,
			long toFileEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileShortcutService.updateFileShortcut(
			fileShortcutId, repositoryId, folderId, toFileEntryId,
			serviceContext);
	}

	@Override
	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileShortcutService.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
	}

	@Override
	public DLFileShortcutService getWrappedService() {
		return _dlFileShortcutService;
	}

	@Override
	public void setWrappedService(DLFileShortcutService dlFileShortcutService) {
		_dlFileShortcutService = dlFileShortcutService;
	}

	private DLFileShortcutService _dlFileShortcutService;

}