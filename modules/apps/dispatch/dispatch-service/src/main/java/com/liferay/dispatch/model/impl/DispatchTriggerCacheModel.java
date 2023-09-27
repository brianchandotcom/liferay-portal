/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.model.impl;

import com.liferay.dispatch.model.DispatchTrigger;
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
 * The cache model class for representing DispatchTrigger in entity cache.
 *
 * @author Matija Petanjek
 * @generated
 */
public class DispatchTriggerCacheModel
	implements CacheModel<DispatchTrigger>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DispatchTriggerCacheModel)) {
			return false;
		}

		DispatchTriggerCacheModel dispatchTriggerCacheModel =
			(DispatchTriggerCacheModel)object;

		if ((dispatchTriggerId ==
				dispatchTriggerCacheModel.dispatchTriggerId) &&
			(mvccVersion == dispatchTriggerCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, dispatchTriggerId);

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
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", dispatchTriggerId=");
		sb.append(dispatchTriggerId);
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
		sb.append(", active=");
		sb.append(active);
		sb.append(", cronExpression=");
		sb.append(cronExpression);
		sb.append(", dispatchTaskClusterMode=");
		sb.append(dispatchTaskClusterMode);
		sb.append(", dispatchTaskExecutorType=");
		sb.append(dispatchTaskExecutorType);
		sb.append(", dispatchTaskSettings=");
		sb.append(dispatchTaskSettings);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", overlapAllowed=");
		sb.append(overlapAllowed);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", system=");
		sb.append(system);
		sb.append(", timeZoneId=");
		sb.append(timeZoneId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DispatchTrigger toEntityModel() {
		DispatchTriggerImpl dispatchTriggerImpl = new DispatchTriggerImpl();

		dispatchTriggerImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			dispatchTriggerImpl.setUuid("");
		}
		else {
			dispatchTriggerImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			dispatchTriggerImpl.setExternalReferenceCode("");
		}
		else {
			dispatchTriggerImpl.setExternalReferenceCode(externalReferenceCode);
		}

		dispatchTriggerImpl.setDispatchTriggerId(dispatchTriggerId);
		dispatchTriggerImpl.setCompanyId(companyId);
		dispatchTriggerImpl.setUserId(userId);

		if (userName == null) {
			dispatchTriggerImpl.setUserName("");
		}
		else {
			dispatchTriggerImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			dispatchTriggerImpl.setCreateDate(null);
		}
		else {
			dispatchTriggerImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dispatchTriggerImpl.setModifiedDate(null);
		}
		else {
			dispatchTriggerImpl.setModifiedDate(new Date(modifiedDate));
		}

		dispatchTriggerImpl.setActive(active);

		if (cronExpression == null) {
			dispatchTriggerImpl.setCronExpression("");
		}
		else {
			dispatchTriggerImpl.setCronExpression(cronExpression);
		}

		dispatchTriggerImpl.setDispatchTaskClusterMode(dispatchTaskClusterMode);

		if (dispatchTaskExecutorType == null) {
			dispatchTriggerImpl.setDispatchTaskExecutorType("");
		}
		else {
			dispatchTriggerImpl.setDispatchTaskExecutorType(
				dispatchTaskExecutorType);
		}

		if (dispatchTaskSettings == null) {
			dispatchTriggerImpl.setDispatchTaskSettings("");
		}
		else {
			dispatchTriggerImpl.setDispatchTaskSettings(dispatchTaskSettings);
		}

		if (endDate == Long.MIN_VALUE) {
			dispatchTriggerImpl.setEndDate(null);
		}
		else {
			dispatchTriggerImpl.setEndDate(new Date(endDate));
		}

		if (name == null) {
			dispatchTriggerImpl.setName("");
		}
		else {
			dispatchTriggerImpl.setName(name);
		}

		dispatchTriggerImpl.setOverlapAllowed(overlapAllowed);

		if (startDate == Long.MIN_VALUE) {
			dispatchTriggerImpl.setStartDate(null);
		}
		else {
			dispatchTriggerImpl.setStartDate(new Date(startDate));
		}

		dispatchTriggerImpl.setSystem(system);

		if (timeZoneId == null) {
			dispatchTriggerImpl.setTimeZoneId("");
		}
		else {
			dispatchTriggerImpl.setTimeZoneId(timeZoneId);
		}

		dispatchTriggerImpl.resetOriginalValues();

		return dispatchTriggerImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		dispatchTriggerId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		active = objectInput.readBoolean();
		cronExpression = objectInput.readUTF();

		dispatchTaskClusterMode = objectInput.readInt();
		dispatchTaskExecutorType = objectInput.readUTF();
		dispatchTaskSettings = (String)objectInput.readObject();
		endDate = objectInput.readLong();
		name = objectInput.readUTF();

		overlapAllowed = objectInput.readBoolean();
		startDate = objectInput.readLong();

		system = objectInput.readBoolean();
		timeZoneId = objectInput.readUTF();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(dispatchTriggerId);

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

		objectOutput.writeBoolean(active);

		if (cronExpression == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(cronExpression);
		}

		objectOutput.writeInt(dispatchTaskClusterMode);

		if (dispatchTaskExecutorType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(dispatchTaskExecutorType);
		}

		if (dispatchTaskSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(dispatchTaskSettings);
		}

		objectOutput.writeLong(endDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(overlapAllowed);
		objectOutput.writeLong(startDate);

		objectOutput.writeBoolean(system);

		if (timeZoneId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(timeZoneId);
		}
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long dispatchTriggerId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean active;
	public String cronExpression;
	public int dispatchTaskClusterMode;
	public String dispatchTaskExecutorType;
	public String dispatchTaskSettings;
	public long endDate;
	public String name;
	public boolean overlapAllowed;
	public long startDate;
	public boolean system;
	public String timeZoneId;

}