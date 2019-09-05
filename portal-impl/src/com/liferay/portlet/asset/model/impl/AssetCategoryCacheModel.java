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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetCategory in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetCategoryCacheModel
	implements CacheModel<AssetCategory>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetCategoryCacheModel)) {
			return false;
		}

		AssetCategoryCacheModel assetCategoryCacheModel =
			(AssetCategoryCacheModel)obj;

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
		StringBundler sb = new StringBundler(35);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", categoryId=");
		sb.append(categoryId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetCategory toEntityModel() {
		AssetCategoryImpl assetCategoryImpl = new AssetCategoryImpl();

		if (uuid == null) {
			assetCategoryImpl.setUuid("");
		}
		else {
			assetCategoryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			assetCategoryImpl.setExternalReferenceCode("");
		}
		else {
			assetCategoryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		assetCategoryImpl.setCategoryId(categoryId);
		assetCategoryImpl.setGroupId(groupId);
		assetCategoryImpl.setCompanyId(companyId);
		assetCategoryImpl.setUserId(userId);

		if (userName == null) {
			assetCategoryImpl.setUserName("");
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
			assetCategoryImpl.setName("");
		}
		else {
			assetCategoryImpl.setName(name);
		}

		if (title == null) {
			assetCategoryImpl.setTitle("");
		}
		else {
			assetCategoryImpl.setTitle(title);
		}

		if (description == null) {
			assetCategoryImpl.setDescription("");
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

		assetCategoryImpl.resetOriginalValues();

		return assetCategoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		categoryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

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

		objectOutput.writeLong(categoryId);

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

		objectOutput.writeLong(parentCategoryId);

		objectOutput.writeLong(leftCategoryId);

		objectOutput.writeLong(rightCategoryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
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

		objectOutput.writeLong(vocabularyId);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public String externalReferenceCode;
	public long categoryId;
	public long groupId;
	public long companyId;
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

}