/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Brian Wing Shun Chan
 * @generated
 * @see com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilder
 */
public class PortletPreferenceValueTable {

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

	private static final String _TABLE_NAME = "PortletPreferenceValue";

	private static final String _TABLE_SQL_CREATE =
		"create table PortletPreferenceValue (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,portletPreferenceValueId LONG not null,companyId LONG,portletPreferencesId LONG,index_ INTEGER,largeValue TEXT null,name VARCHAR(255) null,readOnly BOOLEAN,smallValue VARCHAR(255) null,primary key (portletPreferenceValueId, ctCollectionId))";

}