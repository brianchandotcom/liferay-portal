/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SiteFriendlyURL&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURL
 * @generated
 */
public class SiteFriendlyURLTable extends BaseTable<SiteFriendlyURLTable> {

	public static final SiteFriendlyURLTable INSTANCE =
		new SiteFriendlyURLTable();

	public final Column<SiteFriendlyURLTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SiteFriendlyURLTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SiteFriendlyURLTable, Long> siteFriendlyURLId =
		createColumn(
			"siteFriendlyURLId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SiteFriendlyURLTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SiteFriendlyURLTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SiteFriendlyURLTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SiteFriendlyURLTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SiteFriendlyURLTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SiteFriendlyURLTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SiteFriendlyURLTable, String> friendlyURL =
		createColumn(
			"friendlyURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SiteFriendlyURLTable, String> languageId = createColumn(
		"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SiteFriendlyURLTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private SiteFriendlyURLTable() {
		super("SiteFriendlyURL", SiteFriendlyURLTable::new);
	}

}