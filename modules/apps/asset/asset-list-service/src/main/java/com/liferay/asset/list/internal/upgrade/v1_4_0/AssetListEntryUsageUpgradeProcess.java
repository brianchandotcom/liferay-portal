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

package com.liferay.asset.list.internal.upgrade.v1_4_0;

import com.liferay.asset.list.constants.AssetListEntryUsageConstants;
import com.liferay.asset.list.internal.upgrade.v1_4_0.util.AssetListEntryUsageTable;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;

/**
 * @author Víctor Galán
 */
public class AssetListEntryUsageUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();
	}

	protected void upgradeSchema() throws Exception {
		alter(
			AssetListEntryUsageTable.class,
			new AlterTableAddColumn("containerKey", "VARCHAR(255) null"),
			new AlterTableAddColumn("containerType", "LONG"),
			new AlterTableAddColumn("key_", "VARCHAR(255) null"),
			new AlterTableAddColumn("plid", "LONG"),
			new AlterTableAddColumn("type_", "INTEGER"));

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"update AssetListEntryUsage set classNameId = ?, ",
						"containerKey = portletId, containerType = ?, key_ = ",
						"CAST_TEXT(assetListEntryId), plid = classPK, type_ = ",
						"?")))) {

			ps1.setLong(1, PortalUtil.getClassNameId(AssetListEntry.class));
			ps1.setLong(2, PortalUtil.getClassNameId(Portlet.class));
			ps1.setInt(3, AssetListEntryUsageConstants.TYPE_LAYOUT);

			ps1.execute();
		}
	}

}