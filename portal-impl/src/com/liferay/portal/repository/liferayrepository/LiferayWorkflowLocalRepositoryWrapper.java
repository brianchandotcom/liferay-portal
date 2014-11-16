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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.util.LocalRepositoryWrapper;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class LiferayWorkflowLocalRepositoryWrapper
	extends LocalRepositoryWrapper {

	public LiferayWorkflowLocalRepositoryWrapper(
		LocalRepository localRepository) {

		super(localRepository);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		DLFileVersionReference dlFileVersionReference =
			_getWorkflowDLFileVersion(fileEntryId, serviceContext);

		super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);

		_startWorkflowInstance(
			userId, dlFileVersionReference.fetchDLFileVersion(),
			serviceContext);

		return super.getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileVersionReference dlFileVersionReference =
			_getWorkflowDLFileVersion(fileEntryId, serviceContext);

		super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		_startWorkflowInstance(
			userId, dlFileVersionReference.fetchDLFileVersion(),
			serviceContext);

		return super.getFileEntry(fileEntryId);
	}

	/**
	 * See {@link
	 * com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl#updateFileEntry(
	 * long, long, String, String, String, String, String, String, boolean,
	 * String, long, java.util.Map, File, InputStream, long, ServiceContext)}
	 */
	private DLFileVersionReference _getWorkflowDLFileVersion(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntryId);

		boolean checkedOut = dlFileEntry.isCheckedOut();

		DLFileVersion dlFileVersion =
			DLFileVersionLocalServiceUtil.getLatestFileVersion(
				fileEntryId, !checkedOut);

		boolean autoCheckIn = !checkedOut && dlFileVersion.isApproved();

		if (autoCheckIn) {
			return new DLFileVersionReference(autoCheckIn, fileEntryId, null);
		}

		int workflowAction = serviceContext.getWorkflowAction();

		if (!checkedOut &&
			(workflowAction == WorkflowConstants.ACTION_PUBLISH)) {

			return new DLFileVersionReference(
				autoCheckIn, fileEntryId, dlFileVersion);
		}

		return new DLFileVersionReference(autoCheckIn, fileEntryId, null);
	}

	private void _startWorkflowInstance(
			long userId, DLFileVersion dlFileVersion,
			ServiceContext serviceContext)
		throws PortalException {

		if (dlFileVersion == null) {
			return;
		}

		String syncEvent = DLSyncConstants.EVENT_UPDATE;

		if (dlFileVersion.getVersion().equals(
				DLFileEntryConstants.VERSION_DEFAULT)) {

			syncEvent = DLSyncConstants.EVENT_ADD;
		}

		DLUtil.startWorkflowInstance(
			userId, dlFileVersion, syncEvent, serviceContext);
	}

	private static final class DLFileVersionReference {

		public DLFileVersionReference(
			boolean autoCheckIn, long fileEntryId,
			DLFileVersion dlFileVersion) {

			_autoCheckIn = autoCheckIn;
			_fileEntryId = fileEntryId;
			_dlFileVersion = dlFileVersion;
		}

		public DLFileVersion fetchDLFileVersion() throws PortalException {
			if (_dlFileVersion != null) {
				return _dlFileVersion;
			}

			if (_autoCheckIn) {
				return DLFileVersionLocalServiceUtil.getLatestFileVersion(
					_fileEntryId, false);
			}

			return null;
		}

		private final boolean _autoCheckIn;
		private final DLFileVersion _dlFileVersion;
		private final long _fileEntryId;

	}

}