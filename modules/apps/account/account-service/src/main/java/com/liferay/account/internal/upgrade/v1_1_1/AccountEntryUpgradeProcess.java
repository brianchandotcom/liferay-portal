/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v1_1_1;

import com.liferay.account.constants.AccountConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Drew Brokke
 */
public class AccountEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String oldType = StringUtil.quote("personal", StringPool.APOSTROPHE);
		String newType = StringUtil.quote(
			AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON, StringPool.APOSTROPHE);

		runSQL(
			StringBundler.concat(
				"update AccountEntry set type_ = ", newType, " where type_ = ",
				oldType));
	}

}