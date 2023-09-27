/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		sb.append(", commercePaymentMethodGroupRelId=");
		sb.append(commercePaymentMethodGroupRelId);
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
			setCommercePaymentMethodGroupRelId(commercePaymentMethodGroupRelId);

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

		commercePaymentMethodGroupRelId = objectInput.readLong();
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

		objectOutput.writeLong(commercePaymentMethodGroupRelId);
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
	public long commercePaymentMethodGroupRelId;

}