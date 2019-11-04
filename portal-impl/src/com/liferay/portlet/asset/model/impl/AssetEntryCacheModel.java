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

package com.liferay.portlet.asset.model.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetEntryCacheModel
	implements CacheModel<AssetEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryCacheModel)) {
			return false;
		}

		AssetEntryCacheModel assetEntryCacheModel = (AssetEntryCacheModel)obj;

		if ((entryId == assetEntryCacheModel.entryId) &&
			(mvccVersion == assetEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, entryId);

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
		StringBundler sb = new StringBundler(55);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", entryId=");
		sb.append(entryId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", classUuid=");
		sb.append(classUuid);
		sb.append(", classTypeId=");
		sb.append(classTypeId);
		sb.append(", listable=");
		sb.append(listable);
		sb.append(", visible=");
		sb.append(visible);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", publishDate=");
		sb.append(publishDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", mimeType=");
		sb.append(mimeType);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", summary=");
		sb.append(summary);
		sb.append(", url=");
		sb.append(url);
		sb.append(", layoutUuid=");
		sb.append(layoutUuid);
		sb.append(", height=");
		sb.append(height);
		sb.append(", width=");
		sb.append(width);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetEntry toEntityModel() {
		AssetEntryImpl assetEntryImpl = new AssetEntryImpl();

		assetEntryImpl.setMvccVersion(mvccVersion);
		assetEntryImpl.setEntryId(entryId);
		assetEntryImpl.setGroupId(groupId);
		assetEntryImpl.setCompanyId(companyId);
		assetEntryImpl.setUserId(userId);

		if (userName == null) {
			assetEntryImpl.setUserName("");
		}
		else {
			assetEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetEntryImpl.setCreateDate(null);
		}
		else {
			assetEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetEntryImpl.setModifiedDate(null);
		}
		else {
			assetEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		assetEntryImpl.setClassNameId(classNameId);
		assetEntryImpl.setClassPK(classPK);

		if (classUuid == null) {
			assetEntryImpl.setClassUuid("");
		}
		else {
			assetEntryImpl.setClassUuid(classUuid);
		}

		assetEntryImpl.setClassTypeId(classTypeId);
		assetEntryImpl.setListable(listable);
		assetEntryImpl.setVisible(visible);

		if (startDate == Long.MIN_VALUE) {
			assetEntryImpl.setStartDate(null);
		}
		else {
			assetEntryImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			assetEntryImpl.setEndDate(null);
		}
		else {
			assetEntryImpl.setEndDate(new Date(endDate));
		}

		if (publishDate == Long.MIN_VALUE) {
			assetEntryImpl.setPublishDate(null);
		}
		else {
			assetEntryImpl.setPublishDate(new Date(publishDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			assetEntryImpl.setExpirationDate(null);
		}
		else {
			assetEntryImpl.setExpirationDate(new Date(expirationDate));
		}

		if (mimeType == null) {
			assetEntryImpl.setMimeType("");
		}
		else {
			assetEntryImpl.setMimeType(mimeType);
		}

		if (title == null) {
			assetEntryImpl.setTitle("");
		}
		else {
			assetEntryImpl.setTitle(title);
		}

		if (description == null) {
			assetEntryImpl.setDescription("");
		}
		else {
			assetEntryImpl.setDescription(description);
		}

		if (summary == null) {
			assetEntryImpl.setSummary("");
		}
		else {
			assetEntryImpl.setSummary(summary);
		}

		if (url == null) {
			assetEntryImpl.setUrl("");
		}
		else {
			assetEntryImpl.setUrl(url);
		}

		if (layoutUuid == null) {
			assetEntryImpl.setLayoutUuid("");
		}
		else {
			assetEntryImpl.setLayoutUuid(layoutUuid);
		}

		assetEntryImpl.setHeight(height);
		assetEntryImpl.setWidth(width);
		assetEntryImpl.setPriority(priority);

		assetEntryImpl.resetOriginalValues();

		return assetEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		entryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		classUuid = objectInput.readUTF();

		classTypeId = objectInput.readLong();

		listable = objectInput.readBoolean();

		visible = objectInput.readBoolean();
		startDate = objectInput.readLong();
		endDate = objectInput.readLong();
		publishDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		mimeType = objectInput.readUTF();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		summary = objectInput.readUTF();
		url = objectInput.readUTF();
		layoutUuid = objectInput.readUTF();

		height = objectInput.readInt();

		width = objectInput.readInt();

		priority = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(entryId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (classUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(classUuid);
		}

		objectOutput.writeLong(classTypeId);

		objectOutput.writeBoolean(listable);

		objectOutput.writeBoolean(visible);
		objectOutput.writeLong(startDate);
		objectOutput.writeLong(endDate);
		objectOutput.writeLong(publishDate);
		objectOutput.writeLong(expirationDate);

		if (mimeType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(mimeType);
		}

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (summary == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(summary);
		}

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}

		if (layoutUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(layoutUuid);
		}

		objectOutput.writeInt(height);

		objectOutput.writeInt(width);

		objectOutput.writeDouble(priority);
	}

	public long mvccVersion;
	public long entryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String classUuid;
	public long classTypeId;
	public boolean listable;
	public boolean visible;
	public long startDate;
	public long endDate;
	public long publishDate;
	public long expirationDate;
	public String mimeType;
	public String title;
	public String description;
	public String summary;
	public String url;
	public String layoutUuid;
	public int height;
	public int width;
	public double priority;

}