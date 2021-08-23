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

package com.liferay.custom.elements.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CustomElementsPortletDesc&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementsPortletDescriptor
 * @generated
 */
public class CustomElementsPortletDescriptorTable
	extends BaseTable<CustomElementsPortletDescriptorTable> {

	public static final CustomElementsPortletDescriptorTable INSTANCE =
		new CustomElementsPortletDescriptorTable();

	public final Column<CustomElementsPortletDescriptorTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CustomElementsPortletDescriptorTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CustomElementsPortletDescriptorTable, Long>
		customElementsPortletDescriptorId = createColumn(
			"customElementsPortletDescId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CustomElementsPortletDescriptorTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CustomElementsPortletDescriptorTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CustomElementsPortletDescriptorTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CustomElementsPortletDescriptorTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CustomElementsPortletDescriptorTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CustomElementsPortletDescriptorTable, Clob> cssURLs =
		createColumn("cssURLs", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CustomElementsPortletDescriptorTable, String>
		htmlElementName = createColumn(
			"htmlElementName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CustomElementsPortletDescriptorTable, Boolean>
		instanceable = createColumn(
			"instanceable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CustomElementsPortletDescriptorTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CustomElementsPortletDescriptorTable, Clob> properties =
		createColumn("properties", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private CustomElementsPortletDescriptorTable() {
		super(
			"CustomElementsPortletDesc",
			CustomElementsPortletDescriptorTable::new);
	}

}