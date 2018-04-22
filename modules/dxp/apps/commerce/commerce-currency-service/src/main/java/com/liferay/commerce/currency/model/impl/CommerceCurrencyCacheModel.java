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

package com.liferay.commerce.currency.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.currency.model.CommerceCurrency;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CommerceCurrency in entity cache.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrency
 * @generated
 */
@ProviderType
public class CommerceCurrencyCacheModel implements CacheModel<CommerceCurrency>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCurrencyCacheModel)) {
			return false;
		}

		CommerceCurrencyCacheModel commerceCurrencyCacheModel = (CommerceCurrencyCacheModel)obj;

		if (commerceCurrencyId == commerceCurrencyCacheModel.commerceCurrencyId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceCurrencyId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceCurrencyId=");
		sb.append(commerceCurrencyId);
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
		sb.append(", code=");
		sb.append(code);
		sb.append(", name=");
		sb.append(name);
		sb.append(", rate=");
		sb.append(rate);
		sb.append(", roundingType=");
		sb.append(roundingType);
		sb.append(", primary=");
		sb.append(primary);
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
	public CommerceCurrency toEntityModel() {
		CommerceCurrencyImpl commerceCurrencyImpl = new CommerceCurrencyImpl();

		if (uuid == null) {
			commerceCurrencyImpl.setUuid("");
		}
		else {
			commerceCurrencyImpl.setUuid(uuid);
		}

		commerceCurrencyImpl.setCommerceCurrencyId(commerceCurrencyId);
		commerceCurrencyImpl.setGroupId(groupId);
		commerceCurrencyImpl.setCompanyId(companyId);
		commerceCurrencyImpl.setUserId(userId);

		if (userName == null) {
			commerceCurrencyImpl.setUserName("");
		}
		else {
			commerceCurrencyImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceCurrencyImpl.setCreateDate(null);
		}
		else {
			commerceCurrencyImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceCurrencyImpl.setModifiedDate(null);
		}
		else {
			commerceCurrencyImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (code == null) {
			commerceCurrencyImpl.setCode("");
		}
		else {
			commerceCurrencyImpl.setCode(code);
		}

		if (name == null) {
			commerceCurrencyImpl.setName("");
		}
		else {
			commerceCurrencyImpl.setName(name);
		}

		commerceCurrencyImpl.setRate(rate);

		if (roundingType == null) {
			commerceCurrencyImpl.setRoundingType("");
		}
		else {
			commerceCurrencyImpl.setRoundingType(roundingType);
		}

		commerceCurrencyImpl.setPrimary(primary);
		commerceCurrencyImpl.setPriority(priority);
		commerceCurrencyImpl.setActive(active);

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceCurrencyImpl.setLastPublishDate(null);
		}
		else {
			commerceCurrencyImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		commerceCurrencyImpl.resetOriginalValues();

		return commerceCurrencyImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		uuid = objectInput.readUTF();

		commerceCurrencyId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		code = objectInput.readUTF();
		name = objectInput.readUTF();
		rate = (BigDecimal)objectInput.readObject();
		roundingType = objectInput.readUTF();

		primary = objectInput.readBoolean();

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

		objectOutput.writeLong(commerceCurrencyId);

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

		if (code == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(code);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeObject(rate);

		if (roundingType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(roundingType);
		}

		objectOutput.writeBoolean(primary);

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(active);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long commerceCurrencyId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String code;
	public String name;
	public BigDecimal rate;
	public String roundingType;
	public boolean primary;
	public double priority;
	public boolean active;
	public long lastPublishDate;
}