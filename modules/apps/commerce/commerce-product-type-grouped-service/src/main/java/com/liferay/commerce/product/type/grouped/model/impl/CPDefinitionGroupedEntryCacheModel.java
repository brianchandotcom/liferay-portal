/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.model.impl;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
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
 * The cache model class for representing CPDefinitionGroupedEntry in entity cache.
 *
 * @author Andrea Di Giorgi
 * @generated
 */
public class CPDefinitionGroupedEntryCacheModel
	implements CacheModel<CPDefinitionGroupedEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionGroupedEntryCacheModel)) {
			return false;
		}

		CPDefinitionGroupedEntryCacheModel cpDefinitionGroupedEntryCacheModel =
			(CPDefinitionGroupedEntryCacheModel)object;

		if ((CPDefinitionGroupedEntryId ==
				cpDefinitionGroupedEntryCacheModel.
					CPDefinitionGroupedEntryId) &&
			(mvccVersion == cpDefinitionGroupedEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPDefinitionGroupedEntryId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
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
		sb.append(", entryCProductId=");
		sb.append(entryCProductId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionGroupedEntry toEntityModel() {
		CPDefinitionGroupedEntryImpl cpDefinitionGroupedEntryImpl =
			new CPDefinitionGroupedEntryImpl();

		cpDefinitionGroupedEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			cpDefinitionGroupedEntryImpl.setUuid("");
		}
		else {
			cpDefinitionGroupedEntryImpl.setUuid(uuid);
		}

		cpDefinitionGroupedEntryImpl.setCPDefinitionGroupedEntryId(
			CPDefinitionGroupedEntryId);
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
			cpDefinitionGroupedEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		cpDefinitionGroupedEntryImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionGroupedEntryImpl.setEntryCProductId(entryCProductId);
		cpDefinitionGroupedEntryImpl.setPriority(priority);
		cpDefinitionGroupedEntryImpl.setQuantity(quantity);

		cpDefinitionGroupedEntryImpl.resetOriginalValues();

		return cpDefinitionGroupedEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPDefinitionGroupedEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		entryCProductId = objectInput.readLong();

		priority = objectInput.readDouble();

		quantity = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		objectOutput.writeLong(entryCProductId);

		objectOutput.writeDouble(priority);

		objectOutput.writeInt(quantity);
	}

	public long mvccVersion;
	public String uuid;
	public long CPDefinitionGroupedEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public long entryCProductId;
	public double priority;
	public int quantity;

}