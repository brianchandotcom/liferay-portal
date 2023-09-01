/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v8_2_0;

import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Mateus Santana
 */
public class ObjectValidationRuleSettingsUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update ObjectValidationRuleSetting set name = '" +
				ObjectValidationRuleSettingConstants.
					NAME_OUTPUT_OBJECT_FIELD_ID + "'");
	}

}