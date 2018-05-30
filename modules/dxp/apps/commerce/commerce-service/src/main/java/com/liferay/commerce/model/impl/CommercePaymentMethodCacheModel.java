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

import com.liferay.commerce.model.CommercePaymentMethod;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommercePaymentMethod in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePaymentMethod
 * @generated
 */
@ProviderType
public class CommercePaymentMethodCacheModel implements CacheModel<CommercePaymentMethod>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePaymentMethodCacheModel)) {
			return false;
		}

		CommercePaymentMethodCacheModel commercePaymentMethodCacheModel = (CommercePaymentMethodCacheModel)obj;

		if (commercePaymentMethodId == commercePaymentMethodCacheModel.commercePaymentMethodId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commercePaymentMethodId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{commercePaymentMethodId=");
		sb.append(commercePaymentMethodId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", imageId=");
		sb.append(imageId);
		sb.append(", engineKey=");
		sb.append(engineKey);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePaymentMethod toEntityModel() {
		CommercePaymentMethodImpl commercePaymentMethodImpl = new CommercePaymentMethodImpl();

		commercePaymentMethodImpl.setCommercePaymentMethodId(commercePaymentMethodId);
		commercePaymentMethodImpl.setGroupId(groupId);
		commercePaymentMethodImpl.setCompanyId(companyId);
		commercePaymentMethodImpl.setUserId(userId);

		if (userName == null) {
			commercePaymentMethodImpl.setUserName("");
		}
		else {
			commercePaymentMethodImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePaymentMethodImpl.setCreateDate(null);
		}
		else {
			commercePaymentMethodImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePaymentMethodImpl.setModifiedDate(null);
		}
		else {
			commercePaymentMethodImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commercePaymentMethodImpl.setName("");
		}
		else {
			commercePaymentMethodImpl.setName(name);
		}

		if (description == null) {
			commercePaymentMethodImpl.setDescription("");
		}
		else {
			commercePaymentMethodImpl.setDescription(description);
		}

		commercePaymentMethodImpl.setImageId(imageId);

		if (engineKey == null) {
			commercePaymentMethodImpl.setEngineKey("");
		}
		else {
			commercePaymentMethodImpl.setEngineKey(engineKey);
		}

		commercePaymentMethodImpl.setPriority(priority);
		commercePaymentMethodImpl.setActive(active);

		commercePaymentMethodImpl.resetOriginalValues();

		return commercePaymentMethodImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commercePaymentMethodId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		imageId = objectInput.readLong();
		engineKey = objectInput.readUTF();

		priority = objectInput.readDouble();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commercePaymentMethodId);

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

		objectOutput.writeLong(imageId);

		if (engineKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(engineKey);
		}

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(active);
	}

	public long commercePaymentMethodId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public long imageId;
	public String engineKey;
	public double priority;
	public boolean active;
}