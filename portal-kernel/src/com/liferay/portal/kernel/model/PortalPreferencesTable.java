/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;PortalPreferences&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PortalPreferences
 * @generated
 */
public class PortalPreferencesTable extends BaseTable<PortalPreferencesTable> {

	public static final PortalPreferencesTable INSTANCE =
		new PortalPreferencesTable();

	public final Column<PortalPreferencesTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PortalPreferencesTable, Long> portalPreferencesId =
		createColumn(
			"portalPreferencesId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<PortalPreferencesTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PortalPreferencesTable, Long> ownerId = createColumn(
		"ownerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PortalPreferencesTable, Integer> ownerType =
		createColumn(
			"ownerType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private PortalPreferencesTable() {
		super("PortalPreferences", PortalPreferencesTable::new);
	}

}