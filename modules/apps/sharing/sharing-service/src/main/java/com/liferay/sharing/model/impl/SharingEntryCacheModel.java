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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SharingEntryCacheModel)) {
			return false;
		}

		SharingEntryCacheModel sharingEntryCacheModel =
			(SharingEntryCacheModel)obj;

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
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(uuid);
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

		sharingEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

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

		objectOutput.writeLong(toUserId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeBoolean(shareable);

		objectOutput.writeLong(actionIds);
		objectOutput.writeLong(expirationDate);
	}

	public String uuid;
	public long sharingEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long toUserId;
	public long classNameId;
	public long classPK;
	public boolean shareable;
	public long actionIds;
	public long expirationDate;

}