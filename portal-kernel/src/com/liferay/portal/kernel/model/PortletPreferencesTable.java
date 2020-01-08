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

	public final Column<PortletPreferencesTable, Long> mvccVersion;
	public final Column<PortletPreferencesTable, Long> ctCollectionId;
	public final Column<PortletPreferencesTable, Long> portletPreferencesId;
	public final Column<PortletPreferencesTable, Long> companyId;
	public final Column<PortletPreferencesTable, Long> ownerId;
	public final Column<PortletPreferencesTable, Integer> ownerType;
	public final Column<PortletPreferencesTable, Long> plid;
	public final Column<PortletPreferencesTable, String> portletId;
	public final Column<PortletPreferencesTable, String> preferences;

	private PortletPreferencesTable() {
		super("PortletPreferences", PortletPreferencesTable::new);

		mvccVersion = new Column<>(
			this, "mvccVersion", Long.class, Types.BIGINT);

		ctCollectionId = new Column<>(
			this, "ctCollectionId", Long.class, Types.BIGINT);

		portletPreferencesId = new Column<>(
			this, "portletPreferencesId", Long.class, Types.BIGINT);

		companyId = new Column<>(this, "companyId", Long.class, Types.BIGINT);

		ownerId = new Column<>(this, "ownerId", Long.class, Types.BIGINT);

		ownerType = new Column<>(
			this, "ownerType", Integer.class, Types.INTEGER);

		plid = new Column<>(this, "plid", Long.class, Types.BIGINT);

		portletId = new Column<>(
			this, "portletId", String.class, Types.VARCHAR);

		preferences = new Column<>(
			this, "preferences", String.class, Types.CLOB);

		setColumns(
			mvccVersion, ctCollectionId, portletPreferencesId, companyId,
			ownerId, ownerType, plid, portletId, preferences);
	}

}