/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;RegionLocalization&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RegionLocalization
 * @generated
 */
public class RegionLocalizationTable
	extends BaseTable<RegionLocalizationTable> {

	public static final RegionLocalizationTable INSTANCE =
		new RegionLocalizationTable();

	public final Column<RegionLocalizationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RegionLocalizationTable, Long> regionLocalizationId =
		createColumn(
			"regionLocalizationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RegionLocalizationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegionLocalizationTable, Long> regionId = createColumn(
		"regionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegionLocalizationTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RegionLocalizationTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RegionLocalizationTable() {
		super("RegionLocalization", RegionLocalizationTable::new);
	}

}