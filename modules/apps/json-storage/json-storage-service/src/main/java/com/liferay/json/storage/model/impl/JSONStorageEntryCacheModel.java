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

package com.liferay.json.storage.model.impl;

import com.liferay.json.storage.model.JSONStorageEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing JSONStorageEntry in entity cache.
 *
 * @author Preston Crary
 * @generated
 */
public class JSONStorageEntryCacheModel
	implements CacheModel<JSONStorageEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof JSONStorageEntryCacheModel)) {
			return false;
		}

		JSONStorageEntryCacheModel jsonStorageEntryCacheModel =
			(JSONStorageEntryCacheModel)object;

		if ((jsonStorageEntryId ==
				jsonStorageEntryCacheModel.jsonStorageEntryId) &&
			(mvccVersion == jsonStorageEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, jsonStorageEntryId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", jsonStorageEntryId=");
		sb.append(jsonStorageEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", parentJSONStorageEntryId=");
		sb.append(parentJSONStorageEntryId);
		sb.append(", index=");
		sb.append(index);
		sb.append(", key=");
		sb.append(key);
		sb.append(", type=");
		sb.append(type);
		sb.append(", valueLong=");
		sb.append(valueLong);
		sb.append(", valueString=");
		sb.append(valueString);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public JSONStorageEntry toEntityModel() {
		JSONStorageEntryImpl jsonStorageEntryImpl = new JSONStorageEntryImpl();

		jsonStorageEntryImpl.setMvccVersion(mvccVersion);
		jsonStorageEntryImpl.setCtCollectionId(ctCollectionId);
		jsonStorageEntryImpl.setJsonStorageEntryId(jsonStorageEntryId);
		jsonStorageEntryImpl.setCompanyId(companyId);
		jsonStorageEntryImpl.setClassNameId(classNameId);
		jsonStorageEntryImpl.setClassPK(classPK);
		jsonStorageEntryImpl.setParentJSONStorageEntryId(
			parentJSONStorageEntryId);
		jsonStorageEntryImpl.setIndex(index);

		if (key == null) {
			jsonStorageEntryImpl.setKey("");
		}
		else {
			jsonStorageEntryImpl.setKey(key);
		}

		jsonStorageEntryImpl.setType(type);
		jsonStorageEntryImpl.setValueLong(valueLong);

		if (valueString == null) {
			jsonStorageEntryImpl.setValueString("");
		}
		else {
			jsonStorageEntryImpl.setValueString(valueString);
		}

		jsonStorageEntryImpl.resetOriginalValues();

		return jsonStorageEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		jsonStorageEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		parentJSONStorageEntryId = objectInput.readLong();

		index = objectInput.readInt();
		key = objectInput.readUTF();

		type = objectInput.readInt();

		valueLong = objectInput.readLong();
		valueString = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(jsonStorageEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(parentJSONStorageEntryId);

		objectOutput.writeInt(index);

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		objectOutput.writeInt(type);

		objectOutput.writeLong(valueLong);

		if (valueString == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(valueString);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long jsonStorageEntryId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public long parentJSONStorageEntryId;
	public int index;
	public String key;
	public int type;
	public long valueLong;
	public String valueString;

}