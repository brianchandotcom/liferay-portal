/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceWarehouseItem;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceWarehouseItem in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseItem
 * @generated
 */
@ProviderType
public class CommerceWarehouseItemCacheModel implements CacheModel<CommerceWarehouseItem>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceWarehouseItemCacheModel)) {
			return false;
		}

		CommerceWarehouseItemCacheModel commerceWarehouseItemCacheModel = (CommerceWarehouseItemCacheModel)obj;

		if (commerceWarehouseItemId == commerceWarehouseItemCacheModel.commerceWarehouseItemId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceWarehouseItemId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceWarehouseItemId=");
		sb.append(commerceWarehouseItemId);
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
		sb.append(", commerceWarehouseId=");
		sb.append(commerceWarehouseId);
		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceWarehouseItem toEntityModel() {
		CommerceWarehouseItemImpl commerceWarehouseItemImpl = new CommerceWarehouseItemImpl();

		commerceWarehouseItemImpl.setCommerceWarehouseItemId(commerceWarehouseItemId);
		commerceWarehouseItemImpl.setGroupId(groupId);
		commerceWarehouseItemImpl.setCompanyId(companyId);
		commerceWarehouseItemImpl.setUserId(userId);

		if (userName == null) {
			commerceWarehouseItemImpl.setUserName("");
		}
		else {
			commerceWarehouseItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceWarehouseItemImpl.setCreateDate(null);
		}
		else {
			commerceWarehouseItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceWarehouseItemImpl.setModifiedDate(null);
		}
		else {
			commerceWarehouseItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceWarehouseItemImpl.setCommerceWarehouseId(commerceWarehouseId);
		commerceWarehouseItemImpl.setCPInstanceId(CPInstanceId);
		commerceWarehouseItemImpl.setQuantity(quantity);

		commerceWarehouseItemImpl.resetOriginalValues();

		return commerceWarehouseItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceWarehouseItemId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceWarehouseId = objectInput.readLong();

		CPInstanceId = objectInput.readLong();

		quantity = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceWarehouseItemId);

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

		objectOutput.writeLong(commerceWarehouseId);

		objectOutput.writeLong(CPInstanceId);

		objectOutput.writeInt(quantity);
	}

	public long commerceWarehouseItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceWarehouseId;
	public long CPInstanceId;
	public int quantity;
}