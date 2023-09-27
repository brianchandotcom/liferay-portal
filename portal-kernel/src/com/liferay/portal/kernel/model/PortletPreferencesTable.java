/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;PortletPreferences&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferences
 * @generated
 */
public class PortletPreferencesTable
	extends BaseTable<PortletPreferencesTable> {

	public static final PortletPreferencesTable INSTANCE =
		new PortletPreferencesTable();

	public final Column<PortletPreferencesTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PortletPreferencesTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PortletPreferencesTable, Long> portletPreferencesId =
		createColumn(
			"portletPreferencesId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<PortletPreferencesTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PortletPreferencesTable, Long> ownerId = createColumn(
		"ownerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PortletPreferencesTable, Integer> ownerType =
		createColumn(
			"ownerType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PortletPreferencesTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PortletPreferencesTable, String> portletId =
		createColumn(
			"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private PortletPreferencesTable() {
		super("PortletPreferences", PortletPreferencesTable::new);
	}

}