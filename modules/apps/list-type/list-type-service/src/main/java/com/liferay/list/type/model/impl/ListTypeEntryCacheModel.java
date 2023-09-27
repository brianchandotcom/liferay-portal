/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.model.impl;

import com.liferay.list.type.model.ListTypeEntry;
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
 * The cache model class for representing ListTypeEntry in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class ListTypeEntryCacheModel
	implements CacheModel<ListTypeEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ListTypeEntryCacheModel)) {
			return false;
		}

		ListTypeEntryCacheModel listTypeEntryCacheModel =
			(ListTypeEntryCacheModel)object;

		if ((listTypeEntryId == listTypeEntryCacheModel.listTypeEntryId) &&
			(mvccVersion == listTypeEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, listTypeEntryId);

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
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", listTypeEntryId=");
		sb.append(listTypeEntryId);
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
		sb.append(", listTypeDefinitionId=");
		sb.append(listTypeDefinitionId);
		sb.append(", key=");
		sb.append(key);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ListTypeEntry toEntityModel() {
		ListTypeEntryImpl listTypeEntryImpl = new ListTypeEntryImpl();

		listTypeEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			listTypeEntryImpl.setUuid("");
		}
		else {
			listTypeEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			listTypeEntryImpl.setExternalReferenceCode("");
		}
		else {
			listTypeEntryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		listTypeEntryImpl.setListTypeEntryId(listTypeEntryId);
		listTypeEntryImpl.setCompanyId(companyId);
		listTypeEntryImpl.setUserId(userId);

		if (userName == null) {
			listTypeEntryImpl.setUserName("");
		}
		else {
			listTypeEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			listTypeEntryImpl.setCreateDate(null);
		}
		else {
			listTypeEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			listTypeEntryImpl.setModifiedDate(null);
		}
		else {
			listTypeEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		listTypeEntryImpl.setListTypeDefinitionId(listTypeDefinitionId);

		if (key == null) {
			listTypeEntryImpl.setKey("");
		}
		else {
			listTypeEntryImpl.setKey(key);
		}

		if (name == null) {
			listTypeEntryImpl.setName("");
		}
		else {
			listTypeEntryImpl.setName(name);
		}

		if (type == null) {
			listTypeEntryImpl.setType("");
		}
		else {
			listTypeEntryImpl.setType(type);
		}

		listTypeEntryImpl.resetOriginalValues();

		return listTypeEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		listTypeEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		listTypeDefinitionId = objectInput.readLong();
		key = objectInput.readUTF();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(listTypeEntryId);

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

		objectOutput.writeLong(listTypeDefinitionId);

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long listTypeEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long listTypeDefinitionId;
	public String key;
	public String name;
	public String type;

}