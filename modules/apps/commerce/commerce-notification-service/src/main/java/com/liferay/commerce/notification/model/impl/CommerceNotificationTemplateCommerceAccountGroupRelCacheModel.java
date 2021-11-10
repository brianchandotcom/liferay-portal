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

package com.liferay.commerce.notification.model.impl;

import com.liferay.commerce.notification.model.CommerceNotificationTemplateCommerceAccountGroupRel;
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
 * The cache model class for representing CommerceNotificationTemplateCommerceAccountGroupRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelCacheModel
	implements CacheModel<CommerceNotificationTemplateCommerceAccountGroupRel>,
			   Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommerceNotificationTemplateCommerceAccountGroupRelCacheModel)) {

			return false;
		}

		CommerceNotificationTemplateCommerceAccountGroupRelCacheModel
			commerceNotificationTemplateCommerceAccountGroupRelCacheModel =
				(CommerceNotificationTemplateCommerceAccountGroupRelCacheModel)
					object;

		if ((commerceNotificationTemplateCommerceAccountGroupRelId ==
				commerceNotificationTemplateCommerceAccountGroupRelCacheModel.
					commerceNotificationTemplateCommerceAccountGroupRelId) &&
			(mvccVersion ==
				commerceNotificationTemplateCommerceAccountGroupRelCacheModel.
					mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(
			0, commerceNotificationTemplateCommerceAccountGroupRelId);

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
		sb.append(", commerceNotificationTemplateCommerceAccountGroupRelId=");
		sb.append(commerceNotificationTemplateCommerceAccountGroupRelId);
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
		sb.append(", commerceNotificationTemplateId=");
		sb.append(commerceNotificationTemplateId);
		sb.append(", commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel toEntityModel() {
		CommerceNotificationTemplateCommerceAccountGroupRelImpl
			commerceNotificationTemplateCommerceAccountGroupRelImpl =
				new CommerceNotificationTemplateCommerceAccountGroupRelImpl();

		commerceNotificationTemplateCommerceAccountGroupRelImpl.setMvccVersion(
			mvccVersion);
		commerceNotificationTemplateCommerceAccountGroupRelImpl.
			setCommerceNotificationTemplateCommerceAccountGroupRelId(
				commerceNotificationTemplateCommerceAccountGroupRelId);
		commerceNotificationTemplateCommerceAccountGroupRelImpl.setGroupId(
			groupId);
		commerceNotificationTemplateCommerceAccountGroupRelImpl.setCompanyId(
			companyId);
		commerceNotificationTemplateCommerceAccountGroupRelImpl.setUserId(
			userId);

		if (userName == null) {
			commerceNotificationTemplateCommerceAccountGroupRelImpl.setUserName(
				"");
		}
		else {
			commerceNotificationTemplateCommerceAccountGroupRelImpl.setUserName(
				userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceNotificationTemplateCommerceAccountGroupRelImpl.
				setCreateDate(null);
		}
		else {
			commerceNotificationTemplateCommerceAccountGroupRelImpl.
				setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceNotificationTemplateCommerceAccountGroupRelImpl.
				setModifiedDate(null);
		}
		else {
			commerceNotificationTemplateCommerceAccountGroupRelImpl.
				setModifiedDate(new Date(modifiedDate));
		}

		commerceNotificationTemplateCommerceAccountGroupRelImpl.
			setCommerceNotificationTemplateId(commerceNotificationTemplateId);
		commerceNotificationTemplateCommerceAccountGroupRelImpl.
			setCommerceAccountGroupId(commerceAccountGroupId);

		commerceNotificationTemplateCommerceAccountGroupRelImpl.
			resetOriginalValues();

		return commerceNotificationTemplateCommerceAccountGroupRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceNotificationTemplateCommerceAccountGroupRelId =
			objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceNotificationTemplateId = objectInput.readLong();

		commerceAccountGroupId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(
			commerceNotificationTemplateCommerceAccountGroupRelId);

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

		objectOutput.writeLong(commerceNotificationTemplateId);

		objectOutput.writeLong(commerceAccountGroupId);
	}

	public long mvccVersion;
	public long commerceNotificationTemplateCommerceAccountGroupRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceNotificationTemplateId;
	public long commerceAccountGroupId;

}