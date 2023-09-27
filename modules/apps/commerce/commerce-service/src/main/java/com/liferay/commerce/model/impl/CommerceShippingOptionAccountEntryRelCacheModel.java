/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
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
 * The cache model class for representing CommerceShippingOptionAccountEntryRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShippingOptionAccountEntryRelCacheModel
	implements CacheModel<CommerceShippingOptionAccountEntryRel>,
			   Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommerceShippingOptionAccountEntryRelCacheModel)) {

			return false;
		}

		CommerceShippingOptionAccountEntryRelCacheModel
			commerceShippingOptionAccountEntryRelCacheModel =
				(CommerceShippingOptionAccountEntryRelCacheModel)object;

		if ((CommerceShippingOptionAccountEntryRelId ==
				commerceShippingOptionAccountEntryRelCacheModel.
					CommerceShippingOptionAccountEntryRelId) &&
			(mvccVersion ==
				commerceShippingOptionAccountEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(
			0, CommerceShippingOptionAccountEntryRelId);

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
		sb.append(", CommerceShippingOptionAccountEntryRelId=");
		sb.append(CommerceShippingOptionAccountEntryRelId);
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
		sb.append(", accountEntryId=");
		sb.append(accountEntryId);
		sb.append(", commerceChannelId=");
		sb.append(commerceChannelId);
		sb.append(", commerceShippingMethodKey=");
		sb.append(commerceShippingMethodKey);
		sb.append(", commerceShippingOptionKey=");
		sb.append(commerceShippingOptionKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceShippingOptionAccountEntryRel toEntityModel() {
		CommerceShippingOptionAccountEntryRelImpl
			commerceShippingOptionAccountEntryRelImpl =
				new CommerceShippingOptionAccountEntryRelImpl();

		commerceShippingOptionAccountEntryRelImpl.setMvccVersion(mvccVersion);
		commerceShippingOptionAccountEntryRelImpl.
			setCommerceShippingOptionAccountEntryRelId(
				CommerceShippingOptionAccountEntryRelId);
		commerceShippingOptionAccountEntryRelImpl.setCompanyId(companyId);
		commerceShippingOptionAccountEntryRelImpl.setUserId(userId);

		if (userName == null) {
			commerceShippingOptionAccountEntryRelImpl.setUserName("");
		}
		else {
			commerceShippingOptionAccountEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceShippingOptionAccountEntryRelImpl.setCreateDate(null);
		}
		else {
			commerceShippingOptionAccountEntryRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceShippingOptionAccountEntryRelImpl.setModifiedDate(null);
		}
		else {
			commerceShippingOptionAccountEntryRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceShippingOptionAccountEntryRelImpl.setAccountEntryId(
			accountEntryId);
		commerceShippingOptionAccountEntryRelImpl.setCommerceChannelId(
			commerceChannelId);

		if (commerceShippingMethodKey == null) {
			commerceShippingOptionAccountEntryRelImpl.
				setCommerceShippingMethodKey("");
		}
		else {
			commerceShippingOptionAccountEntryRelImpl.
				setCommerceShippingMethodKey(commerceShippingMethodKey);
		}

		if (commerceShippingOptionKey == null) {
			commerceShippingOptionAccountEntryRelImpl.
				setCommerceShippingOptionKey("");
		}
		else {
			commerceShippingOptionAccountEntryRelImpl.
				setCommerceShippingOptionKey(commerceShippingOptionKey);
		}

		commerceShippingOptionAccountEntryRelImpl.resetOriginalValues();

		return commerceShippingOptionAccountEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		CommerceShippingOptionAccountEntryRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		accountEntryId = objectInput.readLong();

		commerceChannelId = objectInput.readLong();
		commerceShippingMethodKey = objectInput.readUTF();
		commerceShippingOptionKey = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(CommerceShippingOptionAccountEntryRelId);

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

		objectOutput.writeLong(accountEntryId);

		objectOutput.writeLong(commerceChannelId);

		if (commerceShippingMethodKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(commerceShippingMethodKey);
		}

		if (commerceShippingOptionKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(commerceShippingOptionKey);
		}
	}

	public long mvccVersion;
	public long CommerceShippingOptionAccountEntryRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long accountEntryId;
	public long commerceChannelId;
	public String commerceShippingMethodKey;
	public String commerceShippingOptionKey;

}