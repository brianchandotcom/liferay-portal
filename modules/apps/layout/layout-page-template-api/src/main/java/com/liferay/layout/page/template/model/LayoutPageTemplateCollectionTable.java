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

package com.liferay.layout.page.template.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the LayoutPageTemplateCollection.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutPageTemplateCollectionTable
	extends Table<LayoutPageTemplateCollectionTable> {

	public static final LayoutPageTemplateCollectionTable INSTANCE =
		new LayoutPageTemplateCollectionTable();

	public final Column<LayoutPageTemplateCollectionTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<LayoutPageTemplateCollectionTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR);
	public final Column<LayoutPageTemplateCollectionTable, Long>
		layoutPageTemplateCollectionId = createColumn(
			"layoutPageTemplateCollectionId", Long.class, Types.BIGINT);
	public final Column<LayoutPageTemplateCollectionTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<LayoutPageTemplateCollectionTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<LayoutPageTemplateCollectionTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<LayoutPageTemplateCollectionTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<LayoutPageTemplateCollectionTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<LayoutPageTemplateCollectionTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<LayoutPageTemplateCollectionTable, String> name =
		createColumn("name", String.class, Types.VARCHAR);
	public final Column<LayoutPageTemplateCollectionTable, String> description =
		createColumn("description", String.class, Types.VARCHAR);
	public final Column<LayoutPageTemplateCollectionTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP);

	private LayoutPageTemplateCollectionTable() {
		super(
			"LayoutPageTemplateCollection",
			LayoutPageTemplateCollectionTable::new);
	}

}