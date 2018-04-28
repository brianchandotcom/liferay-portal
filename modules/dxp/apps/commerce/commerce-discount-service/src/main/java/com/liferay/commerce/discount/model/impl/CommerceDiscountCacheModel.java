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

package com.liferay.commerce.discount.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.model.CommerceDiscount;

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
 * The cache model class for representing CommerceDiscount in entity cache.
 *
 * @author Marco Leo
 * @see CommerceDiscount
 * @generated
 */
@ProviderType
public class CommerceDiscountCacheModel implements CacheModel<CommerceDiscount>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceDiscountCacheModel)) {
			return false;
		}

		CommerceDiscountCacheModel commerceDiscountCacheModel = (CommerceDiscountCacheModel)obj;

		if (commerceDiscountId == commerceDiscountCacheModel.commerceDiscountId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceDiscountId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(59);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);
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
		sb.append(", title=");
		sb.append(title);
		sb.append(", target=");
		sb.append(target);
		sb.append(", useCouponCode=");
		sb.append(useCouponCode);
		sb.append(", couponCode=");
		sb.append(couponCode);
		sb.append(", usePercentage=");
		sb.append(usePercentage);
		sb.append(", maximumDiscountAmount=");
		sb.append(maximumDiscountAmount);
		sb.append(", level1=");
		sb.append(level1);
		sb.append(", level2=");
		sb.append(level2);
		sb.append(", level3=");
		sb.append(level3);
		sb.append(", limitationType=");
		sb.append(limitationType);
		sb.append(", limitationTimes=");
		sb.append(limitationTimes);
		sb.append(", numberOfUse=");
		sb.append(numberOfUse);
		sb.append(", cumulative=");
		sb.append(cumulative);
		sb.append(", active=");
		sb.append(active);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscount toEntityModel() {
		CommerceDiscountImpl commerceDiscountImpl = new CommerceDiscountImpl();

		if (uuid == null) {
			commerceDiscountImpl.setUuid("");
		}
		else {
			commerceDiscountImpl.setUuid(uuid);
		}

		commerceDiscountImpl.setCommerceDiscountId(commerceDiscountId);
		commerceDiscountImpl.setGroupId(groupId);
		commerceDiscountImpl.setCompanyId(companyId);
		commerceDiscountImpl.setUserId(userId);

		if (userName == null) {
			commerceDiscountImpl.setUserName("");
		}
		else {
			commerceDiscountImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setCreateDate(null);
		}
		else {
			commerceDiscountImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setModifiedDate(null);
		}
		else {
			commerceDiscountImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (title == null) {
			commerceDiscountImpl.setTitle("");
		}
		else {
			commerceDiscountImpl.setTitle(title);
		}

		if (target == null) {
			commerceDiscountImpl.setTarget("");
		}
		else {
			commerceDiscountImpl.setTarget(target);
		}

		commerceDiscountImpl.setUseCouponCode(useCouponCode);

		if (couponCode == null) {
			commerceDiscountImpl.setCouponCode("");
		}
		else {
			commerceDiscountImpl.setCouponCode(couponCode);
		}

		commerceDiscountImpl.setUsePercentage(usePercentage);
		commerceDiscountImpl.setMaximumDiscountAmount(maximumDiscountAmount);
		commerceDiscountImpl.setLevel1(level1);
		commerceDiscountImpl.setLevel2(level2);
		commerceDiscountImpl.setLevel3(level3);

		if (limitationType == null) {
			commerceDiscountImpl.setLimitationType("");
		}
		else {
			commerceDiscountImpl.setLimitationType(limitationType);
		}

		commerceDiscountImpl.setLimitationTimes(limitationTimes);
		commerceDiscountImpl.setNumberOfUse(numberOfUse);
		commerceDiscountImpl.setCumulative(cumulative);
		commerceDiscountImpl.setActive(active);

		if (displayDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setDisplayDate(null);
		}
		else {
			commerceDiscountImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setExpirationDate(null);
		}
		else {
			commerceDiscountImpl.setExpirationDate(new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setLastPublishDate(null);
		}
		else {
			commerceDiscountImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		commerceDiscountImpl.setStatus(status);
		commerceDiscountImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceDiscountImpl.setStatusByUserName("");
		}
		else {
			commerceDiscountImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setStatusDate(null);
		}
		else {
			commerceDiscountImpl.setStatusDate(new Date(statusDate));
		}

		commerceDiscountImpl.resetOriginalValues();

		return commerceDiscountImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		uuid = objectInput.readUTF();

		commerceDiscountId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		title = objectInput.readUTF();
		target = objectInput.readUTF();

		useCouponCode = objectInput.readBoolean();
		couponCode = objectInput.readUTF();

		usePercentage = objectInput.readBoolean();
		maximumDiscountAmount = (BigDecimal)objectInput.readObject();
		level1 = (BigDecimal)objectInput.readObject();
		level2 = (BigDecimal)objectInput.readObject();
		level3 = (BigDecimal)objectInput.readObject();
		limitationType = objectInput.readUTF();

		limitationTimes = objectInput.readInt();

		numberOfUse = objectInput.readInt();

		cumulative = objectInput.readBoolean();

		active = objectInput.readBoolean();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		objectOutput.writeLong(commerceDiscountId);

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

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (target == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(target);
		}

		objectOutput.writeBoolean(useCouponCode);

		if (couponCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(couponCode);
		}

		objectOutput.writeBoolean(usePercentage);
		objectOutput.writeObject(maximumDiscountAmount);
		objectOutput.writeObject(level1);
		objectOutput.writeObject(level2);
		objectOutput.writeObject(level3);

		if (limitationType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(limitationType);
		}

		objectOutput.writeInt(limitationTimes);

		objectOutput.writeInt(numberOfUse);

		objectOutput.writeBoolean(cumulative);

		objectOutput.writeBoolean(active);
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public String uuid;
	public long commerceDiscountId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public String target;
	public boolean useCouponCode;
	public String couponCode;
	public boolean usePercentage;
	public BigDecimal maximumDiscountAmount;
	public BigDecimal level1;
	public BigDecimal level2;
	public BigDecimal level3;
	public String limitationType;
	public int limitationTimes;
	public int numberOfUse;
	public boolean cumulative;
	public boolean active;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}