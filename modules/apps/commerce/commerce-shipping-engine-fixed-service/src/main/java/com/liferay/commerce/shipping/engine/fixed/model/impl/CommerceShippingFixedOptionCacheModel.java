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

package com.liferay.commerce.shipping.engine.fixed.model.impl;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CommerceShippingFixedOption in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShippingFixedOptionCacheModel
	implements CacheModel<CommerceShippingFixedOption>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceShippingFixedOptionCacheModel)) {
			return false;
		}

		CommerceShippingFixedOptionCacheModel
			commerceShippingFixedOptionCacheModel =
				(CommerceShippingFixedOptionCacheModel)object;

		if ((commerceShippingFixedOptionId ==
				commerceShippingFixedOptionCacheModel.
					commerceShippingFixedOptionId) &&
			(mvccVersion ==
				commerceShippingFixedOptionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceShippingFixedOptionId);

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
		sb.append(", commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);
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
		sb.append(", commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);
		sb.append(", amount=");
		sb.append(amount);
		sb.append(", description=");
		sb.append(description);
		sb.append(", key=");
		sb.append(key);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceShippingFixedOption toEntityModel() {
		CommerceShippingFixedOptionImpl commerceShippingFixedOptionImpl =
			new CommerceShippingFixedOptionImpl();

		commerceShippingFixedOptionImpl.setMvccVersion(mvccVersion);
		commerceShippingFixedOptionImpl.setCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId);
		commerceShippingFixedOptionImpl.setGroupId(groupId);
		commerceShippingFixedOptionImpl.setCompanyId(companyId);
		commerceShippingFixedOptionImpl.setUserId(userId);

		if (userName == null) {
			commerceShippingFixedOptionImpl.setUserName("");
		}
		else {
			commerceShippingFixedOptionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceShippingFixedOptionImpl.setCreateDate(null);
		}
		else {
			commerceShippingFixedOptionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceShippingFixedOptionImpl.setModifiedDate(null);
		}
		else {
			commerceShippingFixedOptionImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceShippingFixedOptionImpl.setCommerceShippingMethodId(
			commerceShippingMethodId);
		commerceShippingFixedOptionImpl.setAmount(amount);

		if (description == null) {
			commerceShippingFixedOptionImpl.setDescription("");
		}
		else {
			commerceShippingFixedOptionImpl.setDescription(description);
		}

		if (key == null) {
			commerceShippingFixedOptionImpl.setKey("");
		}
		else {
			commerceShippingFixedOptionImpl.setKey(key);
		}

		if (name == null) {
			commerceShippingFixedOptionImpl.setName("");
		}
		else {
			commerceShippingFixedOptionImpl.setName(name);
		}

		commerceShippingFixedOptionImpl.setPriority(priority);

		commerceShippingFixedOptionImpl.resetOriginalValues();

		return commerceShippingFixedOptionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		commerceShippingFixedOptionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();
		amount = (BigDecimal)objectInput.readObject();
		description = objectInput.readUTF();
		key = objectInput.readUTF();
		name = objectInput.readUTF();

		priority = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceShippingFixedOptionId);

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

		objectOutput.writeLong(commerceShippingMethodId);
		objectOutput.writeObject(amount);

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeDouble(priority);
	}

	public long mvccVersion;
	public long commerceShippingFixedOptionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceShippingMethodId;
	public BigDecimal amount;
	public String description;
	public String key;
	public String name;
	public double priority;

}