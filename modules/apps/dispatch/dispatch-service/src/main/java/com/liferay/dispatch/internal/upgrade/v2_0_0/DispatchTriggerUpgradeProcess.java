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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Igor Beslic
 */
public class DispatchTriggerUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_alterTableAddColumn("endDate", "DATE null");

		_alterTableAddColumn("startDate", "DATE null");

		_alterColumnName("typeSettings", "taskSettings TEXT null");

		_alterColumnName("type_", "taskType VARCHAR(75) null");
	}

	private void _alterColumnName(String oldColumnName, String newColumnName)
		throws Exception {

		alterColumnName("DispatchTrigger", oldColumnName, newColumnName);
	}

	private void _alterTableAddColumn(String columnName, String columnType)
		throws Exception {

		alterTableAddColumn("DispatchTrigger", columnName, columnType);
	}

}