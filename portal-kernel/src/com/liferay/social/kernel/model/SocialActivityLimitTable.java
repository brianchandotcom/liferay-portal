/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;SocialActivityLimit&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimit
 * @generated
 */
public class SocialActivityLimitTable
	extends BaseTable<SocialActivityLimitTable> {

	public static final SocialActivityLimitTable INSTANCE =
		new SocialActivityLimitTable();

	public final Column<SocialActivityLimitTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SocialActivityLimitTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivityLimitTable, Long> activityLimitId =
		createColumn(
			"activityLimitId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivityLimitTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, Integer> activityType =
		createColumn(
			"activityType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, String> activityCounterName =
		createColumn(
			"activityCounterName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, String> value = createColumn(
		"value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private SocialActivityLimitTable() {
		super("SocialActivityLimit", SocialActivityLimitTable::new);
	}

}