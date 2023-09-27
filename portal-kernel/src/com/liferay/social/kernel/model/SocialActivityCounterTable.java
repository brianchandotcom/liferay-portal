/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;SocialActivityCounter&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityCounter
 * @generated
 */
public class SocialActivityCounterTable
	extends BaseTable<SocialActivityCounterTable> {

	public static final SocialActivityCounterTable INSTANCE =
		new SocialActivityCounterTable();

	public final Column<SocialActivityCounterTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SocialActivityCounterTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivityCounterTable, Long> activityCounterId =
		createColumn(
			"activityCounterId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivityCounterTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> ownerType =
		createColumn(
			"ownerType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> currentValue =
		createColumn(
			"currentValue", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> totalValue =
		createColumn(
			"totalValue", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> graceValue =
		createColumn(
			"graceValue", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> startPeriod =
		createColumn(
			"startPeriod", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> endPeriod =
		createColumn(
			"endPeriod", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Boolean> active =
		createColumn(
			"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private SocialActivityCounterTable() {
		super("SocialActivityCounter", SocialActivityCounterTable::new);
	}

}