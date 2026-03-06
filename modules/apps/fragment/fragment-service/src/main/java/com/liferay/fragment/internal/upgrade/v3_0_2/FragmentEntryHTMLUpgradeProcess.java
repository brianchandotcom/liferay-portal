/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v3_0_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Ankita Malik
 */
public class FragmentEntryHTMLUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(
			StringBundler.concat(
				"update FragmentEntry set html = ( select ",
				"FragmentEntryVersion.html from FragmentEntryVersion where ",
				"FragmentEntry.fragmentEntryId = ",
				"FragmentEntryVersion.fragmentEntryId and ",
				"FragmentEntry.fragmentCollectionId = ",
				"FragmentEntryVersion.fragmentCollectionId and ",
				"FragmentEntry.modifiedDate = ",
				"FragmentEntryVersion.modifiedDate ) where exists (select 1 ",
				"from FragmentEntryVersion where ",
				"FragmentEntry.fragmentEntryId = ",
				"FragmentEntryVersion.fragmentEntryId and ",
				"FragmentEntry.fragmentCollectionId = ",
				"FragmentEntryVersion.fragmentCollectionId and ",
				"FragmentEntry.modifiedDate = ",
				"FragmentEntryVersion.modifiedDate)"));

		String sql = StringBundler.concat(
			"update FragmentEntry join FragmentEntryVersion on ",
			"FragmentEntry.fragmentEntryId = ",
			"FragmentEntryVersion.fragmentEntryId and ",
			"FragmentEntry.fragmentCollectionId = ",
			"FragmentEntryVersion.fragmentCollectionId and ",
			"FragmentEntry.modifiedDate = FragmentEntryVersion.modifiedDate ",
			"set FragmentEntry.html = FragmentEntryVersion.html");

		dbTypeToSQLMap.add(DBType.MARIADB, sql);
		dbTypeToSQLMap.add(DBType.MYSQL, sql);

		runSQL(dbTypeToSQLMap);
	}

}