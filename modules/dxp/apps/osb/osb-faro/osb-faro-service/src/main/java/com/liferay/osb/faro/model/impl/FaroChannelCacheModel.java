/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model.impl;

import com.liferay.osb.faro.model.FaroChannel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FaroChannel in entity cache.
 *
 * @author Matthew Kong
 * @generated
 */
public class FaroChannelCacheModel
	implements CacheModel<FaroChannel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroChannelCacheModel)) {
			return false;
		}

		FaroChannelCacheModel faroChannelCacheModel =
			(FaroChannelCacheModel)object;

		if ((faroChannelId == faroChannelCacheModel.faroChannelId) &&
			(mvccVersion == faroChannelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, faroChannelId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", faroChannelId=");
		sb.append(faroChannelId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createTime=");
		sb.append(createTime);
		sb.append(", modifiedTime=");
		sb.append(modifiedTime);
		sb.append(", channelId=");
		sb.append(channelId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", permissionType=");
		sb.append(permissionType);
		sb.append(", workspaceGroupId=");
		sb.append(workspaceGroupId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FaroChannel toEntityModel() {
		FaroChannelImpl faroChannelImpl = new FaroChannelImpl();

		faroChannelImpl.setMvccVersion(mvccVersion);
		faroChannelImpl.setFaroChannelId(faroChannelId);
		faroChannelImpl.setGroupId(groupId);
		faroChannelImpl.setCompanyId(companyId);
		faroChannelImpl.setUserId(userId);

		if (userName == null) {
			faroChannelImpl.setUserName("");
		}
		else {
			faroChannelImpl.setUserName(userName);
		}

		faroChannelImpl.setCreateTime(createTime);
		faroChannelImpl.setModifiedTime(modifiedTime);

		if (channelId == null) {
			faroChannelImpl.setChannelId("");
		}
		else {
			faroChannelImpl.setChannelId(channelId);
		}

		if (name == null) {
			faroChannelImpl.setName("");
		}
		else {
			faroChannelImpl.setName(name);
		}

		faroChannelImpl.setPermissionType(permissionType);
		faroChannelImpl.setWorkspaceGroupId(workspaceGroupId);

		faroChannelImpl.resetOriginalValues();

		return faroChannelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		faroChannelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		createTime = objectInput.readLong();

		modifiedTime = objectInput.readLong();
		channelId = objectInput.readUTF();
		name = objectInput.readUTF();

		permissionType = objectInput.readInt();

		workspaceGroupId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(faroChannelId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createTime);

		objectOutput.writeLong(modifiedTime);

		if (channelId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(channelId);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(permissionType);

		objectOutput.writeLong(workspaceGroupId);
	}

	public long mvccVersion;
	public long faroChannelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createTime;
	public long modifiedTime;
	public String channelId;
	public String name;
	public int permissionType;
	public long workspaceGroupId;

}