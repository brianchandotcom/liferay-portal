/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceOrderPayment;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceOrderPayment in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPayment
 * @generated
 */
@ProviderType
public class CommerceOrderPaymentCacheModel implements CacheModel<CommerceOrderPayment>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceOrderPaymentCacheModel)) {
			return false;
		}

		CommerceOrderPaymentCacheModel commerceOrderPaymentCacheModel = (CommerceOrderPaymentCacheModel)obj;

		if (commerceOrderPaymentId == commerceOrderPaymentCacheModel.commerceOrderPaymentId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceOrderPaymentId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{commerceOrderPaymentId=");
		sb.append(commerceOrderPaymentId);
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
		sb.append(", commerceOrderId=");
		sb.append(commerceOrderId);
		sb.append(", commercePaymentMethodId=");
		sb.append(commercePaymentMethodId);
		sb.append(", status=");
		sb.append(status);
		sb.append(", content=");
		sb.append(content);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceOrderPayment toEntityModel() {
		CommerceOrderPaymentImpl commerceOrderPaymentImpl = new CommerceOrderPaymentImpl();

		commerceOrderPaymentImpl.setCommerceOrderPaymentId(commerceOrderPaymentId);
		commerceOrderPaymentImpl.setGroupId(groupId);
		commerceOrderPaymentImpl.setCompanyId(companyId);
		commerceOrderPaymentImpl.setUserId(userId);

		if (userName == null) {
			commerceOrderPaymentImpl.setUserName("");
		}
		else {
			commerceOrderPaymentImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceOrderPaymentImpl.setCreateDate(null);
		}
		else {
			commerceOrderPaymentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceOrderPaymentImpl.setModifiedDate(null);
		}
		else {
			commerceOrderPaymentImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceOrderPaymentImpl.setCommerceOrderId(commerceOrderId);
		commerceOrderPaymentImpl.setCommercePaymentMethodId(commercePaymentMethodId);
		commerceOrderPaymentImpl.setStatus(status);

		if (content == null) {
			commerceOrderPaymentImpl.setContent("");
		}
		else {
			commerceOrderPaymentImpl.setContent(content);
		}

		commerceOrderPaymentImpl.resetOriginalValues();

		return commerceOrderPaymentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceOrderPaymentId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceOrderId = objectInput.readLong();

		commercePaymentMethodId = objectInput.readLong();

		status = objectInput.readInt();
		content = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceOrderPaymentId);

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

		objectOutput.writeLong(commerceOrderId);

		objectOutput.writeLong(commercePaymentMethodId);

		objectOutput.writeInt(status);

		if (content == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(content);
		}
	}

	public long commerceOrderPaymentId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceOrderId;
	public long commercePaymentMethodId;
	public int status;
	public String content;
}