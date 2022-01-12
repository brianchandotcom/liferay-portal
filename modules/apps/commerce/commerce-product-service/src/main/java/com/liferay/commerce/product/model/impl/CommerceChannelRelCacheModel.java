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

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CommerceChannelRel;
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
 * The cache model class for representing CommerceChannelRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceChannelRelCacheModel
	implements CacheModel<CommerceChannelRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceChannelRelCacheModel)) {
			return false;
		}

		CommerceChannelRelCacheModel commerceChannelRelCacheModel =
			(CommerceChannelRelCacheModel)object;

		if ((commerceChannelRelId ==
				commerceChannelRelCacheModel.commerceChannelRelId) &&
			(mvccVersion == commerceChannelRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceChannelRelId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", commerceChannelRelId=");
		sb.append(commerceChannelRelId);
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
		sb.append(", commerceChannelId=");
		sb.append(commerceChannelId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceChannelRel toEntityModel() {
		CommerceChannelRelImpl commerceChannelRelImpl =
			new CommerceChannelRelImpl();

		commerceChannelRelImpl.setMvccVersion(mvccVersion);
		commerceChannelRelImpl.setCtCollectionId(ctCollectionId);
		commerceChannelRelImpl.setCommerceChannelRelId(commerceChannelRelId);
		commerceChannelRelImpl.setCompanyId(companyId);
		commerceChannelRelImpl.setUserId(userId);

		if (userName == null) {
			commerceChannelRelImpl.setUserName("");
		}
		else {
			commerceChannelRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceChannelRelImpl.setCreateDate(null);
		}
		else {
			commerceChannelRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceChannelRelImpl.setModifiedDate(null);
		}
		else {
			commerceChannelRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceChannelRelImpl.setClassNameId(classNameId);
		commerceChannelRelImpl.setClassPK(classPK);
		commerceChannelRelImpl.setCommerceChannelId(commerceChannelId);

		commerceChannelRelImpl.resetOriginalValues();

		return commerceChannelRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		commerceChannelRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceChannelId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(commerceChannelRelId);

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

		objectOutput.writeLong(commerceChannelId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long commerceChannelRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long commerceChannelId;

}