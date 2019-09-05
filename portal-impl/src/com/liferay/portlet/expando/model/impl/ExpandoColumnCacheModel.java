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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ExpandoColumn in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoColumnCacheModel
	implements CacheModel<ExpandoColumn>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExpandoColumnCacheModel)) {
			return false;
		}

		ExpandoColumnCacheModel expandoColumnCacheModel =
			(ExpandoColumnCacheModel)obj;

		if (columnId == expandoColumnCacheModel.columnId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, columnId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{columnId=");
		sb.append(columnId);
		sb.append(", companyId=");
		sb.append(companyId);
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

		expandoColumnImpl.setColumnId(columnId);
		expandoColumnImpl.setCompanyId(companyId);
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
	public void readExternal(ObjectInput objectInput) throws IOException {
		columnId = objectInput.readLong();

		companyId = objectInput.readLong();

		tableId = objectInput.readLong();
		name = objectInput.readUTF();

		type = objectInput.readInt();
		defaultData = objectInput.readUTF();
		typeSettings = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(columnId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(tableId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(type);

		if (defaultData == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(defaultData);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}
	}

	public long columnId;
	public long companyId;
	public long tableId;
	public String name;
	public int type;
	public String defaultData;
	public String typeSettings;

}