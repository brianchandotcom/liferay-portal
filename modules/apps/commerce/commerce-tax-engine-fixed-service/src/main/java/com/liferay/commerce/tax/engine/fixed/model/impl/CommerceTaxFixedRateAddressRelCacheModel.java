/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.model.impl;

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
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
 * The cache model class for representing CommerceTaxFixedRateAddressRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceTaxFixedRateAddressRelCacheModel
	implements CacheModel<CommerceTaxFixedRateAddressRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceTaxFixedRateAddressRelCacheModel)) {
			return false;
		}

		CommerceTaxFixedRateAddressRelCacheModel
			commerceTaxFixedRateAddressRelCacheModel =
				(CommerceTaxFixedRateAddressRelCacheModel)object;

		if ((commerceTaxFixedRateAddressRelId ==
				commerceTaxFixedRateAddressRelCacheModel.
					commerceTaxFixedRateAddressRelId) &&
			(mvccVersion ==
				commerceTaxFixedRateAddressRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceTaxFixedRateAddressRelId);

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
		sb.append(", commerceTaxFixedRateAddressRelId=");
		sb.append(commerceTaxFixedRateAddressRelId);
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
		sb.append(", CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", regionId=");
		sb.append(regionId);
		sb.append(", zip=");
		sb.append(zip);
		sb.append(", rate=");
		sb.append(rate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTaxFixedRateAddressRel toEntityModel() {
		CommerceTaxFixedRateAddressRelImpl commerceTaxFixedRateAddressRelImpl =
			new CommerceTaxFixedRateAddressRelImpl();

		commerceTaxFixedRateAddressRelImpl.setMvccVersion(mvccVersion);
		commerceTaxFixedRateAddressRelImpl.setCommerceTaxFixedRateAddressRelId(
			commerceTaxFixedRateAddressRelId);
		commerceTaxFixedRateAddressRelImpl.setGroupId(groupId);
		commerceTaxFixedRateAddressRelImpl.setCompanyId(companyId);
		commerceTaxFixedRateAddressRelImpl.setUserId(userId);

		if (userName == null) {
			commerceTaxFixedRateAddressRelImpl.setUserName("");
		}
		else {
			commerceTaxFixedRateAddressRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTaxFixedRateAddressRelImpl.setCreateDate(null);
		}
		else {
			commerceTaxFixedRateAddressRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTaxFixedRateAddressRelImpl.setModifiedDate(null);
		}
		else {
			commerceTaxFixedRateAddressRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceTaxFixedRateAddressRelImpl.setCommerceTaxMethodId(
			commerceTaxMethodId);
		commerceTaxFixedRateAddressRelImpl.setCPTaxCategoryId(CPTaxCategoryId);
		commerceTaxFixedRateAddressRelImpl.setCountryId(countryId);
		commerceTaxFixedRateAddressRelImpl.setRegionId(regionId);

		if (zip == null) {
			commerceTaxFixedRateAddressRelImpl.setZip("");
		}
		else {
			commerceTaxFixedRateAddressRelImpl.setZip(zip);
		}

		commerceTaxFixedRateAddressRelImpl.setRate(rate);

		commerceTaxFixedRateAddressRelImpl.resetOriginalValues();

		return commerceTaxFixedRateAddressRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceTaxFixedRateAddressRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceTaxMethodId = objectInput.readLong();

		CPTaxCategoryId = objectInput.readLong();

		countryId = objectInput.readLong();

		regionId = objectInput.readLong();
		zip = objectInput.readUTF();

		rate = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceTaxFixedRateAddressRelId);

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

		objectOutput.writeLong(CPTaxCategoryId);

		objectOutput.writeLong(countryId);

		objectOutput.writeLong(regionId);

		if (zip == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(zip);
		}

		objectOutput.writeDouble(rate);
	}

	public long mvccVersion;
	public long commerceTaxFixedRateAddressRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceTaxMethodId;
	public long CPTaxCategoryId;
	public long countryId;
	public long regionId;
	public String zip;
	public double rate;

}