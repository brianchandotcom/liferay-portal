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

package com.liferay.commerce.vat.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.vat.model.CommerceVatNumber;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceVatNumber in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumber
 * @generated
 */
@ProviderType
public class CommerceVatNumberCacheModel implements CacheModel<CommerceVatNumber>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceVatNumberCacheModel)) {
			return false;
		}

		CommerceVatNumberCacheModel commerceVatNumberCacheModel = (CommerceVatNumberCacheModel)obj;

		if (commerceVatNumberId == commerceVatNumberCacheModel.commerceVatNumberId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceVatNumberId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{commerceVatNumberId=");
		sb.append(commerceVatNumberId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", value=");
		sb.append(value);
		sb.append(", valid=");
		sb.append(valid);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceVatNumber toEntityModel() {
		CommerceVatNumberImpl commerceVatNumberImpl = new CommerceVatNumberImpl();

		commerceVatNumberImpl.setCommerceVatNumberId(commerceVatNumberId);
		commerceVatNumberImpl.setGroupId(groupId);
		commerceVatNumberImpl.setCompanyId(companyId);
		commerceVatNumberImpl.setUserId(userId);

		if (userName == null) {
			commerceVatNumberImpl.setUserName("");
		}
		else {
			commerceVatNumberImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceVatNumberImpl.setCreateDate(null);
		}
		else {
			commerceVatNumberImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceVatNumberImpl.setModifiedDate(null);
		}
		else {
			commerceVatNumberImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceVatNumberImpl.setClassNameId(classNameId);
		commerceVatNumberImpl.setClassPK(classPK);

		if (value == null) {
			commerceVatNumberImpl.setValue("");
		}
		else {
			commerceVatNumberImpl.setValue(value);
		}

		commerceVatNumberImpl.setValid(valid);

		commerceVatNumberImpl.resetOriginalValues();

		return commerceVatNumberImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceVatNumberId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		value = objectInput.readUTF();

		valid = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceVatNumberId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}

		objectOutput.writeBoolean(valid);
	}

	public long commerceVatNumberId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String value;
	public boolean valid;
}