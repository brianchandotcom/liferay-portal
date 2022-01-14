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

package com.liferay.object.internal.upgrade.v1_2_0;

import com.liferay.object.internal.upgrade.v1_2_0.util.ObjectViewColumnTable;
import com.liferay.object.internal.upgrade.v1_2_0.util.ObjectViewTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectViewUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable(ObjectViewTable.TABLE_NAME)) {
			runSQL(ObjectViewTable.TABLE_SQL_CREATE);
		}

		if (!hasTable(ObjectViewColumnTable.TABLE_NAME)) {
			runSQL(ObjectViewColumnTable.TABLE_SQL_CREATE);
		}
	}

}