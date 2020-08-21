/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.application.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceApplicationBrand&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationBrand
 * @generated
 */
public class CommerceApplicationBrandTable
	extends BaseTable<CommerceApplicationBrandTable> {

	public static final CommerceApplicationBrandTable INSTANCE =
		new CommerceApplicationBrandTable();

	public final Column<CommerceApplicationBrandTable, Long>
		commerceApplicationBrandId = createColumn(
			"commerceApplicationBrandId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceApplicationBrandTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, Long> logoId =
		createColumn("logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private CommerceApplicationBrandTable() {
		super("CommerceApplicationBrand", CommerceApplicationBrandTable::new);
	}

}