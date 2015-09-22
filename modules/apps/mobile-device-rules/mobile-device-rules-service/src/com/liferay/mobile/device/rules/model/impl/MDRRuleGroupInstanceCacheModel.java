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

import aQute.bnd.annotation.ProviderType;

import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MDRRuleGroupInstance in entity cache.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstance
 * @generated
 */
@ProviderType
public class MDRRuleGroupInstanceCacheModel implements CacheModel<MDRRuleGroupInstance>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MDRRuleGroupInstanceCacheModel)) {
			return false;
		}

		MDRRuleGroupInstanceCacheModel mdrRuleGroupInstanceCacheModel = (MDRRuleGroupInstanceCacheModel)obj;

		if (ruleGroupInstanceId == mdrRuleGroupInstanceCacheModel.ruleGroupInstanceId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, ruleGroupInstanceId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", ruleGroupInstanceId=");
		sb.append(ruleGroupInstanceId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", ruleGroupId=");
		sb.append(ruleGroupId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MDRRuleGroupInstance toEntityModel() {
		MDRRuleGroupInstanceImpl mdrRuleGroupInstanceImpl = new MDRRuleGroupInstanceImpl();

		if (uuid == null) {
			mdrRuleGroupInstanceImpl.setUuid(StringPool.BLANK);
		}
		else {
			mdrRuleGroupInstanceImpl.setUuid(uuid);
		}

		mdrRuleGroupInstanceImpl.setRuleGroupInstanceId(ruleGroupInstanceId);
		mdrRuleGroupInstanceImpl.setGroupId(groupId);
		mdrRuleGroupInstanceImpl.setUserId(userId);

		if (userName == null) {
			mdrRuleGroupInstanceImpl.setUserName(StringPool.BLANK);
		}
		else {
			mdrRuleGroupInstanceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mdrRuleGroupInstanceImpl.setCreateDate(null);
		}
		else {
			mdrRuleGroupInstanceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mdrRuleGroupInstanceImpl.setModifiedDate(null);
		}
		else {
			mdrRuleGroupInstanceImpl.setModifiedDate(new Date(modifiedDate));
		}

		mdrRuleGroupInstanceImpl.setClassNameId(classNameId);
		mdrRuleGroupInstanceImpl.setClassPK(classPK);
		mdrRuleGroupInstanceImpl.setRuleGroupId(ruleGroupId);
		mdrRuleGroupInstanceImpl.setPriority(priority);

		if (lastPublishDate == Long.MIN_VALUE) {
			mdrRuleGroupInstanceImpl.setLastPublishDate(null);
		}
		else {
			mdrRuleGroupInstanceImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		mdrRuleGroupInstanceImpl.setCompanyId(companyId);

		mdrRuleGroupInstanceImpl.resetOriginalValues();

		return mdrRuleGroupInstanceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		ruleGroupInstanceId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		ruleGroupId = objectInput.readLong();
		priority = objectInput.readInt();
		lastPublishDate = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(ruleGroupInstanceId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
		objectOutput.writeLong(ruleGroupId);
		objectOutput.writeInt(priority);
		objectOutput.writeLong(lastPublishDate);
		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long ruleGroupInstanceId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long ruleGroupId;
	public int priority;
	public long lastPublishDate;
	public long companyId;
}