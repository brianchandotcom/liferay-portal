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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPRule;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPRule in entity cache.
 *
 * @author Marco Leo
 * @see CPRule
 * @generated
 */
@ProviderType
public class CPRuleCacheModel implements CacheModel<CPRule>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPRuleCacheModel)) {
			return false;
		}

		CPRuleCacheModel cpRuleCacheModel = (CPRuleCacheModel)obj;

		if (CPRuleId == cpRuleCacheModel.CPRuleId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPRuleId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{CPRuleId=");
		sb.append(CPRuleId);
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
		sb.append(", active=");
		sb.append(active);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPRule toEntityModel() {
		CPRuleImpl cpRuleImpl = new CPRuleImpl();

		cpRuleImpl.setCPRuleId(CPRuleId);
		cpRuleImpl.setGroupId(groupId);
		cpRuleImpl.setCompanyId(companyId);
		cpRuleImpl.setUserId(userId);

		if (userName == null) {
			cpRuleImpl.setUserName("");
		}
		else {
			cpRuleImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpRuleImpl.setCreateDate(null);
		}
		else {
			cpRuleImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpRuleImpl.setModifiedDate(null);
		}
		else {
			cpRuleImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			cpRuleImpl.setName("");
		}
		else {
			cpRuleImpl.setName(name);
		}

		cpRuleImpl.setActive(active);

		if (type == null) {
			cpRuleImpl.setType("");
		}
		else {
			cpRuleImpl.setType(type);
		}

		cpRuleImpl.resetOriginalValues();

		return cpRuleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CPRuleId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		active = objectInput.readBoolean();
		type = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(CPRuleId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(active);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long CPRuleId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public boolean active;
	public String type;
}