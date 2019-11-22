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

package com.liferay.layout.seo.model.impl;

import com.liferay.layout.seo.model.SiteSEOEntry;
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
 * The cache model class for representing SiteSEOEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SiteSEOEntryCacheModel
	implements CacheModel<SiteSEOEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SiteSEOEntryCacheModel)) {
			return false;
		}

		SiteSEOEntryCacheModel siteSEOEntryCacheModel =
			(SiteSEOEntryCacheModel)obj;

		if ((siteSEOEntryId == siteSEOEntryCacheModel.siteSEOEntryId) &&
			(mvccVersion == siteSEOEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, siteSEOEntryId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", siteSEOEntryId=");
		sb.append(siteSEOEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", openGraphImageFileEntryId=");
		sb.append(openGraphImageFileEntryId);
		sb.append(", openGraphEnabled=");
		sb.append(openGraphEnabled);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SiteSEOEntry toEntityModel() {
		SiteSEOEntryImpl siteSEOEntryImpl = new SiteSEOEntryImpl();

		siteSEOEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			siteSEOEntryImpl.setUuid("");
		}
		else {
			siteSEOEntryImpl.setUuid(uuid);
		}

		siteSEOEntryImpl.setSiteSEOEntryId(siteSEOEntryId);
		siteSEOEntryImpl.setGroupId(groupId);
		siteSEOEntryImpl.setCompanyId(companyId);
		siteSEOEntryImpl.setUserId(userId);

		if (userName == null) {
			siteSEOEntryImpl.setUserName("");
		}
		else {
			siteSEOEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			siteSEOEntryImpl.setCreateDate(null);
		}
		else {
			siteSEOEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			siteSEOEntryImpl.setModifiedDate(null);
		}
		else {
			siteSEOEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		siteSEOEntryImpl.setOpenGraphImageFileEntryId(
			openGraphImageFileEntryId);
		siteSEOEntryImpl.setOpenGraphEnabled(openGraphEnabled);

		siteSEOEntryImpl.resetOriginalValues();

		return siteSEOEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		siteSEOEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		openGraphImageFileEntryId = objectInput.readLong();

		openGraphEnabled = objectInput.readBoolean();
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

		objectOutput.writeLong(siteSEOEntryId);

		objectOutput.writeLong(groupId);

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

		objectOutput.writeLong(openGraphImageFileEntryId);

		objectOutput.writeBoolean(openGraphEnabled);
	}

	public long mvccVersion;
	public String uuid;
	public long siteSEOEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long openGraphImageFileEntryId;
	public boolean openGraphEnabled;

}