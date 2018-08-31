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

package com.liferay.asset.list.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.list.model.AssetListEntry;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetListEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntry
 * @generated
 */
@ProviderType
public class AssetListEntryCacheModel implements CacheModel<AssetListEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListEntryCacheModel)) {
			return false;
		}

		AssetListEntryCacheModel assetListEntryCacheModel = (AssetListEntryCacheModel)obj;

		if (assetListEntryId == assetListEntryCacheModel.assetListEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetListEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{assetListEntryId=");
		sb.append(assetListEntryId);
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
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", title=");
		sb.append(title);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetListEntry toEntityModel() {
		AssetListEntryImpl assetListEntryImpl = new AssetListEntryImpl();

		assetListEntryImpl.setAssetListEntryId(assetListEntryId);
		assetListEntryImpl.setGroupId(groupId);
		assetListEntryImpl.setCompanyId(companyId);
		assetListEntryImpl.setUserId(userId);

		if (userName == null) {
			assetListEntryImpl.setUserName("");
		}
		else {
			assetListEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetListEntryImpl.setCreateDate(null);
		}
		else {
			assetListEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetListEntryImpl.setModifiedDate(null);
		}
		else {
			assetListEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (typeSettings == null) {
			assetListEntryImpl.setTypeSettings("");
		}
		else {
			assetListEntryImpl.setTypeSettings(typeSettings);
		}

		if (title == null) {
			assetListEntryImpl.setTitle("");
		}
		else {
			assetListEntryImpl.setTitle(title);
		}

		assetListEntryImpl.setType(type);

		assetListEntryImpl.resetOriginalValues();

		return assetListEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		assetListEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		typeSettings = objectInput.readUTF();
		title = objectInput.readUTF();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(assetListEntryId);

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

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeInt(type);
	}

	public long assetListEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String typeSettings;
	public String title;
	public int type;
}