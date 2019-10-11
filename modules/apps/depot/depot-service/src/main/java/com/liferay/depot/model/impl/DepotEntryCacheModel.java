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

package com.liferay.depot.model.impl;

import com.liferay.depot.model.DepotEntry;
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
 * The cache model class for representing DepotEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DepotEntryCacheModel
	implements CacheModel<DepotEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DepotEntryCacheModel)) {
			return false;
		}

		DepotEntryCacheModel depotEntryCacheModel = (DepotEntryCacheModel)obj;

		if ((depotEntryId == depotEntryCacheModel.depotEntryId) &&
			(mvccVersion == depotEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, depotEntryId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", depotEntryId=");
		sb.append(depotEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DepotEntry toEntityModel() {
		DepotEntryImpl depotEntryImpl = new DepotEntryImpl();

		depotEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			depotEntryImpl.setUuid("");
		}
		else {
			depotEntryImpl.setUuid(uuid);
		}

		depotEntryImpl.setDepotEntryId(depotEntryId);
		depotEntryImpl.setGroupId(groupId);
		depotEntryImpl.setCompanyId(companyId);
		depotEntryImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			depotEntryImpl.setCreateDate(null);
		}
		else {
			depotEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			depotEntryImpl.setModifiedDate(null);
		}
		else {
			depotEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		depotEntryImpl.resetOriginalValues();

		return depotEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		depotEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
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

		objectOutput.writeLong(depotEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
	}

	public long mvccVersion;
	public String uuid;
	public long depotEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;

}