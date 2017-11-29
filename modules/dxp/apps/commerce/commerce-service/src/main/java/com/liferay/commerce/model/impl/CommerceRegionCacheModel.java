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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceRegion;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceRegion in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegion
 * @generated
 */
@ProviderType
public class CommerceRegionCacheModel implements CacheModel<CommerceRegion>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceRegionCacheModel)) {
			return false;
		}

		CommerceRegionCacheModel commerceRegionCacheModel = (CommerceRegionCacheModel)obj;

		if (commerceRegionId == commerceRegionCacheModel.commerceRegionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceRegionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceRegionId=");
		sb.append(commerceRegionId);
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
		sb.append(", commerceCountryId=");
		sb.append(commerceCountryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", code=");
		sb.append(code);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", active=");
		sb.append(active);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceRegion toEntityModel() {
		CommerceRegionImpl commerceRegionImpl = new CommerceRegionImpl();

		if (uuid == null) {
			commerceRegionImpl.setUuid("");
		}
		else {
			commerceRegionImpl.setUuid(uuid);
		}

		commerceRegionImpl.setCommerceRegionId(commerceRegionId);
		commerceRegionImpl.setGroupId(groupId);
		commerceRegionImpl.setCompanyId(companyId);
		commerceRegionImpl.setUserId(userId);

		if (userName == null) {
			commerceRegionImpl.setUserName("");
		}
		else {
			commerceRegionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceRegionImpl.setCreateDate(null);
		}
		else {
			commerceRegionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceRegionImpl.setModifiedDate(null);
		}
		else {
			commerceRegionImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceRegionImpl.setCommerceCountryId(commerceCountryId);

		if (name == null) {
			commerceRegionImpl.setName("");
		}
		else {
			commerceRegionImpl.setName(name);
		}

		if (code == null) {
			commerceRegionImpl.setCode("");
		}
		else {
			commerceRegionImpl.setCode(code);
		}

		commerceRegionImpl.setPriority(priority);
		commerceRegionImpl.setActive(active);

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceRegionImpl.setLastPublishDate(null);
		}
		else {
			commerceRegionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		commerceRegionImpl.resetOriginalValues();

		return commerceRegionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceRegionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceCountryId = objectInput.readLong();
		name = objectInput.readUTF();
		code = objectInput.readUTF();

		priority = objectInput.readDouble();

		active = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(commerceRegionId);

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

		objectOutput.writeLong(commerceCountryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (code == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(code);
		}

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(active);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long commerceRegionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceCountryId;
	public String name;
	public String code;
	public double priority;
	public boolean active;
	public long lastPublishDate;
}