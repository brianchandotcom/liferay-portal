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

package com.liferay.commerce.forecast.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.forecast.model.CommerceForecastEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CommerceForecastEntry in entity cache.
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastEntry
 * @generated
 */
@ProviderType
public class CommerceForecastEntryCacheModel implements CacheModel<CommerceForecastEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceForecastEntryCacheModel)) {
			return false;
		}

		CommerceForecastEntryCacheModel commerceForecastEntryCacheModel = (CommerceForecastEntryCacheModel)obj;

		if (commerceForecastEntryId == commerceForecastEntryCacheModel.commerceForecastEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceForecastEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{commerceForecastEntryId=");
		sb.append(commerceForecastEntryId);
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
		sb.append(", time=");
		sb.append(time);
		sb.append(", period=");
		sb.append(period);
		sb.append(", target=");
		sb.append(target);
		sb.append(", customerId=");
		sb.append(customerId);
		sb.append(", sku=");
		sb.append(sku);
		sb.append(", assertivity=");
		sb.append(assertivity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceForecastEntry toEntityModel() {
		CommerceForecastEntryImpl commerceForecastEntryImpl = new CommerceForecastEntryImpl();

		commerceForecastEntryImpl.setCommerceForecastEntryId(commerceForecastEntryId);
		commerceForecastEntryImpl.setCompanyId(companyId);
		commerceForecastEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceForecastEntryImpl.setUserName("");
		}
		else {
			commerceForecastEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceForecastEntryImpl.setCreateDate(null);
		}
		else {
			commerceForecastEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceForecastEntryImpl.setModifiedDate(null);
		}
		else {
			commerceForecastEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceForecastEntryImpl.setTime(time);
		commerceForecastEntryImpl.setPeriod(period);
		commerceForecastEntryImpl.setTarget(target);
		commerceForecastEntryImpl.setCustomerId(customerId);

		if (sku == null) {
			commerceForecastEntryImpl.setSku("");
		}
		else {
			commerceForecastEntryImpl.setSku(sku);
		}

		commerceForecastEntryImpl.setAssertivity(assertivity);

		commerceForecastEntryImpl.resetOriginalValues();

		return commerceForecastEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		commerceForecastEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		time = objectInput.readLong();

		period = objectInput.readInt();

		target = objectInput.readInt();

		customerId = objectInput.readLong();
		sku = objectInput.readUTF();
		assertivity = (BigDecimal)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceForecastEntryId);

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

		objectOutput.writeLong(time);

		objectOutput.writeInt(period);

		objectOutput.writeInt(target);

		objectOutput.writeLong(customerId);

		if (sku == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sku);
		}

		objectOutput.writeObject(assertivity);
	}

	public long commerceForecastEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long time;
	public int period;
	public int target;
	public long customerId;
	public String sku;
	public BigDecimal assertivity;
}