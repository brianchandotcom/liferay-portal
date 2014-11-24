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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

/**
 * @author Michael C. Han
 */
public class VerifyDocumentLibraryTitles extends VerifyProcess {

	protected void checkTitles() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			DLFileEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					DLFileEntry dlFileEntry = (DLFileEntry)object;

					if (dlFileEntry.isInTrash()) {
						return;
					}

					String title = dlFileEntry.getTitle();

					if (StringUtil.contains(
							title, StringPool.DOUBLE_BACK_SLASH)) {

						String newTitle = title.replace(
							StringPool.BACK_SLASH, StringPool.UNDERLINE);

						try {
							dlFileEntry = renameTitle(dlFileEntry, newTitle);
						}
						catch (Exception e) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Unable to rename duplicate title for " +
										"file entry " +
										dlFileEntry.getFileEntryId() +
										": " + e.getMessage(),
									e);
							}
						}
					}

					try {
						DLFileEntryLocalServiceUtil.validateFile(
							dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
							dlFileEntry.getFileEntryId(),
							dlFileEntry.getFileName(), dlFileEntry.getTitle());
					}
					catch (PortalException pe) {
						if (!(pe instanceof DuplicateFileException) &&
							!(pe instanceof DuplicateFolderNameException)) {

							return;
						}

						try {
							renameDuplicateTitle(dlFileEntry);
						}
						catch (Exception e) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Unable to rename duplicate title for " +
										"file entry " +
											dlFileEntry.getFileEntryId() +
												": " + e.getMessage(),
									e);
							}
						}
					}
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	protected void doVerify() throws Exception {
		checkTitles();
	}

	protected void renameDuplicateTitle(DLFileEntry dlFileEntry)
		throws PortalException {

		String title = dlFileEntry.getTitle();
		String titleExtension = StringPool.BLANK;
		String titleWithoutExtension = dlFileEntry.getTitle();

		if (title.endsWith(
				StringPool.PERIOD.concat(dlFileEntry.getExtension()))) {

			titleExtension = dlFileEntry.getExtension();
			titleWithoutExtension = FileUtil.stripExtension(title);
		}

		for (int i = 1;;) {
			String uniqueTitle =
				titleWithoutExtension + StringPool.UNDERLINE +
					String.valueOf(i);

			if (Validator.isNotNull(titleExtension)) {
				uniqueTitle = uniqueTitle.concat(
					StringPool.PERIOD.concat(titleExtension));
			}

			String uniqueFileName = DLUtil.getSanitizedFileName(
				uniqueTitle, dlFileEntry.getExtension());

			try {
				DLFileEntryLocalServiceUtil.validateFile(
					dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
					dlFileEntry.getFileEntryId(), uniqueFileName, uniqueTitle);

				renameTitle(dlFileEntry, uniqueTitle);

				return;
			}
			catch (PortalException pe) {
				if (!(pe instanceof DuplicateFolderNameException) &&
					 !(pe instanceof DuplicateFileException)) {

					throw pe;
				}

				i++;
			}
		}
	}

	protected DLFileEntry renameTitle(DLFileEntry dlFileEntry, String newTitle)
		throws PortalException {

		String title = dlFileEntry.getTitle();

		dlFileEntry.setTitle(newTitle);

		String fileName = DLUtil.getSanitizedFileName(
			newTitle, dlFileEntry.getExtension());

		dlFileEntry.setFileName(fileName);

		DLFileEntry renamedFileEntry =
			DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		dlFileVersion.setTitle(newTitle);
		dlFileVersion.setFileName(fileName);

		DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Invalid title " + title + " renamed to " + newTitle +
					" for file entry " + dlFileEntry.getFileEntryId());
		}

		return renamedFileEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(
		VerifyDocumentLibrary.class);

}