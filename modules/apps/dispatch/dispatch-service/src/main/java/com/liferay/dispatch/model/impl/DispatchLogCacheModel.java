/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.model.impl;

import com.liferay.dispatch.model.DispatchLog;
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
 * The cache model class for representing DispatchLog in entity cache.
 *
 * @author Matija Petanjek
 * @generated
 */
public class DispatchLogCacheModel
	implements CacheModel<DispatchLog>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DispatchLogCacheModel)) {
			return false;
		}

		DispatchLogCacheModel dispatchLogCacheModel =
			(DispatchLogCacheModel)object;

		if ((dispatchLogId == dispatchLogCacheModel.dispatchLogId) &&
			(mvccVersion == dispatchLogCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, dispatchLogId);

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
		sb.append(", dispatchLogId=");
		sb.append(dispatchLogId);
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
		sb.append(", dispatchTriggerId=");
		sb.append(dispatchTriggerId);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", error=");
		sb.append(error);
		sb.append(", output=");
		sb.append(output);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DispatchLog toEntityModel() {
		DispatchLogImpl dispatchLogImpl = new DispatchLogImpl();

		dispatchLogImpl.setMvccVersion(mvccVersion);
		dispatchLogImpl.setDispatchLogId(dispatchLogId);
		dispatchLogImpl.setCompanyId(companyId);
		dispatchLogImpl.setUserId(userId);

		if (userName == null) {
			dispatchLogImpl.setUserName("");
		}
		else {
			dispatchLogImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			dispatchLogImpl.setCreateDate(null);
		}
		else {
			dispatchLogImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dispatchLogImpl.setModifiedDate(null);
		}
		else {
			dispatchLogImpl.setModifiedDate(new Date(modifiedDate));
		}

		dispatchLogImpl.setDispatchTriggerId(dispatchTriggerId);

		if (endDate == Long.MIN_VALUE) {
			dispatchLogImpl.setEndDate(null);
		}
		else {
			dispatchLogImpl.setEndDate(new Date(endDate));
		}

		dispatchLogImpl.setError(error);
		dispatchLogImpl.setOutput(output);

		if (startDate == Long.MIN_VALUE) {
			dispatchLogImpl.setStartDate(null);
		}
		else {
			dispatchLogImpl.setStartDate(new Date(startDate));
		}

		dispatchLogImpl.setStatus(status);

		dispatchLogImpl.resetOriginalValues();

		return dispatchLogImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		dispatchLogId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		dispatchTriggerId = objectInput.readLong();
		endDate = objectInput.readLong();
		error = (String)objectInput.readObject();
		output = (String)objectInput.readObject();
		startDate = objectInput.readLong();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(dispatchLogId);

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

		objectOutput.writeLong(dispatchTriggerId);
		objectOutput.writeLong(endDate);

		if (error == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(error);
		}

		if (output == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(output);
		}

		objectOutput.writeLong(startDate);

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long dispatchLogId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long dispatchTriggerId;
	public long endDate;
	public String error;
	public String output;
	public long startDate;
	public int status;

}