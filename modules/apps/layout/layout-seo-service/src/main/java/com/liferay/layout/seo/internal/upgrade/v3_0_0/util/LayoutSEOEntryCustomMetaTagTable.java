/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.upgrade.v3_0_0.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Lourdes Fernández Besada
 * @generated
 * @see com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilder
 */
public class LayoutSEOEntryCustomMetaTagTable {

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

	private static final String _TABLE_NAME = "LayoutSEOEntryCustomMetaTag";

	private static final String _TABLE_SQL_CREATE =
		"create table LayoutSEOEntryCustomMetaTag (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,layoutSEOEntryCustomMetaTagId LONG not null,groupId LONG,companyId LONG,layoutSEOEntryId LONG,property VARCHAR(75) null,content STRING null,primary key (layoutSEOEntryCustomMetaTagId, ctCollectionId))";

}