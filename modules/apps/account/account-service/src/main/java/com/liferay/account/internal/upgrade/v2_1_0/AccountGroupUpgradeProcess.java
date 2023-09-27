/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v2_1_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Drew Brokke
 */
public class AccountGroupUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("AccountGroup", "type_")) {
			alterTableAddColumn("AccountGroup", "type_", "VARCHAR(75) null");

			String type = StringUtil.quote(
				AccountConstants.ACCOUNT_GROUP_TYPE_STATIC,
				StringPool.APOSTROPHE);

			runSQL(
				"update AccountGroup set type_ = " + type +
					" where defaultAccountGroup = [$FALSE$]");

			type = StringUtil.quote(
				AccountConstants.ACCOUNT_GROUP_TYPE_GUEST,
				StringPool.APOSTROPHE);

			runSQL(
				"update AccountGroup set type_ = " + type +
					" where defaultAccountGroup = [$TRUE$]");
		}
	}

}