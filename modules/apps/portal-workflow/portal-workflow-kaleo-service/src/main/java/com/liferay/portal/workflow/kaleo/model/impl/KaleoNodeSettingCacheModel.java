/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoNodeSetting in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoNodeSettingCacheModel
	implements CacheModel<KaleoNodeSetting>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoNodeSettingCacheModel)) {
			return false;
		}

		KaleoNodeSettingCacheModel kaleoNodeSettingCacheModel =
			(KaleoNodeSettingCacheModel)object;

		if ((kaleoNodeSettingId ==
				kaleoNodeSettingCacheModel.kaleoNodeSettingId) &&
			(mvccVersion == kaleoNodeSettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoNodeSettingId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", kaleoNodeSettingId=");
		sb.append(kaleoNodeSettingId);
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
		sb.append(", kaleoNodeId=");
		sb.append(kaleoNodeId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoNodeSetting toEntityModel() {
		KaleoNodeSettingImpl kaleoNodeSettingImpl = new KaleoNodeSettingImpl();

		kaleoNodeSettingImpl.setMvccVersion(mvccVersion);
		kaleoNodeSettingImpl.setCtCollectionId(ctCollectionId);
		kaleoNodeSettingImpl.setKaleoNodeSettingId(kaleoNodeSettingId);
		kaleoNodeSettingImpl.setCompanyId(companyId);
		kaleoNodeSettingImpl.setUserId(userId);

		if (userName == null) {
			kaleoNodeSettingImpl.setUserName("");
		}
		else {
			kaleoNodeSettingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoNodeSettingImpl.setCreateDate(null);
		}
		else {
			kaleoNodeSettingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoNodeSettingImpl.setModifiedDate(null);
		}
		else {
			kaleoNodeSettingImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoNodeSettingImpl.setKaleoNodeId(kaleoNodeId);

		if (name == null) {
			kaleoNodeSettingImpl.setName("");
		}
		else {
			kaleoNodeSettingImpl.setName(name);
		}

		if (value == null) {
			kaleoNodeSettingImpl.setValue("");
		}
		else {
			kaleoNodeSettingImpl.setValue(value);
		}

		kaleoNodeSettingImpl.resetOriginalValues();

		return kaleoNodeSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		kaleoNodeSettingId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoNodeId = objectInput.readLong();
		name = objectInput.readUTF();
		value = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(kaleoNodeSettingId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(kaleoNodeId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (value == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(value);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long kaleoNodeSettingId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoNodeId;
	public String name;
	public String value;

}
// LIFERAY-SERVICE-BUILDER-HASH:1470298282