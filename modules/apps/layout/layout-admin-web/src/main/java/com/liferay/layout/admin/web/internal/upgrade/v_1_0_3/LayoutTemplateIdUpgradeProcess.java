/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.upgrade.v_1_0_3;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Sam Ziemer
 */
public class LayoutTemplateIdUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateLayoutTemplateId();
	}

	private void _updateLayoutTemplateId() throws Exception {
		runSQL(
			StringBundler.concat(
				"update Layout set typeSettings = REPLACE(typeSettings, ",
				"'layout-template-id=1_2_1_columns\n', ",
				"'layout-template-id=1_2_1_columns_i\n')"));
	}

}