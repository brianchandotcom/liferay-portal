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

import com.liferay.commerce.model.CommerceShipment;
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
 * The cache model class for representing CommerceShipment in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShipmentCacheModel
	implements CacheModel<CommerceShipment>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceShipmentCacheModel)) {
			return false;
		}

		CommerceShipmentCacheModel commerceShipmentCacheModel =
			(CommerceShipmentCacheModel)object;

		if ((commerceShipmentId ==
				commerceShipmentCacheModel.commerceShipmentId) &&
			(mvccVersion == commerceShipmentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceShipmentId);

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
		StringBundler sb = new StringBundler(37);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceShipmentId=");
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
		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);
		sb.append(", commerceAddressId=");
		sb.append(commerceAddressId);
		sb.append(", commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);
		sb.append(", shippingOptionName=");
		sb.append(shippingOptionName);
		sb.append(", carrier=");
		sb.append(carrier);
		sb.append(", trackingNumber=");
		sb.append(trackingNumber);
		sb.append(", shippingDate=");
		sb.append(shippingDate);
		sb.append(", expectedDate=");
		sb.append(expectedDate);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceShipment toEntityModel() {
		CommerceShipmentImpl commerceShipmentImpl = new CommerceShipmentImpl();

		commerceShipmentImpl.setMvccVersion(mvccVersion);

		if (externalReferenceCode == null) {
			commerceShipmentImpl.setExternalReferenceCode("");
		}
		else {
			commerceShipmentImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

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

		commerceShipmentImpl.setCommerceAccountId(commerceAccountId);
		commerceShipmentImpl.setCommerceAddressId(commerceAddressId);
		commerceShipmentImpl.setCommerceShippingMethodId(
			commerceShippingMethodId);

		if (shippingOptionName == null) {
			commerceShipmentImpl.setShippingOptionName("");
		}
		else {
			commerceShipmentImpl.setShippingOptionName(shippingOptionName);
		}

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

		commerceShipmentImpl.setStatus(status);

		commerceShipmentImpl.resetOriginalValues();

		return commerceShipmentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		externalReferenceCode = objectInput.readUTF();

		commerceShipmentId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceAccountId = objectInput.readLong();

		commerceAddressId = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();
		shippingOptionName = (String)objectInput.readObject();
		carrier = objectInput.readUTF();
		trackingNumber = objectInput.readUTF();
		shippingDate = objectInput.readLong();
		expectedDate = objectInput.readLong();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

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

		objectOutput.writeLong(commerceAccountId);

		objectOutput.writeLong(commerceAddressId);

		objectOutput.writeLong(commerceShippingMethodId);

		if (shippingOptionName == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(shippingOptionName);
		}

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

		objectOutput.writeLong(shippingDate);
		objectOutput.writeLong(expectedDate);

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public String externalReferenceCode;
	public long commerceShipmentId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceAccountId;
	public long commerceAddressId;
	public long commerceShippingMethodId;
	public String shippingOptionName;
	public String carrier;
	public String trackingNumber;
	public long shippingDate;
	public long expectedDate;
	public int status;

}