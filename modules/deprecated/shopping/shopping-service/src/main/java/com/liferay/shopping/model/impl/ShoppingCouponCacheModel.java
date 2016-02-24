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

package com.liferay.shopping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.shopping.model.ShoppingCoupon;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ShoppingCoupon in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCoupon
 * @generated
 */
@ProviderType
public class ShoppingCouponCacheModel implements CacheModel<ShoppingCoupon>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShoppingCouponCacheModel)) {
			return false;
		}

		ShoppingCouponCacheModel shoppingCouponCacheModel = (ShoppingCouponCacheModel)obj;

		if (couponId == shoppingCouponCacheModel.couponId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, couponId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{couponId=");
		sb.append(couponId);
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
		sb.append(", description=");
		sb.append(description);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", active=");
		sb.append(active);
		sb.append(", limitCategories=");
		sb.append(limitCategories);
		sb.append(", limitSkus=");
		sb.append(limitSkus);
		sb.append(", minOrder=");
		sb.append(minOrder);
		sb.append(", discount=");
		sb.append(discount);
		sb.append(", discountType=");
		sb.append(discountType);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ShoppingCoupon toEntityModel() {
		ShoppingCouponImpl shoppingCouponImpl = new ShoppingCouponImpl();

		shoppingCouponImpl.setCouponId(couponId);
		shoppingCouponImpl.setGroupId(groupId);
		shoppingCouponImpl.setCompanyId(companyId);
		shoppingCouponImpl.setUserId(userId);

		if (userName == null) {
			shoppingCouponImpl.setUserName(StringPool.BLANK);
		}
		else {
			shoppingCouponImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			shoppingCouponImpl.setCreateDate(null);
		}
		else {
			shoppingCouponImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			shoppingCouponImpl.setModifiedDate(null);
		}
		else {
			shoppingCouponImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (code == null) {
			shoppingCouponImpl.setCode(StringPool.BLANK);
		}
		else {
			shoppingCouponImpl.setCode(code);
		}

		if (name == null) {
			shoppingCouponImpl.setName(StringPool.BLANK);
		}
		else {
			shoppingCouponImpl.setName(name);
		}

		if (description == null) {
			shoppingCouponImpl.setDescription(StringPool.BLANK);
		}
		else {
			shoppingCouponImpl.setDescription(description);
		}

		if (startDate == Long.MIN_VALUE) {
			shoppingCouponImpl.setStartDate(null);
		}
		else {
			shoppingCouponImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			shoppingCouponImpl.setEndDate(null);
		}
		else {
			shoppingCouponImpl.setEndDate(new Date(endDate));
		}

		shoppingCouponImpl.setActive(active);

		if (limitCategories == null) {
			shoppingCouponImpl.setLimitCategories(StringPool.BLANK);
		}
		else {
			shoppingCouponImpl.setLimitCategories(limitCategories);
		}

		if (limitSkus == null) {
			shoppingCouponImpl.setLimitSkus(StringPool.BLANK);
		}
		else {
			shoppingCouponImpl.setLimitSkus(limitSkus);
		}

		shoppingCouponImpl.setMinOrder(minOrder);
		shoppingCouponImpl.setDiscount(discount);

		if (discountType == null) {
			shoppingCouponImpl.setDiscountType(StringPool.BLANK);
		}
		else {
			shoppingCouponImpl.setDiscountType(discountType);
		}

		shoppingCouponImpl.resetOriginalValues();

		return shoppingCouponImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		couponId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		code = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		startDate = objectInput.readLong();
		endDate = objectInput.readLong();

		active = objectInput.readBoolean();
		limitCategories = objectInput.readUTF();
		limitSkus = objectInput.readUTF();

		minOrder = objectInput.readDouble();

		discount = objectInput.readDouble();
		discountType = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(couponId);

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

		if (code == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(code);
		}

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

		objectOutput.writeLong(startDate);
		objectOutput.writeLong(endDate);

		objectOutput.writeBoolean(active);

		if (limitCategories == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(limitCategories);
		}

		if (limitSkus == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(limitSkus);
		}

		objectOutput.writeDouble(minOrder);

		objectOutput.writeDouble(discount);

		if (discountType == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(discountType);
		}
	}

	public long couponId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String code;
	public String name;
	public String description;
	public long startDate;
	public long endDate;
	public boolean active;
	public String limitCategories;
	public String limitSkus;
	public double minOrder;
	public double discount;
	public String discountType;
}