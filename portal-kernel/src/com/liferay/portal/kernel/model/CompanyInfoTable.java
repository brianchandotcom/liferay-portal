/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;CompanyInfo&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CompanyInfo
 * @generated
 */
public class CompanyInfoTable extends BaseTable<CompanyInfoTable> {

	public static final CompanyInfoTable INSTANCE = new CompanyInfoTable();

	public final Column<CompanyInfoTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CompanyInfoTable, Long> companyInfoId = createColumn(
		"companyInfoId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CompanyInfoTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CompanyInfoTable, Clob> key = createColumn(
		"key_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private CompanyInfoTable() {
		super("CompanyInfo", CompanyInfoTable::new);
	}

}