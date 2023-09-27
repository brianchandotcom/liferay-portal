/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_4;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Jorge Díaz
 */
public class DDMTemplateUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update DDMTemplate set templateKey = " +
				"CONCAT(CAST_TEXT(templateId), '_key') where templateKey is " +
					"null or templateKey = ''");
	}

}