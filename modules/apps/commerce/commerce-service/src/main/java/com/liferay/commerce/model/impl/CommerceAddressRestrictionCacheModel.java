/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceAddressRestriction;
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
 * The cache model class for representing CommerceAddressRestriction in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceAddressRestrictionCacheModel
	implements CacheModel<CommerceAddressRestriction>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAddressRestrictionCacheModel)) {
			return false;
		}

		CommerceAddressRestrictionCacheModel
			commerceAddressRestrictionCacheModel =
				(CommerceAddressRestrictionCacheModel)object;

		if ((commerceAddressRestrictionId ==
				commerceAddressRestrictionCacheModel.
					commerceAddressRestrictionId) &&
			(mvccVersion == commerceAddressRestrictionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceAddressRestrictionId);

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
		sb.append(", commerceAddressRestrictionId=");
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
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceAddressRestriction toEntityModel() {
		CommerceAddressRestrictionImpl commerceAddressRestrictionImpl =
			new CommerceAddressRestrictionImpl();

		commerceAddressRestrictionImpl.setMvccVersion(mvccVersion);
		commerceAddressRestrictionImpl.setCommerceAddressRestrictionId(
			commerceAddressRestrictionId);
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
			commerceAddressRestrictionImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceAddressRestrictionImpl.setClassNameId(classNameId);
		commerceAddressRestrictionImpl.setClassPK(classPK);
		commerceAddressRestrictionImpl.setCountryId(countryId);

		commerceAddressRestrictionImpl.resetOriginalValues();

		return commerceAddressRestrictionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceAddressRestrictionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		countryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		objectOutput.writeLong(countryId);
	}

	public long mvccVersion;
	public long commerceAddressRestrictionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long countryId;

}