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

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceOrderRuleEntryRel in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceOrderRuleEntryRelCacheModel
	implements CacheModel<CommerceOrderRuleEntryRel>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceOrderRuleEntryRelCacheModel)) {
			return false;
		}

		CommerceOrderRuleEntryRelCacheModel
			commerceOrderRuleEntryRelCacheModel =
				(CommerceOrderRuleEntryRelCacheModel)object;

		if (commerceOrderRuleEntryRelId ==
				commerceOrderRuleEntryRelCacheModel.
					commerceOrderRuleEntryRelId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceOrderRuleEntryRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{commerceOrderRuleEntryRelId=");
		sb.append(commerceOrderRuleEntryRelId);
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
		sb.append(", commerceOrderRuleEntryId=");
		sb.append(commerceOrderRuleEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceOrderRuleEntryRel toEntityModel() {
		CommerceOrderRuleEntryRelImpl commerceOrderRuleEntryRelImpl =
			new CommerceOrderRuleEntryRelImpl();

		commerceOrderRuleEntryRelImpl.setCommerceOrderRuleEntryRelId(
			commerceOrderRuleEntryRelId);
		commerceOrderRuleEntryRelImpl.setCompanyId(companyId);
		commerceOrderRuleEntryRelImpl.setUserId(userId);

		if (userName == null) {
			commerceOrderRuleEntryRelImpl.setUserName("");
		}
		else {
			commerceOrderRuleEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceOrderRuleEntryRelImpl.setCreateDate(null);
		}
		else {
			commerceOrderRuleEntryRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceOrderRuleEntryRelImpl.setModifiedDate(null);
		}
		else {
			commerceOrderRuleEntryRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceOrderRuleEntryRelImpl.setClassNameId(classNameId);
		commerceOrderRuleEntryRelImpl.setClassPK(classPK);
		commerceOrderRuleEntryRelImpl.setCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId);

		commerceOrderRuleEntryRelImpl.resetOriginalValues();

		return commerceOrderRuleEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceOrderRuleEntryRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceOrderRuleEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(commerceOrderRuleEntryRelId);

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

		objectOutput.writeLong(commerceOrderRuleEntryId);
	}

	public long commerceOrderRuleEntryRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long commerceOrderRuleEntryId;

}