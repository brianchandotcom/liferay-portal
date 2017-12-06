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

import com.liferay.commerce.model.CommerceShipment;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceShipment in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipment
 * @generated
 */
@ProviderType
public class CommerceShipmentCacheModel implements CacheModel<CommerceShipment>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceShipmentCacheModel)) {
			return false;
		}

		CommerceShipmentCacheModel commerceShipmentCacheModel = (CommerceShipmentCacheModel)obj;

		if (commerceShipmentId == commerceShipmentCacheModel.commerceShipmentId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceShipmentId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{commerceShipmentId=");
		sb.append(commerceShipmentId);
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
		sb.append(", shipmentUserId=");
		sb.append(shipmentUserId);
		sb.append(", commerceAddressId=");
		sb.append(commerceAddressId);
		sb.append(", commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);
		sb.append(", carrier=");
		sb.append(carrier);
		sb.append(", trackingNumber=");
		sb.append(trackingNumber);
		sb.append(", expectedDuration=");
		sb.append(expectedDuration);
		sb.append(", status=");
		sb.append(status);
		sb.append(", shippingDate=");
		sb.append(shippingDate);
		sb.append(", expectedDate=");
		sb.append(expectedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceShipment toEntityModel() {
		CommerceShipmentImpl commerceShipmentImpl = new CommerceShipmentImpl();

		commerceShipmentImpl.setCommerceShipmentId(commerceShipmentId);
		commerceShipmentImpl.setGroupId(groupId);
		commerceShipmentImpl.setCompanyId(companyId);
		commerceShipmentImpl.setUserId(userId);

		if (userName == null) {
			commerceShipmentImpl.setUserName("");
		}
		else {
			commerceShipmentImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceShipmentImpl.setCreateDate(null);
		}
		else {
			commerceShipmentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceShipmentImpl.setModifiedDate(null);
		}
		else {
			commerceShipmentImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceShipmentImpl.setShipmentUserId(shipmentUserId);
		commerceShipmentImpl.setCommerceAddressId(commerceAddressId);
		commerceShipmentImpl.setCommerceShippingMethodId(commerceShippingMethodId);

		if (carrier == null) {
			commerceShipmentImpl.setCarrier("");
		}
		else {
			commerceShipmentImpl.setCarrier(carrier);
		}

		if (trackingNumber == null) {
			commerceShipmentImpl.setTrackingNumber("");
		}
		else {
			commerceShipmentImpl.setTrackingNumber(trackingNumber);
		}

		commerceShipmentImpl.setExpectedDuration(expectedDuration);
		commerceShipmentImpl.setStatus(status);

		if (shippingDate == Long.MIN_VALUE) {
			commerceShipmentImpl.setShippingDate(null);
		}
		else {
			commerceShipmentImpl.setShippingDate(new Date(shippingDate));
		}

		if (expectedDate == Long.MIN_VALUE) {
			commerceShipmentImpl.setExpectedDate(null);
		}
		else {
			commerceShipmentImpl.setExpectedDate(new Date(expectedDate));
		}

		commerceShipmentImpl.resetOriginalValues();

		return commerceShipmentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceShipmentId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		shipmentUserId = objectInput.readLong();

		commerceAddressId = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();
		carrier = objectInput.readUTF();
		trackingNumber = objectInput.readUTF();

		expectedDuration = objectInput.readInt();

		status = objectInput.readInt();
		shippingDate = objectInput.readLong();
		expectedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceShipmentId);

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

		objectOutput.writeLong(shipmentUserId);

		objectOutput.writeLong(commerceAddressId);

		objectOutput.writeLong(commerceShippingMethodId);

		if (carrier == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(carrier);
		}

		if (trackingNumber == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(trackingNumber);
		}

		objectOutput.writeInt(expectedDuration);

		objectOutput.writeInt(status);
		objectOutput.writeLong(shippingDate);
		objectOutput.writeLong(expectedDate);
	}

	public long commerceShipmentId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long shipmentUserId;
	public long commerceAddressId;
	public long commerceShippingMethodId;
	public String carrier;
	public String trackingNumber;
	public int expectedDuration;
	public int status;
	public long shippingDate;
	public long expectedDate;
}