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

import com.liferay.dataset.view.model.DatasetViewStateEntry;
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
 * The cache model class for representing DatasetViewStateEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DatasetViewStateEntryCacheModel
	implements CacheModel<DatasetViewStateEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DatasetViewStateEntryCacheModel)) {
			return false;
		}

		DatasetViewStateEntryCacheModel datasetViewStateEntryCacheModel =
			(DatasetViewStateEntryCacheModel)object;

		if ((datasetViewStateEntryId ==
				datasetViewStateEntryCacheModel.datasetViewStateEntryId) &&
			(mvccVersion == datasetViewStateEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, datasetViewStateEntryId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", datasetViewStateEntryId=");
		sb.append(datasetViewStateEntryId);
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
		sb.append(", viewState=");
		sb.append(viewState);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DatasetViewStateEntry toEntityModel() {
		DatasetViewStateEntryImpl datasetViewStateEntryImpl =
			new DatasetViewStateEntryImpl();

		datasetViewStateEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			datasetViewStateEntryImpl.setUuid("");
		}
		else {
			datasetViewStateEntryImpl.setUuid(uuid);
		}

		datasetViewStateEntryImpl.setDatasetViewStateEntryId(
			datasetViewStateEntryId);
		datasetViewStateEntryImpl.setCompanyId(companyId);
		datasetViewStateEntryImpl.setUserId(userId);

		if (userName == null) {
			datasetViewStateEntryImpl.setUserName("");
		}
		else {
			datasetViewStateEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			datasetViewStateEntryImpl.setCreateDate(null);
		}
		else {
			datasetViewStateEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			datasetViewStateEntryImpl.setModifiedDate(null);
		}
		else {
			datasetViewStateEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (viewState == null) {
			datasetViewStateEntryImpl.setViewState("");
		}
		else {
			datasetViewStateEntryImpl.setViewState(viewState);
		}

		datasetViewStateEntryImpl.resetOriginalValues();

		return datasetViewStateEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		datasetViewStateEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		viewState = objectInput.readUTF();
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

		objectOutput.writeLong(datasetViewStateEntryId);

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

		if (viewState == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(viewState);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long datasetViewStateEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String viewState;

}