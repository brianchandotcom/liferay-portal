/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;OrgLabor&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see OrgLabor
 * @generated
 */
public class OrgLaborTable extends BaseTable<OrgLaborTable> {

	public static final OrgLaborTable INSTANCE = new OrgLaborTable();

	public final Column<OrgLaborTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<OrgLaborTable, Long> orgLaborId = createColumn(
		"orgLaborId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<OrgLaborTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Long> organizationId = createColumn(
		"organizationId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Long> listTypeId = createColumn(
		"listTypeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> sunOpen = createColumn(
		"sunOpen", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> sunClose = createColumn(
		"sunClose", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> monOpen = createColumn(
		"monOpen", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> monClose = createColumn(
		"monClose", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> tueOpen = createColumn(
		"tueOpen", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> tueClose = createColumn(
		"tueClose", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> wedOpen = createColumn(
		"wedOpen", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> wedClose = createColumn(
		"wedClose", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> thuOpen = createColumn(
		"thuOpen", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> thuClose = createColumn(
		"thuClose", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> friOpen = createColumn(
		"friOpen", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> friClose = createColumn(
		"friClose", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> satOpen = createColumn(
		"satOpen", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<OrgLaborTable, Integer> satClose = createColumn(
		"satClose", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private OrgLaborTable() {
		super("OrgLabor", OrgLaborTable::new);
	}

}