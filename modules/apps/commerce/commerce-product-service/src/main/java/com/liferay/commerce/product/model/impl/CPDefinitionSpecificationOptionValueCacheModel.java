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

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
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
 * The cache model class for representing CPDefinitionSpecificationOptionValue in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPDefinitionSpecificationOptionValueCacheModel
	implements CacheModel<CPDefinitionSpecificationOptionValue>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CPDefinitionSpecificationOptionValueCacheModel)) {

			return false;
		}

		CPDefinitionSpecificationOptionValueCacheModel
			cpDefinitionSpecificationOptionValueCacheModel =
				(CPDefinitionSpecificationOptionValueCacheModel)object;

		if ((CPDefinitionSpecificationOptionValueId ==
				cpDefinitionSpecificationOptionValueCacheModel.
					CPDefinitionSpecificationOptionValueId) &&
			(mvccVersion ==
				cpDefinitionSpecificationOptionValueCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPDefinitionSpecificationOptionValueId);

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
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionSpecificationOptionValueId=");
		sb.append(CPDefinitionSpecificationOptionValueId);
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
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", CPSpecificationOptionId=");
		sb.append(CPSpecificationOptionId);
		sb.append(", CPOptionCategoryId=");
		sb.append(CPOptionCategoryId);
		sb.append(", value=");
		sb.append(value);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionSpecificationOptionValue toEntityModel() {
		CPDefinitionSpecificationOptionValueImpl
			cpDefinitionSpecificationOptionValueImpl =
				new CPDefinitionSpecificationOptionValueImpl();

		cpDefinitionSpecificationOptionValueImpl.setMvccVersion(mvccVersion);
		cpDefinitionSpecificationOptionValueImpl.setCtCollectionId(
			ctCollectionId);

		if (uuid == null) {
			cpDefinitionSpecificationOptionValueImpl.setUuid("");
		}
		else {
			cpDefinitionSpecificationOptionValueImpl.setUuid(uuid);
		}

		cpDefinitionSpecificationOptionValueImpl.
			setCPDefinitionSpecificationOptionValueId(
				CPDefinitionSpecificationOptionValueId);
		cpDefinitionSpecificationOptionValueImpl.setGroupId(groupId);
		cpDefinitionSpecificationOptionValueImpl.setCompanyId(companyId);
		cpDefinitionSpecificationOptionValueImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionSpecificationOptionValueImpl.setUserName("");
		}
		else {
			cpDefinitionSpecificationOptionValueImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionSpecificationOptionValueImpl.setCreateDate(null);
		}
		else {
			cpDefinitionSpecificationOptionValueImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionSpecificationOptionValueImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionSpecificationOptionValueImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		cpDefinitionSpecificationOptionValueImpl.setCPDefinitionId(
			CPDefinitionId);
		cpDefinitionSpecificationOptionValueImpl.setCPSpecificationOptionId(
			CPSpecificationOptionId);
		cpDefinitionSpecificationOptionValueImpl.setCPOptionCategoryId(
			CPOptionCategoryId);

		if (value == null) {
			cpDefinitionSpecificationOptionValueImpl.setValue("");
		}
		else {
			cpDefinitionSpecificationOptionValueImpl.setValue(value);
		}

		cpDefinitionSpecificationOptionValueImpl.setPriority(priority);

		if (lastPublishDate == Long.MIN_VALUE) {
			cpDefinitionSpecificationOptionValueImpl.setLastPublishDate(null);
		}
		else {
			cpDefinitionSpecificationOptionValueImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		cpDefinitionSpecificationOptionValueImpl.resetOriginalValues();

		return cpDefinitionSpecificationOptionValueImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPDefinitionSpecificationOptionValueId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		CPSpecificationOptionId = objectInput.readLong();

		CPOptionCategoryId = objectInput.readLong();
		value = objectInput.readUTF();

		priority = objectInput.readDouble();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPDefinitionSpecificationOptionValueId);

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

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(CPSpecificationOptionId);

		objectOutput.writeLong(CPOptionCategoryId);

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}

		objectOutput.writeDouble(priority);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long CPDefinitionSpecificationOptionValueId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public long CPSpecificationOptionId;
	public long CPOptionCategoryId;
	public String value;
	public double priority;
	public long lastPublishDate;

}