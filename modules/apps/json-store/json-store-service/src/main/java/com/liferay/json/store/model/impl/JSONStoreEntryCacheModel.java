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

package com.liferay.json.store.model.impl;

import com.liferay.json.store.model.JSONStoreEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing JSONStoreEntry in entity cache.
 *
 * @author Preston Crary
 * @generated
 */
public class JSONStoreEntryCacheModel
	implements CacheModel<JSONStoreEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof JSONStoreEntryCacheModel)) {
			return false;
		}

		JSONStoreEntryCacheModel jsonStoreEntryCacheModel =
			(JSONStoreEntryCacheModel)object;

		if ((jsonStoreEntryId == jsonStoreEntryCacheModel.jsonStoreEntryId) &&
			(mvccVersion == jsonStoreEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, jsonStoreEntryId);

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
		sb.append(", jsonStoreEntryId=");
		sb.append(jsonStoreEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", parentJSONStoreEntryId=");
		sb.append(parentJSONStoreEntryId);
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
	public JSONStoreEntry toEntityModel() {
		JSONStoreEntryImpl jsonStoreEntryImpl = new JSONStoreEntryImpl();

		jsonStoreEntryImpl.setMvccVersion(mvccVersion);
		jsonStoreEntryImpl.setCtCollectionId(ctCollectionId);
		jsonStoreEntryImpl.setJsonStoreEntryId(jsonStoreEntryId);
		jsonStoreEntryImpl.setCompanyId(companyId);
		jsonStoreEntryImpl.setClassNameId(classNameId);
		jsonStoreEntryImpl.setClassPK(classPK);
		jsonStoreEntryImpl.setParentJSONStoreEntryId(parentJSONStoreEntryId);
		jsonStoreEntryImpl.setIndex(index);

		if (key == null) {
			jsonStoreEntryImpl.setKey("");
		}
		else {
			jsonStoreEntryImpl.setKey(key);
		}

		jsonStoreEntryImpl.setType(type);
		jsonStoreEntryImpl.setValueLong(valueLong);

		if (valueString == null) {
			jsonStoreEntryImpl.setValueString("");
		}
		else {
			jsonStoreEntryImpl.setValueString(valueString);
		}

		jsonStoreEntryImpl.resetOriginalValues();

		return jsonStoreEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		jsonStoreEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		parentJSONStoreEntryId = objectInput.readLong();

		index = objectInput.readInt();
		key = objectInput.readUTF();

		type = objectInput.readInt();

		valueLong = objectInput.readLong();
		valueString = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(jsonStoreEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(parentJSONStoreEntryId);

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
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(valueString);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long jsonStoreEntryId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public long parentJSONStoreEntryId;
	public int index;
	public String key;
	public int type;
	public long valueLong;
	public String valueString;

}