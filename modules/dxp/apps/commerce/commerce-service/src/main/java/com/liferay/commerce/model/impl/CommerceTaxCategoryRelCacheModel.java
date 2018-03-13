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

import com.liferay.commerce.model.CommerceTaxCategoryRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceTaxCategoryRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryRel
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryRelCacheModel implements CacheModel<CommerceTaxCategoryRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTaxCategoryRelCacheModel)) {
			return false;
		}

		CommerceTaxCategoryRelCacheModel commerceTaxCategoryRelCacheModel = (CommerceTaxCategoryRelCacheModel)obj;

		if (commerceTaxCategoryRelId == commerceTaxCategoryRelCacheModel.commerceTaxCategoryRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceTaxCategoryRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceTaxCategoryRelId=");
		sb.append(commerceTaxCategoryRelId);
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
		sb.append(", commerceTaxCategoryId=");
		sb.append(commerceTaxCategoryId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTaxCategoryRel toEntityModel() {
		CommerceTaxCategoryRelImpl commerceTaxCategoryRelImpl = new CommerceTaxCategoryRelImpl();

		commerceTaxCategoryRelImpl.setCommerceTaxCategoryRelId(commerceTaxCategoryRelId);
		commerceTaxCategoryRelImpl.setGroupId(groupId);
		commerceTaxCategoryRelImpl.setCompanyId(companyId);
		commerceTaxCategoryRelImpl.setUserId(userId);

		if (userName == null) {
			commerceTaxCategoryRelImpl.setUserName("");
		}
		else {
			commerceTaxCategoryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTaxCategoryRelImpl.setCreateDate(null);
		}
		else {
			commerceTaxCategoryRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTaxCategoryRelImpl.setModifiedDate(null);
		}
		else {
			commerceTaxCategoryRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceTaxCategoryRelImpl.setCommerceTaxCategoryId(commerceTaxCategoryId);
		commerceTaxCategoryRelImpl.setClassNameId(classNameId);
		commerceTaxCategoryRelImpl.setClassPK(classPK);

		commerceTaxCategoryRelImpl.resetOriginalValues();

		return commerceTaxCategoryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceTaxCategoryRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceTaxCategoryId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceTaxCategoryRelId);

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

		objectOutput.writeLong(commerceTaxCategoryId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
	}

	public long commerceTaxCategoryRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceTaxCategoryId;
	public long classNameId;
	public long classPK;
}