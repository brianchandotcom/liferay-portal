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

package com.liferay.portlet.messageboards.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.messageboards.model.MBBan;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MBBan in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see MBBan
 * @generated
 */
@ProviderType
public class MBBanCacheModel implements CacheModel<MBBan>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBBanCacheModel)) {
			return false;
		}

		MBBanCacheModel mbBanCacheModel = (MBBanCacheModel)obj;

		if (banId == mbBanCacheModel.banId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, banId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", banId=");
		sb.append(banId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", banUserId=");
		sb.append(banUserId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MBBan toEntityModel() {
		MBBanImpl mbBanImpl = new MBBanImpl();

		if (uuid == null) {
			mbBanImpl.setUuid(StringPool.BLANK);
		}
		else {
			mbBanImpl.setUuid(uuid);
		}

		mbBanImpl.setBanId(banId);
		mbBanImpl.setGroupId(groupId);
		mbBanImpl.setUserId(userId);

		if (userName == null) {
			mbBanImpl.setUserName(StringPool.BLANK);
		}
		else {
			mbBanImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mbBanImpl.setCreateDate(null);
		}
		else {
			mbBanImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mbBanImpl.setModifiedDate(null);
		}
		else {
			mbBanImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbBanImpl.setBanUserId(banUserId);

		if (lastPublishDate == Long.MIN_VALUE) {
			mbBanImpl.setLastPublishDate(null);
		}
		else {
			mbBanImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		mbBanImpl.setCompanyId(companyId);

		mbBanImpl.resetOriginalValues();

		return mbBanImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		banId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		banUserId = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(banId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(banUserId);
		objectOutput.writeLong(lastPublishDate);
		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long banId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long banUserId;
	public long lastPublishDate;
	public long companyId;
}