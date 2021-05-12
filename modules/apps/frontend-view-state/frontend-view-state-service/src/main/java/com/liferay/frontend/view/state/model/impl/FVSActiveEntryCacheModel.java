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

package com.liferay.frontend.view.state.model.impl;

import com.liferay.frontend.view.state.model.FVSActiveEntry;
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
 * The cache model class for representing FVSActiveEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FVSActiveEntryCacheModel
	implements CacheModel<FVSActiveEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FVSActiveEntryCacheModel)) {
			return false;
		}

		FVSActiveEntryCacheModel fvsActiveEntryCacheModel =
			(FVSActiveEntryCacheModel)object;

		if ((fvsActiveEntryId == fvsActiveEntryCacheModel.fvsActiveEntryId) &&
			(mvccVersion == fvsActiveEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fvsActiveEntryId);

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
		sb.append(", fvsActiveEntryId=");
		sb.append(fvsActiveEntryId);
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
		sb.append(", fvsEntryId=");
		sb.append(fvsEntryId);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FVSActiveEntry toEntityModel() {
		FVSActiveEntryImpl fvsActiveEntryImpl = new FVSActiveEntryImpl();

		fvsActiveEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			fvsActiveEntryImpl.setUuid("");
		}
		else {
			fvsActiveEntryImpl.setUuid(uuid);
		}

		fvsActiveEntryImpl.setFvsActiveEntryId(fvsActiveEntryId);
		fvsActiveEntryImpl.setCompanyId(companyId);
		fvsActiveEntryImpl.setUserId(userId);

		if (userName == null) {
			fvsActiveEntryImpl.setUserName("");
		}
		else {
			fvsActiveEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			fvsActiveEntryImpl.setCreateDate(null);
		}
		else {
			fvsActiveEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fvsActiveEntryImpl.setModifiedDate(null);
		}
		else {
			fvsActiveEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (datasetDisplayId == null) {
			fvsActiveEntryImpl.setDatasetDisplayId("");
		}
		else {
			fvsActiveEntryImpl.setDatasetDisplayId(datasetDisplayId);
		}

		fvsActiveEntryImpl.setFvsEntryId(fvsEntryId);
		fvsActiveEntryImpl.setPlid(plid);

		if (portletId == null) {
			fvsActiveEntryImpl.setPortletId("");
		}
		else {
			fvsActiveEntryImpl.setPortletId(portletId);
		}

		fvsActiveEntryImpl.resetOriginalValues();

		return fvsActiveEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		fvsActiveEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		datasetDisplayId = objectInput.readUTF();

		fvsEntryId = objectInput.readLong();

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

		objectOutput.writeLong(fvsActiveEntryId);

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

		objectOutput.writeLong(fvsEntryId);

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
	public long fvsActiveEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String datasetDisplayId;
	public long fvsEntryId;
	public long plid;
	public String portletId;

}