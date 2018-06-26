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

package com.liferay.commerce.discount.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceDiscountUsageEntry in entity cache.
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntry
 * @generated
 */
@ProviderType
public class CommerceDiscountUsageEntryCacheModel implements CacheModel<CommerceDiscountUsageEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceDiscountUsageEntryCacheModel)) {
			return false;
		}

		CommerceDiscountUsageEntryCacheModel commerceDiscountUsageEntryCacheModel =
			(CommerceDiscountUsageEntryCacheModel)obj;

		if (commerceDiscountUsageEntryId == commerceDiscountUsageEntryCacheModel.commerceDiscountUsageEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceDiscountUsageEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{commerceDiscountUsageEntryId=");
		sb.append(commerceDiscountUsageEntryId);
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
		sb.append(", discountUserId=");
		sb.append(discountUserId);
		sb.append(", discountOrganizationId=");
		sb.append(discountOrganizationId);
		sb.append(", commerceOrderId=");
		sb.append(commerceOrderId);
		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscountUsageEntry toEntityModel() {
		CommerceDiscountUsageEntryImpl commerceDiscountUsageEntryImpl = new CommerceDiscountUsageEntryImpl();

		commerceDiscountUsageEntryImpl.setCommerceDiscountUsageEntryId(commerceDiscountUsageEntryId);
		commerceDiscountUsageEntryImpl.setGroupId(groupId);
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
			commerceDiscountUsageEntryImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceDiscountUsageEntryImpl.setDiscountUserId(discountUserId);
		commerceDiscountUsageEntryImpl.setDiscountOrganizationId(discountOrganizationId);
		commerceDiscountUsageEntryImpl.setCommerceOrderId(commerceOrderId);
		commerceDiscountUsageEntryImpl.setCommerceDiscountId(commerceDiscountId);

		commerceDiscountUsageEntryImpl.resetOriginalValues();

		return commerceDiscountUsageEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceDiscountUsageEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		discountUserId = objectInput.readLong();

		discountOrganizationId = objectInput.readLong();

		commerceOrderId = objectInput.readLong();

		commerceDiscountId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceDiscountUsageEntryId);

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

		objectOutput.writeLong(discountUserId);

		objectOutput.writeLong(discountOrganizationId);

		objectOutput.writeLong(commerceOrderId);

		objectOutput.writeLong(commerceDiscountId);
	}

	public long commerceDiscountUsageEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long discountUserId;
	public long discountOrganizationId;
	public long commerceOrderId;
	public long commerceDiscountId;
}