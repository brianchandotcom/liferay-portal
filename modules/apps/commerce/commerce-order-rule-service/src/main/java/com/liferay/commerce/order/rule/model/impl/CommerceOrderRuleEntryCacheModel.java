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

package com.liferay.commerce.order.rule.model.impl;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceOrderRuleEntry in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceOrderRuleEntryCacheModel
	implements CacheModel<CommerceOrderRuleEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceOrderRuleEntryCacheModel)) {
			return false;
		}

		CommerceOrderRuleEntryCacheModel commerceOrderRuleEntryCacheModel =
			(CommerceOrderRuleEntryCacheModel)object;

		if (commerceOrderRuleEntryId ==
				commerceOrderRuleEntryCacheModel.commerceOrderRuleEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceOrderRuleEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(41);

		sb.append("{externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceOrderRuleEntryId=");
		sb.append(commerceOrderRuleEntryId);
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
		sb.append(", active=");
		sb.append(active);
		sb.append(", description=");
		sb.append(description);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceOrderRuleEntry toEntityModel() {
		CommerceOrderRuleEntryImpl commerceOrderRuleEntryImpl =
			new CommerceOrderRuleEntryImpl();

		if (externalReferenceCode == null) {
			commerceOrderRuleEntryImpl.setExternalReferenceCode("");
		}
		else {
			commerceOrderRuleEntryImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceOrderRuleEntryImpl.setCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId);
		commerceOrderRuleEntryImpl.setCompanyId(companyId);
		commerceOrderRuleEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceOrderRuleEntryImpl.setUserName("");
		}
		else {
			commerceOrderRuleEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceOrderRuleEntryImpl.setCreateDate(null);
		}
		else {
			commerceOrderRuleEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceOrderRuleEntryImpl.setModifiedDate(null);
		}
		else {
			commerceOrderRuleEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceOrderRuleEntryImpl.setActive(active);

		if (description == null) {
			commerceOrderRuleEntryImpl.setDescription("");
		}
		else {
			commerceOrderRuleEntryImpl.setDescription(description);
		}

		if (displayDate == Long.MIN_VALUE) {
			commerceOrderRuleEntryImpl.setDisplayDate(null);
		}
		else {
			commerceOrderRuleEntryImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commerceOrderRuleEntryImpl.setExpirationDate(null);
		}
		else {
			commerceOrderRuleEntryImpl.setExpirationDate(
				new Date(expirationDate));
		}

		if (name == null) {
			commerceOrderRuleEntryImpl.setName("");
		}
		else {
			commerceOrderRuleEntryImpl.setName(name);
		}

		commerceOrderRuleEntryImpl.setPriority(priority);

		if (type == null) {
			commerceOrderRuleEntryImpl.setType("");
		}
		else {
			commerceOrderRuleEntryImpl.setType(type);
		}

		if (typeSettings == null) {
			commerceOrderRuleEntryImpl.setTypeSettings("");
		}
		else {
			commerceOrderRuleEntryImpl.setTypeSettings(typeSettings);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceOrderRuleEntryImpl.setLastPublishDate(null);
		}
		else {
			commerceOrderRuleEntryImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commerceOrderRuleEntryImpl.setStatus(status);
		commerceOrderRuleEntryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceOrderRuleEntryImpl.setStatusByUserName("");
		}
		else {
			commerceOrderRuleEntryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceOrderRuleEntryImpl.setStatusDate(null);
		}
		else {
			commerceOrderRuleEntryImpl.setStatusDate(new Date(statusDate));
		}

		commerceOrderRuleEntryImpl.resetOriginalValues();

		return commerceOrderRuleEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		externalReferenceCode = objectInput.readUTF();

		commerceOrderRuleEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		active = objectInput.readBoolean();
		description = objectInput.readUTF();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		name = objectInput.readUTF();

		priority = objectInput.readInt();
		type = objectInput.readUTF();
		typeSettings = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commerceOrderRuleEntryId);

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

		objectOutput.writeBoolean(active);

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(priority);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

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
	}

	public String externalReferenceCode;
	public long commerceOrderRuleEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean active;
	public String description;
	public long displayDate;
	public long expirationDate;
	public String name;
	public int priority;
	public String type;
	public String typeSettings;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}