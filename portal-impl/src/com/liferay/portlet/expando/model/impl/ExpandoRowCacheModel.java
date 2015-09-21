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
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.expando.model.ExpandoRow;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ExpandoRow in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoRow
 * @generated
 */
@ProviderType
public class ExpandoRowCacheModel implements CacheModel<ExpandoRow>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExpandoRowCacheModel)) {
			return false;
		}

		ExpandoRowCacheModel expandoRowCacheModel = (ExpandoRowCacheModel)obj;

		if (rowId == expandoRowCacheModel.rowId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, rowId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{rowId=");
		sb.append(rowId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", tableId=");
		sb.append(tableId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ExpandoRow toEntityModel() {
		ExpandoRowImpl expandoRowImpl = new ExpandoRowImpl();

		expandoRowImpl.setRowId(rowId);

		if (modifiedDate == Long.MIN_VALUE) {
			expandoRowImpl.setModifiedDate(null);
		}
		else {
			expandoRowImpl.setModifiedDate(new Date(modifiedDate));
		}

		expandoRowImpl.setTableId(tableId);
		expandoRowImpl.setClassPK(classPK);
		expandoRowImpl.setCompanyId(companyId);

		expandoRowImpl.resetOriginalValues();

		return expandoRowImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		rowId = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		tableId = objectInput.readLong();
		classPK = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(rowId);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(tableId);
		objectOutput.writeLong(classPK);
		objectOutput.writeLong(companyId);
	}

	public long rowId;
	public long modifiedDate;
	public long tableId;
	public long classPK;
	public long companyId;
}