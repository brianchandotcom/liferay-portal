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

import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
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
 * The cache model class for representing MDRRuleGroupInstance in entity cache.
 *
 * @author Edward C. Han
 * @generated
 */
public class MDRRuleGroupInstanceCacheModel
	implements CacheModel<MDRRuleGroupInstance>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MDRRuleGroupInstanceCacheModel)) {
			return false;
		}

		MDRRuleGroupInstanceCacheModel mdrRuleGroupInstanceCacheModel =
			(MDRRuleGroupInstanceCacheModel)obj;

		if ((ruleGroupInstanceId ==
				mdrRuleGroupInstanceCacheModel.ruleGroupInstanceId) &&
			(mvccVersion == mdrRuleGroupInstanceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ruleGroupInstanceId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", ruleGroupInstanceId=");
		sb.append(ruleGroupInstanceId);
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
		sb.append(", ruleGroupId=");
		sb.append(ruleGroupId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MDRRuleGroupInstance toEntityModel() {
		MDRRuleGroupInstanceImpl mdrRuleGroupInstanceImpl =
			new MDRRuleGroupInstanceImpl();

		mdrRuleGroupInstanceImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			mdrRuleGroupInstanceImpl.setUuid("");
		}
		else {
			mdrRuleGroupInstanceImpl.setUuid(uuid);
		}

		mdrRuleGroupInstanceImpl.setRuleGroupInstanceId(ruleGroupInstanceId);
		mdrRuleGroupInstanceImpl.setGroupId(groupId);
		mdrRuleGroupInstanceImpl.setCompanyId(companyId);
		mdrRuleGroupInstanceImpl.setUserId(userId);

		if (userName == null) {
			mdrRuleGroupInstanceImpl.setUserName("");
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
			mdrRuleGroupInstanceImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		mdrRuleGroupInstanceImpl.resetOriginalValues();

		return mdrRuleGroupInstanceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		ruleGroupInstanceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		ruleGroupId = objectInput.readLong();

		priority = objectInput.readInt();
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

		objectOutput.writeLong(ruleGroupInstanceId);

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

		objectOutput.writeLong(ruleGroupId);

		objectOutput.writeInt(priority);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long ruleGroupInstanceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long ruleGroupId;
	public int priority;
	public long lastPublishDate;

}