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

package com.liferay.commerce.account.model.impl;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceAccountGroup in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceAccountGroupCacheModel
	implements CacheModel<CommerceAccountGroup>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAccountGroupCacheModel)) {
			return false;
		}

		CommerceAccountGroupCacheModel commerceAccountGroupCacheModel =
			(CommerceAccountGroupCacheModel)object;

		if (commerceAccountGroupId ==
				commerceAccountGroupCacheModel.commerceAccountGroupId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceAccountGroupId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);
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
		sb.append(", type=");
		sb.append(type);
		sb.append(", system=");
		sb.append(system);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceAccountGroup toEntityModel() {
		CommerceAccountGroupImpl commerceAccountGroupImpl =
			new CommerceAccountGroupImpl();

		if (externalReferenceCode == null) {
			commerceAccountGroupImpl.setExternalReferenceCode("");
		}
		else {
			commerceAccountGroupImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceAccountGroupImpl.setCommerceAccountGroupId(
			commerceAccountGroupId);
		commerceAccountGroupImpl.setCompanyId(companyId);
		commerceAccountGroupImpl.setUserId(userId);

		if (userName == null) {
			commerceAccountGroupImpl.setUserName("");
		}
		else {
			commerceAccountGroupImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceAccountGroupImpl.setCreateDate(null);
		}
		else {
			commerceAccountGroupImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceAccountGroupImpl.setModifiedDate(null);
		}
		else {
			commerceAccountGroupImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceAccountGroupImpl.setName("");
		}
		else {
			commerceAccountGroupImpl.setName(name);
		}

		commerceAccountGroupImpl.setType(type);
		commerceAccountGroupImpl.setSystem(system);

		commerceAccountGroupImpl.resetOriginalValues();

		return commerceAccountGroupImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		externalReferenceCode = objectInput.readUTF();

		commerceAccountGroupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		type = objectInput.readInt();

		system = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commerceAccountGroupId);

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

		objectOutput.writeInt(type);

		objectOutput.writeBoolean(system);
	}

	public String externalReferenceCode;
	public long commerceAccountGroupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public int type;
	public boolean system;

}