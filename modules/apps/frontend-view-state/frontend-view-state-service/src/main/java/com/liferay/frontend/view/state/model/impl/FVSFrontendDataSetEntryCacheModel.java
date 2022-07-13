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

import com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry;
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
 * The cache model class for representing FVSFrontendDataSetEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FVSFrontendDataSetEntryCacheModel
	implements CacheModel<FVSFrontendDataSetEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FVSFrontendDataSetEntryCacheModel)) {
			return false;
		}

		FVSFrontendDataSetEntryCacheModel fvsFrontendDataSetEntryCacheModel =
			(FVSFrontendDataSetEntryCacheModel)object;

		if ((fvsFrontendDataSetEntryId ==
				fvsFrontendDataSetEntryCacheModel.fvsFrontendDataSetEntryId) &&
			(mvccVersion == fvsFrontendDataSetEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fvsFrontendDataSetEntryId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", fvsFrontendDataSetEntryId=");
		sb.append(fvsFrontendDataSetEntryId);
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
		sb.append(", fdsName=");
		sb.append(fdsName);
		sb.append(", fvsEntryId=");
		sb.append(fvsEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FVSFrontendDataSetEntry toEntityModel() {
		FVSFrontendDataSetEntryImpl fvsFrontendDataSetEntryImpl =
			new FVSFrontendDataSetEntryImpl();

		fvsFrontendDataSetEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			fvsFrontendDataSetEntryImpl.setUuid("");
		}
		else {
			fvsFrontendDataSetEntryImpl.setUuid(uuid);
		}

		fvsFrontendDataSetEntryImpl.setFvsFrontendDataSetEntryId(
			fvsFrontendDataSetEntryId);
		fvsFrontendDataSetEntryImpl.setCompanyId(companyId);
		fvsFrontendDataSetEntryImpl.setUserId(userId);

		if (userName == null) {
			fvsFrontendDataSetEntryImpl.setUserName("");
		}
		else {
			fvsFrontendDataSetEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			fvsFrontendDataSetEntryImpl.setCreateDate(null);
		}
		else {
			fvsFrontendDataSetEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fvsFrontendDataSetEntryImpl.setModifiedDate(null);
		}
		else {
			fvsFrontendDataSetEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (fdsName == null) {
			fvsFrontendDataSetEntryImpl.setFdsName("");
		}
		else {
			fvsFrontendDataSetEntryImpl.setFdsName(fdsName);
		}

		fvsFrontendDataSetEntryImpl.setFvsEntryId(fvsEntryId);

		if (name == null) {
			fvsFrontendDataSetEntryImpl.setName("");
		}
		else {
			fvsFrontendDataSetEntryImpl.setName(name);
		}

		fvsFrontendDataSetEntryImpl.setPlid(plid);

		if (portletId == null) {
			fvsFrontendDataSetEntryImpl.setPortletId("");
		}
		else {
			fvsFrontendDataSetEntryImpl.setPortletId(portletId);
		}

		fvsFrontendDataSetEntryImpl.resetOriginalValues();

		return fvsFrontendDataSetEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		fvsFrontendDataSetEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		fdsName = objectInput.readUTF();

		fvsEntryId = objectInput.readLong();
		name = objectInput.readUTF();

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

		objectOutput.writeLong(fvsFrontendDataSetEntryId);

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

		if (fdsName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fdsName);
		}

		objectOutput.writeLong(fvsEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

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
	public long fvsFrontendDataSetEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String fdsName;
	public long fvsEntryId;
	public String name;
	public long plid;
	public String portletId;

}