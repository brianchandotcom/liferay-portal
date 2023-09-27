/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		StringBundler sb = new StringBundler(31);

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
		sb.append(", active=");
		sb.append(active);
		sb.append(", imageId=");
		sb.append(imageId);
		sb.append(", paymentIntegrationKey=");
		sb.append(paymentIntegrationKey);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
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

		commercePaymentMethodGroupRelImpl.setActive(active);
		commercePaymentMethodGroupRelImpl.setImageId(imageId);

		if (paymentIntegrationKey == null) {
			commercePaymentMethodGroupRelImpl.setPaymentIntegrationKey("");
		}
		else {
			commercePaymentMethodGroupRelImpl.setPaymentIntegrationKey(
				paymentIntegrationKey);
		}

		commercePaymentMethodGroupRelImpl.setPriority(priority);

		if (typeSettings == null) {
			commercePaymentMethodGroupRelImpl.setTypeSettings("");
		}
		else {
			commercePaymentMethodGroupRelImpl.setTypeSettings(typeSettings);
		}

		commercePaymentMethodGroupRelImpl.resetOriginalValues();

		return commercePaymentMethodGroupRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

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

		active = objectInput.readBoolean();

		imageId = objectInput.readLong();
		paymentIntegrationKey = objectInput.readUTF();

		priority = objectInput.readDouble();
		typeSettings = (String)objectInput.readObject();
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

		objectOutput.writeBoolean(active);

		objectOutput.writeLong(imageId);

		if (paymentIntegrationKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(paymentIntegrationKey);
		}

		objectOutput.writeDouble(priority);

		if (typeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(typeSettings);
		}
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
	public boolean active;
	public long imageId;
	public String paymentIntegrationKey;
	public double priority;
	public String typeSettings;

}