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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DDMTemplateLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLink
 * @generated
 */
public class DDMTemplateLinkTable extends BaseTable<DDMTemplateLinkTable> {

	public static final DDMTemplateLinkTable INSTANCE =
		new DDMTemplateLinkTable();

	public final Column<DDMTemplateLinkTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMTemplateLinkTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMTemplateLinkTable, Long> templateLinkId =
		createColumn(
			"templateLinkId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMTemplateLinkTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateLinkTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateLinkTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateLinkTable, Long> templateId = createColumn(
		"templateId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private DDMTemplateLinkTable() {
		super("DDMTemplateLink", DDMTemplateLinkTable::new);
	}

}