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

package com.liferay.commerce.product.type.grouped.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDefinitionGroupedEntry in entity cache.
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntry
 * @generated
 */
@ProviderType
public class CPDefinitionGroupedEntryCacheModel implements CacheModel<CPDefinitionGroupedEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionGroupedEntryCacheModel)) {
			return false;
		}

		CPDefinitionGroupedEntryCacheModel cpDefinitionGroupedEntryCacheModel = (CPDefinitionGroupedEntryCacheModel)obj;

		if (CPDefinitionGroupedEntryId == cpDefinitionGroupedEntryCacheModel.CPDefinitionGroupedEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionGroupedEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionGroupedEntryId=");
		sb.append(CPDefinitionGroupedEntryId);
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
		sb.append(", entryCPDefinitionId=");
		sb.append(entryCPDefinitionId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionGroupedEntry toEntityModel() {
		CPDefinitionGroupedEntryImpl cpDefinitionGroupedEntryImpl = new CPDefinitionGroupedEntryImpl();

		if (uuid == null) {
			cpDefinitionGroupedEntryImpl.setUuid("");
		}
		else {
			cpDefinitionGroupedEntryImpl.setUuid(uuid);
		}

		cpDefinitionGroupedEntryImpl.setCPDefinitionGroupedEntryId(CPDefinitionGroupedEntryId);
		cpDefinitionGroupedEntryImpl.setGroupId(groupId);
		cpDefinitionGroupedEntryImpl.setCompanyId(companyId);
		cpDefinitionGroupedEntryImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionGroupedEntryImpl.setUserName("");
		}
		else {
			cpDefinitionGroupedEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionGroupedEntryImpl.setCreateDate(null);
		}
		else {
			cpDefinitionGroupedEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionGroupedEntryImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionGroupedEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpDefinitionGroupedEntryImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionGroupedEntryImpl.setEntryCPDefinitionId(entryCPDefinitionId);
		cpDefinitionGroupedEntryImpl.setPriority(priority);
		cpDefinitionGroupedEntryImpl.setQuantity(quantity);

		cpDefinitionGroupedEntryImpl.resetOriginalValues();

		return cpDefinitionGroupedEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDefinitionGroupedEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		entryCPDefinitionId = objectInput.readLong();

		priority = objectInput.readDouble();

		quantity = objectInput.readInt();
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

		objectOutput.writeLong(CPDefinitionGroupedEntryId);

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

		objectOutput.writeLong(entryCPDefinitionId);

		objectOutput.writeDouble(priority);

		objectOutput.writeInt(quantity);
	}

	public String uuid;
	public long CPDefinitionGroupedEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public long entryCPDefinitionId;
	public double priority;
	public int quantity;
}