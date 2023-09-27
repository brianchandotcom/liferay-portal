/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.model.impl;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
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
 * The cache model class for representing CommerceInventoryWarehouseRel in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryWarehouseRelCacheModel
	implements CacheModel<CommerceInventoryWarehouseRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceInventoryWarehouseRelCacheModel)) {
			return false;
		}

		CommerceInventoryWarehouseRelCacheModel
			commerceInventoryWarehouseRelCacheModel =
				(CommerceInventoryWarehouseRelCacheModel)object;

		if ((commerceInventoryWarehouseRelId ==
				commerceInventoryWarehouseRelCacheModel.
					commerceInventoryWarehouseRelId) &&
			(mvccVersion ==
				commerceInventoryWarehouseRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceInventoryWarehouseRelId);

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
		sb.append(", commerceInventoryWarehouseRelId=");
		sb.append(commerceInventoryWarehouseRelId);
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
		sb.append(", commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceInventoryWarehouseRel toEntityModel() {
		CommerceInventoryWarehouseRelImpl commerceInventoryWarehouseRelImpl =
			new CommerceInventoryWarehouseRelImpl();

		commerceInventoryWarehouseRelImpl.setMvccVersion(mvccVersion);
		commerceInventoryWarehouseRelImpl.setCommerceInventoryWarehouseRelId(
			commerceInventoryWarehouseRelId);
		commerceInventoryWarehouseRelImpl.setCompanyId(companyId);
		commerceInventoryWarehouseRelImpl.setUserId(userId);

		if (userName == null) {
			commerceInventoryWarehouseRelImpl.setUserName("");
		}
		else {
			commerceInventoryWarehouseRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceInventoryWarehouseRelImpl.setCreateDate(null);
		}
		else {
			commerceInventoryWarehouseRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceInventoryWarehouseRelImpl.setModifiedDate(null);
		}
		else {
			commerceInventoryWarehouseRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceInventoryWarehouseRelImpl.setClassNameId(classNameId);
		commerceInventoryWarehouseRelImpl.setClassPK(classPK);
		commerceInventoryWarehouseRelImpl.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);

		commerceInventoryWarehouseRelImpl.resetOriginalValues();

		return commerceInventoryWarehouseRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceInventoryWarehouseRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceInventoryWarehouseId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceInventoryWarehouseRelId);

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

		objectOutput.writeLong(commerceInventoryWarehouseId);
	}

	public long mvccVersion;
	public long commerceInventoryWarehouseRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long commerceInventoryWarehouseId;

}