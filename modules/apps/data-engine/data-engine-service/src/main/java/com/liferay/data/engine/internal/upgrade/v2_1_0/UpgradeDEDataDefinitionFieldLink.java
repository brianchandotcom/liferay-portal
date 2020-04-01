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

package com.liferay.data.engine.internal.upgrade.v2_1_0;

import com.liferay.data.engine.internal.upgrade.v2_1_0.util.DEDataDefinitionFieldLinkTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Pavel Savinov
 */
public class UpgradeDEDataDefinitionFieldLink extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("DEDataDefinitionFieldLink", "createDate")) {
			alter(
				DEDataDefinitionFieldLinkTable.class,
				new AlterTableAddColumn("createDate", "DATE null"));
		}

		if (!hasColumn("DEDataDefinitionFieldLink", "modifiedDate")) {
			alter(
				DEDataDefinitionFieldLinkTable.class,
				new AlterTableAddColumn("modifiedDate", "DATE null"));
		}

		if (!hasColumn("DEDataDefinitionFieldLink", "lastPublishDate")) {
			alter(
				DEDataDefinitionFieldLinkTable.class,
				new AlterTableAddColumn("lastPublishDate", "DATE null"));
		}
	}

}