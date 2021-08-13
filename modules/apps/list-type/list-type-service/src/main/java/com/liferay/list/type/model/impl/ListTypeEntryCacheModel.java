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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", listTypeEntryId=");
		sb.append(listTypeEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", listTypeDefinitionId=");
		sb.append(listTypeDefinitionId);
		sb.append(", label=");
		sb.append(label);
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

		listTypeEntryImpl.setListTypeEntryId(listTypeEntryId);
		listTypeEntryImpl.setCompanyId(companyId);
		listTypeEntryImpl.setListTypeDefinitionId(listTypeDefinitionId);

		if (label == null) {
			listTypeEntryImpl.setLabel("");
		}
		else {
			listTypeEntryImpl.setLabel(label);
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

		listTypeEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		listTypeDefinitionId = objectInput.readLong();
		label = objectInput.readUTF();
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

		objectOutput.writeLong(listTypeEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(listTypeDefinitionId);

		if (label == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(label);
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
	public long listTypeEntryId;
	public long companyId;
	public long listTypeDefinitionId;
	public String label;
	public String name;
	public String type;

}