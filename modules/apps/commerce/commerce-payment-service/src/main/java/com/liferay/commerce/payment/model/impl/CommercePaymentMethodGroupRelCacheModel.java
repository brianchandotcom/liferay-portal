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

package com.liferay.commerce.payment.model.impl;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
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
 * The cache model class for representing CommercePaymentMethodGroupRel in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommercePaymentMethodGroupRelCacheModel
	implements CacheModel<CommercePaymentMethodGroupRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePaymentMethodGroupRelCacheModel)) {
			return false;
		}

		CommercePaymentMethodGroupRelCacheModel
			commercePaymentMethodGroupRelCacheModel =
				(CommercePaymentMethodGroupRelCacheModel)object;

		if ((commercePaymentMethodGroupRelId ==
				commercePaymentMethodGroupRelCacheModel.
					commercePaymentMethodGroupRelId) &&
			(mvccVersion ==
				commercePaymentMethodGroupRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePaymentMethodGroupRelId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commercePaymentMethodGroupRelId=");
		sb.append(commercePaymentMethodGroupRelId);
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
	public CommercePaymentMethodGroupRel toEntityModel() {
		CommercePaymentMethodGroupRelImpl commercePaymentMethodGroupRelImpl =
			new CommercePaymentMethodGroupRelImpl();

		commercePaymentMethodGroupRelImpl.setMvccVersion(mvccVersion);
		commercePaymentMethodGroupRelImpl.setCommercePaymentMethodGroupRelId(
			commercePaymentMethodGroupRelId);
		commercePaymentMethodGroupRelImpl.setGroupId(groupId);
		commercePaymentMethodGroupRelImpl.setCompanyId(companyId);
		commercePaymentMethodGroupRelImpl.setUserId(userId);

		if (userName == null) {
			commercePaymentMethodGroupRelImpl.setUserName("");
		}
		else {
			commercePaymentMethodGroupRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePaymentMethodGroupRelImpl.setCreateDate(null);
		}
		else {
			commercePaymentMethodGroupRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePaymentMethodGroupRelImpl.setModifiedDate(null);
		}
		else {
			commercePaymentMethodGroupRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (name == null) {
			commercePaymentMethodGroupRelImpl.setName("");
		}
		else {
			commercePaymentMethodGroupRelImpl.setName(name);
		}

		if (description == null) {
			commercePaymentMethodGroupRelImpl.setDescription("");
		}
		else {
			commercePaymentMethodGroupRelImpl.setDescription(description);
		}

		commercePaymentMethodGroupRelImpl.setImageId(imageId);

		if (engineKey == null) {
			commercePaymentMethodGroupRelImpl.setEngineKey("");
		}
		else {
			commercePaymentMethodGroupRelImpl.setEngineKey(engineKey);
		}

		commercePaymentMethodGroupRelImpl.setPriority(priority);
		commercePaymentMethodGroupRelImpl.setActive(active);

		commercePaymentMethodGroupRelImpl.resetOriginalValues();

		return commercePaymentMethodGroupRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commercePaymentMethodGroupRelId = objectInput.readLong();

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
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commercePaymentMethodGroupRelId);

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

	public long mvccVersion;
	public long commercePaymentMethodGroupRelId;
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