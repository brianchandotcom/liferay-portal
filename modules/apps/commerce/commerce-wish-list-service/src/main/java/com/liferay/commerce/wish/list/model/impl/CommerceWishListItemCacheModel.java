/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.model.impl;

import com.liferay.commerce.wish.list.model.CommerceWishListItem;
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
 * The cache model class for representing CommerceWishListItem in entity cache.
 *
 * @author Andrea Di Giorgi
 * @generated
 */
public class CommerceWishListItemCacheModel
	implements CacheModel<CommerceWishListItem>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceWishListItemCacheModel)) {
			return false;
		}

		CommerceWishListItemCacheModel commerceWishListItemCacheModel =
			(CommerceWishListItemCacheModel)object;

		if ((commerceWishListItemId ==
				commerceWishListItemCacheModel.commerceWishListItemId) &&
			(mvccVersion == commerceWishListItemCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceWishListItemId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceWishListItemId=");
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
		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append(", json=");
		sb.append(json);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceWishListItem toEntityModel() {
		CommerceWishListItemImpl commerceWishListItemImpl =
			new CommerceWishListItemImpl();

		commerceWishListItemImpl.setMvccVersion(mvccVersion);
		commerceWishListItemImpl.setCommerceWishListItemId(
			commerceWishListItemId);
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

		if (CPInstanceUuid == null) {
			commerceWishListItemImpl.setCPInstanceUuid("");
		}
		else {
			commerceWishListItemImpl.setCPInstanceUuid(CPInstanceUuid);
		}

		commerceWishListItemImpl.setCProductId(CProductId);

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
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		commerceWishListItemId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceWishListId = objectInput.readLong();
		CPInstanceUuid = objectInput.readUTF();

		CProductId = objectInput.readLong();
		json = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		if (CPInstanceUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(CPInstanceUuid);
		}

		objectOutput.writeLong(CProductId);

		if (json == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(json);
		}
	}

	public long mvccVersion;
	public long commerceWishListItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceWishListId;
	public String CPInstanceUuid;
	public long CProductId;
	public String json;

}