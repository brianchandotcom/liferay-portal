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

package com.liferay.site.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the SiteFriendlyURL.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SiteFriendlyURLTable extends Table<SiteFriendlyURLTable> {

	public static final SiteFriendlyURLTable INSTANCE =
		new SiteFriendlyURLTable();

	public final Column<SiteFriendlyURLTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<SiteFriendlyURLTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<SiteFriendlyURLTable, Long> siteFriendlyURLId =
		createColumn("siteFriendlyURLId", Long.class, Types.BIGINT);
	public final Column<SiteFriendlyURLTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SiteFriendlyURLTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SiteFriendlyURLTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<SiteFriendlyURLTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<SiteFriendlyURLTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<SiteFriendlyURLTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<SiteFriendlyURLTable, String> friendlyURL =
		createColumn("friendlyURL", String.class, Types.VARCHAR);
	public final Column<SiteFriendlyURLTable, String> languageId = createColumn(
		"languageId", String.class, Types.VARCHAR);
	public final Column<SiteFriendlyURLTable, Date> lastPublishDate =
		createColumn("lastPublishDate", Date.class, Types.TIMESTAMP);

	private SiteFriendlyURLTable() {
		super("SiteFriendlyURL", SiteFriendlyURLTable::new);
	}

}