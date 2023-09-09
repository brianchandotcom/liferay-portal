/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model.impl;

import com.liferay.commerce.discount.model.CommerceDiscountRel;
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
 * The cache model class for representing CommerceDiscountRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountRelCacheModel
	implements CacheModel<CommerceDiscountRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceDiscountRelCacheModel)) {
			return false;
		}

		CommerceDiscountRelCacheModel commerceDiscountRelCacheModel =
			(CommerceDiscountRelCacheModel)object;

		if ((commerceDiscountRelId ==
				commerceDiscountRelCacheModel.commerceDiscountRelId) &&
			(mvccVersion == commerceDiscountRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceDiscountRelId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceDiscountRelId=");
		sb.append(commerceDiscountRelId);
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
		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscountRel toEntityModel() {
		CommerceDiscountRelImpl commerceDiscountRelImpl =
			new CommerceDiscountRelImpl();

		commerceDiscountRelImpl.setMvccVersion(mvccVersion);
		commerceDiscountRelImpl.setCommerceDiscountRelId(commerceDiscountRelId);
		commerceDiscountRelImpl.setCompanyId(companyId);
		commerceDiscountRelImpl.setUserId(userId);

		if (userName == null) {
			commerceDiscountRelImpl.setUserName("");
		}
		else {
			commerceDiscountRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDiscountRelImpl.setCreateDate(null);
		}
		else {
			commerceDiscountRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDiscountRelImpl.setModifiedDate(null);
		}
		else {
			commerceDiscountRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceDiscountRelImpl.setCommerceDiscountId(commerceDiscountId);
		commerceDiscountRelImpl.setClassNameId(classNameId);
		commerceDiscountRelImpl.setClassPK(classPK);

		if (typeSettings == null) {
			commerceDiscountRelImpl.setTypeSettings("");
		}
		else {
			commerceDiscountRelImpl.setTypeSettings(typeSettings);
		}

		commerceDiscountRelImpl.resetOriginalValues();

		return commerceDiscountRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		commerceDiscountRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceDiscountId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		typeSettings = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceDiscountRelId);

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

		objectOutput.writeLong(commerceDiscountId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (typeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(typeSettings);
		}
	}

	public long mvccVersion;
	public long commerceDiscountRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceDiscountId;
	public long classNameId;
	public long classPK;
	public String typeSettings;

}