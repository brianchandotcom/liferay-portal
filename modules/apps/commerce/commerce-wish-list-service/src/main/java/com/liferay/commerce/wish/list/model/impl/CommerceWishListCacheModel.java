/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.model.impl;

import com.liferay.commerce.wish.list.model.CommerceWishList;
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
 * The cache model class for representing CommerceWishList in entity cache.
 *
 * @author Andrea Di Giorgi
 * @generated
 */
public class CommerceWishListCacheModel
	implements CacheModel<CommerceWishList>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceWishListCacheModel)) {
			return false;
		}

		CommerceWishListCacheModel commerceWishListCacheModel =
			(CommerceWishListCacheModel)object;

		if ((commerceWishListId ==
				commerceWishListCacheModel.commerceWishListId) &&
			(mvccVersion == commerceWishListCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceWishListId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", commerceWishListId=");
		sb.append(commerceWishListId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", defaultWishList=");
		sb.append(defaultWishList);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceWishList toEntityModel() {
		CommerceWishListImpl commerceWishListImpl = new CommerceWishListImpl();

		commerceWishListImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceWishListImpl.setUuid("");
		}
		else {
			commerceWishListImpl.setUuid(uuid);
		}

		commerceWishListImpl.setCommerceWishListId(commerceWishListId);
		commerceWishListImpl.setGroupId(groupId);
		commerceWishListImpl.setCompanyId(companyId);
		commerceWishListImpl.setUserId(userId);

		if (userName == null) {
			commerceWishListImpl.setUserName("");
		}
		else {
			commerceWishListImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceWishListImpl.setCreateDate(null);
		}
		else {
			commerceWishListImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceWishListImpl.setModifiedDate(null);
		}
		else {
			commerceWishListImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceWishListImpl.setName("");
		}
		else {
			commerceWishListImpl.setName(name);
		}

		commerceWishListImpl.setDefaultWishList(defaultWishList);

		commerceWishListImpl.resetOriginalValues();

		return commerceWishListImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		commerceWishListId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		defaultWishList = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(commerceWishListId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(defaultWishList);
	}

	public long mvccVersion;
	public String uuid;
	public long commerceWishListId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public boolean defaultWishList;

}