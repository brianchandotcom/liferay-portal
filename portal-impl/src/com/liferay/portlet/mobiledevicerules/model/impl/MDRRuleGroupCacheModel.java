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

package com.liferay.portlet.mobiledevicerules.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MDRRuleGroup in entity cache.
 *
 * @author Edward C. Han
 * @see MDRRuleGroup
 * @generated
 */
@ProviderType
public class MDRRuleGroupCacheModel implements CacheModel<MDRRuleGroup>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", ruleGroupId=");
		sb.append(ruleGroupId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MDRRuleGroup toEntityModel() {
		MDRRuleGroupImpl mdrRuleGroupImpl = new MDRRuleGroupImpl();

		if (uuid == null) {
			mdrRuleGroupImpl.setUuid(StringPool.BLANK);
		}
		else {
			mdrRuleGroupImpl.setUuid(uuid);
		}

		mdrRuleGroupImpl.setRuleGroupId(ruleGroupId);
		mdrRuleGroupImpl.setGroupId(groupId);
		mdrRuleGroupImpl.setCompanyId(companyId);
		mdrRuleGroupImpl.setUserId(userId);

		if (userName == null) {
			mdrRuleGroupImpl.setUserName(StringPool.BLANK);
		}
		else {
			mdrRuleGroupImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mdrRuleGroupImpl.setCreateDate(null);
		}
		else {
			mdrRuleGroupImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mdrRuleGroupImpl.setModifiedDate(null);
		}
		else {
			mdrRuleGroupImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			mdrRuleGroupImpl.setName(StringPool.BLANK);
		}
		else {
			mdrRuleGroupImpl.setName(name);
		}

		if (description == null) {
			mdrRuleGroupImpl.setDescription(StringPool.BLANK);
		}
		else {
			mdrRuleGroupImpl.setDescription(description);
		}

		mdrRuleGroupImpl.resetOriginalValues();

		return mdrRuleGroupImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		ruleGroupId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
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

		objectOutput.writeLong(ruleGroupId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

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
	}

	public String uuid;
	public long ruleGroupId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
}