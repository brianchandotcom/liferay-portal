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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Group in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class GroupCacheModel
	implements CacheModel<Group>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof GroupCacheModel)) {
			return false;
		}

		GroupCacheModel groupCacheModel = (GroupCacheModel)obj;

		if ((groupId == groupCacheModel.groupId) &&
			(mvccVersion == groupCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, groupId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(45);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
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
		sb.append(", groupKey=");
		sb.append(groupKey);
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
		sb.append(", inheritContent=");
		sb.append(inheritContent);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Group toEntityModel() {
		GroupImpl groupImpl = new GroupImpl();

		groupImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			groupImpl.setUuid("");
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
			groupImpl.setTreePath("");
		}
		else {
			groupImpl.setTreePath(treePath);
		}

		if (groupKey == null) {
			groupImpl.setGroupKey("");
		}
		else {
			groupImpl.setGroupKey(groupKey);
		}

		if (name == null) {
			groupImpl.setName("");
		}
		else {
			groupImpl.setName(name);
		}

		if (description == null) {
			groupImpl.setDescription("");
		}
		else {
			groupImpl.setDescription(description);
		}

		groupImpl.setType(type);

		if (typeSettings == null) {
			groupImpl.setTypeSettings("");
		}
		else {
			groupImpl.setTypeSettings(typeSettings);
		}

		groupImpl.setManualMembership(manualMembership);
		groupImpl.setMembershipRestriction(membershipRestriction);

		if (friendlyURL == null) {
			groupImpl.setFriendlyURL("");
		}
		else {
			groupImpl.setFriendlyURL(friendlyURL);
		}

		groupImpl.setSite(site);
		groupImpl.setRemoteStagingGroupCount(remoteStagingGroupCount);
		groupImpl.setInheritContent(inheritContent);
		groupImpl.setActive(active);

		groupImpl.resetOriginalValues();

		return groupImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		creatorUserId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		parentGroupId = objectInput.readLong();

		liveGroupId = objectInput.readLong();
		treePath = objectInput.readUTF();
		groupKey = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		type = objectInput.readInt();
		typeSettings = objectInput.readUTF();

		manualMembership = objectInput.readBoolean();

		membershipRestriction = objectInput.readInt();
		friendlyURL = objectInput.readUTF();

		site = objectInput.readBoolean();

		remoteStagingGroupCount = objectInput.readInt();

		inheritContent = objectInput.readBoolean();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
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
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(treePath);
		}

		if (groupKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(groupKey);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(type);

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeBoolean(manualMembership);

		objectOutput.writeInt(membershipRestriction);

		if (friendlyURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(friendlyURL);
		}

		objectOutput.writeBoolean(site);

		objectOutput.writeInt(remoteStagingGroupCount);

		objectOutput.writeBoolean(inheritContent);

		objectOutput.writeBoolean(active);
	}

	public long mvccVersion;
	public String uuid;
	public long groupId;
	public long companyId;
	public long creatorUserId;
	public long classNameId;
	public long classPK;
	public long parentGroupId;
	public long liveGroupId;
	public String treePath;
	public String groupKey;
	public String name;
	public String description;
	public int type;
	public String typeSettings;
	public boolean manualMembership;
	public int membershipRestriction;
	public String friendlyURL;
	public boolean site;
	public int remoteStagingGroupCount;
	public boolean inheritContent;
	public boolean active;

}