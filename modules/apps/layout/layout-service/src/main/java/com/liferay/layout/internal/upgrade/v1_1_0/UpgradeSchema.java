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

package com.liferay.layout.internal.upgrade.v1_1_0;

import com.liferay.layout.internal.upgrade.v1_1_0.util.LayoutClassedModelUsageTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Statement;

/**
 * @author Pavel Savinov
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			LayoutClassedModelUsageTable.class,
			new AlterTableAddColumn("companyId LONG"));

		upgradeCompanyId();
	}

	protected void upgradeCompanyId() throws Exception {
		try (Statement s = connection.createStatement()) {
			s.execute(
				StringBundler.concat(
					"update LayoutClassedModelUsage set companyId = (",
					"select companyId from Group_ where Group_.groupId = ",
					"LayoutClassedModelUsage.groupId)"));
		}
	}

}