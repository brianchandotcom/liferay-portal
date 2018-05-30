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

package com.liferay.commerce.wish.list.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.wish.list.model.CommerceWishListItem;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceWishListItem in entity cache.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItem
 * @generated
 */
@ProviderType
public class CommerceWishListItemCacheModel implements CacheModel<CommerceWishListItem>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceWishListItemCacheModel)) {
			return false;
		}

		CommerceWishListItemCacheModel commerceWishListItemCacheModel = (CommerceWishListItemCacheModel)obj;

		if (commerceWishListItemId == commerceWishListItemCacheModel.commerceWishListItemId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceWishListItemId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{commerceWishListItemId=");
		sb.append(commerceWishListItemId);
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
		sb.append(", commerceWishListId=");
		sb.append(commerceWishListId);
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);
		sb.append(", json=");
		sb.append(json);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceWishListItem toEntityModel() {
		CommerceWishListItemImpl commerceWishListItemImpl = new CommerceWishListItemImpl();

		commerceWishListItemImpl.setCommerceWishListItemId(commerceWishListItemId);
		commerceWishListItemImpl.setGroupId(groupId);
		commerceWishListItemImpl.setCompanyId(companyId);
		commerceWishListItemImpl.setUserId(userId);

		if (userName == null) {
			commerceWishListItemImpl.setUserName("");
		}
		else {
			commerceWishListItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceWishListItemImpl.setCreateDate(null);
		}
		else {
			commerceWishListItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceWishListItemImpl.setModifiedDate(null);
		}
		else {
			commerceWishListItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceWishListItemImpl.setCommerceWishListId(commerceWishListId);
		commerceWishListItemImpl.setCPDefinitionId(CPDefinitionId);
		commerceWishListItemImpl.setCPInstanceId(CPInstanceId);

		if (json == null) {
			commerceWishListItemImpl.setJson("");
		}
		else {
			commerceWishListItemImpl.setJson(json);
		}

		commerceWishListItemImpl.resetOriginalValues();

		return commerceWishListItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceWishListItemId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceWishListId = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		CPInstanceId = objectInput.readLong();
		json = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceWishListItemId);

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

		objectOutput.writeLong(commerceWishListId);

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(CPInstanceId);

		if (json == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(json);
		}
	}

	public long commerceWishListItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceWishListId;
	public long CPDefinitionId;
	public long CPInstanceId;
	public String json;
}