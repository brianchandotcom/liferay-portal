/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.service;

import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLAppHelperLocalServiceWrapper;
import com.liferay.document.library.kernel.util.DLAppHelperThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.util.RepositoryUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class ExternalRepositoryDLAppHelperLocalServiceWrapper
	extends DLAppHelperLocalServiceWrapper {

	@Override
	public void addFolder(
			long userId, Folder folder, ServiceContext serviceContext)
		throws PortalException {

		if (_isEnabled(folder)) {
			super.addFolder(userId, folder, serviceContext);
		}
	}

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		if (_isEnabled(fileEntry)) {
			super.deleteFileEntry(fileEntry);
		}
	}

	@Override
	public void deleteFolder(Folder folder) throws PortalException {
		if (_isEnabled(folder)) {
			super.deleteFolder(folder);
		}
	}

	@Override
	public void updateFileEntry(
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, long assetClassPK)
		throws PortalException {

		if (_isEnabled(fileEntry)) {
			super.updateFileEntry(
				userId, fileEntry, sourceFileVersion, destinationFileVersion,
				assetClassPK);
		}
	}

	@Override
	public void updateFileEntry(
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, ServiceContext serviceContext)
		throws PortalException {

		if (_isEnabled(fileEntry)) {
			super.updateFileEntry(
				userId, fileEntry, sourceFileVersion, destinationFileVersion,
				serviceContext);
		}
	}

	@Override
	public void updateFolder(
			long userId, Folder folder, ServiceContext serviceContext)
		throws PortalException {

		if (_isEnabled(folder)) {
			super.updateFolder(userId, folder, serviceContext);
		}
	}

	@Override
	public void updateStatus(
			long userId, FileEntry fileEntry, FileVersion latestFileVersion,
			int oldStatus, int newStatus, ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		if (_isEnabled(fileEntry)) {
			super.updateStatus(
				userId, fileEntry, latestFileVersion, oldStatus, newStatus,
				serviceContext, workflowContext);
		}
	}

	private boolean _isEnabled(FileEntry fileEntry) {
		if (!DLAppHelperThreadLocal.isEnabled() ||
			RepositoryUtil.isExternalRepository(fileEntry.getRepositoryId())) {

			return false;
		}

		return true;
	}

	private boolean _isEnabled(Folder folder) {
		if (!DLAppHelperThreadLocal.isEnabled() ||
			(!folder.isMountPoint() &&
			 RepositoryUtil.isExternalRepository(folder.getRepositoryId()))) {

			return false;
		}

		return true;
	}

	@Reference
	private DLAppHelperLocalService _dlAppHelperLocalService;

}