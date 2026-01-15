/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.TableOrphanReferencesDataCleanupPreupgradeProcess;

/**
 * @author Luis Ortiz
 */
public class LayoutDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null,
				StringBundler.concat(
					"[$SOURCE_TABLE_ALIAS$].classNameId = (select classNameId ",
					"from ClassName_ where value = '", Layout.class.getName(),
					"')"),
				"classPK", "Layout", "plid", "Layout"));

		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"not exists (select 1 from (select layoutRevisionId from " +
					"LayoutRevision) AS temp where temp.layoutRevisionId = " +
						"[$SOURCE_TABLE_ALIAS$].plid)",
				new String[0], "plid", new String[] {"plid"}, "Layout"));

		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				StringBundler.concat(
					"[$SOURCE_TABLE_ALIAS$].classNameId in (select ",
					"classNameId from ClassName_ where value = '",
					Layout.class.getName(), "' or value like '",
					Layout.class.getName(), "-%')"),
				new String[] {"classNameId"}, "classPK", new String[] {"plid"},
				"Layout"));

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null,
				StringBundler.concat(
					"[$SOURCE_TABLE_ALIAS$].scope = ",
					ResourceConstants.SCOPE_INDIVIDUAL, " and ",
					"[$SOURCE_TABLE_ALIAS$].name = '", Layout.class.getName(),
					"'"),
				"primKeyId", "ResourcePermission", "plid", "Layout"));
	}

}