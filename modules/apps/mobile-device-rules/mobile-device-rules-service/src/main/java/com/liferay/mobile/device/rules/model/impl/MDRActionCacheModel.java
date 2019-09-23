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

package com.liferay.mobile.device.rules.model.impl;

import com.liferay.mobile.device.rules.model.MDRAction;
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
 * The cache model class for representing MDRAction in entity cache.
 *
 * @author Edward C. Han
 * @generated
 */
public class MDRActionCacheModel
	implements CacheModel<MDRAction>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MDRActionCacheModel)) {
			return false;
		}

		MDRActionCacheModel mdrActionCacheModel = (MDRActionCacheModel)obj;

		if ((actionId == mdrActionCacheModel.actionId) &&
			(mvccVersion == mdrActionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, actionId);

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
		StringBundler sb = new StringBundler(35);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", actionId=");
		sb.append(actionId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", ruleGroupInstanceId=");
		sb.append(ruleGroupInstanceId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MDRAction toEntityModel() {
		MDRActionImpl mdrActionImpl = new MDRActionImpl();

		mdrActionImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			mdrActionImpl.setUuid("");
		}
		else {
			mdrActionImpl.setUuid(uuid);
		}

		mdrActionImpl.setActionId(actionId);
		mdrActionImpl.setGroupId(groupId);
		mdrActionImpl.setCompanyId(companyId);
		mdrActionImpl.setUserId(userId);

		if (userName == null) {
			mdrActionImpl.setUserName("");
		}
		else {
			mdrActionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mdrActionImpl.setCreateDate(null);
		}
		else {
			mdrActionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mdrActionImpl.setModifiedDate(null);
		}
		else {
			mdrActionImpl.setModifiedDate(new Date(modifiedDate));
		}

		mdrActionImpl.setClassNameId(classNameId);
		mdrActionImpl.setClassPK(classPK);
		mdrActionImpl.setRuleGroupInstanceId(ruleGroupInstanceId);

		if (name == null) {
			mdrActionImpl.setName("");
		}
		else {
			mdrActionImpl.setName(name);
		}

		if (description == null) {
			mdrActionImpl.setDescription("");
		}
		else {
			mdrActionImpl.setDescription(description);
		}

		if (type == null) {
			mdrActionImpl.setType("");
		}
		else {
			mdrActionImpl.setType(type);
		}

		if (typeSettings == null) {
			mdrActionImpl.setTypeSettings("");
		}
		else {
			mdrActionImpl.setTypeSettings(typeSettings);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			mdrActionImpl.setLastPublishDate(null);
		}
		else {
			mdrActionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		mdrActionImpl.resetOriginalValues();

		return mdrActionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		actionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		ruleGroupInstanceId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		type = objectInput.readUTF();
		typeSettings = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(actionId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(ruleGroupInstanceId);

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

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long actionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long ruleGroupInstanceId;
	public String name;
	public String description;
	public String type;
	public String typeSettings;
	public long lastPublishDate;

}