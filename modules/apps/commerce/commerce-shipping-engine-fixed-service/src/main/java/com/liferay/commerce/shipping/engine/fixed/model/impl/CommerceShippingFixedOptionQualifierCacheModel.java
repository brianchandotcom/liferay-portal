/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.model.impl;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
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
 * The cache model class for representing CommerceShippingFixedOptionQualifier in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShippingFixedOptionQualifierCacheModel
	implements CacheModel<CommerceShippingFixedOptionQualifier>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommerceShippingFixedOptionQualifierCacheModel)) {

			return false;
		}

		CommerceShippingFixedOptionQualifierCacheModel
			commerceShippingFixedOptionQualifierCacheModel =
				(CommerceShippingFixedOptionQualifierCacheModel)object;

		if ((commerceShippingFixedOptionQualifierId ==
				commerceShippingFixedOptionQualifierCacheModel.
					commerceShippingFixedOptionQualifierId) &&
			(mvccVersion ==
				commerceShippingFixedOptionQualifierCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceShippingFixedOptionQualifierId);

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
		sb.append(", commerceShippingFixedOptionQualifierId=");
		sb.append(commerceShippingFixedOptionQualifierId);
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
		sb.append(", commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceShippingFixedOptionQualifier toEntityModel() {
		CommerceShippingFixedOptionQualifierImpl
			commerceShippingFixedOptionQualifierImpl =
				new CommerceShippingFixedOptionQualifierImpl();

		commerceShippingFixedOptionQualifierImpl.setMvccVersion(mvccVersion);
		commerceShippingFixedOptionQualifierImpl.
			setCommerceShippingFixedOptionQualifierId(
				commerceShippingFixedOptionQualifierId);
		commerceShippingFixedOptionQualifierImpl.setCompanyId(companyId);
		commerceShippingFixedOptionQualifierImpl.setUserId(userId);

		if (userName == null) {
			commerceShippingFixedOptionQualifierImpl.setUserName("");
		}
		else {
			commerceShippingFixedOptionQualifierImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceShippingFixedOptionQualifierImpl.setCreateDate(null);
		}
		else {
			commerceShippingFixedOptionQualifierImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceShippingFixedOptionQualifierImpl.setModifiedDate(null);
		}
		else {
			commerceShippingFixedOptionQualifierImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceShippingFixedOptionQualifierImpl.setClassNameId(classNameId);
		commerceShippingFixedOptionQualifierImpl.setClassPK(classPK);
		commerceShippingFixedOptionQualifierImpl.
			setCommerceShippingFixedOptionId(commerceShippingFixedOptionId);

		commerceShippingFixedOptionQualifierImpl.resetOriginalValues();

		return commerceShippingFixedOptionQualifierImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceShippingFixedOptionQualifierId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceShippingFixedOptionId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceShippingFixedOptionQualifierId);

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

		objectOutput.writeLong(commerceShippingFixedOptionId);
	}

	public long mvccVersion;
	public long commerceShippingFixedOptionQualifierId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long commerceShippingFixedOptionId;

}