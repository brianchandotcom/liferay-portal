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

package com.liferay.portal.route.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.route.model.GroupFriendlyURL;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing GroupFriendlyURL in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see GroupFriendlyURL
 * @generated
 */
@ProviderType
public class GroupFriendlyURLCacheModel implements CacheModel<GroupFriendlyURL>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof GroupFriendlyURLCacheModel)) {
			return false;
		}

		GroupFriendlyURLCacheModel groupFriendlyURLCacheModel = (GroupFriendlyURLCacheModel)obj;

		if ((groupFriendlyURLId == groupFriendlyURLCacheModel.groupFriendlyURLId) &&
				(mvccVersion == groupFriendlyURLCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, groupFriendlyURLId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", groupFriendlyURLId=");
		sb.append(groupFriendlyURLId);
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
		sb.append(", friendlyURL=");
		sb.append(friendlyURL);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public GroupFriendlyURL toEntityModel() {
		GroupFriendlyURLImpl groupFriendlyURLImpl = new GroupFriendlyURLImpl();

		groupFriendlyURLImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			groupFriendlyURLImpl.setUuid(StringPool.BLANK);
		}
		else {
			groupFriendlyURLImpl.setUuid(uuid);
		}

		groupFriendlyURLImpl.setGroupFriendlyURLId(groupFriendlyURLId);
		groupFriendlyURLImpl.setGroupId(groupId);
		groupFriendlyURLImpl.setCompanyId(companyId);
		groupFriendlyURLImpl.setUserId(userId);

		if (userName == null) {
			groupFriendlyURLImpl.setUserName(StringPool.BLANK);
		}
		else {
			groupFriendlyURLImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			groupFriendlyURLImpl.setCreateDate(null);
		}
		else {
			groupFriendlyURLImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			groupFriendlyURLImpl.setModifiedDate(null);
		}
		else {
			groupFriendlyURLImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (friendlyURL == null) {
			groupFriendlyURLImpl.setFriendlyURL(StringPool.BLANK);
		}
		else {
			groupFriendlyURLImpl.setFriendlyURL(friendlyURL);
		}

		if (languageId == null) {
			groupFriendlyURLImpl.setLanguageId(StringPool.BLANK);
		}
		else {
			groupFriendlyURLImpl.setLanguageId(languageId);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			groupFriendlyURLImpl.setLastPublishDate(null);
		}
		else {
			groupFriendlyURLImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		groupFriendlyURLImpl.resetOriginalValues();

		return groupFriendlyURLImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		groupFriendlyURLId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		friendlyURL = objectInput.readUTF();
		languageId = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(groupFriendlyURLId);

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

		if (friendlyURL == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(friendlyURL);
		}

		if (languageId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long groupFriendlyURLId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String friendlyURL;
	public String languageId;
	public long lastPublishDate;
}