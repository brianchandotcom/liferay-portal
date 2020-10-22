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
 * The table class for the &quot;PortletPreferenceValue&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferenceValue
 * @generated
 */
public class PortletPreferenceValueTable
	extends BaseTable<PortletPreferenceValueTable> {

	public static final PortletPreferenceValueTable INSTANCE =
		new PortletPreferenceValueTable();

	public final Column<PortletPreferenceValueTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PortletPreferenceValueTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PortletPreferenceValueTable, Long>
		portletPreferenceValueId = createColumn(
			"portletPreferenceValueId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<PortletPreferenceValueTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PortletPreferenceValueTable, Long>
		portletPreferencesId = createColumn(
			"portletPreferencesId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<PortletPreferenceValueTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PortletPreferenceValueTable, Integer> index =
		createColumn(
			"index_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PortletPreferenceValueTable, String> smallValue =
		createColumn(
			"smallValue", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PortletPreferenceValueTable, String> largeValue =
		createColumn(
			"largeValue", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PortletPreferenceValueTable, Boolean> readOnly =
		createColumn(
			"readOnly", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private PortletPreferenceValueTable() {
		super("PortletPreferenceValue", PortletPreferenceValueTable::new);
	}

}