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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.asset.model.AssetCategory;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetCategory in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategory
 * @generated
 */
@ProviderType
public class AssetCategoryCacheModel implements CacheModel<AssetCategory>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetCategoryCacheModel)) {
			return false;
		}

		AssetCategoryCacheModel assetCategoryCacheModel = (AssetCategoryCacheModel)obj;

		if (categoryId == assetCategoryCacheModel.categoryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, categoryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", categoryId=");
		sb.append(categoryId);
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
		sb.append(", parentCategoryId=");
		sb.append(parentCategoryId);
		sb.append(", leftCategoryId=");
		sb.append(leftCategoryId);
		sb.append(", rightCategoryId=");
		sb.append(rightCategoryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", vocabularyId=");
		sb.append(vocabularyId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetCategory toEntityModel() {
		AssetCategoryImpl assetCategoryImpl = new AssetCategoryImpl();

		if (uuid == null) {
			assetCategoryImpl.setUuid(StringPool.BLANK);
		}
		else {
			assetCategoryImpl.setUuid(uuid);
		}

		assetCategoryImpl.setCategoryId(categoryId);
		assetCategoryImpl.setGroupId(groupId);
		assetCategoryImpl.setUserId(userId);

		if (userName == null) {
			assetCategoryImpl.setUserName(StringPool.BLANK);
		}
		else {
			assetCategoryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetCategoryImpl.setCreateDate(null);
		}
		else {
			assetCategoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetCategoryImpl.setModifiedDate(null);
		}
		else {
			assetCategoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		assetCategoryImpl.setParentCategoryId(parentCategoryId);
		assetCategoryImpl.setLeftCategoryId(leftCategoryId);
		assetCategoryImpl.setRightCategoryId(rightCategoryId);

		if (name == null) {
			assetCategoryImpl.setName(StringPool.BLANK);
		}
		else {
			assetCategoryImpl.setName(name);
		}

		if (title == null) {
			assetCategoryImpl.setTitle(StringPool.BLANK);
		}
		else {
			assetCategoryImpl.setTitle(title);
		}

		if (description == null) {
			assetCategoryImpl.setDescription(StringPool.BLANK);
		}
		else {
			assetCategoryImpl.setDescription(description);
		}

		assetCategoryImpl.setVocabularyId(vocabularyId);

		if (lastPublishDate == Long.MIN_VALUE) {
			assetCategoryImpl.setLastPublishDate(null);
		}
		else {
			assetCategoryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		assetCategoryImpl.setCompanyId(companyId);

		assetCategoryImpl.resetOriginalValues();

		return assetCategoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		categoryId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		parentCategoryId = objectInput.readLong();
		leftCategoryId = objectInput.readLong();
		rightCategoryId = objectInput.readLong();
		name = objectInput.readUTF();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		vocabularyId = objectInput.readLong();
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

		objectOutput.writeLong(categoryId);
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
		objectOutput.writeLong(parentCategoryId);
		objectOutput.writeLong(leftCategoryId);
		objectOutput.writeLong(rightCategoryId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(vocabularyId);
		objectOutput.writeLong(lastPublishDate);
		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long categoryId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentCategoryId;
	public long leftCategoryId;
	public long rightCategoryId;
	public String name;
	public String title;
	public String description;
	public long vocabularyId;
	public long lastPublishDate;
	public long companyId;
}