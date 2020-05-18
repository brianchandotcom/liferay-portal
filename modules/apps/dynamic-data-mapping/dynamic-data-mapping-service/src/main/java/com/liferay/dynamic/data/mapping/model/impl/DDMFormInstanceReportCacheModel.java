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

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
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
 * The cache model class for representing DDMFormInstanceReport in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceReportCacheModel
	implements CacheModel<DDMFormInstanceReport>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormInstanceReportCacheModel)) {
			return false;
		}

		DDMFormInstanceReportCacheModel ddmFormInstanceReportCacheModel =
			(DDMFormInstanceReportCacheModel)obj;

		if ((formInstanceReportId ==
				ddmFormInstanceReportCacheModel.formInstanceReportId) &&
			(mvccVersion == ddmFormInstanceReportCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, formInstanceReportId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", formInstanceReportId=");
		sb.append(formInstanceReportId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", formInstanceId=");
		sb.append(formInstanceId);
		sb.append(", data=");
		sb.append(data);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMFormInstanceReport toEntityModel() {
		DDMFormInstanceReportImpl ddmFormInstanceReportImpl =
			new DDMFormInstanceReportImpl();

		ddmFormInstanceReportImpl.setMvccVersion(mvccVersion);
		ddmFormInstanceReportImpl.setCtCollectionId(ctCollectionId);
		ddmFormInstanceReportImpl.setFormInstanceReportId(formInstanceReportId);
		ddmFormInstanceReportImpl.setGroupId(groupId);
		ddmFormInstanceReportImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			ddmFormInstanceReportImpl.setCreateDate(null);
		}
		else {
			ddmFormInstanceReportImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddmFormInstanceReportImpl.setModifiedDate(null);
		}
		else {
			ddmFormInstanceReportImpl.setModifiedDate(new Date(modifiedDate));
		}

		ddmFormInstanceReportImpl.setFormInstanceId(formInstanceId);

		if (data == null) {
			ddmFormInstanceReportImpl.setData("");
		}
		else {
			ddmFormInstanceReportImpl.setData(data);
		}

		ddmFormInstanceReportImpl.resetOriginalValues();

		return ddmFormInstanceReportImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		formInstanceReportId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		formInstanceId = objectInput.readLong();
		data = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(formInstanceReportId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(formInstanceId);

		if (data == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(data);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long formInstanceReportId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long formInstanceId;
	public String data;

}