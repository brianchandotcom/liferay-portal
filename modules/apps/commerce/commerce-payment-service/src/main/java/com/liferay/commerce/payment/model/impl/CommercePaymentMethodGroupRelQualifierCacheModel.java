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

package com.liferay.commerce.payment.model.impl;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
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
 * The cache model class for representing CommercePaymentMethodGroupRelQualifier in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommercePaymentMethodGroupRelQualifierCacheModel
	implements CacheModel<CommercePaymentMethodGroupRelQualifier>,
			   Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommercePaymentMethodGroupRelQualifierCacheModel)) {

			return false;
		}

		CommercePaymentMethodGroupRelQualifierCacheModel
			commercePaymentMethodGroupRelQualifierCacheModel =
				(CommercePaymentMethodGroupRelQualifierCacheModel)object;

		if ((commercePaymentMethodGroupRelQualifierId ==
				commercePaymentMethodGroupRelQualifierCacheModel.
					commercePaymentMethodGroupRelQualifierId) &&
			(mvccVersion ==
				commercePaymentMethodGroupRelQualifierCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(
			0, commercePaymentMethodGroupRelQualifierId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commercePaymentMethodGroupRelQualifierId=");
		sb.append(commercePaymentMethodGroupRelQualifierId);
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
		sb.append(", CommercePaymentMethodGroupRelId=");
		sb.append(CommercePaymentMethodGroupRelId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePaymentMethodGroupRelQualifier toEntityModel() {
		CommercePaymentMethodGroupRelQualifierImpl
			commercePaymentMethodGroupRelQualifierImpl =
				new CommercePaymentMethodGroupRelQualifierImpl();

		commercePaymentMethodGroupRelQualifierImpl.setMvccVersion(mvccVersion);
		commercePaymentMethodGroupRelQualifierImpl.
			setCommercePaymentMethodGroupRelQualifierId(
				commercePaymentMethodGroupRelQualifierId);
		commercePaymentMethodGroupRelQualifierImpl.setCompanyId(companyId);
		commercePaymentMethodGroupRelQualifierImpl.setUserId(userId);

		if (userName == null) {
			commercePaymentMethodGroupRelQualifierImpl.setUserName("");
		}
		else {
			commercePaymentMethodGroupRelQualifierImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePaymentMethodGroupRelQualifierImpl.setCreateDate(null);
		}
		else {
			commercePaymentMethodGroupRelQualifierImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePaymentMethodGroupRelQualifierImpl.setModifiedDate(null);
		}
		else {
			commercePaymentMethodGroupRelQualifierImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commercePaymentMethodGroupRelQualifierImpl.setClassNameId(classNameId);
		commercePaymentMethodGroupRelQualifierImpl.setClassPK(classPK);
		commercePaymentMethodGroupRelQualifierImpl.
			setCommercePaymentMethodGroupRelId(CommercePaymentMethodGroupRelId);

		commercePaymentMethodGroupRelQualifierImpl.resetOriginalValues();

		return commercePaymentMethodGroupRelQualifierImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commercePaymentMethodGroupRelQualifierId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		CommercePaymentMethodGroupRelId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commercePaymentMethodGroupRelQualifierId);

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

		objectOutput.writeLong(CommercePaymentMethodGroupRelId);
	}

	public long mvccVersion;
	public long commercePaymentMethodGroupRelQualifierId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long CommercePaymentMethodGroupRelId;

}