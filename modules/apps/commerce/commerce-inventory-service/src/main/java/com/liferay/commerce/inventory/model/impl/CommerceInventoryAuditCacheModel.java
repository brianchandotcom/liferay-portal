/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.model.impl;

import com.liferay.commerce.inventory.model.CommerceInventoryAudit;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CommerceInventoryAudit in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryAuditCacheModel
	implements CacheModel<CommerceInventoryAudit>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceInventoryAuditCacheModel)) {
			return false;
		}

		CommerceInventoryAuditCacheModel commerceInventoryAuditCacheModel =
			(CommerceInventoryAuditCacheModel)object;

		if ((commerceInventoryAuditId ==
				commerceInventoryAuditCacheModel.commerceInventoryAuditId) &&
			(mvccVersion == commerceInventoryAuditCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceInventoryAuditId);

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
		sb.append(", commerceInventoryAuditId=");
		sb.append(commerceInventoryAuditId);
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
		sb.append(", logType=");
		sb.append(logType);
		sb.append(", logTypeSettings=");
		sb.append(logTypeSettings);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", sku=");
		sb.append(sku);
		sb.append(", unitOfMeasureKey=");
		sb.append(unitOfMeasureKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceInventoryAudit toEntityModel() {
		CommerceInventoryAuditImpl commerceInventoryAuditImpl =
			new CommerceInventoryAuditImpl();

		commerceInventoryAuditImpl.setMvccVersion(mvccVersion);
		commerceInventoryAuditImpl.setCommerceInventoryAuditId(
			commerceInventoryAuditId);
		commerceInventoryAuditImpl.setCompanyId(companyId);
		commerceInventoryAuditImpl.setUserId(userId);

		if (userName == null) {
			commerceInventoryAuditImpl.setUserName("");
		}
		else {
			commerceInventoryAuditImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceInventoryAuditImpl.setCreateDate(null);
		}
		else {
			commerceInventoryAuditImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceInventoryAuditImpl.setModifiedDate(null);
		}
		else {
			commerceInventoryAuditImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (logType == null) {
			commerceInventoryAuditImpl.setLogType("");
		}
		else {
			commerceInventoryAuditImpl.setLogType(logType);
		}

		if (logTypeSettings == null) {
			commerceInventoryAuditImpl.setLogTypeSettings("");
		}
		else {
			commerceInventoryAuditImpl.setLogTypeSettings(logTypeSettings);
		}

		commerceInventoryAuditImpl.setQuantity(quantity);

		if (sku == null) {
			commerceInventoryAuditImpl.setSku("");
		}
		else {
			commerceInventoryAuditImpl.setSku(sku);
		}

		if (unitOfMeasureKey == null) {
			commerceInventoryAuditImpl.setUnitOfMeasureKey("");
		}
		else {
			commerceInventoryAuditImpl.setUnitOfMeasureKey(unitOfMeasureKey);
		}

		commerceInventoryAuditImpl.resetOriginalValues();

		return commerceInventoryAuditImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		commerceInventoryAuditId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		logType = objectInput.readUTF();
		logTypeSettings = (String)objectInput.readObject();
		quantity = (BigDecimal)objectInput.readObject();
		sku = objectInput.readUTF();
		unitOfMeasureKey = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceInventoryAuditId);

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

		if (logType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(logType);
		}

		if (logTypeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(logTypeSettings);
		}

		objectOutput.writeObject(quantity);

		if (sku == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sku);
		}

		if (unitOfMeasureKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(unitOfMeasureKey);
		}
	}

	public long mvccVersion;
	public long commerceInventoryAuditId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String logType;
	public String logTypeSettings;
	public BigDecimal quantity;
	public String sku;
	public String unitOfMeasureKey;

}