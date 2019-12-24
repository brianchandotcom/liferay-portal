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

		mvccVersion = Column.create(
			this, "mvccVersion", Long.class, Types.BIGINT);

		uuid = Column.create(this, "uuid_", String.class, Types.VARCHAR);

		groupId = Column.create(this, "groupId", Long.class, Types.BIGINT);

		companyId = Column.create(this, "companyId", Long.class, Types.BIGINT);

		creatorUserId = Column.create(
			this, "creatorUserId", Long.class, Types.BIGINT);

		classNameId = Column.create(
			this, "classNameId", Long.class, Types.BIGINT);

		classPK = Column.create(this, "classPK", Long.class, Types.BIGINT);

		parentGroupId = Column.create(
			this, "parentGroupId", Long.class, Types.BIGINT);

		liveGroupId = Column.create(
			this, "liveGroupId", Long.class, Types.BIGINT);

		treePath = Column.create(this, "treePath", String.class, Types.VARCHAR);

		groupKey = Column.create(this, "groupKey", String.class, Types.VARCHAR);

		name = Column.create(this, "name", String.class, Types.VARCHAR);

		description = Column.create(
			this, "description", String.class, Types.VARCHAR);

		type = Column.create(this, "type_", Integer.class, Types.INTEGER);

		typeSettings = Column.create(
			this, "typeSettings", String.class, Types.CLOB);

		manualMembership = Column.create(
			this, "manualMembership", Boolean.class, Types.BOOLEAN);

		membershipRestriction = Column.create(
			this, "membershipRestriction", Integer.class, Types.INTEGER);

		friendlyURL = Column.create(
			this, "friendlyURL", String.class, Types.VARCHAR);

		site = Column.create(this, "site", Boolean.class, Types.BOOLEAN);

		remoteStagingGroupCount = Column.create(
			this, "remoteStagingGroupCount", Integer.class, Types.INTEGER);

		inheritContent = Column.create(
			this, "inheritContent", Boolean.class, Types.BOOLEAN);

		active = Column.create(this, "active_", Boolean.class, Types.BOOLEAN);

		setColumns(
			mvccVersion, uuid, groupId, companyId, creatorUserId, classNameId,
			classPK, parentGroupId, liveGroupId, treePath, groupKey, name,
			description, type, typeSettings, manualMembership,
			membershipRestriction, friendlyURL, site, remoteStagingGroupCount,
			inheritContent, active);
	}

}