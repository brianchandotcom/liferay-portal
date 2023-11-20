/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v8_8_1;

import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.impl.ObjectFieldSettingModelImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Pedro Tavares
 */
public class ObjectFieldSettingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update ", ObjectFieldSettingModelImpl.TABLE_NAME,
				" set name = '",
				ObjectFieldSettingConstants.NAME_OBJECT_DEFINITION_1_SHORT_NAME,
				"' where name = 'ObjectDefinition1ShortName'"));
	}

}