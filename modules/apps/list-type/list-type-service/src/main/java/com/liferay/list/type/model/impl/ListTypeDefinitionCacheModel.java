/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.model.impl;

import com.liferay.list.type.model.ListTypeDefinition;
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
 * The cache model class for representing ListTypeDefinition in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class ListTypeDefinitionCacheModel
	implements CacheModel<ListTypeDefinition>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ListTypeDefinitionCacheModel)) {
			return false;
		}

		ListTypeDefinitionCacheModel listTypeDefinitionCacheModel =
			(ListTypeDefinitionCacheModel)object;

		if ((listTypeDefinitionId ==
				listTypeDefinitionCacheModel.listTypeDefinitionId) &&
			(mvccVersion == listTypeDefinitionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, listTypeDefinitionId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", listTypeDefinitionId=");
		sb.append(listTypeDefinitionId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", system=");
		sb.append(system);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ListTypeDefinition toEntityModel() {
		ListTypeDefinitionImpl listTypeDefinitionImpl =
			new ListTypeDefinitionImpl();

		listTypeDefinitionImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			listTypeDefinitionImpl.setUuid("");
		}
		else {
			listTypeDefinitionImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			listTypeDefinitionImpl.setExternalReferenceCode("");
		}
		else {
			listTypeDefinitionImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		listTypeDefinitionImpl.setListTypeDefinitionId(listTypeDefinitionId);
		listTypeDefinitionImpl.setCompanyId(companyId);
		listTypeDefinitionImpl.setUserId(userId);

		if (userName == null) {
			listTypeDefinitionImpl.setUserName("");
		}
		else {
			listTypeDefinitionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			listTypeDefinitionImpl.setCreateDate(null);
		}
		else {
			listTypeDefinitionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			listTypeDefinitionImpl.setModifiedDate(null);
		}
		else {
			listTypeDefinitionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			listTypeDefinitionImpl.setName("");
		}
		else {
			listTypeDefinitionImpl.setName(name);
		}

		listTypeDefinitionImpl.setSystem(system);

		listTypeDefinitionImpl.resetOriginalValues();

		return listTypeDefinitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		listTypeDefinitionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		system = objectInput.readBoolean();
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

		objectOutput.writeLong(listTypeDefinitionId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(system);
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long listTypeDefinitionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public boolean system;

}