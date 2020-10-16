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

package com.liferay.dispatch.internal.upgrade.v3_0_0;

import com.liferay.dispatch.internal.upgrade.v2_0_0.util.DispatchTriggerTable;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Igor Beslic
 */
public class DispatchTriggerUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String oldColumnName = "taskType";
		String newColumnName = "taskExecutorType";

		if (!hasColumn(DispatchTriggerTable.TABLE_NAME, oldColumnName)) {
			if (hasColumn(DispatchTriggerTable.TABLE_NAME, newColumnName)) {
				return;
			}

			throw new UpgradeException(
				String.format(
					"Unable to rename %s to %s in table %s", oldColumnName,
					newColumnName, DispatchTriggerTable.TABLE_NAME));
		}

		alter(
			DispatchTriggerTable.class,
			new AlterColumnName(oldColumnName, newColumnName + " varchar(75)"));
	}

}