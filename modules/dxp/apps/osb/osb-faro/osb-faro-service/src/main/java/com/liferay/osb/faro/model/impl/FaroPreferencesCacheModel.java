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

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FaroPreferences in entity cache.
 *
 * @author Matthew Kong
 * @generated
 */
public class FaroPreferencesCacheModel
	implements CacheModel<FaroPreferences>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroPreferencesCacheModel)) {
			return false;
		}

		FaroPreferencesCacheModel faroPreferencesCacheModel =
			(FaroPreferencesCacheModel)object;

		if (faroPreferencesId == faroPreferencesCacheModel.faroPreferencesId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, faroPreferencesId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{faroPreferencesId=");
		sb.append(faroPreferencesId);
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
		sb.append(", ownerId=");
		sb.append(ownerId);
		sb.append(", preferences=");
		sb.append(preferences);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FaroPreferences toEntityModel() {
		FaroPreferencesImpl faroPreferencesImpl = new FaroPreferencesImpl();

		faroPreferencesImpl.setFaroPreferencesId(faroPreferencesId);
		faroPreferencesImpl.setGroupId(groupId);
		faroPreferencesImpl.setUserId(userId);

		if (userName == null) {
			faroPreferencesImpl.setUserName("");
		}
		else {
			faroPreferencesImpl.setUserName(userName);
		}

		faroPreferencesImpl.setCreateTime(createTime);
		faroPreferencesImpl.setModifiedTime(modifiedTime);
		faroPreferencesImpl.setOwnerId(ownerId);

		if (preferences == null) {
			faroPreferencesImpl.setPreferences("");
		}
		else {
			faroPreferencesImpl.setPreferences(preferences);
		}

		faroPreferencesImpl.resetOriginalValues();

		return faroPreferencesImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		faroPreferencesId = objectInput.readLong();

		groupId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		createTime = objectInput.readLong();

		modifiedTime = objectInput.readLong();

		ownerId = objectInput.readLong();
		preferences = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(faroPreferencesId);

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

		objectOutput.writeLong(ownerId);

		if (preferences == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(preferences);
		}
	}

	public long faroPreferencesId;
	public long groupId;
	public long userId;
	public String userName;
	public long createTime;
	public long modifiedTime;
	public long ownerId;
	public String preferences;

}