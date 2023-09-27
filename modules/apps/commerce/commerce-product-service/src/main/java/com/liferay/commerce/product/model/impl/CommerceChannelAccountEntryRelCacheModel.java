/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
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
 * The cache model class for representing CommerceChannelAccountEntryRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceChannelAccountEntryRelCacheModel
	implements CacheModel<CommerceChannelAccountEntryRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceChannelAccountEntryRelCacheModel)) {
			return false;
		}

		CommerceChannelAccountEntryRelCacheModel
			commerceChannelAccountEntryRelCacheModel =
				(CommerceChannelAccountEntryRelCacheModel)object;

		if ((commerceChannelAccountEntryRelId ==
				commerceChannelAccountEntryRelCacheModel.
					commerceChannelAccountEntryRelId) &&
			(mvccVersion ==
				commerceChannelAccountEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceChannelAccountEntryRelId);

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", commerceChannelAccountEntryRelId=");
		sb.append(commerceChannelAccountEntryRelId);
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
		sb.append(", accountEntryId=");
		sb.append(accountEntryId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", commerceChannelId=");
		sb.append(commerceChannelId);
		sb.append(", overrideEligibility=");
		sb.append(overrideEligibility);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceChannelAccountEntryRel toEntityModel() {
		CommerceChannelAccountEntryRelImpl commerceChannelAccountEntryRelImpl =
			new CommerceChannelAccountEntryRelImpl();

		commerceChannelAccountEntryRelImpl.setMvccVersion(mvccVersion);
		commerceChannelAccountEntryRelImpl.setCtCollectionId(ctCollectionId);
		commerceChannelAccountEntryRelImpl.setCommerceChannelAccountEntryRelId(
			commerceChannelAccountEntryRelId);
		commerceChannelAccountEntryRelImpl.setCompanyId(companyId);
		commerceChannelAccountEntryRelImpl.setUserId(userId);

		if (userName == null) {
			commerceChannelAccountEntryRelImpl.setUserName("");
		}
		else {
			commerceChannelAccountEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceChannelAccountEntryRelImpl.setCreateDate(null);
		}
		else {
			commerceChannelAccountEntryRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceChannelAccountEntryRelImpl.setModifiedDate(null);
		}
		else {
			commerceChannelAccountEntryRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceChannelAccountEntryRelImpl.setAccountEntryId(accountEntryId);
		commerceChannelAccountEntryRelImpl.setClassNameId(classNameId);
		commerceChannelAccountEntryRelImpl.setClassPK(classPK);
		commerceChannelAccountEntryRelImpl.setCommerceChannelId(
			commerceChannelId);
		commerceChannelAccountEntryRelImpl.setOverrideEligibility(
			overrideEligibility);
		commerceChannelAccountEntryRelImpl.setPriority(priority);
		commerceChannelAccountEntryRelImpl.setType(type);

		commerceChannelAccountEntryRelImpl.resetOriginalValues();

		return commerceChannelAccountEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		commerceChannelAccountEntryRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		accountEntryId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceChannelId = objectInput.readLong();

		overrideEligibility = objectInput.readBoolean();

		priority = objectInput.readDouble();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(commerceChannelAccountEntryRelId);

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

		objectOutput.writeLong(accountEntryId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(commerceChannelId);

		objectOutput.writeBoolean(overrideEligibility);

		objectOutput.writeDouble(priority);

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long commerceChannelAccountEntryRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long accountEntryId;
	public long classNameId;
	public long classPK;
	public long commerceChannelId;
	public boolean overrideEligibility;
	public double priority;
	public int type;

}