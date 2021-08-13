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

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

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
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", listTypeDefinitionId=");
		sb.append(listTypeDefinitionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", label=");
		sb.append(label);
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

		listTypeDefinitionImpl.setListTypeDefinitionId(listTypeDefinitionId);
		listTypeDefinitionImpl.setCompanyId(companyId);

		if (label == null) {
			listTypeDefinitionImpl.setLabel("");
		}
		else {
			listTypeDefinitionImpl.setLabel(label);
		}

		listTypeDefinitionImpl.resetOriginalValues();

		return listTypeDefinitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		listTypeDefinitionId = objectInput.readLong();

		companyId = objectInput.readLong();
		label = objectInput.readUTF();
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

		objectOutput.writeLong(listTypeDefinitionId);

		objectOutput.writeLong(companyId);

		if (label == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(label);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long listTypeDefinitionId;
	public long companyId;
	public String label;

}