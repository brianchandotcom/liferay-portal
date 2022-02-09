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

package com.liferay.remote.app.internal.upgrade.v2_5_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.remote.app.internal.upgrade.v2_5_0.util.RemoteAppEntryTable;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteAppEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_addCustomElementUseESMColumn();
	}

	private void _addCustomElementUseESMColumn() throws Exception {
		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "customElementUseESM")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("customElementUseESM", "BOOLEAN"));

			runSQL("update RemoteAppEntry set customElementUseESM = [$FALSE$]");
		}
	}

}