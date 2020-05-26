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

package com.liferay.account.internal.upgrade.v1_1_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.internal.upgrade.v1_1_0.util.AccountEntryTable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Drew Brokke
 */
public class UpgradeAccountEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("AccountEntry", "externalReferenceCode")) {
			alter(
				AccountEntryTable.class,
				new AlterTableAddColumn(
					"externalReferenceCode", "VARCHAR(75)"));
		}

		if (!hasColumn("AccountEntry", "taxIdNumber")) {
			alter(
				AccountEntryTable.class,
				new AlterTableAddColumn("taxIdNumber", "VARCHAR(75)"));
		}

		if (!hasColumn("AccountEntry", "type_")) {
			alter(
				AccountEntryTable.class,
				new AlterTableAddColumn("type_", "VARCHAR(75)"));

			String defaultType = StringUtil.quote(
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS, StringPool.QUOTE);

			runSQL("update AccountEntry set type_ = " + defaultType);
		}
	}

}