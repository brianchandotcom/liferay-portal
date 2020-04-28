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

import java.sql.Types;

/**
 * The table class for the &quot;VirtualHost&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHost
 * @generated
 */
public class VirtualHostTable extends BaseTable<VirtualHostTable> {

	public static final VirtualHostTable INSTANCE = new VirtualHostTable();

	public final Column<VirtualHostTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<VirtualHostTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<VirtualHostTable, Long> virtualHostId = createColumn(
		"virtualHostId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<VirtualHostTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<VirtualHostTable, Long> layoutSetId = createColumn(
		"layoutSetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<VirtualHostTable, String> hostname = createColumn(
		"hostname", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<VirtualHostTable, Boolean> defaultVirtualHost =
		createColumn(
			"defaultVirtualHost", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<VirtualHostTable, String> languageId = createColumn(
		"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private VirtualHostTable() {
		super("VirtualHost", VirtualHostTable::new);
	}

}