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

package com.liferay.commerce.shipping.engine.fixed.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CommerceShippingFixedOption in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOption
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionCacheModel implements CacheModel<CommerceShippingFixedOption>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceShippingFixedOptionCacheModel)) {
			return false;
		}

		CommerceShippingFixedOptionCacheModel commerceShippingFixedOptionCacheModel =
			(CommerceShippingFixedOptionCacheModel)obj;

		if (commerceShippingFixedOptionId == commerceShippingFixedOptionCacheModel.commerceShippingFixedOptionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceShippingFixedOptionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);
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
		sb.append(", commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", amount=");
		sb.append(amount);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceShippingFixedOption toEntityModel() {
		CommerceShippingFixedOptionImpl commerceShippingFixedOptionImpl = new CommerceShippingFixedOptionImpl();

		commerceShippingFixedOptionImpl.setCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
		commerceShippingFixedOptionImpl.setGroupId(groupId);
		commerceShippingFixedOptionImpl.setCompanyId(companyId);
		commerceShippingFixedOptionImpl.setUserId(userId);

		if (userName == null) {
			commerceShippingFixedOptionImpl.setUserName("");
		}
		else {
			commerceShippingFixedOptionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceShippingFixedOptionImpl.setCreateDate(null);
		}
		else {
			commerceShippingFixedOptionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceShippingFixedOptionImpl.setModifiedDate(null);
		}
		else {
			commerceShippingFixedOptionImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceShippingFixedOptionImpl.setCommerceShippingMethodId(commerceShippingMethodId);

		if (name == null) {
			commerceShippingFixedOptionImpl.setName("");
		}
		else {
			commerceShippingFixedOptionImpl.setName(name);
		}

		if (description == null) {
			commerceShippingFixedOptionImpl.setDescription("");
		}
		else {
			commerceShippingFixedOptionImpl.setDescription(description);
		}

		commerceShippingFixedOptionImpl.setAmount(amount);
		commerceShippingFixedOptionImpl.setPriority(priority);

		commerceShippingFixedOptionImpl.resetOriginalValues();

		return commerceShippingFixedOptionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		commerceShippingFixedOptionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		amount = (BigDecimal)objectInput.readObject();

		priority = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceShippingFixedOptionId);

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

		objectOutput.writeLong(commerceShippingMethodId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeObject(amount);

		objectOutput.writeDouble(priority);
	}

	public long commerceShippingFixedOptionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceShippingMethodId;
	public String name;
	public String description;
	public BigDecimal amount;
	public double priority;
}