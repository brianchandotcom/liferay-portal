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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.expando.model.ExpandoValue;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ExpandoValue in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValue
 * @generated
 */
@ProviderType
public class ExpandoValueCacheModel implements CacheModel<ExpandoValue>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExpandoValueCacheModel)) {
			return false;
		}

		ExpandoValueCacheModel expandoValueCacheModel = (ExpandoValueCacheModel)obj;

		if (valueId == expandoValueCacheModel.valueId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, valueId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{valueId=");
		sb.append(valueId);
		sb.append(", tableId=");
		sb.append(tableId);
		sb.append(", columnId=");
		sb.append(columnId);
		sb.append(", rowId=");
		sb.append(rowId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", data=");
		sb.append(data);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ExpandoValue toEntityModel() {
		ExpandoValueImpl expandoValueImpl = new ExpandoValueImpl();

		expandoValueImpl.setValueId(valueId);
		expandoValueImpl.setTableId(tableId);
		expandoValueImpl.setColumnId(columnId);
		expandoValueImpl.setRowId(rowId);
		expandoValueImpl.setClassNameId(classNameId);
		expandoValueImpl.setClassPK(classPK);

		if (data == null) {
			expandoValueImpl.setData(StringPool.BLANK);
		}
		else {
			expandoValueImpl.setData(data);
		}

		expandoValueImpl.setCompanyId(companyId);

		expandoValueImpl.resetOriginalValues();

		return expandoValueImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		valueId = objectInput.readLong();
		tableId = objectInput.readLong();
		columnId = objectInput.readLong();
		rowId = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		data = objectInput.readUTF();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(valueId);
		objectOutput.writeLong(tableId);
		objectOutput.writeLong(columnId);
		objectOutput.writeLong(rowId);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);

		if (data == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(data);
		}

		objectOutput.writeLong(companyId);
	}

	public long valueId;
	public long tableId;
	public long columnId;
	public long rowId;
	public long classNameId;
	public long classPK;
	public String data;
	public long companyId;
}