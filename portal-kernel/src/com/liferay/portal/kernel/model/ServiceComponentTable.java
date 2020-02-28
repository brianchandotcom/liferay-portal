/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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