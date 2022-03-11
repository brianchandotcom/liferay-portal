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

package com.liferay.fragment.internal.upgrade.v2_6_0.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Brian Wing Shun Chan
 * @generated
 * @see com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilder
 */
public class FragmentEntryVersionTable {

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

	private static final String _TABLE_NAME = "FragmentEntryVersion";

	private static final String _TABLE_SQL_CREATE =
		"create table FragmentEntryVersion (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,fragmentEntryVersionId LONG not null,version INTEGER,uuid_ VARCHAR(75) null,fragmentEntryId LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,fragmentCollectionId LONG,fragmentEntryKey VARCHAR(75) null,name VARCHAR(75) null,css TEXT null,html TEXT null,js TEXT null,cacheable BOOLEAN,configuration TEXT null,icon VARCHAR(75) null,previewFileEntryId LONG,readOnly BOOLEAN,type_ INTEGER,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null,primary key (fragmentEntryVersionId, ctCollectionId))";

}