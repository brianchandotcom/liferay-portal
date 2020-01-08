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

/**
 * The table class for the Group.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class GroupTable extends Table<GroupTable> {

	public static final GroupTable INSTANCE = new GroupTable();

	public final Column<GroupTable, Long> mvccVersion;
	public final Column<GroupTable, String> uuid;
	public final Column<GroupTable, Long> groupId;
	public final Column<GroupTable, Long> companyId;
	public final Column<GroupTable, Long> creatorUserId;
	public final Column<GroupTable, Long> classNameId;
	public final Column<GroupTable, Long> classPK;
	public final Column<GroupTable, Long> parentGroupId;
	public final Column<GroupTable, Long> liveGroupId;
	public final Column<GroupTable, String> treePath;
	public final Column<GroupTable, String> groupKey;
	public final Column<GroupTable, String> name;
	public final Column<GroupTable, String> description;
	public final Column<GroupTable, Integer> type;
	public final Column<GroupTable, String> typeSettings;
	public final Column<GroupTable, Boolean> manualMembership;
	public final Column<GroupTable, Integer> membershipRestriction;
	public final Column<GroupTable, String> friendlyURL;
	public final Column<GroupTable, Boolean> site;
	public final Column<GroupTable, Integer> remoteStagingGroupCount;
	public final Column<GroupTable, Boolean> inheritContent;
	public final Column<GroupTable, Boolean> active;

	private GroupTable() {
		super("Group_", GroupTable::new);

		mvccVersion = new Column<>(
			this, "mvccVersion", Long.class, Types.BIGINT);

		uuid = new Column<>(this, "uuid_", String.class, Types.VARCHAR);

		groupId = new Column<>(this, "groupId", Long.class, Types.BIGINT);

		companyId = new Column<>(this, "companyId", Long.class, Types.BIGINT);

		creatorUserId = new Column<>(
			this, "creatorUserId", Long.class, Types.BIGINT);

		classNameId = new Column<>(
			this, "classNameId", Long.class, Types.BIGINT);

		classPK = new Column<>(this, "classPK", Long.class, Types.BIGINT);

		parentGroupId = new Column<>(
			this, "parentGroupId", Long.class, Types.BIGINT);

		liveGroupId = new Column<>(
			this, "liveGroupId", Long.class, Types.BIGINT);

		treePath = new Column<>(this, "treePath", String.class, Types.VARCHAR);

		groupKey = new Column<>(this, "groupKey", String.class, Types.VARCHAR);

		name = new Column<>(this, "name", String.class, Types.VARCHAR);

		description = new Column<>(
			this, "description", String.class, Types.VARCHAR);

		type = new Column<>(this, "type_", Integer.class, Types.INTEGER);

		typeSettings = new Column<>(
			this, "typeSettings", String.class, Types.CLOB);

		manualMembership = new Column<>(
			this, "manualMembership", Boolean.class, Types.BOOLEAN);

		membershipRestriction = new Column<>(
			this, "membershipRestriction", Integer.class, Types.INTEGER);

		friendlyURL = new Column<>(
			this, "friendlyURL", String.class, Types.VARCHAR);

		site = new Column<>(this, "site", Boolean.class, Types.BOOLEAN);

		remoteStagingGroupCount = new Column<>(
			this, "remoteStagingGroupCount", Integer.class, Types.INTEGER);

		inheritContent = new Column<>(
			this, "inheritContent", Boolean.class, Types.BOOLEAN);

		active = new Column<>(this, "active_", Boolean.class, Types.BOOLEAN);

		setColumns(
			mvccVersion, uuid, groupId, companyId, creatorUserId, classNameId,
			classPK, parentGroupId, liveGroupId, treePath, groupKey, name,
			description, type, typeSettings, manualMembership,
			membershipRestriction, friendlyURL, site, remoteStagingGroupCount,
			inheritContent, active);
	}

}