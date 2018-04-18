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

import com.liferay.commerce.product.model.CPRuleUserSegmentRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPRuleUserSegmentRel in entity cache.
 *
 * @author Marco Leo
 * @see CPRuleUserSegmentRel
 * @generated
 */
@ProviderType
public class CPRuleUserSegmentRelCacheModel implements CacheModel<CPRuleUserSegmentRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPRuleUserSegmentRelCacheModel)) {
			return false;
		}

		CPRuleUserSegmentRelCacheModel cpRuleUserSegmentRelCacheModel = (CPRuleUserSegmentRelCacheModel)obj;

		if (CPRuleUserSegmentRelId == cpRuleUserSegmentRelCacheModel.CPRuleUserSegmentRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPRuleUserSegmentRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{CPRuleUserSegmentRelId=");
		sb.append(CPRuleUserSegmentRelId);
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
		sb.append(", CPRuleId=");
		sb.append(CPRuleId);
		sb.append(", commerceUserSegmentEntryId=");
		sb.append(commerceUserSegmentEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPRuleUserSegmentRel toEntityModel() {
		CPRuleUserSegmentRelImpl cpRuleUserSegmentRelImpl = new CPRuleUserSegmentRelImpl();

		cpRuleUserSegmentRelImpl.setCPRuleUserSegmentRelId(CPRuleUserSegmentRelId);
		cpRuleUserSegmentRelImpl.setGroupId(groupId);
		cpRuleUserSegmentRelImpl.setCompanyId(companyId);
		cpRuleUserSegmentRelImpl.setUserId(userId);

		if (userName == null) {
			cpRuleUserSegmentRelImpl.setUserName("");
		}
		else {
			cpRuleUserSegmentRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpRuleUserSegmentRelImpl.setCreateDate(null);
		}
		else {
			cpRuleUserSegmentRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpRuleUserSegmentRelImpl.setModifiedDate(null);
		}
		else {
			cpRuleUserSegmentRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpRuleUserSegmentRelImpl.setCPRuleId(CPRuleId);
		cpRuleUserSegmentRelImpl.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);

		cpRuleUserSegmentRelImpl.resetOriginalValues();

		return cpRuleUserSegmentRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CPRuleUserSegmentRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPRuleId = objectInput.readLong();

		commerceUserSegmentEntryId = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(CPRuleUserSegmentRelId);

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

		objectOutput.writeLong(CPRuleId);

		objectOutput.writeBoolean(commerceUserSegmentEntryId);
	}

	public long CPRuleUserSegmentRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPRuleId;
	public boolean commerceUserSegmentEntryId;
}