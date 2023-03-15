/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.model.impl;

import com.liferay.osb.faro.model.FaroUser;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FaroUser in entity cache.
 *
 * @author Matthew Kong
 * @generated
 */
public class FaroUserCacheModel
	implements CacheModel<FaroUser>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroUserCacheModel)) {
			return false;
		}

		FaroUserCacheModel faroUserCacheModel = (FaroUserCacheModel)object;

		if (faroUserId == faroUserCacheModel.faroUserId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, faroUserId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{faroUserId=");
		sb.append(faroUserId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createTime=");
		sb.append(createTime);
		sb.append(", modifiedTime=");
		sb.append(modifiedTime);
		sb.append(", liveUserId=");
		sb.append(liveUserId);
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append(", emailAddress=");
		sb.append(emailAddress);
		sb.append(", key=");
		sb.append(key);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FaroUser toEntityModel() {
		FaroUserImpl faroUserImpl = new FaroUserImpl();

		faroUserImpl.setFaroUserId(faroUserId);
		faroUserImpl.setGroupId(groupId);
		faroUserImpl.setUserId(userId);

		if (userName == null) {
			faroUserImpl.setUserName("");
		}
		else {
			faroUserImpl.setUserName(userName);
		}

		faroUserImpl.setCreateTime(createTime);
		faroUserImpl.setModifiedTime(modifiedTime);
		faroUserImpl.setLiveUserId(liveUserId);
		faroUserImpl.setRoleId(roleId);

		if (emailAddress == null) {
			faroUserImpl.setEmailAddress("");
		}
		else {
			faroUserImpl.setEmailAddress(emailAddress);
		}

		if (key == null) {
			faroUserImpl.setKey("");
		}
		else {
			faroUserImpl.setKey(key);
		}

		faroUserImpl.setStatus(status);

		faroUserImpl.resetOriginalValues();

		return faroUserImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		faroUserId = objectInput.readLong();

		groupId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		createTime = objectInput.readLong();

		modifiedTime = objectInput.readLong();

		liveUserId = objectInput.readLong();

		roleId = objectInput.readLong();
		emailAddress = objectInput.readUTF();
		key = objectInput.readUTF();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(faroUserId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createTime);

		objectOutput.writeLong(modifiedTime);

		objectOutput.writeLong(liveUserId);

		objectOutput.writeLong(roleId);

		if (emailAddress == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(emailAddress);
		}

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		objectOutput.writeInt(status);
	}

	public long faroUserId;
	public long groupId;
	public long userId;
	public String userName;
	public long createTime;
	public long modifiedTime;
	public long liveUserId;
	public long roleId;
	public String emailAddress;
	public String key;
	public int status;

}