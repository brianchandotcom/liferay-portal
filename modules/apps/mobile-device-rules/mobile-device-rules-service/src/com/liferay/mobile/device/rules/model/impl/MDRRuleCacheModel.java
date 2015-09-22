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

import com.liferay.mobile.device.rules.model.MDRRule;

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
 * The cache model class for representing MDRRule in entity cache.
 *
 * @author Edward C. Han
 * @see MDRRule
 * @generated
 */
@ProviderType
public class MDRRuleCacheModel implements CacheModel<MDRRule>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MDRRuleCacheModel)) {
			return false;
		}

		MDRRuleCacheModel mdrRuleCacheModel = (MDRRuleCacheModel)obj;

		if (ruleId == mdrRuleCacheModel.ruleId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, ruleId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", ruleId=");
		sb.append(ruleId);
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
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MDRRule toEntityModel() {
		MDRRuleImpl mdrRuleImpl = new MDRRuleImpl();

		if (uuid == null) {
			mdrRuleImpl.setUuid(StringPool.BLANK);
		}
		else {
			mdrRuleImpl.setUuid(uuid);
		}

		mdrRuleImpl.setRuleId(ruleId);
		mdrRuleImpl.setGroupId(groupId);
		mdrRuleImpl.setUserId(userId);

		if (userName == null) {
			mdrRuleImpl.setUserName(StringPool.BLANK);
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
			mdrRuleImpl.setName(StringPool.BLANK);
		}
		else {
			mdrRuleImpl.setName(name);
		}

		if (description == null) {
			mdrRuleImpl.setDescription(StringPool.BLANK);
		}
		else {
			mdrRuleImpl.setDescription(description);
		}

		if (type == null) {
			mdrRuleImpl.setType(StringPool.BLANK);
		}
		else {
			mdrRuleImpl.setType(type);
		}

		if (typeSettings == null) {
			mdrRuleImpl.setTypeSettings(StringPool.BLANK);
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

		mdrRuleImpl.setCompanyId(companyId);

		mdrRuleImpl.resetOriginalValues();

		return mdrRuleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		ruleId = objectInput.readLong();
		groupId = objectInput.readLong();
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

		objectOutput.writeLong(ruleId);
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
		objectOutput.writeLong(ruleGroupId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (type == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeLong(lastPublishDate);
		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long ruleId;
	public long groupId;
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
	public long companyId;
}