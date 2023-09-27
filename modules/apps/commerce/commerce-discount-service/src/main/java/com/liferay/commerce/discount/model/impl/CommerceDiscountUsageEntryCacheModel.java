/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model.impl;

import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;
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
 * The cache model class for representing CommerceDiscountUsageEntry in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountUsageEntryCacheModel
	implements CacheModel<CommerceDiscountUsageEntry>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceDiscountUsageEntryCacheModel)) {
			return false;
		}

		CommerceDiscountUsageEntryCacheModel
			commerceDiscountUsageEntryCacheModel =
				(CommerceDiscountUsageEntryCacheModel)object;

		if ((commerceDiscountUsageEntryId ==
				commerceDiscountUsageEntryCacheModel.
					commerceDiscountUsageEntryId) &&
			(mvccVersion == commerceDiscountUsageEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceDiscountUsageEntryId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceDiscountUsageEntryId=");
		sb.append(commerceDiscountUsageEntryId);
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
		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);
		sb.append(", commerceOrderId=");
		sb.append(commerceOrderId);
		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscountUsageEntry toEntityModel() {
		CommerceDiscountUsageEntryImpl commerceDiscountUsageEntryImpl =
			new CommerceDiscountUsageEntryImpl();

		commerceDiscountUsageEntryImpl.setMvccVersion(mvccVersion);
		commerceDiscountUsageEntryImpl.setCommerceDiscountUsageEntryId(
			commerceDiscountUsageEntryId);
		commerceDiscountUsageEntryImpl.setCompanyId(companyId);
		commerceDiscountUsageEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceDiscountUsageEntryImpl.setUserName("");
		}
		else {
			commerceDiscountUsageEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDiscountUsageEntryImpl.setCreateDate(null);
		}
		else {
			commerceDiscountUsageEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDiscountUsageEntryImpl.setModifiedDate(null);
		}
		else {
			commerceDiscountUsageEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceDiscountUsageEntryImpl.setCommerceAccountId(commerceAccountId);
		commerceDiscountUsageEntryImpl.setCommerceOrderId(commerceOrderId);
		commerceDiscountUsageEntryImpl.setCommerceDiscountId(
			commerceDiscountId);

		commerceDiscountUsageEntryImpl.resetOriginalValues();

		return commerceDiscountUsageEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceDiscountUsageEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceAccountId = objectInput.readLong();

		commerceOrderId = objectInput.readLong();

		commerceDiscountId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceDiscountUsageEntryId);

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

		objectOutput.writeLong(commerceAccountId);

		objectOutput.writeLong(commerceOrderId);

		objectOutput.writeLong(commerceDiscountId);
	}

	public long mvccVersion;
	public long commerceDiscountUsageEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceAccountId;
	public long commerceOrderId;
	public long commerceDiscountId;

}