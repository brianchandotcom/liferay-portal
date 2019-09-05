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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ListType in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ListTypeCacheModel
	implements CacheModel<ListType>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ListTypeCacheModel)) {
			return false;
		}

		ListTypeCacheModel listTypeCacheModel = (ListTypeCacheModel)obj;

		if ((listTypeId == listTypeCacheModel.listTypeId) &&
			(mvccVersion == listTypeCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, listTypeId);

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
		StringBundler sb = new StringBundler(9);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", listTypeId=");
		sb.append(listTypeId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ListType toEntityModel() {
		ListTypeImpl listTypeImpl = new ListTypeImpl();

		listTypeImpl.setMvccVersion(mvccVersion);
		listTypeImpl.setListTypeId(listTypeId);

		if (name == null) {
			listTypeImpl.setName("");
		}
		else {
			listTypeImpl.setName(name);
		}

		if (type == null) {
			listTypeImpl.setType("");
		}
		else {
			listTypeImpl.setType(type);
		}

		listTypeImpl.resetOriginalValues();

		return listTypeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		listTypeId = objectInput.readLong();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(listTypeId);

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
	public long listTypeId;
	public String name;
	public String type;

}