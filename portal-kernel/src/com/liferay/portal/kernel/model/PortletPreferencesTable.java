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

import com.liferay.portal.kernel.dao.model.Column;
import com.liferay.portal.kernel.dao.model.Table;

import java.sql.Types;

/**
 * The table class for the PortletPreferences.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortletPreferencesTable extends Table<PortletPreferencesTable> {

	public static final PortletPreferencesTable INSTANCE =
		new PortletPreferencesTable();

	public final Column<PortletPreferencesTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<PortletPreferencesTable, Long> ctCollectionId =
		createColumn("ctCollectionId", Long.class, Types.BIGINT);
	public final Column<PortletPreferencesTable, Long> portletPreferencesId =
		createColumn("portletPreferencesId", Long.class, Types.BIGINT);
	public final Column<PortletPreferencesTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<PortletPreferencesTable, Long> ownerId = createColumn(
		"ownerId", Long.class, Types.BIGINT);
	public final Column<PortletPreferencesTable, Integer> ownerType =
		createColumn("ownerType", Integer.class, Types.INTEGER);
	public final Column<PortletPreferencesTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT);
	public final Column<PortletPreferencesTable, String> portletId =
		createColumn("portletId", String.class, Types.VARCHAR);
	public final Column<PortletPreferencesTable, String> preferences =
		createColumn("preferences", String.class, Types.CLOB);

	private PortletPreferencesTable() {
		super("PortletPreferences", PortletPreferencesTable::new);
	}

}