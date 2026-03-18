/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.sharing.model.SharingEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SharingEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SharingEntryCacheModel
	implements CacheModel<SharingEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SharingEntryCacheModel)) {
			return false;
		}

		SharingEntryCacheModel sharingEntryCacheModel =
			(SharingEntryCacheModel)object;

		if (sharingEntryId == sharingEntryCacheModel.sharingEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, sharingEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", sharingEntryId=");
		sb.append(sharingEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", toUserGroupId=");
		sb.append(toUserGroupId);
		sb.append(", toUserId=");
		sb.append(toUserId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", shareable=");
		sb.append(shareable);
		sb.append(", actionIds=");
		sb.append(actionIds);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SharingEntry toEntityModel() {
		SharingEntryImpl sharingEntryImpl = new SharingEntryImpl();

		if (uuid == null) {
			sharingEntryImpl.setUuid("");
		}
		else {
			sharingEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			sharingEntryImpl.setExternalReferenceCode("");
		}
		else {
			sharingEntryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		sharingEntryImpl.setSharingEntryId(sharingEntryId);
		sharingEntryImpl.setGroupId(groupId);
		sharingEntryImpl.setCompanyId(companyId);
		sharingEntryImpl.setUserId(userId);

		if (userName == null) {
			sharingEntryImpl.setUserName("");
		}
		else {
			sharingEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			sharingEntryImpl.setCreateDate(null);
		}
		else {
			sharingEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			sharingEntryImpl.setModifiedDate(null);
		}
		else {
			sharingEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		sharingEntryImpl.setToUserGroupId(toUserGroupId);
		sharingEntryImpl.setToUserId(toUserId);
		sharingEntryImpl.setClassNameId(classNameId);
		sharingEntryImpl.setClassPK(classPK);
		sharingEntryImpl.setShareable(shareable);
		sharingEntryImpl.setActionIds(actionIds);

		if (expirationDate == Long.MIN_VALUE) {
			sharingEntryImpl.setExpirationDate(null);
		}
		else {
			sharingEntryImpl.setExpirationDate(new Date(expirationDate));
		}

		sharingEntryImpl.resetOriginalValues();

		return sharingEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		sharingEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		toUserGroupId = objectInput.readLong();

		toUserId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		shareable = objectInput.readBoolean();

		actionIds = objectInput.readLong();
		expirationDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(sharingEntryId);

		objectOutput.writeLong(groupId);

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

		objectOutput.writeLong(toUserGroupId);

		objectOutput.writeLong(toUserId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeBoolean(shareable);

		objectOutput.writeLong(actionIds);
		objectOutput.writeLong(expirationDate);
	}

	public String uuid;
	public String externalReferenceCode;
	public long sharingEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long toUserGroupId;
	public long toUserId;
	public long classNameId;
	public long classPK;
	public boolean shareable;
	public long actionIds;
	public long expirationDate;

}
// LIFERAY-SERVICE-BUILDER-HASH:376552530