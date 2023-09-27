/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_1_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Shuyang Zhou
 */
public class UpgradeExternalReferenceCode extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String tableName : _TABLE_NAMES) {
			alterTableAddColumn(
				tableName, "externalReferenceCode", "VARCHAR(75) null");
		}
	}

	private static final String[] _TABLE_NAMES = {
		"AssetCategory", "AssetVocabulary", "Organization_", "UserGroup",
		"User_"
	};

}