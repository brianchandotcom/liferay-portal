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

import com.liferay.mobile.device.rules.model.MDRRule;
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
 * The cache model class for representing MDRRule in entity cache.
 *
 * @author Edward C. Han
 * @generated
 */
public class MDRRuleCacheModel
	implements CacheModel<MDRRule>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MDRRuleCacheModel)) {
			return false;
		}

		MDRRuleCacheModel mdrRuleCacheModel = (MDRRuleCacheModel)obj;

		if ((ruleId == mdrRuleCacheModel.ruleId) &&
			(mvccVersion == mdrRuleCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ruleId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", ruleId=");
		sb.append(ruleId);
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
		sb.append(", ruleGroupId=");
		sb.append(ruleGroupId);
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
	public MDRRule toEntityModel() {
		MDRRuleImpl mdrRuleImpl = new MDRRuleImpl();

		mdrRuleImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			mdrRuleImpl.setUuid("");
		}
		else {
			mdrRuleImpl.setUuid(uuid);
		}

		mdrRuleImpl.setRuleId(ruleId);
		mdrRuleImpl.setGroupId(groupId);
		mdrRuleImpl.setCompanyId(companyId);
		mdrRuleImpl.setUserId(userId);

		if (userName == null) {
			mdrRuleImpl.setUserName("");
		}
		else {
			mdrRuleImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mdrRuleImpl.setCreateDate(null);
		}
		else {
			mdrRuleImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mdrRuleImpl.setModifiedDate(null);
		}
		else {
			mdrRuleImpl.setModifiedDate(new Date(modifiedDate));
		}

		mdrRuleImpl.setRuleGroupId(ruleGroupId);

		if (name == null) {
			mdrRuleImpl.setName("");
		}
		else {
			mdrRuleImpl.setName(name);
		}

		if (description == null) {
			mdrRuleImpl.setDescription("");
		}
		else {
			mdrRuleImpl.setDescription(description);
		}

		if (type == null) {
			mdrRuleImpl.setType("");
		}
		else {
			mdrRuleImpl.setType(type);
		}

		if (typeSettings == null) {
			mdrRuleImpl.setTypeSettings("");
		}
		else {
			mdrRuleImpl.setTypeSettings(typeSettings);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			mdrRuleImpl.setLastPublishDate(null);
		}
		else {
			mdrRuleImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		mdrRuleImpl.resetOriginalValues();

		return mdrRuleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		ruleId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		ruleGroupId = objectInput.readLong();
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

		objectOutput.writeLong(ruleId);

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

		objectOutput.writeLong(ruleGroupId);

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
	public long ruleId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long ruleGroupId;
	public String name;
	public String description;
	public String type;
	public String typeSettings;
	public long lastPublishDate;

}