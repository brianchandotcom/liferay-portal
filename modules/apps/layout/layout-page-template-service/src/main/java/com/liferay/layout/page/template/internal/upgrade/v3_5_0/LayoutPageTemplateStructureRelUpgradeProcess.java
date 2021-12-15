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

package com.liferay.layout.page.template.internal.upgrade.v3_5_0;

import com.liferay.layout.page.template.internal.upgrade.v3_5_0.util.LayoutPageTemplateStructureRelTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateStructureRelUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(
				LayoutPageTemplateStructureRelTable.TABLE_NAME,
				"lastPublishDate")) {

			alter(
				LayoutPageTemplateStructureRelTable.class,
				new AlterTableAddColumn("lastPublishDate", "DATE"));
		}

		if (!hasColumn(
				LayoutPageTemplateStructureRelTable.TABLE_NAME, "status")) {

			alter(
				LayoutPageTemplateStructureRelTable.class,
				new AlterTableAddColumn("status", "INTEGER"));

			runSQL("update LayoutPageTemplateStructureRel set status = 0");
		}

		if (!hasColumn(
				LayoutPageTemplateStructureRelTable.TABLE_NAME,
				"statusByUserId")) {

			alter(
				LayoutPageTemplateStructureRelTable.class,
				new AlterTableAddColumn("statusByUserId", "LONG"));
		}

		if (!hasColumn(
				LayoutPageTemplateStructureRelTable.TABLE_NAME,
				"statusByUserName")) {

			alter(
				LayoutPageTemplateStructureRelTable.class,
				new AlterTableAddColumn("statusByUserName", "VARCHAR(75)"));
		}

		if (!hasColumn(
				LayoutPageTemplateStructureRelTable.TABLE_NAME, "statusDate")) {

			alter(
				LayoutPageTemplateStructureRelTable.class,
				new AlterTableAddColumn("statusDate", "DATE"));
		}
	}

}