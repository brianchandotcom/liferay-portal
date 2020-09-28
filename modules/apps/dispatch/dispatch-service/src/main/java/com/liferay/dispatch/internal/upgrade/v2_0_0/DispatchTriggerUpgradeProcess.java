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

package com.liferay.dispatch.internal.upgrade.v2_0_0;

import com.liferay.dispatch.model.impl.DispatchTriggerModelImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Igor Beslic
 */
public class DispatchTriggerUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_alterTableAddColumn("endDate", "DATE");

		_alterTableAddColumn("startDate", "DATE");

		_alterTableColumnName("type_", "taskType");

		_alterTableColumnName("typeSettings", "taskProperties");
	}

	private void _alterTableAddColumn(String columnName, String columnType)
		throws Exception {

		if (hasColumn("DispatchTrigger", columnName)) {
			return;
		}

		alter(
			DispatchTriggerModelImpl.class,
			new AlterTableAddColumn(columnName, columnType));
	}

	private void _alterTableColumnName(
			String oldColumnName, String newColumnName)
		throws Exception {

		if (!hasColumn("DispatchTrigger", oldColumnName)) {
			return;
		}

		alter(
			DispatchTriggerModelImpl.class,
			new AlterColumnName(oldColumnName, newColumnName));
	}

}