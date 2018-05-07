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

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDefinitionOptionValueRel in entity cache.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionValueRel
 * @generated
 */
@ProviderType
public class CPDefinitionOptionValueRelCacheModel implements CacheModel<CPDefinitionOptionValueRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionOptionValueRelCacheModel)) {
			return false;
		}

		CPDefinitionOptionValueRelCacheModel cpDefinitionOptionValueRelCacheModel =
			(CPDefinitionOptionValueRelCacheModel)obj;

		if (CPDefinitionOptionValueRelId == cpDefinitionOptionValueRelCacheModel.CPDefinitionOptionValueRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionOptionValueRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionOptionValueRelId=");
		sb.append(CPDefinitionOptionValueRelId);
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
		sb.append(", CPDefinitionOptionRelId=");
		sb.append(CPDefinitionOptionRelId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", key=");
		sb.append(key);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionOptionValueRel toEntityModel() {
		CPDefinitionOptionValueRelImpl cpDefinitionOptionValueRelImpl = new CPDefinitionOptionValueRelImpl();

		if (uuid == null) {
			cpDefinitionOptionValueRelImpl.setUuid("");
		}
		else {
			cpDefinitionOptionValueRelImpl.setUuid(uuid);
		}

		cpDefinitionOptionValueRelImpl.setCPDefinitionOptionValueRelId(CPDefinitionOptionValueRelId);
		cpDefinitionOptionValueRelImpl.setGroupId(groupId);
		cpDefinitionOptionValueRelImpl.setCompanyId(companyId);
		cpDefinitionOptionValueRelImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionOptionValueRelImpl.setUserName("");
		}
		else {
			cpDefinitionOptionValueRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionOptionValueRelImpl.setCreateDate(null);
		}
		else {
			cpDefinitionOptionValueRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionOptionValueRelImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionOptionValueRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		cpDefinitionOptionValueRelImpl.setCPDefinitionOptionRelId(CPDefinitionOptionRelId);

		if (name == null) {
			cpDefinitionOptionValueRelImpl.setName("");
		}
		else {
			cpDefinitionOptionValueRelImpl.setName(name);
		}

		cpDefinitionOptionValueRelImpl.setPriority(priority);

		if (key == null) {
			cpDefinitionOptionValueRelImpl.setKey("");
		}
		else {
			cpDefinitionOptionValueRelImpl.setKey(key);
		}

		cpDefinitionOptionValueRelImpl.resetOriginalValues();

		return cpDefinitionOptionValueRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDefinitionOptionValueRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionOptionRelId = objectInput.readLong();
		name = objectInput.readUTF();

		priority = objectInput.readDouble();
		key = objectInput.readUTF();
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

		objectOutput.writeLong(CPDefinitionOptionValueRelId);

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

		objectOutput.writeLong(CPDefinitionOptionRelId);

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
	}

	public String uuid;
	public long CPDefinitionOptionValueRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionOptionRelId;
	public String name;
	public double priority;
	public String key;
}