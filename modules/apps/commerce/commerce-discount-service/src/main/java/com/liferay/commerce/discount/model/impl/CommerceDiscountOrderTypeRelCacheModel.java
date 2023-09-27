/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model.impl;

import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
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
 * The cache model class for representing CommerceDiscountOrderTypeRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountOrderTypeRelCacheModel
	implements CacheModel<CommerceDiscountOrderTypeRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceDiscountOrderTypeRelCacheModel)) {
			return false;
		}

		CommerceDiscountOrderTypeRelCacheModel
			commerceDiscountOrderTypeRelCacheModel =
				(CommerceDiscountOrderTypeRelCacheModel)object;

		if ((commerceDiscountOrderTypeRelId ==
				commerceDiscountOrderTypeRelCacheModel.
					commerceDiscountOrderTypeRelId) &&
			(mvccVersion ==
				commerceDiscountOrderTypeRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceDiscountOrderTypeRelId);

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
		sb.append(", commerceDiscountOrderTypeRelId=");
		sb.append(commerceDiscountOrderTypeRelId);
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
		sb.append(", commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscountOrderTypeRel toEntityModel() {
		CommerceDiscountOrderTypeRelImpl commerceDiscountOrderTypeRelImpl =
			new CommerceDiscountOrderTypeRelImpl();

		commerceDiscountOrderTypeRelImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceDiscountOrderTypeRelImpl.setUuid("");
		}
		else {
			commerceDiscountOrderTypeRelImpl.setUuid(uuid);
		}

		commerceDiscountOrderTypeRelImpl.setCommerceDiscountOrderTypeRelId(
			commerceDiscountOrderTypeRelId);
		commerceDiscountOrderTypeRelImpl.setCompanyId(companyId);
		commerceDiscountOrderTypeRelImpl.setUserId(userId);

		if (userName == null) {
			commerceDiscountOrderTypeRelImpl.setUserName("");
		}
		else {
			commerceDiscountOrderTypeRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDiscountOrderTypeRelImpl.setCreateDate(null);
		}
		else {
			commerceDiscountOrderTypeRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDiscountOrderTypeRelImpl.setModifiedDate(null);
		}
		else {
			commerceDiscountOrderTypeRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceDiscountOrderTypeRelImpl.setCommerceDiscountId(
			commerceDiscountId);
		commerceDiscountOrderTypeRelImpl.setCommerceOrderTypeId(
			commerceOrderTypeId);
		commerceDiscountOrderTypeRelImpl.setPriority(priority);

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceDiscountOrderTypeRelImpl.setLastPublishDate(null);
		}
		else {
			commerceDiscountOrderTypeRelImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commerceDiscountOrderTypeRelImpl.resetOriginalValues();

		return commerceDiscountOrderTypeRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		commerceDiscountOrderTypeRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceDiscountId = objectInput.readLong();

		commerceOrderTypeId = objectInput.readLong();

		priority = objectInput.readInt();
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

		objectOutput.writeLong(commerceDiscountOrderTypeRelId);

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

		objectOutput.writeLong(commerceOrderTypeId);

		objectOutput.writeInt(priority);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long commerceDiscountOrderTypeRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceDiscountId;
	public long commerceOrderTypeId;
	public int priority;
	public long lastPublishDate;

}