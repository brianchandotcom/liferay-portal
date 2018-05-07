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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPOptionValue;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPOptionValue in entity cache.
 *
 * @author Marco Leo
 * @see CPOptionValue
 * @generated
 */
@ProviderType
public class CPOptionValueCacheModel implements CacheModel<CPOptionValue>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPOptionValueCacheModel)) {
			return false;
		}

		CPOptionValueCacheModel cpOptionValueCacheModel = (CPOptionValueCacheModel)obj;

		if (CPOptionValueId == cpOptionValueCacheModel.CPOptionValueId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPOptionValueId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPOptionValueId=");
		sb.append(CPOptionValueId);
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
		sb.append(", CPOptionId=");
		sb.append(CPOptionId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", key=");
		sb.append(key);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPOptionValue toEntityModel() {
		CPOptionValueImpl cpOptionValueImpl = new CPOptionValueImpl();

		if (uuid == null) {
			cpOptionValueImpl.setUuid("");
		}
		else {
			cpOptionValueImpl.setUuid(uuid);
		}

		cpOptionValueImpl.setCPOptionValueId(CPOptionValueId);
		cpOptionValueImpl.setGroupId(groupId);
		cpOptionValueImpl.setCompanyId(companyId);
		cpOptionValueImpl.setUserId(userId);

		if (userName == null) {
			cpOptionValueImpl.setUserName("");
		}
		else {
			cpOptionValueImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpOptionValueImpl.setCreateDate(null);
		}
		else {
			cpOptionValueImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpOptionValueImpl.setModifiedDate(null);
		}
		else {
			cpOptionValueImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpOptionValueImpl.setCPOptionId(CPOptionId);

		if (name == null) {
			cpOptionValueImpl.setName("");
		}
		else {
			cpOptionValueImpl.setName(name);
		}

		cpOptionValueImpl.setPriority(priority);

		if (key == null) {
			cpOptionValueImpl.setKey("");
		}
		else {
			cpOptionValueImpl.setKey(key);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			cpOptionValueImpl.setLastPublishDate(null);
		}
		else {
			cpOptionValueImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		cpOptionValueImpl.resetOriginalValues();

		return cpOptionValueImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPOptionValueId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPOptionId = objectInput.readLong();
		name = objectInput.readUTF();

		priority = objectInput.readDouble();
		key = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPOptionValueId);

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

		objectOutput.writeLong(CPOptionId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeDouble(priority);

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long CPOptionValueId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPOptionId;
	public String name;
	public double priority;
	public String key;
	public long lastPublishDate;
}