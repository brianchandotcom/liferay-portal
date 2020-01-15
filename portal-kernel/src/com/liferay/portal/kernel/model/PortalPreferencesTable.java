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

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the PortalPreferences.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortalPreferencesTable extends Table<PortalPreferencesTable> {

	public static final PortalPreferencesTable INSTANCE =
		new PortalPreferencesTable();

	public final Column<PortalPreferencesTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<PortalPreferencesTable, Long> portalPreferencesId =
		createColumn("portalPreferencesId", Long.class, Types.BIGINT);
	public final Column<PortalPreferencesTable, Long> ownerId = createColumn(
		"ownerId", Long.class, Types.BIGINT);
	public final Column<PortalPreferencesTable, Integer> ownerType =
		createColumn("ownerType", Integer.class, Types.INTEGER);
	public final Column<PortalPreferencesTable, Clob> preferences =
		createColumn("preferences", Clob.class, Types.CLOB);

	private PortalPreferencesTable() {
		super("PortalPreferences", PortalPreferencesTable::new);
	}

}