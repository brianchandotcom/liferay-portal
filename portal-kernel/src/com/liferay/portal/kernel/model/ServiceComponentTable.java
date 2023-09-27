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
 * The table class for the &quot;ServiceComponent&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponent
 * @generated
 */
public class ServiceComponentTable extends BaseTable<ServiceComponentTable> {

	public static final ServiceComponentTable INSTANCE =
		new ServiceComponentTable();

	public final Column<ServiceComponentTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ServiceComponentTable, Long> serviceComponentId =
		createColumn(
			"serviceComponentId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ServiceComponentTable, String> buildNamespace =
		createColumn(
			"buildNamespace", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ServiceComponentTable, Long> buildNumber = createColumn(
		"buildNumber", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ServiceComponentTable, Long> buildDate = createColumn(
		"buildDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ServiceComponentTable, Clob> data = createColumn(
		"data_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private ServiceComponentTable() {
		super("ServiceComponent", ServiceComponentTable::new);
	}

}