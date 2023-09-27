/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v3_27_1;

import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.impl.ObjectFieldSettingModelImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectFieldSettingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update ", ObjectFieldSettingModelImpl.TABLE_NAME,
				" set name = '",
				ObjectFieldSettingConstants.
					NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
				"' where name like 'objectRelationshipERCFieldName'"));
	}

}