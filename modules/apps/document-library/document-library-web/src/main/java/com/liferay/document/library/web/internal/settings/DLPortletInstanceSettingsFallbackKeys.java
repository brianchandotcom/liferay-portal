/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.settings;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.util.PropsKeys;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = {
		"settingsId=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"settingsId=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"settingsId=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY
	},
	service = FallbackKeys.class
)
public class DLPortletInstanceSettingsFallbackKeys extends FallbackKeys {

	@Activate
	protected void activate() {
		add("displayViews", PropsKeys.DL_DISPLAY_VIEWS);
		add("enableCommentRatings", PropsKeys.DL_COMMENT_RATINGS_ENABLED);
		add("enableFileEntryDrafts", PropsKeys.DL_FILE_ENTRY_DRAFTS_ENABLED);
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
		add("showActions", PropsKeys.DL_ACTIONS_VISIBLE);
		add("showFoldersSearch", PropsKeys.DL_FOLDERS_SEARCH_VISIBLE);
		add("showSubfolders", PropsKeys.DL_SUBFOLDERS_VISIBLE);
	}

}