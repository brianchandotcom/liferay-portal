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

import com.liferay.commerce.product.model.CPGroup;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPGroup in entity cache.
 *
 * @author Marco Leo
 * @see CPGroup
 * @generated
 */
@ProviderType
public class CPGroupCacheModel implements CacheModel<CPGroup>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPGroupCacheModel)) {
			return false;
		}

		CPGroupCacheModel cpGroupCacheModel = (CPGroupCacheModel)obj;

		if (CPGroupId == cpGroupCacheModel.CPGroupId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPGroupId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{CPGroupId=");
		sb.append(CPGroupId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPGroup toEntityModel() {
		CPGroupImpl cpGroupImpl = new CPGroupImpl();

		cpGroupImpl.setCPGroupId(CPGroupId);
		cpGroupImpl.setGroupId(groupId);
		cpGroupImpl.setCompanyId(companyId);
		cpGroupImpl.setUserId(userId);

		if (userName == null) {
			cpGroupImpl.setUserName(StringPool.BLANK);
		}
		else {
			cpGroupImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpGroupImpl.setCreateDate(null);
		}
		else {
			cpGroupImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpGroupImpl.setModifiedDate(null);
		}
		else {
			cpGroupImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpGroupImpl.resetOriginalValues();

		return cpGroupImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CPGroupId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(CPGroupId);

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
	}

	public long CPGroupId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
}