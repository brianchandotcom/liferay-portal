/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model.impl;

import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
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
 * The cache model class for representing CommerceDiscountAccountRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountAccountRelCacheModel
	implements CacheModel<CommerceDiscountAccountRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceDiscountAccountRelCacheModel)) {
			return false;
		}

		CommerceDiscountAccountRelCacheModel
			commerceDiscountAccountRelCacheModel =
				(CommerceDiscountAccountRelCacheModel)object;

		if ((commerceDiscountAccountRelId ==
				commerceDiscountAccountRelCacheModel.
					commerceDiscountAccountRelId) &&
			(mvccVersion == commerceDiscountAccountRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceDiscountAccountRelId);

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
		sb.append(", commerceDiscountAccountRelId=");
		sb.append(commerceDiscountAccountRelId);
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
		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);
		sb.append(", order=");
		sb.append(order);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscountAccountRel toEntityModel() {
		CommerceDiscountAccountRelImpl commerceDiscountAccountRelImpl =
			new CommerceDiscountAccountRelImpl();

		commerceDiscountAccountRelImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceDiscountAccountRelImpl.setUuid("");
		}
		else {
			commerceDiscountAccountRelImpl.setUuid(uuid);
		}

		commerceDiscountAccountRelImpl.setCommerceDiscountAccountRelId(
			commerceDiscountAccountRelId);
		commerceDiscountAccountRelImpl.setCompanyId(companyId);
		commerceDiscountAccountRelImpl.setUserId(userId);

		if (userName == null) {
			commerceDiscountAccountRelImpl.setUserName("");
		}
		else {
			commerceDiscountAccountRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDiscountAccountRelImpl.setCreateDate(null);
		}
		else {
			commerceDiscountAccountRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDiscountAccountRelImpl.setModifiedDate(null);
		}
		else {
			commerceDiscountAccountRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceDiscountAccountRelImpl.setCommerceAccountId(commerceAccountId);
		commerceDiscountAccountRelImpl.setCommerceDiscountId(
			commerceDiscountId);
		commerceDiscountAccountRelImpl.setOrder(order);

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceDiscountAccountRelImpl.setLastPublishDate(null);
		}
		else {
			commerceDiscountAccountRelImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commerceDiscountAccountRelImpl.resetOriginalValues();

		return commerceDiscountAccountRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		commerceDiscountAccountRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceAccountId = objectInput.readLong();

		commerceDiscountId = objectInput.readLong();

		order = objectInput.readInt();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(commerceDiscountAccountRelId);

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

		objectOutput.writeLong(commerceDiscountId);

		objectOutput.writeInt(order);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long commerceDiscountAccountRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceAccountId;
	public long commerceDiscountId;
	public int order;
	public long lastPublishDate;

}