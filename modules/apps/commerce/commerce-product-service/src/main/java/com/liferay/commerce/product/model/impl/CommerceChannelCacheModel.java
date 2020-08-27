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

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceChannel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceChannelCacheModel
	implements CacheModel<CommerceChannel>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceChannelCacheModel)) {
			return false;
		}

		CommerceChannelCacheModel commerceChannelCacheModel =
			(CommerceChannelCacheModel)object;

		if (commerceChannelId == commerceChannelCacheModel.commerceChannelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceChannelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceChannelId=");
		sb.append(commerceChannelId);
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
		sb.append(", siteGroupId=");
		sb.append(siteGroupId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", commerceCurrencyCode=");
		sb.append(commerceCurrencyCode);
		sb.append(", priceDisplayType=");
		sb.append(priceDisplayType);
		sb.append(", discountsTargetNetPrice=");
		sb.append(discountsTargetNetPrice);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceChannel toEntityModel() {
		CommerceChannelImpl commerceChannelImpl = new CommerceChannelImpl();

		if (externalReferenceCode == null) {
			commerceChannelImpl.setExternalReferenceCode("");
		}
		else {
			commerceChannelImpl.setExternalReferenceCode(externalReferenceCode);
		}

		commerceChannelImpl.setCommerceChannelId(commerceChannelId);
		commerceChannelImpl.setCompanyId(companyId);
		commerceChannelImpl.setUserId(userId);

		if (userName == null) {
			commerceChannelImpl.setUserName("");
		}
		else {
			commerceChannelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceChannelImpl.setCreateDate(null);
		}
		else {
			commerceChannelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceChannelImpl.setModifiedDate(null);
		}
		else {
			commerceChannelImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceChannelImpl.setSiteGroupId(siteGroupId);

		if (name == null) {
			commerceChannelImpl.setName("");
		}
		else {
			commerceChannelImpl.setName(name);
		}

		if (type == null) {
			commerceChannelImpl.setType("");
		}
		else {
			commerceChannelImpl.setType(type);
		}

		if (typeSettings == null) {
			commerceChannelImpl.setTypeSettings("");
		}
		else {
			commerceChannelImpl.setTypeSettings(typeSettings);
		}

		if (commerceCurrencyCode == null) {
			commerceChannelImpl.setCommerceCurrencyCode("");
		}
		else {
			commerceChannelImpl.setCommerceCurrencyCode(commerceCurrencyCode);
		}

		if (priceDisplayType == null) {
			commerceChannelImpl.setPriceDisplayType("");
		}
		else {
			commerceChannelImpl.setPriceDisplayType(priceDisplayType);
		}

		commerceChannelImpl.setDiscountsTargetNetPrice(discountsTargetNetPrice);

		commerceChannelImpl.resetOriginalValues();

		return commerceChannelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		externalReferenceCode = objectInput.readUTF();

		commerceChannelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		siteGroupId = objectInput.readLong();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
		typeSettings = objectInput.readUTF();
		commerceCurrencyCode = objectInput.readUTF();
		priceDisplayType = objectInput.readUTF();

		discountsTargetNetPrice = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commerceChannelId);

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

		objectOutput.writeLong(siteGroupId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
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

		if (commerceCurrencyCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(commerceCurrencyCode);
		}

		if (priceDisplayType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(priceDisplayType);
		}

		objectOutput.writeBoolean(discountsTargetNetPrice);
	}

	public String externalReferenceCode;
	public long commerceChannelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long siteGroupId;
	public String name;
	public String type;
	public String typeSettings;
	public String commerceCurrencyCode;
	public String priceDisplayType;
	public boolean discountsTargetNetPrice;

}