/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;SocialActivitySet&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySet
 * @generated
 */
public class SocialActivitySetTable extends BaseTable<SocialActivitySetTable> {

	public static final SocialActivitySetTable INSTANCE =
		new SocialActivitySetTable();

	public final Column<SocialActivitySetTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SocialActivitySetTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivitySetTable, Long> activitySetId =
		createColumn(
			"activitySetId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivitySetTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySetTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySetTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySetTable, Long> createDate = createColumn(
		"createDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySetTable, Long> modifiedDate =
		createColumn(
			"modifiedDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySetTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySetTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySetTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySetTable, String> extraData =
		createColumn(
			"extraData", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySetTable, Integer> activityCount =
		createColumn(
			"activityCount", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private SocialActivitySetTable() {
		super("SocialActivitySet", SocialActivitySetTable::new);
	}

}