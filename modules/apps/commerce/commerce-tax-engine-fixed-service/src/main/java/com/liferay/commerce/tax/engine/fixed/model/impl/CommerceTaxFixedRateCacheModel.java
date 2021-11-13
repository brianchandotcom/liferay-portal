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

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceTaxFixedRate in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceTaxFixedRateCacheModel
	implements CacheModel<CommerceTaxFixedRate>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceTaxFixedRateCacheModel)) {
			return false;
		}

		CommerceTaxFixedRateCacheModel commerceTaxFixedRateCacheModel =
			(CommerceTaxFixedRateCacheModel)object;

		if ((commerceTaxFixedRateId ==
				commerceTaxFixedRateCacheModel.commerceTaxFixedRateId) &&
			(mvccVersion == commerceTaxFixedRateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceTaxFixedRateId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceTaxFixedRateId=");
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
		sb.append(", CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);
		sb.append(", commerceTaxMethodId=");
		sb.append(commerceTaxMethodId);
		sb.append(", rate=");
		sb.append(rate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTaxFixedRate toEntityModel() {
		CommerceTaxFixedRateImpl commerceTaxFixedRateImpl =
			new CommerceTaxFixedRateImpl();

		commerceTaxFixedRateImpl.setMvccVersion(mvccVersion);
		commerceTaxFixedRateImpl.setCommerceTaxFixedRateId(
			commerceTaxFixedRateId);
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

		commerceTaxFixedRateImpl.setCPTaxCategoryId(CPTaxCategoryId);
		commerceTaxFixedRateImpl.setCommerceTaxMethodId(commerceTaxMethodId);
		commerceTaxFixedRateImpl.setRate(rate);

		commerceTaxFixedRateImpl.resetOriginalValues();

		return commerceTaxFixedRateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceTaxFixedRateId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPTaxCategoryId = objectInput.readLong();

		commerceTaxMethodId = objectInput.readLong();

		rate = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		objectOutput.writeLong(CPTaxCategoryId);

		objectOutput.writeLong(commerceTaxMethodId);

		objectOutput.writeDouble(rate);
	}

	public long mvccVersion;
	public long commerceTaxFixedRateId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPTaxCategoryId;
	public long commerceTaxMethodId;
	public double rate;

}