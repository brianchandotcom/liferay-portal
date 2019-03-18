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

import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.CTEntryException;
import com.liferay.change.tracking.exception.CTException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceWrapper;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Luiz Marins
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class CTDLFileEntryLocalServiceWrapper
	extends DLFileEntryLocalServiceWrapper {

	public CTDLFileEntryLocalServiceWrapper() {
		super(null);
	}

	public CTDLFileEntryLocalServiceWrapper(
		DLFileEntryLocalService dlFileEntryLocalService) {

		super(dlFileEntryLocalService);
	}

	@Override
	public DLFileEntry addFileEntry(
			long userId, long groupId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, long fileEntryTypeId,
			Map<String, DDMFormValues> ddmFormValuesMap, File file,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry fileEntry = _ctManager.executeModelUpdate(
			() -> super.addFileEntry(
				userId, groupId, repositoryId, folderId, sourceFileName,
				mimeType, title, description, changeLog, fileEntryTypeId,
				ddmFormValuesMap, file, is, size, serviceContext));

		_registerChange(fileEntry, CTConstants.CT_CHANGE_TYPE_ADDITION, true);

		return fileEntry;
	}

	@Override
	public DLFileEntry deleteFileEntry(DLFileEntry dlFileEntry)
		throws PortalException {

		_unregisterChange(dlFileEntry);

		return super.deleteFileEntry(dlFileEntry);
	}

	@Override
	public DLFileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			long fileEntryTypeId, Map<String, DDMFormValues> ddmFormValuesMap,
			File file, InputStream is, long size, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry fileEntry = _ctManager.executeModelUpdate(
			() -> super.updateFileEntry(userId, fileEntryId, sourceFileName,
				mimeType, title, description, changeLog,
				dlVersionNumberIncrease, fileEntryTypeId, ddmFormValuesMap,
				file, is, size, serviceContext));

		_registerChange(fileEntry, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return fileEntry;
	}

	private void _registerChange(DLFileEntry fileEntry, int changeType)
		throws PortalException {

		_registerChange(fileEntry, changeType, false);
	}

	private void _registerChange(
			DLFileEntry fileEntry, int changeType, boolean force)
		throws PortalException {

		if (fileEntry == null) {
			return;
		}

		try {
			DLFileVersion fileVersion = fileEntry.getLatestFileVersion(true);

			_ctManager.registerModelChange(
				PrincipalThreadLocal.getUserId(),
				_portal.getClassNameId(DLFileVersion.class.getName()),
				fileVersion.getFileVersionId(), fileEntry.getFileEntryId(),
				changeType, force);
		}
		catch (CTException cte) {
			if (cte instanceof CTEntryException) {
				if (_log.isWarnEnabled()) {
					_log.warn(cte.getMessage());
				}
			}
			else {
				throw cte;
			}
		}
	}

	private void _unregisterChange(DLFileEntry fileEntry) {
		if (fileEntry == null) {
			return;
		}

		List<DLFileVersion> fileVersions = fileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		for (DLFileVersion dlFileVersion : fileVersions) {
			_ctManager.unregisterModelChange(
				PrincipalThreadLocal.getUserId(),
				_portal.getClassNameId(DLFileVersion.class.getName()),
				dlFileVersion.getFileVersionId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTDLFileEntryLocalServiceWrapper.class);

	@Reference
	private CTManager _ctManager;

	@Reference
	private Portal _portal;

}