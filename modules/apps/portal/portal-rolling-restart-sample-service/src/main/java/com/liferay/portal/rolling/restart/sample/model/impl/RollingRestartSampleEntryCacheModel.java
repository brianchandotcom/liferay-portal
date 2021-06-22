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

package com.liferay.portal.rolling.restart.sample.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.rolling.restart.sample.model.RollingRestartSampleEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RollingRestartSampleEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RollingRestartSampleEntryCacheModel
	implements CacheModel<RollingRestartSampleEntry>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RollingRestartSampleEntryCacheModel)) {
			return false;
		}

		RollingRestartSampleEntryCacheModel
			rollingRestartSampleEntryCacheModel =
				(RollingRestartSampleEntryCacheModel)object;

		if ((entryId == rollingRestartSampleEntryCacheModel.entryId) &&
			(mvccVersion == rollingRestartSampleEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, entryId);

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
		sb.append(", entryId=");
		sb.append(entryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RollingRestartSampleEntry toEntityModel() {
		RollingRestartSampleEntryImpl rollingRestartSampleEntryImpl =
			new RollingRestartSampleEntryImpl();

		rollingRestartSampleEntryImpl.setMvccVersion(mvccVersion);
		rollingRestartSampleEntryImpl.setEntryId(entryId);
		rollingRestartSampleEntryImpl.setCompanyId(companyId);
		rollingRestartSampleEntryImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			rollingRestartSampleEntryImpl.setCreateDate(null);
		}
		else {
			rollingRestartSampleEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			rollingRestartSampleEntryImpl.setModifiedDate(null);
		}
		else {
			rollingRestartSampleEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (name == null) {
			rollingRestartSampleEntryImpl.setName("");
		}
		else {
			rollingRestartSampleEntryImpl.setName(name);
		}

		if (description == null) {
			rollingRestartSampleEntryImpl.setDescription("");
		}
		else {
			rollingRestartSampleEntryImpl.setDescription(description);
		}

		rollingRestartSampleEntryImpl.setStatus(status);

		rollingRestartSampleEntryImpl.resetOriginalValues();

		return rollingRestartSampleEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		entryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(entryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long entryId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public int status;

}