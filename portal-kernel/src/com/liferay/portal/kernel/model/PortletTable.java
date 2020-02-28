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
 * The table class for the &quot;Portlet&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Portlet
 * @generated
 */
public class PortletTable extends BaseTable<PortletTable> {

	public static final PortletTable INSTANCE = new PortletTable();

	public final Column<PortletTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PortletTable, Long> id = createColumn(
		"id_", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PortletTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PortletTable, String> portletId = createColumn(
		"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PortletTable, String> roles = createColumn(
		"roles", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PortletTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private PortletTable() {
		super("Portlet", PortletTable::new);
	}

}