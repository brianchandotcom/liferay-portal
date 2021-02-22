/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
			runSQL("alter table AccountGroup add type_ VARCHAR(75) null");

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