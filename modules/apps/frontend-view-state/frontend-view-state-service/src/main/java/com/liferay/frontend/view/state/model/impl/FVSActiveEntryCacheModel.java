/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		sb.append(", fvsEntryId=");
		sb.append(fvsEntryId);
		sb.append(", clayDataSetDisplayId=");
		sb.append(clayDataSetDisplayId);
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

		fvsActiveEntryImpl.setFvsEntryId(fvsEntryId);

		if (clayDataSetDisplayId == null) {
			fvsActiveEntryImpl.setClayDataSetDisplayId("");
		}
		else {
			fvsActiveEntryImpl.setClayDataSetDisplayId(clayDataSetDisplayId);
		}

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

		fvsEntryId = objectInput.readLong();
		clayDataSetDisplayId = objectInput.readUTF();

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

		objectOutput.writeLong(fvsEntryId);

		if (clayDataSetDisplayId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clayDataSetDisplayId);
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
	public long fvsActiveEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long fvsEntryId;
	public String clayDataSetDisplayId;
	public long plid;
	public String portletId;

}