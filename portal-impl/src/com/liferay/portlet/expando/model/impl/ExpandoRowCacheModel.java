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

import com.liferay.expando.kernel.model.ExpandoRow;
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
 * The cache model class for representing ExpandoRow in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoRowCacheModel
	implements CacheModel<ExpandoRow>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExpandoRowCacheModel)) {
			return false;
		}

		ExpandoRowCacheModel expandoRowCacheModel = (ExpandoRowCacheModel)obj;

		if ((rowId == expandoRowCacheModel.rowId) &&
			(mvccVersion == expandoRowCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, rowId);

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
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", rowId=");
		sb.append(rowId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", tableId=");
		sb.append(tableId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ExpandoRow toEntityModel() {
		ExpandoRowImpl expandoRowImpl = new ExpandoRowImpl();

		expandoRowImpl.setMvccVersion(mvccVersion);
		expandoRowImpl.setCtCollectionId(ctCollectionId);
		expandoRowImpl.setRowId(rowId);
		expandoRowImpl.setCompanyId(companyId);

		if (modifiedDate == Long.MIN_VALUE) {
			expandoRowImpl.setModifiedDate(null);
		}
		else {
			expandoRowImpl.setModifiedDate(new Date(modifiedDate));
		}

		expandoRowImpl.setTableId(tableId);
		expandoRowImpl.setClassPK(classPK);

		expandoRowImpl.resetOriginalValues();

		return expandoRowImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		rowId = objectInput.readLong();

		companyId = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		tableId = objectInput.readLong();

		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(rowId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(tableId);

		objectOutput.writeLong(classPK);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long rowId;
	public long companyId;
	public long modifiedDate;
	public long tableId;
	public long classPK;

}