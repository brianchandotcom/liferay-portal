/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Team;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Team in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Team
 * @generated
 */
public class TeamCacheModel implements CacheModel<Team>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{teamId=");
		sb.append(teamId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Team toEntityModel() {
		TeamImpl teamImpl = new TeamImpl();

		teamImpl.setTeamId(teamId);
		teamImpl.setCompanyId(companyId);
		teamImpl.setUserId(userId);

		if (userName == null) {
			teamImpl.setUserName(StringPool.BLANK);
		}
		else {
			teamImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			teamImpl.setCreateDate(null);
		}
		else {
			teamImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			teamImpl.setModifiedDate(null);
		}
		else {
			teamImpl.setModifiedDate(new Date(modifiedDate));
		}

		teamImpl.setGroupId(groupId);

		if (name == null) {
			teamImpl.setName(StringPool.BLANK);
		}
		else {
			teamImpl.setName(name);
		}

		if (description == null) {
			teamImpl.setDescription(StringPool.BLANK);
		}
		else {
			teamImpl.setDescription(description);
		}

		teamImpl.setMvccVersion(mvccVersion);

		teamImpl.resetOriginalValues();

		return teamImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		teamId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		groupId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(teamId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(groupId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(mvccVersion);
	}

	public long teamId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long groupId;
	public String name;
	public String description;
	public long mvccVersion;
}