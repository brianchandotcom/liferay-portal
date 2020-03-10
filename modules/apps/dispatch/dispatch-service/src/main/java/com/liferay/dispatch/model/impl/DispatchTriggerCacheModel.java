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
 * @author Alessio Antonio Rendina
 * @generated
 */
public class DispatchTriggerCacheModel
	implements CacheModel<DispatchTrigger>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DispatchTriggerCacheModel)) {
			return false;
		}

		DispatchTriggerCacheModel dispatchTriggerCacheModel =
			(DispatchTriggerCacheModel)obj;

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
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
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", system=");
		sb.append(system);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DispatchTrigger toEntityModel() {
		DispatchTriggerImpl dispatchTriggerImpl = new DispatchTriggerImpl();

		dispatchTriggerImpl.setMvccVersion(mvccVersion);
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

		if (startDate == Long.MIN_VALUE) {
			dispatchTriggerImpl.setStartDate(null);
		}
		else {
			dispatchTriggerImpl.setStartDate(new Date(startDate));
		}

		dispatchTriggerImpl.setSystem(system);

		if (type == null) {
			dispatchTriggerImpl.setType("");
		}
		else {
			dispatchTriggerImpl.setType(type);
		}

		if (typeSettings == null) {
			dispatchTriggerImpl.setTypeSettings("");
		}
		else {
			dispatchTriggerImpl.setTypeSettings(typeSettings);
		}

		dispatchTriggerImpl.resetOriginalValues();

		return dispatchTriggerImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		dispatchTriggerId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		active = objectInput.readBoolean();
		cronExpression = objectInput.readUTF();
		endDate = objectInput.readLong();
		name = objectInput.readUTF();
		startDate = objectInput.readLong();

		system = objectInput.readBoolean();
		type = objectInput.readUTF();
		typeSettings = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		objectOutput.writeLong(endDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(startDate);

		objectOutput.writeBoolean(system);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(typeSettings);
		}
	}

	public long mvccVersion;
	public long dispatchTriggerId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean active;
	public String cronExpression;
	public long endDate;
	public String name;
	public long startDate;
	public boolean system;
	public String type;
	public String typeSettings;

}