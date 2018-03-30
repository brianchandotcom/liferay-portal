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

package com.liferay.service.builder.test.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import com.liferay.service.builder.test.model.BigDecimalEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

/**
 * The cache model class for representing BigDecimalEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see BigDecimalEntry
 * @generated
 */
@ProviderType
public class BigDecimalEntryCacheModel implements CacheModel<BigDecimalEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BigDecimalEntryCacheModel)) {
			return false;
		}

		BigDecimalEntryCacheModel bigDecimalEntryCacheModel = (BigDecimalEntryCacheModel)obj;

		if (bigDecimalEntryId == bigDecimalEntryCacheModel.bigDecimalEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, bigDecimalEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{bigDecimalEntryId=");
		sb.append(bigDecimalEntryId);
		sb.append(", bigDecimalValue=");
		sb.append(bigDecimalValue);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BigDecimalEntry toEntityModel() {
		BigDecimalEntryImpl bigDecimalEntryImpl = new BigDecimalEntryImpl();

		bigDecimalEntryImpl.setBigDecimalEntryId(bigDecimalEntryId);
		bigDecimalEntryImpl.setBigDecimalValue(bigDecimalValue);

		bigDecimalEntryImpl.resetOriginalValues();

		return bigDecimalEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		bigDecimalEntryId = objectInput.readLong();
		bigDecimalValue = (BigDecimal)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(bigDecimalEntryId);
		objectOutput.writeObject(bigDecimalValue);
	}

	public long bigDecimalEntryId;
	public BigDecimal bigDecimalValue;
}