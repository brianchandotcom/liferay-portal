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

package com.liferay.friendly.url.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.model.FriendlyURL;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing FriendlyURL in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURL
 * @generated
 */
@ProviderType
public class FriendlyURLCacheModel implements CacheModel<FriendlyURL>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLCacheModel)) {
			return false;
		}

		FriendlyURLCacheModel friendlyURLCacheModel = (FriendlyURLCacheModel)obj;

		if (friendlyUrlId == friendlyURLCacheModel.friendlyUrlId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, friendlyUrlId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", friendlyUrlId=");
		sb.append(friendlyUrlId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", urlTitle=");
		sb.append(urlTitle);
		sb.append(", main=");
		sb.append(main);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FriendlyURL toEntityModel() {
		FriendlyURLImpl friendlyURLImpl = new FriendlyURLImpl();

		if (uuid == null) {
			friendlyURLImpl.setUuid(StringPool.BLANK);
		}
		else {
			friendlyURLImpl.setUuid(uuid);
		}

		friendlyURLImpl.setFriendlyUrlId(friendlyUrlId);
		friendlyURLImpl.setGroupId(groupId);
		friendlyURLImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			friendlyURLImpl.setCreateDate(null);
		}
		else {
			friendlyURLImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			friendlyURLImpl.setModifiedDate(null);
		}
		else {
			friendlyURLImpl.setModifiedDate(new Date(modifiedDate));
		}

		friendlyURLImpl.setClassNameId(classNameId);
		friendlyURLImpl.setClassPK(classPK);

		if (urlTitle == null) {
			friendlyURLImpl.setUrlTitle(StringPool.BLANK);
		}
		else {
			friendlyURLImpl.setUrlTitle(urlTitle);
		}

		friendlyURLImpl.setMain(main);

		friendlyURLImpl.resetOriginalValues();

		return friendlyURLImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		friendlyUrlId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		urlTitle = objectInput.readUTF();

		main = objectInput.readBoolean();
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

		objectOutput.writeLong(friendlyUrlId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (urlTitle == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(urlTitle);
		}

		objectOutput.writeBoolean(main);
	}

	public String uuid;
	public long friendlyUrlId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String urlTitle;
	public boolean main;
}