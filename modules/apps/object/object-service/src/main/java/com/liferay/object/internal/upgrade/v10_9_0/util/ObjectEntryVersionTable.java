/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_9_0.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Feliphe Marinho
 * @generated
 * @see com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilder
 */
public class ObjectEntryVersionTable {

	public static UpgradeProcess create() {
		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				if (!hasTable(_TABLE_NAME)) {
					runSQL(_TABLE_SQL_CREATE);
				}
			}

		};
	}

	private static final String _TABLE_NAME = "ObjectEntryVersion";

	private static final String _TABLE_SQL_CREATE =
		"create table ObjectEntryVersion (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,objectEntryVersionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,objectEntryId LONG,content TEXT null,version INTEGER,status INTEGER)";

}