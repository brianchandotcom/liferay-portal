/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.model.impl;

import com.liferay.frontend.view.state.model.FVSCustomEntry;
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
 * The cache model class for representing FVSCustomEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FVSCustomEntryCacheModel
	implements CacheModel<FVSCustomEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FVSCustomEntryCacheModel)) {
			return false;
		}

		FVSCustomEntryCacheModel fvsCustomEntryCacheModel =
			(FVSCustomEntryCacheModel)object;

		if ((fvsCustomEntryId == fvsCustomEntryCacheModel.fvsCustomEntryId) &&
			(mvccVersion == fvsCustomEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fvsCustomEntryId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", fvsCustomEntryId=");
		sb.append(fvsCustomEntryId);
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
		sb.append(", fvsEntryId=");
		sb.append(fvsEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FVSCustomEntry toEntityModel() {
		FVSCustomEntryImpl fvsCustomEntryImpl = new FVSCustomEntryImpl();

		fvsCustomEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			fvsCustomEntryImpl.setUuid("");
		}
		else {
			fvsCustomEntryImpl.setUuid(uuid);
		}

		fvsCustomEntryImpl.setFvsCustomEntryId(fvsCustomEntryId);
		fvsCustomEntryImpl.setCompanyId(companyId);
		fvsCustomEntryImpl.setUserId(userId);

		if (userName == null) {
			fvsCustomEntryImpl.setUserName("");
		}
		else {
			fvsCustomEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			fvsCustomEntryImpl.setCreateDate(null);
		}
		else {
			fvsCustomEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fvsCustomEntryImpl.setModifiedDate(null);
		}
		else {
			fvsCustomEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		fvsCustomEntryImpl.setFvsEntryId(fvsEntryId);

		if (name == null) {
			fvsCustomEntryImpl.setName("");
		}
		else {
			fvsCustomEntryImpl.setName(name);
		}

		fvsCustomEntryImpl.resetOriginalValues();

		return fvsCustomEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		fvsCustomEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		fvsEntryId = objectInput.readLong();
		name = objectInput.readUTF();
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

		objectOutput.writeLong(fvsCustomEntryId);

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

		objectOutput.writeLong(fvsEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long fvsCustomEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long fvsEntryId;
	public String name;

}