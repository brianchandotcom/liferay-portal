/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
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
 * The cache model class for representing CPInstanceUnitOfMeasure in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPInstanceUnitOfMeasureCacheModel
	implements CacheModel<CPInstanceUnitOfMeasure>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPInstanceUnitOfMeasureCacheModel)) {
			return false;
		}

		CPInstanceUnitOfMeasureCacheModel cpInstanceUnitOfMeasureCacheModel =
			(CPInstanceUnitOfMeasureCacheModel)object;

		if ((CPInstanceUnitOfMeasureId ==
				cpInstanceUnitOfMeasureCacheModel.CPInstanceUnitOfMeasureId) &&
			(mvccVersion == cpInstanceUnitOfMeasureCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPInstanceUnitOfMeasureId);

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
		StringBundler sb = new StringBundler(39);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", CPInstanceUnitOfMeasureId=");
		sb.append(CPInstanceUnitOfMeasureId);
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
		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);
		sb.append(", active=");
		sb.append(active);
		sb.append(", incrementalOrderQuantity=");
		sb.append(incrementalOrderQuantity);
		sb.append(", key=");
		sb.append(key);
		sb.append(", name=");
		sb.append(name);
		sb.append(", precision=");
		sb.append(precision);
		sb.append(", primary=");
		sb.append(primary);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", rate=");
		sb.append(rate);
		sb.append(", sku=");
		sb.append(sku);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPInstanceUnitOfMeasure toEntityModel() {
		CPInstanceUnitOfMeasureImpl cpInstanceUnitOfMeasureImpl =
			new CPInstanceUnitOfMeasureImpl();

		cpInstanceUnitOfMeasureImpl.setMvccVersion(mvccVersion);
		cpInstanceUnitOfMeasureImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			cpInstanceUnitOfMeasureImpl.setUuid("");
		}
		else {
			cpInstanceUnitOfMeasureImpl.setUuid(uuid);
		}

		cpInstanceUnitOfMeasureImpl.setCPInstanceUnitOfMeasureId(
			CPInstanceUnitOfMeasureId);
		cpInstanceUnitOfMeasureImpl.setCompanyId(companyId);
		cpInstanceUnitOfMeasureImpl.setUserId(userId);

		if (userName == null) {
			cpInstanceUnitOfMeasureImpl.setUserName("");
		}
		else {
			cpInstanceUnitOfMeasureImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpInstanceUnitOfMeasureImpl.setCreateDate(null);
		}
		else {
			cpInstanceUnitOfMeasureImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpInstanceUnitOfMeasureImpl.setModifiedDate(null);
		}
		else {
			cpInstanceUnitOfMeasureImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpInstanceUnitOfMeasureImpl.setCPInstanceId(CPInstanceId);
		cpInstanceUnitOfMeasureImpl.setActive(active);
		cpInstanceUnitOfMeasureImpl.setIncrementalOrderQuantity(
			incrementalOrderQuantity);

		if (key == null) {
			cpInstanceUnitOfMeasureImpl.setKey("");
		}
		else {
			cpInstanceUnitOfMeasureImpl.setKey(key);
		}

		if (name == null) {
			cpInstanceUnitOfMeasureImpl.setName("");
		}
		else {
			cpInstanceUnitOfMeasureImpl.setName(name);
		}

		cpInstanceUnitOfMeasureImpl.setPrecision(precision);
		cpInstanceUnitOfMeasureImpl.setPrimary(primary);
		cpInstanceUnitOfMeasureImpl.setPriority(priority);
		cpInstanceUnitOfMeasureImpl.setRate(rate);

		if (sku == null) {
			cpInstanceUnitOfMeasureImpl.setSku("");
		}
		else {
			cpInstanceUnitOfMeasureImpl.setSku(sku);
		}

		cpInstanceUnitOfMeasureImpl.resetOriginalValues();

		return cpInstanceUnitOfMeasureImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPInstanceUnitOfMeasureId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPInstanceId = objectInput.readLong();

		active = objectInput.readBoolean();
		incrementalOrderQuantity = (BigDecimal)objectInput.readObject();
		key = objectInput.readUTF();
		name = objectInput.readUTF();

		precision = objectInput.readInt();

		primary = objectInput.readBoolean();

		priority = objectInput.readDouble();
		rate = (BigDecimal)objectInput.readObject();
		sku = objectInput.readUTF();
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

		objectOutput.writeLong(CPInstanceUnitOfMeasureId);

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

		objectOutput.writeLong(CPInstanceId);

		objectOutput.writeBoolean(active);
		objectOutput.writeObject(incrementalOrderQuantity);

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(precision);

		objectOutput.writeBoolean(primary);

		objectOutput.writeDouble(priority);
		objectOutput.writeObject(rate);

		if (sku == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sku);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long CPInstanceUnitOfMeasureId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPInstanceId;
	public boolean active;
	public BigDecimal incrementalOrderQuantity;
	public String key;
	public String name;
	public int precision;
	public boolean primary;
	public double priority;
	public BigDecimal rate;
	public String sku;

}