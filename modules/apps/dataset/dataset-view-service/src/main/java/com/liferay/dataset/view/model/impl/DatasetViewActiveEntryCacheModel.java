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

package com.liferay.dataset.view.model.impl;

import com.liferay.dataset.view.model.DatasetViewActiveEntry;
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
 * The cache model class for representing DatasetViewActiveEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DatasetViewActiveEntryCacheModel
	implements CacheModel<DatasetViewActiveEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DatasetViewActiveEntryCacheModel)) {
			return false;
		}

		DatasetViewActiveEntryCacheModel datasetViewActiveEntryCacheModel =
			(DatasetViewActiveEntryCacheModel)object;

		if ((datasetViewActiveEntryId ==
				datasetViewActiveEntryCacheModel.datasetViewActiveEntryId) &&
			(mvccVersion == datasetViewActiveEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, datasetViewActiveEntryId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", datasetViewActiveEntryId=");
		sb.append(datasetViewActiveEntryId);
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
		sb.append(", datasetDisplayId=");
		sb.append(datasetDisplayId);
		sb.append(", datasetViewStateEntryId=");
		sb.append(datasetViewStateEntryId);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DatasetViewActiveEntry toEntityModel() {
		DatasetViewActiveEntryImpl datasetViewActiveEntryImpl =
			new DatasetViewActiveEntryImpl();

		datasetViewActiveEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			datasetViewActiveEntryImpl.setUuid("");
		}
		else {
			datasetViewActiveEntryImpl.setUuid(uuid);
		}

		datasetViewActiveEntryImpl.setDatasetViewActiveEntryId(
			datasetViewActiveEntryId);
		datasetViewActiveEntryImpl.setCompanyId(companyId);
		datasetViewActiveEntryImpl.setUserId(userId);

		if (userName == null) {
			datasetViewActiveEntryImpl.setUserName("");
		}
		else {
			datasetViewActiveEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			datasetViewActiveEntryImpl.setCreateDate(null);
		}
		else {
			datasetViewActiveEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			datasetViewActiveEntryImpl.setModifiedDate(null);
		}
		else {
			datasetViewActiveEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (datasetDisplayId == null) {
			datasetViewActiveEntryImpl.setDatasetDisplayId("");
		}
		else {
			datasetViewActiveEntryImpl.setDatasetDisplayId(datasetDisplayId);
		}

		datasetViewActiveEntryImpl.setDatasetViewStateEntryId(
			datasetViewStateEntryId);
		datasetViewActiveEntryImpl.setPlid(plid);

		if (portletId == null) {
			datasetViewActiveEntryImpl.setPortletId("");
		}
		else {
			datasetViewActiveEntryImpl.setPortletId(portletId);
		}

		datasetViewActiveEntryImpl.resetOriginalValues();

		return datasetViewActiveEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		datasetViewActiveEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		datasetDisplayId = objectInput.readUTF();

		datasetViewStateEntryId = objectInput.readLong();

		plid = objectInput.readLong();
		portletId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(datasetViewActiveEntryId);

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

		if (datasetDisplayId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(datasetDisplayId);
		}

		objectOutput.writeLong(datasetViewStateEntryId);

		objectOutput.writeLong(plid);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long datasetViewActiveEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String datasetDisplayId;
	public long datasetViewStateEntryId;
	public long plid;
	public String portletId;

}