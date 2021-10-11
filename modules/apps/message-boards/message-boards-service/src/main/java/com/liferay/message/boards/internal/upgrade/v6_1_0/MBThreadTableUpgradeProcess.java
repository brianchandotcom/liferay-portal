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

package com.liferay.message.boards.internal.upgrade.v6_1_0;

import com.liferay.message.boards.internal.upgrade.v2_0_0.util.MBThreadTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alicia García
 */
public class MBThreadTableUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumnType(
				MBThreadTable.TABLE_NAME, "title", "VARCHAR(75) null")) {

			alter(
				MBThreadTable.class,
				new UpgradeProcess.AlterColumnType(
					"title", "VARCHAR(75) null"));
		}
	}

}