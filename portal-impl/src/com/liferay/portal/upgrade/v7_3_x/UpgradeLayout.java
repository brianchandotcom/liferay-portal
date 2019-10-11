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

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_3_x.util.LayoutTable;

import java.sql.PreparedStatement;

/**
 * @author Preston Crary
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("Layout", "headId") || hasColumn("Layout", "head")) {
			alter(
				LayoutTable.class, new AlterTableDropColumn("headId"),
				new AlterTableDropColumn("head"));
		}

		if (!hasColumn("Layout", "mLayoutPageTemplateEntryId")) {
			alter(
				LayoutTable.class,
				new AlterTableDropColumn("mLayoutPageTemplateEntryId"));
		}

		try (PreparedStatement ps = connection.prepareStatement(
				SQLTransformer.transform(
					"update Layout set mLayoutPageTemplateEntryId = 0"))) {

			if (ps.executeUpdate() == 0) {
				return;
			}
		}

		runSQL("DROP_TABLE_IF_EXISTS(LayoutVersion)");
	}

}