/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Raymond Augé
 * @author Douglas Wong
 */
public class VerifyDocumentLibrary extends VerifyProcess {

	protected void checkFileEntryType() throws Exception {
		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		if (dlFileEntryType != null) {
			return;
		}

		Date now = new Date();

		dlFileEntryType = DLFileEntryTypeLocalServiceUtil.createDLFileEntryType(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		dlFileEntryType.setCreateDate(now);
		dlFileEntryType.setModifiedDate(now);
		dlFileEntryType.setName(DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT);

		DLFileEntryTypeLocalServiceUtil.updateDLFileEntryType(
			dlFileEntryType, false);
	}

	@Override
	protected void doVerify() throws Exception {
		checkFileEntryType();
		removeOrphanedFileEntries();
		updateAssets();
	}

	protected void removeOrphanedFileEntries() throws Exception {
		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getOrphanedFileEntries();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + dlFileEntries.size() +
					" file entries with no group");
		}

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			try {
				DLFileEntryLocalServiceUtil.deleteFileEntry(
					dlFileEntry.getFileEntryId());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to remove file entry " +
							dlFileEntry.getFileEntryId() + ": " +
								e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Removed orphaned file entries");
		}
	}

	protected void updateAssets() throws Exception {
		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getNoAssetFileEntries();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + dlFileEntries.size() +
					" file entries with no asset");
		}

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);
			FileVersion fileVersion = new LiferayFileVersion(
				dlFileEntry.getFileVersion());

			try {
				DLAppHelperLocalServiceUtil.updateAsset(
					dlFileEntry.getUserId(), fileEntry, fileVersion, null, null,
					null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for file entry " +
							dlFileEntry.getFileEntryId() + ": " +
								e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for file entries");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		VerifyDocumentLibrary.class);

}