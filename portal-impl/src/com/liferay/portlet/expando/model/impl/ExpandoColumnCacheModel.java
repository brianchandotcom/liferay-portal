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

package com.liferay.portlet.expando.model.impl;

import com.liferay.expando.kernel.model.ExpandoColumn;
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
 * The cache model class for representing ExpandoColumn in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoColumnCacheModel
	implements CacheModel<ExpandoColumn>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ExpandoColumnCacheModel)) {
			return false;
		}

		ExpandoColumnCacheModel expandoColumnCacheModel =
			(ExpandoColumnCacheModel)object;

		if ((columnId == expandoColumnCacheModel.columnId) &&
			(mvccVersion == expandoColumnCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, columnId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", columnId=");
		sb.append(columnId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", tableId=");
		sb.append(tableId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append(", defaultData=");
		sb.append(defaultData);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ExpandoColumn toEntityModel() {
		ExpandoColumnImpl expandoColumnImpl = new ExpandoColumnImpl();

		expandoColumnImpl.setMvccVersion(mvccVersion);
		expandoColumnImpl.setCtCollectionId(ctCollectionId);
		expandoColumnImpl.setColumnId(columnId);
		expandoColumnImpl.setCompanyId(companyId);

		if (modifiedDate == Long.MIN_VALUE) {
			expandoColumnImpl.setModifiedDate(null);
		}
		else {
			expandoColumnImpl.setModifiedDate(new Date(modifiedDate));
		}

		expandoColumnImpl.setTableId(tableId);

		if (name == null) {
			expandoColumnImpl.setName("");
		}
		else {
			expandoColumnImpl.setName(name);
		}

		expandoColumnImpl.setType(type);

		if (defaultData == null) {
			expandoColumnImpl.setDefaultData("");
		}
		else {
			expandoColumnImpl.setDefaultData(defaultData);
		}

		if (typeSettings == null) {
			expandoColumnImpl.setTypeSettings("");
		}
		else {
			expandoColumnImpl.setTypeSettings(typeSettings);
		}

		expandoColumnImpl.resetOriginalValues();

		return expandoColumnImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		columnId = objectInput.readLong();

		companyId = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		tableId = objectInput.readLong();
		name = objectInput.readUTF();

		type = objectInput.readInt();
		defaultData = (String)objectInput.readObject();
		typeSettings = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(columnId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(tableId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(type);

		if (defaultData == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(defaultData);
		}

		if (typeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(typeSettings);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long columnId;
	public long companyId;
	public long modifiedDate;
	public long tableId;
	public String name;
	public int type;
	public String defaultData;
	public String typeSettings;

}