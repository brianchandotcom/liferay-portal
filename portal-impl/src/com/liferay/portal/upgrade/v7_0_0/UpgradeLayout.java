/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Jonathan McCann
 */
public class UpgradeLayout extends UpgradeProcess {

	protected void deleteLinkedOrphanedLayouts() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				StringBundler.concat(
					"delete from Layout where layoutPrototypeUuid != '' and ",
					"layoutPrototypeUuid not in (select uuid_ from ",
					"LayoutPrototype) and layoutPrototypeLinkEnabled = ",
					"[$TRUE$]"));
		}
	}

	protected void deleteOrphanedFriendlyURL() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String sql =
				"delete from LayoutFriendlyURL where plid not in (select " +
					"plid from Layout)";

			DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(sql);

			sql =
				"delete from LayoutFriendlyURL where not exists (select null " +
					"from Layout where Layout.plid = LayoutFriendlyURL.plid)";

			dbTypeToSQLMap.add(DBType.POSTGRESQL, sql);

			runSQL(dbTypeToSQLMap);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteLinkedOrphanedLayouts();
		deleteOrphanedFriendlyURL();
		updateLayoutPrototypeLinkEnabled();
		updateUnlinkedOrphanedLayouts();
	}

	protected void updateLayoutPrototypeLinkEnabled() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"update Layout set layoutPrototypeLinkEnabled = [$FALSE$] " +
					"where type_ = 'link_to_layout' and " +
						"layoutPrototypeLinkEnabled = [$TRUE$]");
		}
	}

	protected void updateUnlinkedOrphanedLayouts() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				StringBundler.concat(
					"update Layout set layoutPrototypeUuid = null where ",
					"layoutPrototypeUuid != '' and layoutPrototypeUuid not in ",
					"(select uuid_ from LayoutPrototype) and ",
					"layoutPrototypeLinkEnabled = [$FALSE$]"));
		}
	}

}