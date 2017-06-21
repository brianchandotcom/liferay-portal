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

import com.liferay.commerce.product.model.CPDefinitionLink;

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
 * The cache model class for representing CPDefinitionLink in entity cache.
 *
 * @author Marco Leo
 * @see CPDefinitionLink
 * @generated
 */
@ProviderType
public class CPDefinitionLinkCacheModel implements CacheModel<CPDefinitionLink>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionLinkCacheModel)) {
			return false;
		}

		CPDefinitionLinkCacheModel cpDefinitionLinkCacheModel = (CPDefinitionLinkCacheModel)obj;

		if (CPDefinitionLinkId == cpDefinitionLinkCacheModel.CPDefinitionLinkId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionLinkId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{CPDefinitionLinkId=");
		sb.append(CPDefinitionLinkId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", CPDefinitionId1=");
		sb.append(CPDefinitionId1);
		sb.append(", CPDefinitionId2=");
		sb.append(CPDefinitionId2);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionLink toEntityModel() {
		CPDefinitionLinkImpl cpDefinitionLinkImpl = new CPDefinitionLinkImpl();

		cpDefinitionLinkImpl.setCPDefinitionLinkId(CPDefinitionLinkId);
		cpDefinitionLinkImpl.setCompanyId(companyId);
		cpDefinitionLinkImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionLinkImpl.setUserName(StringPool.BLANK);
		}
		else {
			cpDefinitionLinkImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionLinkImpl.setCreateDate(null);
		}
		else {
			cpDefinitionLinkImpl.setCreateDate(new Date(createDate));
		}

		cpDefinitionLinkImpl.setCPDefinitionId1(CPDefinitionId1);
		cpDefinitionLinkImpl.setCPDefinitionId2(CPDefinitionId2);
		cpDefinitionLinkImpl.setPriority(priority);
		cpDefinitionLinkImpl.setType(type);

		cpDefinitionLinkImpl.resetOriginalValues();

		return cpDefinitionLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CPDefinitionLinkId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		CPDefinitionId1 = objectInput.readLong();

		CPDefinitionId2 = objectInput.readLong();

		priority = objectInput.readDouble();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(CPDefinitionLinkId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);

		objectOutput.writeLong(CPDefinitionId1);

		objectOutput.writeLong(CPDefinitionId2);

		objectOutput.writeDouble(priority);

		objectOutput.writeInt(type);
	}

	public long CPDefinitionLinkId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long CPDefinitionId1;
	public long CPDefinitionId2;
	public double priority;
	public int type;
}