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

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPDefinition;
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
 * The cache model class for representing CPDefinition in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPDefinitionCacheModel
	implements CacheModel<CPDefinition>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionCacheModel)) {
			return false;
		}

		CPDefinitionCacheModel cpDefinitionCacheModel =
			(CPDefinitionCacheModel)object;

		if ((CPDefinitionId == cpDefinitionCacheModel.CPDefinitionId) &&
			(mvccVersion == cpDefinitionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPDefinitionId);

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
		StringBundler sb = new StringBundler(97);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", defaultLanguageId=");
		sb.append(defaultLanguageId);
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
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
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append(", CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);
		sb.append(", productTypeName=");
		sb.append(productTypeName);
		sb.append(", availableIndividually=");
		sb.append(availableIndividually);
		sb.append(", ignoreSKUCombinations=");
		sb.append(ignoreSKUCombinations);
		sb.append(", shippable=");
		sb.append(shippable);
		sb.append(", freeShipping=");
		sb.append(freeShipping);
		sb.append(", shipSeparately=");
		sb.append(shipSeparately);
		sb.append(", shippingExtraPrice=");
		sb.append(shippingExtraPrice);
		sb.append(", width=");
		sb.append(width);
		sb.append(", height=");
		sb.append(height);
		sb.append(", depth=");
		sb.append(depth);
		sb.append(", weight=");
		sb.append(weight);
		sb.append(", taxExempt=");
		sb.append(taxExempt);
		sb.append(", telcoOrElectronics=");
		sb.append(telcoOrElectronics);
		sb.append(", DDMStructureKey=");
		sb.append(DDMStructureKey);
		sb.append(", published=");
		sb.append(published);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", subscriptionEnabled=");
		sb.append(subscriptionEnabled);
		sb.append(", subscriptionLength=");
		sb.append(subscriptionLength);
		sb.append(", subscriptionType=");
		sb.append(subscriptionType);
		sb.append(", subscriptionTypeSettings=");
		sb.append(subscriptionTypeSettings);
		sb.append(", maxSubscriptionCycles=");
		sb.append(maxSubscriptionCycles);
		sb.append(", deliverySubscriptionEnabled=");
		sb.append(deliverySubscriptionEnabled);
		sb.append(", deliverySubscriptionLength=");
		sb.append(deliverySubscriptionLength);
		sb.append(", deliverySubscriptionType=");
		sb.append(deliverySubscriptionType);
		sb.append(", deliverySubscriptionTypeSettings=");
		sb.append(deliverySubscriptionTypeSettings);
		sb.append(", deliveryMaxSubscriptionCycles=");
		sb.append(deliveryMaxSubscriptionCycles);
		sb.append(", accountGroupFilterEnabled=");
		sb.append(accountGroupFilterEnabled);
		sb.append(", channelFilterEnabled=");
		sb.append(channelFilterEnabled);
		sb.append(", version=");
		sb.append(version);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinition toEntityModel() {
		CPDefinitionImpl cpDefinitionImpl = new CPDefinitionImpl();

		cpDefinitionImpl.setMvccVersion(mvccVersion);
		cpDefinitionImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			cpDefinitionImpl.setUuid("");
		}
		else {
			cpDefinitionImpl.setUuid(uuid);
		}

		if (defaultLanguageId == null) {
			cpDefinitionImpl.setDefaultLanguageId("");
		}
		else {
			cpDefinitionImpl.setDefaultLanguageId(defaultLanguageId);
		}

		cpDefinitionImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionImpl.setGroupId(groupId);
		cpDefinitionImpl.setCompanyId(companyId);
		cpDefinitionImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionImpl.setUserName("");
		}
		else {
			cpDefinitionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setCreateDate(null);
		}
		else {
			cpDefinitionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpDefinitionImpl.setCProductId(CProductId);
		cpDefinitionImpl.setCPTaxCategoryId(CPTaxCategoryId);

		if (productTypeName == null) {
			cpDefinitionImpl.setProductTypeName("");
		}
		else {
			cpDefinitionImpl.setProductTypeName(productTypeName);
		}

		cpDefinitionImpl.setAvailableIndividually(availableIndividually);
		cpDefinitionImpl.setIgnoreSKUCombinations(ignoreSKUCombinations);
		cpDefinitionImpl.setShippable(shippable);
		cpDefinitionImpl.setFreeShipping(freeShipping);
		cpDefinitionImpl.setShipSeparately(shipSeparately);
		cpDefinitionImpl.setShippingExtraPrice(shippingExtraPrice);
		cpDefinitionImpl.setWidth(width);
		cpDefinitionImpl.setHeight(height);
		cpDefinitionImpl.setDepth(depth);
		cpDefinitionImpl.setWeight(weight);
		cpDefinitionImpl.setTaxExempt(taxExempt);
		cpDefinitionImpl.setTelcoOrElectronics(telcoOrElectronics);

		if (DDMStructureKey == null) {
			cpDefinitionImpl.setDDMStructureKey("");
		}
		else {
			cpDefinitionImpl.setDDMStructureKey(DDMStructureKey);
		}

		cpDefinitionImpl.setPublished(published);

		if (displayDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setDisplayDate(null);
		}
		else {
			cpDefinitionImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setExpirationDate(null);
		}
		else {
			cpDefinitionImpl.setExpirationDate(new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setLastPublishDate(null);
		}
		else {
			cpDefinitionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		cpDefinitionImpl.setSubscriptionEnabled(subscriptionEnabled);
		cpDefinitionImpl.setSubscriptionLength(subscriptionLength);

		if (subscriptionType == null) {
			cpDefinitionImpl.setSubscriptionType("");
		}
		else {
			cpDefinitionImpl.setSubscriptionType(subscriptionType);
		}

		if (subscriptionTypeSettings == null) {
			cpDefinitionImpl.setSubscriptionTypeSettings("");
		}
		else {
			cpDefinitionImpl.setSubscriptionTypeSettings(
				subscriptionTypeSettings);
		}

		cpDefinitionImpl.setMaxSubscriptionCycles(maxSubscriptionCycles);
		cpDefinitionImpl.setDeliverySubscriptionEnabled(
			deliverySubscriptionEnabled);
		cpDefinitionImpl.setDeliverySubscriptionLength(
			deliverySubscriptionLength);

		if (deliverySubscriptionType == null) {
			cpDefinitionImpl.setDeliverySubscriptionType("");
		}
		else {
			cpDefinitionImpl.setDeliverySubscriptionType(
				deliverySubscriptionType);
		}

		if (deliverySubscriptionTypeSettings == null) {
			cpDefinitionImpl.setDeliverySubscriptionTypeSettings("");
		}
		else {
			cpDefinitionImpl.setDeliverySubscriptionTypeSettings(
				deliverySubscriptionTypeSettings);
		}

		cpDefinitionImpl.setDeliveryMaxSubscriptionCycles(
			deliveryMaxSubscriptionCycles);
		cpDefinitionImpl.setAccountGroupFilterEnabled(
			accountGroupFilterEnabled);
		cpDefinitionImpl.setChannelFilterEnabled(channelFilterEnabled);
		cpDefinitionImpl.setVersion(version);
		cpDefinitionImpl.setStatus(status);
		cpDefinitionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			cpDefinitionImpl.setStatusByUserName("");
		}
		else {
			cpDefinitionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setStatusDate(null);
		}
		else {
			cpDefinitionImpl.setStatusDate(new Date(statusDate));
		}

		cpDefinitionImpl.resetOriginalValues();

		return cpDefinitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		defaultLanguageId = objectInput.readUTF();

		CPDefinitionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CProductId = objectInput.readLong();

		CPTaxCategoryId = objectInput.readLong();
		productTypeName = objectInput.readUTF();

		availableIndividually = objectInput.readBoolean();

		ignoreSKUCombinations = objectInput.readBoolean();

		shippable = objectInput.readBoolean();

		freeShipping = objectInput.readBoolean();

		shipSeparately = objectInput.readBoolean();

		shippingExtraPrice = objectInput.readDouble();

		width = objectInput.readDouble();

		height = objectInput.readDouble();

		depth = objectInput.readDouble();

		weight = objectInput.readDouble();

		taxExempt = objectInput.readBoolean();

		telcoOrElectronics = objectInput.readBoolean();
		DDMStructureKey = objectInput.readUTF();

		published = objectInput.readBoolean();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();

		subscriptionEnabled = objectInput.readBoolean();

		subscriptionLength = objectInput.readInt();
		subscriptionType = objectInput.readUTF();
		subscriptionTypeSettings = (String)objectInput.readObject();

		maxSubscriptionCycles = objectInput.readLong();

		deliverySubscriptionEnabled = objectInput.readBoolean();

		deliverySubscriptionLength = objectInput.readInt();
		deliverySubscriptionType = objectInput.readUTF();
		deliverySubscriptionTypeSettings = objectInput.readUTF();

		deliveryMaxSubscriptionCycles = objectInput.readLong();

		accountGroupFilterEnabled = objectInput.readBoolean();

		channelFilterEnabled = objectInput.readBoolean();

		version = objectInput.readInt();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (defaultLanguageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(defaultLanguageId);
		}

		objectOutput.writeLong(CPDefinitionId);

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

		objectOutput.writeLong(CProductId);

		objectOutput.writeLong(CPTaxCategoryId);

		if (productTypeName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(productTypeName);
		}

		objectOutput.writeBoolean(availableIndividually);

		objectOutput.writeBoolean(ignoreSKUCombinations);

		objectOutput.writeBoolean(shippable);

		objectOutput.writeBoolean(freeShipping);

		objectOutput.writeBoolean(shipSeparately);

		objectOutput.writeDouble(shippingExtraPrice);

		objectOutput.writeDouble(width);

		objectOutput.writeDouble(height);

		objectOutput.writeDouble(depth);

		objectOutput.writeDouble(weight);

		objectOutput.writeBoolean(taxExempt);

		objectOutput.writeBoolean(telcoOrElectronics);

		if (DDMStructureKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(DDMStructureKey);
		}

		objectOutput.writeBoolean(published);
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeBoolean(subscriptionEnabled);

		objectOutput.writeInt(subscriptionLength);

		if (subscriptionType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(subscriptionType);
		}

		if (subscriptionTypeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(subscriptionTypeSettings);
		}

		objectOutput.writeLong(maxSubscriptionCycles);

		objectOutput.writeBoolean(deliverySubscriptionEnabled);

		objectOutput.writeInt(deliverySubscriptionLength);

		if (deliverySubscriptionType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(deliverySubscriptionType);
		}

		if (deliverySubscriptionTypeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(deliverySubscriptionTypeSettings);
		}

		objectOutput.writeLong(deliveryMaxSubscriptionCycles);

		objectOutput.writeBoolean(accountGroupFilterEnabled);

		objectOutput.writeBoolean(channelFilterEnabled);

		objectOutput.writeInt(version);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String defaultLanguageId;
	public long CPDefinitionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CProductId;
	public long CPTaxCategoryId;
	public String productTypeName;
	public boolean availableIndividually;
	public boolean ignoreSKUCombinations;
	public boolean shippable;
	public boolean freeShipping;
	public boolean shipSeparately;
	public double shippingExtraPrice;
	public double width;
	public double height;
	public double depth;
	public double weight;
	public boolean taxExempt;
	public boolean telcoOrElectronics;
	public String DDMStructureKey;
	public boolean published;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public boolean subscriptionEnabled;
	public int subscriptionLength;
	public String subscriptionType;
	public String subscriptionTypeSettings;
	public long maxSubscriptionCycles;
	public boolean deliverySubscriptionEnabled;
	public int deliverySubscriptionLength;
	public String deliverySubscriptionType;
	public String deliverySubscriptionTypeSettings;
	public long deliveryMaxSubscriptionCycles;
	public boolean accountGroupFilterEnabled;
	public boolean channelFilterEnabled;
	public int version;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}