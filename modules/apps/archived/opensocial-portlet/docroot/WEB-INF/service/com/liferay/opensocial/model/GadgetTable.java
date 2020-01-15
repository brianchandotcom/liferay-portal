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

package com.liferay.opensocial.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the Gadget.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class GadgetTable extends Table<GadgetTable> {

	public static final GadgetTable INSTANCE = new GadgetTable();

	public final Column<GadgetTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<GadgetTable, Long> gadgetId = createColumn(
		"gadgetId", Long.class, Types.BIGINT);
	public final Column<GadgetTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<GadgetTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<GadgetTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<GadgetTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<GadgetTable, String> url = createColumn(
		"url", String.class, Types.VARCHAR);
	public final Column<GadgetTable, String> portletCategoryNames =
		createColumn("portletCategoryNames", String.class, Types.VARCHAR);
	public final Column<GadgetTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);

	private GadgetTable() {
		super("OpenSocial_Gadget", GadgetTable::new);
	}

}