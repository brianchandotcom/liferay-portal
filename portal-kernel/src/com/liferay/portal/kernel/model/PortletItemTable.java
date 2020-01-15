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

import java.util.Date;

/**
 * The table class for the PortletItem.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortletItemTable extends Table<PortletItemTable> {

	public static final PortletItemTable INSTANCE = new PortletItemTable();

	public final Column<PortletItemTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<PortletItemTable, Long> portletItemId = createColumn(
		"portletItemId", Long.class, Types.BIGINT);
	public final Column<PortletItemTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<PortletItemTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<PortletItemTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<PortletItemTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<PortletItemTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<PortletItemTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<PortletItemTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<PortletItemTable, String> portletId = createColumn(
		"portletId", String.class, Types.VARCHAR);
	public final Column<PortletItemTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);

	private PortletItemTable() {
		super("PortletItem", PortletItemTable::new);
	}

}