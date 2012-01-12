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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.v5_2_3.util.TagsAssetTable;
import com.liferay.portal.upgrade.v5_2_3.util.TagsPropertyTable;
import com.liferay.portal.upgrade.v5_2_3.util.TagsPropertyValueUpgradeColumnImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * @author Brian Wing Shun Chan
 * @author Samuel Kong
 */
public class UpgradeTags extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (isSupportsAlterColumnType()) {
			runSQL("alter_column_type TagsAsset title VARCHAR(255) null");
		}
		else {

			// TagsAsset

			upgradeTable(
				TagsAssetTable.TABLE_NAME, TagsAssetTable.TABLE_COLUMNS,
				TagsAssetTable.TABLE_SQL_CREATE,
				TagsAssetTable.TABLE_SQL_ADD_INDEXES);
		}

		updateAssetViewCount();

		// TagsProperty

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			TagsPropertyTable.TABLE_NAME, TagsPropertyTable.TABLE_COLUMNS,
			new TagsPropertyValueUpgradeColumnImpl("value"));

		upgradeTable.setCreateSQL(TagsPropertyTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(TagsPropertyTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();
	}

	protected void updateAssetViewCount() throws Exception {
		updateAssetViewCount(
			BookmarksEntry.class.getName(), "BookmarksEntry", "entryId",
			"visits");

		updateAssetViewCount(
			DLFileEntry.class.getName(), "DLFileEntry", "fileEntryId",
			"readCount");
	}

	protected void updateAssetViewCount(
			String className, String tableName, String columnClassPK,
			String columnViewCount)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(className);

		StringBundler sb = new StringBundler(12);

		sb.append("update TagsAsset set viewCount = (select ");
		sb.append(tableName);
		sb.append(".");
		sb.append(columnViewCount);
		sb.append(" from ");
		sb.append(tableName);
		sb.append(" where TagsAsset.classPK = ");
		sb.append(tableName);
		sb.append(".");
		sb.append(columnClassPK);
		sb.append(") where TagsAsset.classNameId = ");
		sb.append(classNameId);

		runSQL(sb.toString());
	}

}