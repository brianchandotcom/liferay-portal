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

import java.util.Date;

/**
 * The table class for the &quot;Image&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Image
 * @generated
 */
public class ImageTable extends BaseTable<ImageTable> {

	public static final ImageTable INSTANCE = new ImageTable();

	public final Column<ImageTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ImageTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ImageTable, Long> imageId = createColumn(
		"imageId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ImageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ImageTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ImageTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ImageTable, Integer> height = createColumn(
		"height", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ImageTable, Integer> width = createColumn(
		"width", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ImageTable, Integer> size = createColumn(
		"size_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ImageTable() {
		super("Image", ImageTable::new);
	}

}