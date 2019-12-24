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

	private LayoutTable() {
		super("Layout", LayoutTable::new);

		mvccVersion = Column.create(
			this, "mvccVersion", Long.class, Types.BIGINT);

		ctCollectionId = Column.create(
			this, "ctCollectionId", Long.class, Types.BIGINT);

		uuid = Column.create(this, "uuid_", String.class, Types.VARCHAR);

		plid = Column.create(this, "plid", Long.class, Types.BIGINT);

		groupId = Column.create(this, "groupId", Long.class, Types.BIGINT);

		companyId = Column.create(this, "companyId", Long.class, Types.BIGINT);

		userId = Column.create(this, "userId", Long.class, Types.BIGINT);

		userName = Column.create(this, "userName", String.class, Types.VARCHAR);

		createDate = Column.create(
			this, "createDate", Date.class, Types.TIMESTAMP);

		modifiedDate = Column.create(
			this, "modifiedDate", Date.class, Types.TIMESTAMP);

		parentPlid = Column.create(
			this, "parentPlid", Long.class, Types.BIGINT);

		privateLayout = Column.create(
			this, "privateLayout", Boolean.class, Types.BOOLEAN);

		layoutId = Column.create(this, "layoutId", Long.class, Types.BIGINT);

		parentLayoutId = Column.create(
			this, "parentLayoutId", Long.class, Types.BIGINT);

		classNameId = Column.create(
			this, "classNameId", Long.class, Types.BIGINT);

		classPK = Column.create(this, "classPK", Long.class, Types.BIGINT);

		name = Column.create(this, "name", String.class, Types.VARCHAR);

		title = Column.create(this, "title", String.class, Types.VARCHAR);

		description = Column.create(
			this, "description", String.class, Types.VARCHAR);

		keywords = Column.create(this, "keywords", String.class, Types.VARCHAR);

		robots = Column.create(this, "robots", String.class, Types.VARCHAR);

		type = Column.create(this, "type_", String.class, Types.VARCHAR);

		typeSettings = Column.create(
			this, "typeSettings", String.class, Types.CLOB);

		hidden = Column.create(this, "hidden_", Boolean.class, Types.BOOLEAN);

		system = Column.create(this, "system_", Boolean.class, Types.BOOLEAN);

		friendlyURL = Column.create(
			this, "friendlyURL", String.class, Types.VARCHAR);

		iconImageId = Column.create(
			this, "iconImageId", Long.class, Types.BIGINT);

		themeId = Column.create(this, "themeId", String.class, Types.VARCHAR);

		colorSchemeId = Column.create(
			this, "colorSchemeId", String.class, Types.VARCHAR);

		css = Column.create(this, "css", String.class, Types.CLOB);

		priority = Column.create(
			this, "priority", Integer.class, Types.INTEGER);

		masterLayoutPlid = Column.create(
			this, "masterLayoutPlid", Long.class, Types.BIGINT);

		layoutPrototypeUuid = Column.create(
			this, "layoutPrototypeUuid", String.class, Types.VARCHAR);

		layoutPrototypeLinkEnabled = Column.create(
			this, "layoutPrototypeLinkEnabled", Boolean.class, Types.BOOLEAN);

		sourcePrototypeLayoutUuid = Column.create(
			this, "sourcePrototypeLayoutUuid", String.class, Types.VARCHAR);

		publishDate = Column.create(
			this, "publishDate", Date.class, Types.TIMESTAMP);

		lastPublishDate = Column.create(
			this, "lastPublishDate", Date.class, Types.TIMESTAMP);

		setColumns(
			mvccVersion, ctCollectionId, uuid, plid, groupId, companyId, userId,
			userName, createDate, modifiedDate, parentPlid, privateLayout,
			layoutId, parentLayoutId, classNameId, classPK, name, title,
			description, keywords, robots, type, typeSettings, hidden, system,
			friendlyURL, iconImageId, themeId, colorSchemeId, css, priority,
			masterLayoutPlid, layoutPrototypeUuid, layoutPrototypeLinkEnabled,
			sourcePrototypeLayoutUuid, publishDate, lastPublishDate);
	}

}