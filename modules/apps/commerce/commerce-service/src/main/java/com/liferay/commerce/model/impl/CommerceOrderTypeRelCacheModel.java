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

import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceOrderTypeRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderTypeRelCacheModel
	implements CacheModel<CommerceOrderTypeRel>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceOrderTypeRelCacheModel)) {
			return false;
		}

		CommerceOrderTypeRelCacheModel commerceOrderTypeRelCacheModel =
			(CommerceOrderTypeRelCacheModel)object;

		if (commerceOrderTypeRelId ==
				commerceOrderTypeRelCacheModel.commerceOrderTypeRelId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceOrderTypeRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceOrderTypeRelId=");
		sb.append(commerceOrderTypeRelId);
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
		sb.append(", commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceOrderTypeRel toEntityModel() {
		CommerceOrderTypeRelImpl commerceOrderTypeRelImpl =
			new CommerceOrderTypeRelImpl();

		if (externalReferenceCode == null) {
			commerceOrderTypeRelImpl.setExternalReferenceCode("");
		}
		else {
			commerceOrderTypeRelImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceOrderTypeRelImpl.setCommerceOrderTypeRelId(
			commerceOrderTypeRelId);
		commerceOrderTypeRelImpl.setCompanyId(companyId);
		commerceOrderTypeRelImpl.setUserId(userId);

		if (userName == null) {
			commerceOrderTypeRelImpl.setUserName("");
		}
		else {
			commerceOrderTypeRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceOrderTypeRelImpl.setCreateDate(null);
		}
		else {
			commerceOrderTypeRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceOrderTypeRelImpl.setModifiedDate(null);
		}
		else {
			commerceOrderTypeRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceOrderTypeRelImpl.setClassNameId(classNameId);
		commerceOrderTypeRelImpl.setClassPK(classPK);
		commerceOrderTypeRelImpl.setCommerceOrderTypeId(commerceOrderTypeId);

		commerceOrderTypeRelImpl.resetOriginalValues();

		return commerceOrderTypeRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		externalReferenceCode = objectInput.readUTF();

		commerceOrderTypeRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceOrderTypeId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commerceOrderTypeRelId);

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

		objectOutput.writeLong(commerceOrderTypeId);
	}

	public String externalReferenceCode;
	public long commerceOrderTypeRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long commerceOrderTypeId;

}