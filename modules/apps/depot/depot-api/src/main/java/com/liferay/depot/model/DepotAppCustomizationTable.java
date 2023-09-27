/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DepotAppCustomization&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DepotAppCustomization
 * @generated
 */
public class DepotAppCustomizationTable
	extends BaseTable<DepotAppCustomizationTable> {

	public static final DepotAppCustomizationTable INSTANCE =
		new DepotAppCustomizationTable();

	public final Column<DepotAppCustomizationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DepotAppCustomizationTable, Long>
		depotAppCustomizationId = createColumn(
			"depotAppCustomizationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DepotAppCustomizationTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DepotAppCustomizationTable, Long> depotEntryId =
		createColumn(
			"depotEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DepotAppCustomizationTable, Boolean> enabled =
		createColumn(
			"enabled", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<DepotAppCustomizationTable, String> portletId =
		createColumn(
			"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private DepotAppCustomizationTable() {
		super("DepotAppCustomization", DepotAppCustomizationTable::new);
	}

}