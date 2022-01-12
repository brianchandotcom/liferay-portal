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

package com.liferay.commerce.pricing.model.impl;

import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
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
 * The cache model class for representing CommercePricingClassCPDefinitionRel in entity cache.
 *
 * @author Riccardo Alberti
 * @generated
 */
public class CommercePricingClassCPDefinitionRelCacheModel
	implements CacheModel<CommercePricingClassCPDefinitionRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommercePricingClassCPDefinitionRelCacheModel)) {

			return false;
		}

		CommercePricingClassCPDefinitionRelCacheModel
			commercePricingClassCPDefinitionRelCacheModel =
				(CommercePricingClassCPDefinitionRelCacheModel)object;

		if ((CommercePricingClassCPDefinitionRelId ==
				commercePricingClassCPDefinitionRelCacheModel.
					CommercePricingClassCPDefinitionRelId) &&
			(mvccVersion ==
				commercePricingClassCPDefinitionRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CommercePricingClassCPDefinitionRelId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", CommercePricingClassCPDefinitionRelId=");
		sb.append(CommercePricingClassCPDefinitionRelId);
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
		sb.append(", commercePricingClassId=");
		sb.append(commercePricingClassId);
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePricingClassCPDefinitionRel toEntityModel() {
		CommercePricingClassCPDefinitionRelImpl
			commercePricingClassCPDefinitionRelImpl =
				new CommercePricingClassCPDefinitionRelImpl();

		commercePricingClassCPDefinitionRelImpl.setMvccVersion(mvccVersion);
		commercePricingClassCPDefinitionRelImpl.setCtCollectionId(
			ctCollectionId);
		commercePricingClassCPDefinitionRelImpl.
			setCommercePricingClassCPDefinitionRelId(
				CommercePricingClassCPDefinitionRelId);
		commercePricingClassCPDefinitionRelImpl.setCompanyId(companyId);
		commercePricingClassCPDefinitionRelImpl.setUserId(userId);

		if (userName == null) {
			commercePricingClassCPDefinitionRelImpl.setUserName("");
		}
		else {
			commercePricingClassCPDefinitionRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePricingClassCPDefinitionRelImpl.setCreateDate(null);
		}
		else {
			commercePricingClassCPDefinitionRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePricingClassCPDefinitionRelImpl.setModifiedDate(null);
		}
		else {
			commercePricingClassCPDefinitionRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commercePricingClassCPDefinitionRelImpl.setCommercePricingClassId(
			commercePricingClassId);
		commercePricingClassCPDefinitionRelImpl.setCPDefinitionId(
			CPDefinitionId);

		commercePricingClassCPDefinitionRelImpl.resetOriginalValues();

		return commercePricingClassCPDefinitionRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		CommercePricingClassCPDefinitionRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePricingClassId = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(CommercePricingClassCPDefinitionRelId);

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

		objectOutput.writeLong(commercePricingClassId);

		objectOutput.writeLong(CPDefinitionId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long CommercePricingClassCPDefinitionRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePricingClassId;
	public long CPDefinitionId;

}