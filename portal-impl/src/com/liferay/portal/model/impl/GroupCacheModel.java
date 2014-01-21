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
import com.liferay.portal.model.Group;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Group in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Group
 * @generated
 */
public class GroupCacheModel implements CacheModel<Group>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(41);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", creatorUserId=");
		sb.append(creatorUserId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", parentGroupId=");
		sb.append(parentGroupId);
		sb.append(", liveGroupId=");
		sb.append(liveGroupId);
		sb.append(", treePath=");
		sb.append(treePath);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", manualMembership=");
		sb.append(manualMembership);
		sb.append(", membershipRestriction=");
		sb.append(membershipRestriction);
		sb.append(", friendlyURL=");
		sb.append(friendlyURL);
		sb.append(", site=");
		sb.append(site);
		sb.append(", remoteStagingGroupCount=");
		sb.append(remoteStagingGroupCount);
		sb.append(", active=");
		sb.append(active);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Group toEntityModel() {
		GroupImpl groupImpl = new GroupImpl();

		if (uuid == null) {
			groupImpl.setUuid(StringPool.BLANK);
		}
		else {
			groupImpl.setUuid(uuid);
		}

		groupImpl.setGroupId(groupId);
		groupImpl.setCompanyId(companyId);
		groupImpl.setCreatorUserId(creatorUserId);
		groupImpl.setClassNameId(classNameId);
		groupImpl.setClassPK(classPK);
		groupImpl.setParentGroupId(parentGroupId);
		groupImpl.setLiveGroupId(liveGroupId);

		if (treePath == null) {
			groupImpl.setTreePath(StringPool.BLANK);
		}
		else {
			groupImpl.setTreePath(treePath);
		}

		if (name == null) {
			groupImpl.setName(StringPool.BLANK);
		}
		else {
			groupImpl.setName(name);
		}

		if (description == null) {
			groupImpl.setDescription(StringPool.BLANK);
		}
		else {
			groupImpl.setDescription(description);
		}

		groupImpl.setType(type);

		if (typeSettings == null) {
			groupImpl.setTypeSettings(StringPool.BLANK);
		}
		else {
			groupImpl.setTypeSettings(typeSettings);
		}

		groupImpl.setManualMembership(manualMembership);
		groupImpl.setMembershipRestriction(membershipRestriction);

		if (friendlyURL == null) {
			groupImpl.setFriendlyURL(StringPool.BLANK);
		}
		else {
			groupImpl.setFriendlyURL(friendlyURL);
		}

		groupImpl.setSite(site);
		groupImpl.setRemoteStagingGroupCount(remoteStagingGroupCount);
		groupImpl.setActive(active);
		groupImpl.setMvccVersion(mvccVersion);

		groupImpl.resetOriginalValues();

		return groupImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		creatorUserId = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		parentGroupId = objectInput.readLong();
		liveGroupId = objectInput.readLong();
		treePath = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		type = objectInput.readInt();
		typeSettings = objectInput.readUTF();
		manualMembership = objectInput.readBoolean();
		membershipRestriction = objectInput.readInt();
		friendlyURL = objectInput.readUTF();
		site = objectInput.readBoolean();
		remoteStagingGroupCount = objectInput.readInt();
		active = objectInput.readBoolean();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(creatorUserId);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
		objectOutput.writeLong(parentGroupId);
		objectOutput.writeLong(liveGroupId);

		if (treePath == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(treePath);
		}

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

		objectOutput.writeInt(type);

		if (typeSettings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeBoolean(manualMembership);
		objectOutput.writeInt(membershipRestriction);

		if (friendlyURL == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(friendlyURL);
		}

		objectOutput.writeBoolean(site);
		objectOutput.writeInt(remoteStagingGroupCount);
		objectOutput.writeBoolean(active);
		objectOutput.writeLong(mvccVersion);
	}

	public String uuid;
	public long groupId;
	public long companyId;
	public long creatorUserId;
	public long classNameId;
	public long classPK;
	public long parentGroupId;
	public long liveGroupId;
	public String treePath;
	public String name;
	public String description;
	public int type;
	public String typeSettings;
	public boolean manualMembership;
	public int membershipRestriction;
	public String friendlyURL;
	public boolean site;
	public int remoteStagingGroupCount;
	public boolean active;
	public long mvccVersion;
}