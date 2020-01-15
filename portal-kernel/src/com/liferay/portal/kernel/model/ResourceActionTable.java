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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the ResourceAction.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ResourceActionTable extends Table<ResourceActionTable> {

	public static final ResourceActionTable INSTANCE =
		new ResourceActionTable();

	public final Column<ResourceActionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<ResourceActionTable, Long> resourceActionId =
		createColumn("resourceActionId", Long.class, Types.BIGINT);
	public final Column<ResourceActionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<ResourceActionTable, String> actionId = createColumn(
		"actionId", String.class, Types.VARCHAR);
	public final Column<ResourceActionTable, Long> bitwiseValue = createColumn(
		"bitwiseValue", Long.class, Types.BIGINT);

	private ResourceActionTable() {
		super("ResourceAction", ResourceActionTable::new);
	}

}