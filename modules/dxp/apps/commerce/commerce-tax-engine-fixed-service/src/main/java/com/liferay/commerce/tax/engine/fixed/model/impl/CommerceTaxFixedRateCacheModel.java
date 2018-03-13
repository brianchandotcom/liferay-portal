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

package com.liferay.commerce.tax.engine.fixed.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceTaxFixedRate in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRate
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateCacheModel implements CacheModel<CommerceTaxFixedRate>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTaxFixedRateCacheModel)) {
			return false;
		}

		CommerceTaxFixedRateCacheModel commerceTaxFixedRateCacheModel = (CommerceTaxFixedRateCacheModel)obj;

		if (commerceTaxFixedRateId == commerceTaxFixedRateCacheModel.commerceTaxFixedRateId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceTaxFixedRateId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceTaxFixedRateId=");
		sb.append(commerceTaxFixedRateId);
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
		sb.append(", commerceTaxMethodId=");
		sb.append(commerceTaxMethodId);
		sb.append(", commerceTaxCategoryId=");
		sb.append(commerceTaxCategoryId);
		sb.append(", rate=");
		sb.append(rate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTaxFixedRate toEntityModel() {
		CommerceTaxFixedRateImpl commerceTaxFixedRateImpl = new CommerceTaxFixedRateImpl();

		commerceTaxFixedRateImpl.setCommerceTaxFixedRateId(commerceTaxFixedRateId);
		commerceTaxFixedRateImpl.setGroupId(groupId);
		commerceTaxFixedRateImpl.setCompanyId(companyId);
		commerceTaxFixedRateImpl.setUserId(userId);

		if (userName == null) {
			commerceTaxFixedRateImpl.setUserName("");
		}
		else {
			commerceTaxFixedRateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTaxFixedRateImpl.setCreateDate(null);
		}
		else {
			commerceTaxFixedRateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTaxFixedRateImpl.setModifiedDate(null);
		}
		else {
			commerceTaxFixedRateImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceTaxFixedRateImpl.setCommerceTaxMethodId(commerceTaxMethodId);
		commerceTaxFixedRateImpl.setCommerceTaxCategoryId(commerceTaxCategoryId);
		commerceTaxFixedRateImpl.setRate(rate);

		commerceTaxFixedRateImpl.resetOriginalValues();

		return commerceTaxFixedRateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceTaxFixedRateId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceTaxMethodId = objectInput.readLong();

		commerceTaxCategoryId = objectInput.readLong();

		rate = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceTaxFixedRateId);

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

		objectOutput.writeLong(commerceTaxMethodId);

		objectOutput.writeLong(commerceTaxCategoryId);

		objectOutput.writeDouble(rate);
	}

	public long commerceTaxFixedRateId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceTaxMethodId;
	public long commerceTaxCategoryId;
	public double rate;
}