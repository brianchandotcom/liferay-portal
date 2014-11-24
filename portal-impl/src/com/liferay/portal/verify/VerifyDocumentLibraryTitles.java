/*
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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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

	protected void checkDuplicateTitles() throws Exception {
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

	protected void checkTitles() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			DLFileEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property titleProperty = PropertyFactoryUtil.forName(
						"title");

					dynamicQuery.add(titleProperty.like("%\\\\%"));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					dynamicQuery.add(
						statusProperty.ne(WorkflowConstants.STATUS_IN_TRASH));
				}

			});

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					DLFileEntry dlFileEntry = (DLFileEntry)object;

					String title = dlFileEntry.getTitle();

					String newTitle = title.replace(
						StringPool.BACK_SLASH, StringPool.UNDERLINE);

					try {
						renameTitle(dlFileEntry, newTitle);
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
			});

		actionableDynamicQuery.performActions();

		checkDuplicateTitles();
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

	protected void renameTitle(DLFileEntry dlFileEntry, String newTitle)
		throws PortalException {

		String title = dlFileEntry.getTitle();

		dlFileEntry.setTitle(newTitle);

		String fileName = DLUtil.getSanitizedFileName(
			newTitle, dlFileEntry.getExtension());

		dlFileEntry.setFileName(fileName);

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
	}

	private static Log _log = LogFactoryUtil.getLog(
		VerifyDocumentLibrary.class);

}