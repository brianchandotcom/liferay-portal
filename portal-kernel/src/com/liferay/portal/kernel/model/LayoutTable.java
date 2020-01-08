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

import com.liferay.portal.kernel.dao.model.Column;
import com.liferay.portal.kernel.dao.model.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the Layout.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutTable extends Table<LayoutTable> {

	public static final LayoutTable INSTANCE = new LayoutTable();

	public final Column<LayoutTable, Long> mvccVersion;
	public final Column<LayoutTable, Long> ctCollectionId;
	public final Column<LayoutTable, String> uuid;
	public final Column<LayoutTable, Long> plid;
	public final Column<LayoutTable, Long> groupId;
	public final Column<LayoutTable, Long> companyId;
	public final Column<LayoutTable, Long> userId;
	public final Column<LayoutTable, String> userName;
	public final Column<LayoutTable, Date> createDate;
	public final Column<LayoutTable, Date> modifiedDate;
	public final Column<LayoutTable, Long> parentPlid;
	public final Column<LayoutTable, Boolean> privateLayout;
	public final Column<LayoutTable, Long> layoutId;
	public final Column<LayoutTable, Long> parentLayoutId;
	public final Column<LayoutTable, Long> classNameId;
	public final Column<LayoutTable, Long> classPK;
	public final Column<LayoutTable, String> name;
	public final Column<LayoutTable, String> title;
	public final Column<LayoutTable, String> description;
	public final Column<LayoutTable, String> keywords;
	public final Column<LayoutTable, String> robots;
	public final Column<LayoutTable, String> type;
	public final Column<LayoutTable, String> typeSettings;
	public final Column<LayoutTable, Boolean> hidden;
	public final Column<LayoutTable, Boolean> system;
	public final Column<LayoutTable, String> friendlyURL;
	public final Column<LayoutTable, Long> iconImageId;
	public final Column<LayoutTable, String> themeId;
	public final Column<LayoutTable, String> colorSchemeId;
	public final Column<LayoutTable, String> css;
	public final Column<LayoutTable, Integer> priority;
	public final Column<LayoutTable, Long> masterLayoutPlid;
	public final Column<LayoutTable, String> layoutPrototypeUuid;
	public final Column<LayoutTable, Boolean> layoutPrototypeLinkEnabled;
	public final Column<LayoutTable, String> sourcePrototypeLayoutUuid;
	public final Column<LayoutTable, Date> publishDate;
	public final Column<LayoutTable, Date> lastPublishDate;
	public final Column<LayoutTable, Integer> status;
	public final Column<LayoutTable, Long> statusByUserId;
	public final Column<LayoutTable, String> statusByUserName;
	public final Column<LayoutTable, Date> statusDate;

	private LayoutTable() {
		super("Layout", LayoutTable::new);

		mvccVersion = new Column<>(
			this, "mvccVersion", Long.class, Types.BIGINT);

		ctCollectionId = new Column<>(
			this, "ctCollectionId", Long.class, Types.BIGINT);

		uuid = new Column<>(this, "uuid_", String.class, Types.VARCHAR);

		plid = new Column<>(this, "plid", Long.class, Types.BIGINT);

		groupId = new Column<>(this, "groupId", Long.class, Types.BIGINT);

		companyId = new Column<>(this, "companyId", Long.class, Types.BIGINT);

		userId = new Column<>(this, "userId", Long.class, Types.BIGINT);

		userName = new Column<>(this, "userName", String.class, Types.VARCHAR);

		createDate = new Column<>(
			this, "createDate", Date.class, Types.TIMESTAMP);

		modifiedDate = new Column<>(
			this, "modifiedDate", Date.class, Types.TIMESTAMP);

		parentPlid = new Column<>(this, "parentPlid", Long.class, Types.BIGINT);

		privateLayout = new Column<>(
			this, "privateLayout", Boolean.class, Types.BOOLEAN);

		layoutId = new Column<>(this, "layoutId", Long.class, Types.BIGINT);

		parentLayoutId = new Column<>(
			this, "parentLayoutId", Long.class, Types.BIGINT);

		classNameId = new Column<>(
			this, "classNameId", Long.class, Types.BIGINT);

		classPK = new Column<>(this, "classPK", Long.class, Types.BIGINT);

		name = new Column<>(this, "name", String.class, Types.VARCHAR);

		title = new Column<>(this, "title", String.class, Types.VARCHAR);

		description = new Column<>(
			this, "description", String.class, Types.VARCHAR);

		keywords = new Column<>(this, "keywords", String.class, Types.VARCHAR);

		robots = new Column<>(this, "robots", String.class, Types.VARCHAR);

		type = new Column<>(this, "type_", String.class, Types.VARCHAR);

		typeSettings = new Column<>(
			this, "typeSettings", String.class, Types.CLOB);

		hidden = new Column<>(this, "hidden_", Boolean.class, Types.BOOLEAN);

		system = new Column<>(this, "system_", Boolean.class, Types.BOOLEAN);

		friendlyURL = new Column<>(
			this, "friendlyURL", String.class, Types.VARCHAR);

		iconImageId = new Column<>(
			this, "iconImageId", Long.class, Types.BIGINT);

		themeId = new Column<>(this, "themeId", String.class, Types.VARCHAR);

		colorSchemeId = new Column<>(
			this, "colorSchemeId", String.class, Types.VARCHAR);

		css = new Column<>(this, "css", String.class, Types.CLOB);

		priority = new Column<>(this, "priority", Integer.class, Types.INTEGER);

		masterLayoutPlid = new Column<>(
			this, "masterLayoutPlid", Long.class, Types.BIGINT);

		layoutPrototypeUuid = new Column<>(
			this, "layoutPrototypeUuid", String.class, Types.VARCHAR);

		layoutPrototypeLinkEnabled = new Column<>(
			this, "layoutPrototypeLinkEnabled", Boolean.class, Types.BOOLEAN);

		sourcePrototypeLayoutUuid = new Column<>(
			this, "sourcePrototypeLayoutUuid", String.class, Types.VARCHAR);

		publishDate = new Column<>(
			this, "publishDate", Date.class, Types.TIMESTAMP);

		lastPublishDate = new Column<>(
			this, "lastPublishDate", Date.class, Types.TIMESTAMP);

		status = new Column<>(this, "status", Integer.class, Types.INTEGER);

		statusByUserId = new Column<>(
			this, "statusByUserId", Long.class, Types.BIGINT);

		statusByUserName = new Column<>(
			this, "statusByUserName", String.class, Types.VARCHAR);

		statusDate = new Column<>(
			this, "statusDate", Date.class, Types.TIMESTAMP);

		setColumns(
			mvccVersion, ctCollectionId, uuid, plid, groupId, companyId, userId,
			userName, createDate, modifiedDate, parentPlid, privateLayout,
			layoutId, parentLayoutId, classNameId, classPK, name, title,
			description, keywords, robots, type, typeSettings, hidden, system,
			friendlyURL, iconImageId, themeId, colorSchemeId, css, priority,
			masterLayoutPlid, layoutPrototypeUuid, layoutPrototypeLinkEnabled,
			sourcePrototypeLayoutUuid, publishDate, lastPublishDate, status,
			statusByUserId, statusByUserName, statusDate);
	}

}