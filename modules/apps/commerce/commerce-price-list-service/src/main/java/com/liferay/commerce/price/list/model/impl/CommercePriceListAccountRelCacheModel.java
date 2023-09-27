/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.model.impl;

import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
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
 * The cache model class for representing CommercePriceListAccountRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListAccountRelCacheModel
	implements CacheModel<CommercePriceListAccountRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceListAccountRelCacheModel)) {
			return false;
		}

		CommercePriceListAccountRelCacheModel
			commercePriceListAccountRelCacheModel =
				(CommercePriceListAccountRelCacheModel)object;

		if ((commercePriceListAccountRelId ==
				commercePriceListAccountRelCacheModel.
					commercePriceListAccountRelId) &&
			(mvccVersion ==
				commercePriceListAccountRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePriceListAccountRelId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", commercePriceListAccountRelId=");
		sb.append(commercePriceListAccountRelId);
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
		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);
		sb.append(", commercePriceListId=");
		sb.append(commercePriceListId);
		sb.append(", order=");
		sb.append(order);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePriceListAccountRel toEntityModel() {
		CommercePriceListAccountRelImpl commercePriceListAccountRelImpl =
			new CommercePriceListAccountRelImpl();

		commercePriceListAccountRelImpl.setMvccVersion(mvccVersion);
		commercePriceListAccountRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			commercePriceListAccountRelImpl.setUuid("");
		}
		else {
			commercePriceListAccountRelImpl.setUuid(uuid);
		}

		commercePriceListAccountRelImpl.setCommercePriceListAccountRelId(
			commercePriceListAccountRelId);
		commercePriceListAccountRelImpl.setCompanyId(companyId);
		commercePriceListAccountRelImpl.setUserId(userId);

		if (userName == null) {
			commercePriceListAccountRelImpl.setUserName("");
		}
		else {
			commercePriceListAccountRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceListAccountRelImpl.setCreateDate(null);
		}
		else {
			commercePriceListAccountRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceListAccountRelImpl.setModifiedDate(null);
		}
		else {
			commercePriceListAccountRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commercePriceListAccountRelImpl.setCommerceAccountId(commerceAccountId);
		commercePriceListAccountRelImpl.setCommercePriceListId(
			commercePriceListId);
		commercePriceListAccountRelImpl.setOrder(order);

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceListAccountRelImpl.setLastPublishDate(null);
		}
		else {
			commercePriceListAccountRelImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commercePriceListAccountRelImpl.resetOriginalValues();

		return commercePriceListAccountRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		commercePriceListAccountRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceAccountId = objectInput.readLong();

		commercePriceListId = objectInput.readLong();

		order = objectInput.readInt();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(commercePriceListAccountRelId);

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

		objectOutput.writeLong(commerceAccountId);

		objectOutput.writeLong(commercePriceListId);

		objectOutput.writeInt(order);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long commercePriceListAccountRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceAccountId;
	public long commercePriceListId;
	public int order;
	public long lastPublishDate;

}