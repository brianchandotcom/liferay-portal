/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.documentlibrary.model.DLFileRank;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DLFileRank in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileRank
 * @generated
 */
public class DLFileRankCacheModel implements CacheModel<DLFileRank>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", fileRankId=");
		sb.append(fileRankId);
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
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	public DLFileRank toEntityModel() {
		DLFileRankImpl dlFileRankImpl = new DLFileRankImpl();

		if (uuid == null) {
			dlFileRankImpl.setUuid(StringPool.BLANK);
		}
		else {
			dlFileRankImpl.setUuid(uuid);
		}

		dlFileRankImpl.setFileRankId(fileRankId);
		dlFileRankImpl.setGroupId(groupId);
		dlFileRankImpl.setCompanyId(companyId);
		dlFileRankImpl.setUserId(userId);

		if (userName == null) {
			dlFileRankImpl.setUserName(StringPool.BLANK);
		}
		else {
			dlFileRankImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			dlFileRankImpl.setCreateDate(null);
		}
		else {
			dlFileRankImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dlFileRankImpl.setModifiedDate(null);
		}
		else {
			dlFileRankImpl.setModifiedDate(new Date(modifiedDate));
		}

		dlFileRankImpl.setFileEntryId(fileEntryId);
		dlFileRankImpl.setActive(active);

		dlFileRankImpl.resetOriginalValues();

		return dlFileRankImpl;
	}

	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		fileRankId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		fileEntryId = objectInput.readLong();
		active = objectInput.readBoolean();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(fileRankId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(fileEntryId);
		objectOutput.writeBoolean(active);
	}

	public String uuid;
	public long fileRankId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long fileEntryId;
	public boolean active;
}