/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model.impl;

import com.liferay.osb.faro.model.FaroNotification;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FaroNotification in entity cache.
 *
 * @author Matthew Kong
 * @generated
 */
public class FaroNotificationCacheModel
	implements CacheModel<FaroNotification>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroNotificationCacheModel)) {
			return false;
		}

		FaroNotificationCacheModel faroNotificationCacheModel =
			(FaroNotificationCacheModel)object;

		if ((faroNotificationId ==
				faroNotificationCacheModel.faroNotificationId) &&
			(mvccVersion == faroNotificationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, faroNotificationId);

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
		sb.append(", faroNotificationId=");
		sb.append(faroNotificationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createTime=");
		sb.append(createTime);
		sb.append(", modifiedTime=");
		sb.append(modifiedTime);
		sb.append(", ownerId=");
		sb.append(ownerId);
		sb.append(", scope=");
		sb.append(scope);
		sb.append(", read=");
		sb.append(read);
		sb.append(", type=");
		sb.append(type);
		sb.append(", subtype=");
		sb.append(subtype);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FaroNotification toEntityModel() {
		FaroNotificationImpl faroNotificationImpl = new FaroNotificationImpl();

		faroNotificationImpl.setMvccVersion(mvccVersion);
		faroNotificationImpl.setFaroNotificationId(faroNotificationId);
		faroNotificationImpl.setGroupId(groupId);
		faroNotificationImpl.setCompanyId(companyId);
		faroNotificationImpl.setUserId(userId);
		faroNotificationImpl.setCreateTime(createTime);
		faroNotificationImpl.setModifiedTime(modifiedTime);
		faroNotificationImpl.setOwnerId(ownerId);

		if (scope == null) {
			faroNotificationImpl.setScope("");
		}
		else {
			faroNotificationImpl.setScope(scope);
		}

		faroNotificationImpl.setRead(read);

		if (type == null) {
			faroNotificationImpl.setType("");
		}
		else {
			faroNotificationImpl.setType(type);
		}

		if (subtype == null) {
			faroNotificationImpl.setSubtype("");
		}
		else {
			faroNotificationImpl.setSubtype(subtype);
		}

		faroNotificationImpl.resetOriginalValues();

		return faroNotificationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		faroNotificationId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		createTime = objectInput.readLong();

		modifiedTime = objectInput.readLong();

		ownerId = objectInput.readLong();
		scope = objectInput.readUTF();

		read = objectInput.readBoolean();
		type = objectInput.readUTF();
		subtype = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(faroNotificationId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(createTime);

		objectOutput.writeLong(modifiedTime);

		objectOutput.writeLong(ownerId);

		if (scope == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(scope);
		}

		objectOutput.writeBoolean(read);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (subtype == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(subtype);
		}
	}

	public long mvccVersion;
	public long faroNotificationId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createTime;
	public long modifiedTime;
	public long ownerId;
	public String scope;
	public boolean read;
	public String type;
	public String subtype;

}