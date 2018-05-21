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

import com.liferay.commerce.forecast.model.CommerceForecastValue;

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
 * The cache model class for representing CommerceForecastValue in entity cache.
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastValue
 * @generated
 */
@ProviderType
public class CommerceForecastValueCacheModel implements CacheModel<CommerceForecastValue>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceForecastValueCacheModel)) {
			return false;
		}

		CommerceForecastValueCacheModel commerceForecastValueCacheModel = (CommerceForecastValueCacheModel)obj;

		if (commerceForecastValueId == commerceForecastValueCacheModel.commerceForecastValueId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceForecastValueId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{commerceForecastValueId=");
		sb.append(commerceForecastValueId);
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
		sb.append(", commerceForecastEntryId=");
		sb.append(commerceForecastEntryId);
		sb.append(", time=");
		sb.append(time);
		sb.append(", lowerValue=");
		sb.append(lowerValue);
		sb.append(", value=");
		sb.append(value);
		sb.append(", upperValue=");
		sb.append(upperValue);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceForecastValue toEntityModel() {
		CommerceForecastValueImpl commerceForecastValueImpl = new CommerceForecastValueImpl();

		commerceForecastValueImpl.setCommerceForecastValueId(commerceForecastValueId);
		commerceForecastValueImpl.setCompanyId(companyId);
		commerceForecastValueImpl.setUserId(userId);

		if (userName == null) {
			commerceForecastValueImpl.setUserName("");
		}
		else {
			commerceForecastValueImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceForecastValueImpl.setCreateDate(null);
		}
		else {
			commerceForecastValueImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceForecastValueImpl.setModifiedDate(null);
		}
		else {
			commerceForecastValueImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceForecastValueImpl.setCommerceForecastEntryId(commerceForecastEntryId);
		commerceForecastValueImpl.setTime(time);
		commerceForecastValueImpl.setLowerValue(lowerValue);
		commerceForecastValueImpl.setValue(value);
		commerceForecastValueImpl.setUpperValue(upperValue);

		commerceForecastValueImpl.resetOriginalValues();

		return commerceForecastValueImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		commerceForecastValueId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceForecastEntryId = objectInput.readLong();

		time = objectInput.readLong();
		lowerValue = (BigDecimal)objectInput.readObject();
		value = (BigDecimal)objectInput.readObject();
		upperValue = (BigDecimal)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceForecastValueId);

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

		objectOutput.writeLong(commerceForecastEntryId);

		objectOutput.writeLong(time);
		objectOutput.writeObject(lowerValue);
		objectOutput.writeObject(value);
		objectOutput.writeObject(upperValue);
	}

	public long commerceForecastValueId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceForecastEntryId;
	public long time;
	public BigDecimal lowerValue;
	public BigDecimal value;
	public BigDecimal upperValue;
}