/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model.impl;

import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
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
 * The cache model class for representing CommerceDiscountCommerceAccountGroupRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountCommerceAccountGroupRelCacheModel
	implements CacheModel<CommerceDiscountCommerceAccountGroupRel>,
			   Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommerceDiscountCommerceAccountGroupRelCacheModel)) {

			return false;
		}

		CommerceDiscountCommerceAccountGroupRelCacheModel
			commerceDiscountCommerceAccountGroupRelCacheModel =
				(CommerceDiscountCommerceAccountGroupRelCacheModel)object;

		if ((commerceDiscountCommerceAccountGroupRelId ==
				commerceDiscountCommerceAccountGroupRelCacheModel.
					commerceDiscountCommerceAccountGroupRelId) &&
			(mvccVersion ==
				commerceDiscountCommerceAccountGroupRelCacheModel.
					mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(
			0, commerceDiscountCommerceAccountGroupRelId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceDiscountCommerceAccountGroupRelId=");
		sb.append(commerceDiscountCommerceAccountGroupRelId);
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
		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);
		sb.append(", commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscountCommerceAccountGroupRel toEntityModel() {
		CommerceDiscountCommerceAccountGroupRelImpl
			commerceDiscountCommerceAccountGroupRelImpl =
				new CommerceDiscountCommerceAccountGroupRelImpl();

		commerceDiscountCommerceAccountGroupRelImpl.setMvccVersion(mvccVersion);
		commerceDiscountCommerceAccountGroupRelImpl.
			setCommerceDiscountCommerceAccountGroupRelId(
				commerceDiscountCommerceAccountGroupRelId);
		commerceDiscountCommerceAccountGroupRelImpl.setCompanyId(companyId);
		commerceDiscountCommerceAccountGroupRelImpl.setUserId(userId);

		if (userName == null) {
			commerceDiscountCommerceAccountGroupRelImpl.setUserName("");
		}
		else {
			commerceDiscountCommerceAccountGroupRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDiscountCommerceAccountGroupRelImpl.setCreateDate(null);
		}
		else {
			commerceDiscountCommerceAccountGroupRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDiscountCommerceAccountGroupRelImpl.setModifiedDate(null);
		}
		else {
			commerceDiscountCommerceAccountGroupRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceDiscountCommerceAccountGroupRelImpl.setCommerceDiscountId(
			commerceDiscountId);
		commerceDiscountCommerceAccountGroupRelImpl.setCommerceAccountGroupId(
			commerceAccountGroupId);

		commerceDiscountCommerceAccountGroupRelImpl.resetOriginalValues();

		return commerceDiscountCommerceAccountGroupRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceDiscountCommerceAccountGroupRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceDiscountId = objectInput.readLong();

		commerceAccountGroupId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceDiscountCommerceAccountGroupRelId);

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

		objectOutput.writeLong(commerceDiscountId);

		objectOutput.writeLong(commerceAccountGroupId);
	}

	public long mvccVersion;
	public long commerceDiscountCommerceAccountGroupRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceDiscountId;
	public long commerceAccountGroupId;

}