/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.model.impl;

import com.liferay.commerce.tax.model.CommerceTaxCategoryMapping;
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
 * The cache model class for representing CommerceTaxCategoryMapping in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceTaxCategoryMappingCacheModel
	implements CacheModel<CommerceTaxCategoryMapping>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceTaxCategoryMappingCacheModel)) {
			return false;
		}

		CommerceTaxCategoryMappingCacheModel
			commerceTaxCategoryMappingCacheModel =
				(CommerceTaxCategoryMappingCacheModel)object;

		if ((commerceTaxCategoryMappingId ==
				commerceTaxCategoryMappingCacheModel.
					commerceTaxCategoryMappingId) &&
			(mvccVersion == commerceTaxCategoryMappingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceTaxCategoryMappingId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceTaxCategoryMappingId=");
		sb.append(commerceTaxCategoryMappingId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTaxCategoryMapping toEntityModel() {
		CommerceTaxCategoryMappingImpl commerceTaxCategoryMappingImpl =
			new CommerceTaxCategoryMappingImpl();

		commerceTaxCategoryMappingImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceTaxCategoryMappingImpl.setUuid("");
		}
		else {
			commerceTaxCategoryMappingImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commerceTaxCategoryMappingImpl.setExternalReferenceCode("");
		}
		else {
			commerceTaxCategoryMappingImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceTaxCategoryMappingImpl.setCommerceTaxCategoryMappingId(
			commerceTaxCategoryMappingId);
		commerceTaxCategoryMappingImpl.setGroupId(groupId);
		commerceTaxCategoryMappingImpl.setCompanyId(companyId);
		commerceTaxCategoryMappingImpl.setUserId(userId);

		if (userName == null) {
			commerceTaxCategoryMappingImpl.setUserName("");
		}
		else {
			commerceTaxCategoryMappingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTaxCategoryMappingImpl.setCreateDate(null);
		}
		else {
			commerceTaxCategoryMappingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTaxCategoryMappingImpl.setModifiedDate(null);
		}
		else {
			commerceTaxCategoryMappingImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceTaxCategoryMappingImpl.setCommerceTaxMethodId(
			commerceTaxMethodId);
		commerceTaxCategoryMappingImpl.setCPTaxCategoryId(CPTaxCategoryId);

		commerceTaxCategoryMappingImpl.resetOriginalValues();

		return commerceTaxCategoryMappingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commerceTaxCategoryMappingId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceTaxMethodId = objectInput.readLong();

		CPTaxCategoryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commerceTaxCategoryMappingId);

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
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long commerceTaxCategoryMappingId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceTaxMethodId;
	public long CPTaxCategoryId;

}
// LIFERAY-SERVICE-BUILDER-HASH:-840371906