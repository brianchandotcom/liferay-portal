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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinition;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDefinition in entity cache.
 *
 * @author Marco Leo
 * @see CPDefinition
 * @generated
 */
@ProviderType
public class CPDefinitionCacheModel implements CacheModel<CPDefinition>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionCacheModel)) {
			return false;
		}

		CPDefinitionCacheModel cpDefinitionCacheModel = (CPDefinitionCacheModel)obj;

		if (CPDefinitionId == cpDefinitionCacheModel.CPDefinitionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(65);

		sb.append("{uuid=");
		sb.append(uuid);
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
		sb.append(", CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);
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
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", defaultLanguageId=");
		sb.append(defaultLanguageId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinition toEntityModel() {
		CPDefinitionImpl cpDefinitionImpl = new CPDefinitionImpl();

		if (uuid == null) {
			cpDefinitionImpl.setUuid("");
		}
		else {
			cpDefinitionImpl.setUuid(uuid);
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
		cpDefinitionImpl.setCPTaxCategoryId(CPTaxCategoryId);
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

		if (defaultLanguageId == null) {
			cpDefinitionImpl.setDefaultLanguageId("");
		}
		else {
			cpDefinitionImpl.setDefaultLanguageId(defaultLanguageId);
		}

		cpDefinitionImpl.resetOriginalValues();

		return cpDefinitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDefinitionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
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

		CPTaxCategoryId = objectInput.readLong();

		taxExempt = objectInput.readBoolean();

		telcoOrElectronics = objectInput.readBoolean();
		DDMStructureKey = objectInput.readUTF();

		published = objectInput.readBoolean();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
		defaultLanguageId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
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

		objectOutput.writeLong(CPTaxCategoryId);

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

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);

		if (defaultLanguageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(defaultLanguageId);
		}
	}

	public String uuid;
	public long CPDefinitionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
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
	public long CPTaxCategoryId;
	public boolean taxExempt;
	public boolean telcoOrElectronics;
	public String DDMStructureKey;
	public boolean published;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public String defaultLanguageId;
}