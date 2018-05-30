/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceAddressRestriction;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceAddressRestriction in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestriction
 * @generated
 */
@ProviderType
public class CommerceAddressRestrictionCacheModel implements CacheModel<CommerceAddressRestriction>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceAddressRestrictionCacheModel)) {
			return false;
		}

		CommerceAddressRestrictionCacheModel commerceAddressRestrictionCacheModel =
			(CommerceAddressRestrictionCacheModel)obj;

		if (commerceAddressRestrictionId == commerceAddressRestrictionCacheModel.commerceAddressRestrictionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceAddressRestrictionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceAddressRestrictionId=");
		sb.append(commerceAddressRestrictionId);
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
		sb.append(", commerceCountryId=");
		sb.append(commerceCountryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceAddressRestriction toEntityModel() {
		CommerceAddressRestrictionImpl commerceAddressRestrictionImpl = new CommerceAddressRestrictionImpl();

		commerceAddressRestrictionImpl.setCommerceAddressRestrictionId(commerceAddressRestrictionId);
		commerceAddressRestrictionImpl.setGroupId(groupId);
		commerceAddressRestrictionImpl.setCompanyId(companyId);
		commerceAddressRestrictionImpl.setUserId(userId);

		if (userName == null) {
			commerceAddressRestrictionImpl.setUserName("");
		}
		else {
			commerceAddressRestrictionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceAddressRestrictionImpl.setCreateDate(null);
		}
		else {
			commerceAddressRestrictionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceAddressRestrictionImpl.setModifiedDate(null);
		}
		else {
			commerceAddressRestrictionImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceAddressRestrictionImpl.setClassNameId(classNameId);
		commerceAddressRestrictionImpl.setClassPK(classPK);
		commerceAddressRestrictionImpl.setCommerceCountryId(commerceCountryId);

		commerceAddressRestrictionImpl.resetOriginalValues();

		return commerceAddressRestrictionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceAddressRestrictionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceCountryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceAddressRestrictionId);

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

		objectOutput.writeLong(commerceCountryId);
	}

	public long commerceAddressRestrictionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long commerceCountryId;
}