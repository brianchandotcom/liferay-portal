/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;FragmentComposition&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentComposition
 * @generated
 */
public class FragmentCompositionTable
	extends BaseTable<FragmentCompositionTable> {

	public static final FragmentCompositionTable INSTANCE =
		new FragmentCompositionTable();

	public final Column<FragmentCompositionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FragmentCompositionTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FragmentCompositionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Long> fragmentCompositionId =
		createColumn(
			"fragmentCompositionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FragmentCompositionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Long> fragmentCollectionId =
		createColumn(
			"fragmentCollectionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, String>
		fragmentCompositionKey = createColumn(
			"fragmentCompositionKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Clob> data = createColumn(
		"data_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Long> previewFileEntryId =
		createColumn(
			"previewFileEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<FragmentCompositionTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private FragmentCompositionTable() {
		super("FragmentComposition", FragmentCompositionTable::new);
	}

}