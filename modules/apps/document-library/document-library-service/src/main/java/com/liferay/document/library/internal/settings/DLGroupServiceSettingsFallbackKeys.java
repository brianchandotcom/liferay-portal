/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.settings;

import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "settingsId=" + DLConstants.SERVICE_NAME,
	service = FallbackKeys.class
)
public class DLGroupServiceSettingsFallbackKeys extends FallbackKeys {

	@Activate
	protected void activate() {
		add(
			"emailFileEntryAddedBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_BODY);
		add(
			"emailFileEntryAddedEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_ENABLED);
		add(
			"emailFileEntryAddedSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_SUBJECT);
		add(
			"emailFileEntryExpiredBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_EXPIRED_BODY);
		add(
			"emailFileEntryExpiredEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_EXPIRED_ENABLED);
		add(
			"emailFileEntryExpiredSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_EXPIRED_SUBJECT);
		add(
			"emailFileEntryReviewBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_REVIEW_BODY);
		add(
			"emailFileEntryReviewEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_REVIEW_ENABLED);
		add(
			"emailFileEntryReviewSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_REVIEW_SUBJECT);
		add(
			"emailFileEntryUpdatedBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_BODY);
		add(
			"emailFileEntryUpdatedEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_ENABLED);
		add(
			"emailFileEntryUpdatedSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_SUBJECT);
		add(
			"emailFromAddress", PropsKeys.DL_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		add(
			"emailFromName", PropsKeys.DL_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		add("enableCommentRatings", PropsKeys.DL_COMMENT_RATINGS_ENABLED);
		add("enableRatings", PropsKeys.DL_RATINGS_ENABLED);
		add("enableRelatedAssets", PropsKeys.DL_RELATED_ASSETS_ENABLED);
		add("entriesPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		add("entryColumns", PropsKeys.DL_ENTRY_COLUMNS);
		add("fileEntryColumns", PropsKeys.DL_FILE_ENTRY_COLUMNS);
		add("folderColumns", PropsKeys.DL_FOLDER_COLUMNS);
		add("foldersPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		add(
			"fileEntriesPerPage",
			PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		add("showFoldersSearch", PropsKeys.DL_FOLDERS_SEARCH_VISIBLE);
		add("showHiddenMountFolders", PropsKeys.DL_SHOW_HIDDEN_MOUNT_FOLDERS);
		add("showSubfolders", PropsKeys.DL_SUBFOLDERS_VISIBLE);
	}

}