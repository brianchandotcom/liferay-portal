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

package com.liferay.asset.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the AssetVocabulary.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetVocabularyTable extends Table<AssetVocabularyTable> {

	public static final AssetVocabularyTable INSTANCE =
		new AssetVocabularyTable();

	public final Column<AssetVocabularyTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<AssetVocabularyTable, Long> ctCollectionId =
		createColumn("ctCollectionId", Long.class, Types.BIGINT);
	public final Column<AssetVocabularyTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<AssetVocabularyTable, String> externalReferenceCode =
		createColumn("externalReferenceCode", String.class, Types.VARCHAR);
	public final Column<AssetVocabularyTable, Long> vocabularyId = createColumn(
		"vocabularyId", Long.class, Types.BIGINT);
	public final Column<AssetVocabularyTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<AssetVocabularyTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<AssetVocabularyTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<AssetVocabularyTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<AssetVocabularyTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetVocabularyTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetVocabularyTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<AssetVocabularyTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR);
	public final Column<AssetVocabularyTable, String> description =
		createColumn("description", String.class, Types.VARCHAR);
	public final Column<AssetVocabularyTable, String> settings = createColumn(
		"settings_", String.class, Types.VARCHAR);
	public final Column<AssetVocabularyTable, Date> lastPublishDate =
		createColumn("lastPublishDate", Date.class, Types.TIMESTAMP);

	private AssetVocabularyTable() {
		super("AssetVocabulary", AssetVocabularyTable::new);
	}

}