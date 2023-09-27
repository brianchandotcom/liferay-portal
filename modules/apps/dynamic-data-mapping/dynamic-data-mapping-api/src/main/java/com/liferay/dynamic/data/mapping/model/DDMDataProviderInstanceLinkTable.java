/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DDMDataProviderInstanceLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLink
 * @generated
 */
public class DDMDataProviderInstanceLinkTable
	extends BaseTable<DDMDataProviderInstanceLinkTable> {

	public static final DDMDataProviderInstanceLinkTable INSTANCE =
		new DDMDataProviderInstanceLinkTable();

	public final Column<DDMDataProviderInstanceLinkTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMDataProviderInstanceLinkTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMDataProviderInstanceLinkTable, Long>
		dataProviderInstanceLinkId = createColumn(
			"dataProviderInstanceLinkId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DDMDataProviderInstanceLinkTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceLinkTable, Long>
		dataProviderInstanceId = createColumn(
			"dataProviderInstanceId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceLinkTable, Long> structureId =
		createColumn(
			"structureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private DDMDataProviderInstanceLinkTable() {
		super(
			"DDMDataProviderInstanceLink",
			DDMDataProviderInstanceLinkTable::new);
	}

}