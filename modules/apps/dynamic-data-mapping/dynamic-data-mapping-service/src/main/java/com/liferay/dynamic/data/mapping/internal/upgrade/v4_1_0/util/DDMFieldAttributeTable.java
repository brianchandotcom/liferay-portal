/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_1_0.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Brian Wing Shun Chan
 * @generated
 * @see com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilder
 */
public class DDMFieldAttributeTable {

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

	private static final String _TABLE_NAME = "DDMFieldAttribute";

	private static final String _TABLE_SQL_CREATE =
		"create table DDMFieldAttribute (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,fieldAttributeId LONG not null,companyId LONG,fieldId LONG,storageId LONG,attributeName VARCHAR(255) null,languageId VARCHAR(75) null,largeAttributeValue TEXT null,smallAttributeValue VARCHAR(255) null,primary key (fieldAttributeId, ctCollectionId))";

}