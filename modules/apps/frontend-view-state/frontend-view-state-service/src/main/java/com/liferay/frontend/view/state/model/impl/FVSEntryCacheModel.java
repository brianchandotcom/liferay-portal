/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.model.impl;

import com.liferay.frontend.view.state.model.FVSEntry;
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
 * The cache model class for representing FVSEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FVSEntryCacheModel
	implements CacheModel<FVSEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FVSEntryCacheModel)) {
			return false;
		}

		FVSEntryCacheModel fvsEntryCacheModel = (FVSEntryCacheModel)object;

		if ((fvsEntryId == fvsEntryCacheModel.fvsEntryId) &&
			(mvccVersion == fvsEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fvsEntryId);

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
		sb.append(", fvsEntryId=");
		sb.append(fvsEntryId);
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
	public FVSEntry toEntityModel() {
		FVSEntryImpl fvsEntryImpl = new FVSEntryImpl();

		fvsEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			fvsEntryImpl.setUuid("");
		}
		else {
			fvsEntryImpl.setUuid(uuid);
		}

		fvsEntryImpl.setFvsEntryId(fvsEntryId);
		fvsEntryImpl.setCompanyId(companyId);
		fvsEntryImpl.setUserId(userId);

		if (userName == null) {
			fvsEntryImpl.setUserName("");
		}
		else {
			fvsEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			fvsEntryImpl.setCreateDate(null);
		}
		else {
			fvsEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fvsEntryImpl.setModifiedDate(null);
		}
		else {
			fvsEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (viewState == null) {
			fvsEntryImpl.setViewState("");
		}
		else {
			fvsEntryImpl.setViewState(viewState);
		}

		fvsEntryImpl.resetOriginalValues();

		return fvsEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		fvsEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		viewState = (String)objectInput.readObject();
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

		objectOutput.writeLong(fvsEntryId);

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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(viewState);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long fvsEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String viewState;

}