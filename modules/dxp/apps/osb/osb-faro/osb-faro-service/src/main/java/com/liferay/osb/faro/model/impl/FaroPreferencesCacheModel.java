/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model.impl;

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

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
	implements CacheModel<FaroPreferences>, Externalizable, MVCCModel {

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

		if ((faroPreferencesId ==
				faroPreferencesCacheModel.faroPreferencesId) &&
			(mvccVersion == faroPreferencesCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, faroPreferencesId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", faroPreferencesId=");
		sb.append(faroPreferencesId);
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

		faroPreferencesImpl.setMvccVersion(mvccVersion);
		faroPreferencesImpl.setFaroPreferencesId(faroPreferencesId);
		faroPreferencesImpl.setGroupId(groupId);
		faroPreferencesImpl.setCompanyId(companyId);
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
		mvccVersion = objectInput.readLong();

		faroPreferencesId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		createTime = objectInput.readLong();

		modifiedTime = objectInput.readLong();

		ownerId = objectInput.readLong();
		preferences = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(faroPreferencesId);

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

		objectOutput.writeLong(ownerId);

		if (preferences == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(preferences);
		}
	}

	public long mvccVersion;
	public long faroPreferencesId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createTime;
	public long modifiedTime;
	public long ownerId;
	public String preferences;

}