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

package com.liferay.data.engine.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the DEDataDefinitionFieldLink.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DEDataDefinitionFieldLinkTable
	extends Table<DEDataDefinitionFieldLinkTable> {

	public static final DEDataDefinitionFieldLinkTable INSTANCE =
		new DEDataDefinitionFieldLinkTable();

	public final Column<DEDataDefinitionFieldLinkTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR);
	public final Column<DEDataDefinitionFieldLinkTable, Long>
		deDataDefinitionFieldLinkId = createColumn(
			"deDataDefinitionFieldLinkId", Long.class, Types.BIGINT);
	public final Column<DEDataDefinitionFieldLinkTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<DEDataDefinitionFieldLinkTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<DEDataDefinitionFieldLinkTable, Long> classNameId =
		createColumn("classNameId", Long.class, Types.BIGINT);
	public final Column<DEDataDefinitionFieldLinkTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT);
	public final Column<DEDataDefinitionFieldLinkTable, Long> ddmStructureId =
		createColumn("ddmStructureId", Long.class, Types.BIGINT);
	public final Column<DEDataDefinitionFieldLinkTable, String> fieldName =
		createColumn("fieldName", String.class, Types.VARCHAR);

	private DEDataDefinitionFieldLinkTable() {
		super("DEDataDefinitionFieldLink", DEDataDefinitionFieldLinkTable::new);
	}

}