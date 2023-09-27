/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.upgrade.v2_0_0;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.portal.upgrade.util.BaseUpgradeResourceBlock;

/**
 * @author Preston Crary
 */
public class UpgradeBookmarksFolderResourceBlock
	extends BaseUpgradeResourceBlock {

	@Override
	protected String getClassName() {
		return BookmarksFolder.class.getName();
	}

	@Override
	protected String getPrimaryKeyName() {
		return "folderId";
	}

	@Override
	protected String getTableName() {
		return "BookmarksFolder";
	}

	@Override
	protected boolean hasUserId() {
		return true;
	}

}