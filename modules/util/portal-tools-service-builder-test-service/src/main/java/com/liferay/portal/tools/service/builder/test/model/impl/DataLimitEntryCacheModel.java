/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.DataLimitEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DataLimitEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DataLimitEntryCacheModel
	implements CacheModel<DataLimitEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataLimitEntryCacheModel)) {
			return false;
		}

		DataLimitEntryCacheModel dataLimitEntryCacheModel =
			(DataLimitEntryCacheModel)object;

		if (dataLimitEntryId == dataLimitEntryCacheModel.dataLimitEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, dataLimitEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{dataLimitEntryId=");
		sb.append(dataLimitEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DataLimitEntry toEntityModel() {
		DataLimitEntryImpl dataLimitEntryImpl = new DataLimitEntryImpl();

		dataLimitEntryImpl.setDataLimitEntryId(dataLimitEntryId);
		dataLimitEntryImpl.setCompanyId(companyId);
		dataLimitEntryImpl.setUserId(userId);

		if (userName == null) {
			dataLimitEntryImpl.setUserName("");
		}
		else {
			dataLimitEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			dataLimitEntryImpl.setCreateDate(null);
		}
		else {
			dataLimitEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dataLimitEntryImpl.setModifiedDate(null);
		}
		else {
			dataLimitEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		dataLimitEntryImpl.resetOriginalValues();

		return dataLimitEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		dataLimitEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(dataLimitEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
	}

	public long dataLimitEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;

}