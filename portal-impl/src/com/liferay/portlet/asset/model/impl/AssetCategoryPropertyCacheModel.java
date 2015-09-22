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

import com.liferay.portlet.asset.model.AssetCategoryProperty;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetCategoryProperty in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryProperty
 * @generated
 */
@ProviderType
public class AssetCategoryPropertyCacheModel implements CacheModel<AssetCategoryProperty>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetCategoryPropertyCacheModel)) {
			return false;
		}

		AssetCategoryPropertyCacheModel assetCategoryPropertyCacheModel = (AssetCategoryPropertyCacheModel)obj;

		if (categoryPropertyId == assetCategoryPropertyCacheModel.categoryPropertyId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, categoryPropertyId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{categoryPropertyId=");
		sb.append(categoryPropertyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", categoryId=");
		sb.append(categoryId);
		sb.append(", key=");
		sb.append(key);
		sb.append(", value=");
		sb.append(value);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetCategoryProperty toEntityModel() {
		AssetCategoryPropertyImpl assetCategoryPropertyImpl = new AssetCategoryPropertyImpl();

		assetCategoryPropertyImpl.setCategoryPropertyId(categoryPropertyId);
		assetCategoryPropertyImpl.setUserId(userId);

		if (userName == null) {
			assetCategoryPropertyImpl.setUserName(StringPool.BLANK);
		}
		else {
			assetCategoryPropertyImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetCategoryPropertyImpl.setCreateDate(null);
		}
		else {
			assetCategoryPropertyImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetCategoryPropertyImpl.setModifiedDate(null);
		}
		else {
			assetCategoryPropertyImpl.setModifiedDate(new Date(modifiedDate));
		}

		assetCategoryPropertyImpl.setCategoryId(categoryId);

		if (key == null) {
			assetCategoryPropertyImpl.setKey(StringPool.BLANK);
		}
		else {
			assetCategoryPropertyImpl.setKey(key);
		}

		if (value == null) {
			assetCategoryPropertyImpl.setValue(StringPool.BLANK);
		}
		else {
			assetCategoryPropertyImpl.setValue(value);
		}

		assetCategoryPropertyImpl.setCompanyId(companyId);

		assetCategoryPropertyImpl.resetOriginalValues();

		return assetCategoryPropertyImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		categoryPropertyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		categoryId = objectInput.readLong();
		key = objectInput.readUTF();
		value = objectInput.readUTF();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(categoryPropertyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(categoryId);

		if (key == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(key);
		}

		if (value == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(value);
		}

		objectOutput.writeLong(companyId);
	}

	public long categoryPropertyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long categoryId;
	public String key;
	public String value;
	public long companyId;
}