/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

/**
 * The cache model class for representing BigDecimalEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BigDecimalEntryCacheModel
	implements CacheModel<BigDecimalEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BigDecimalEntryCacheModel)) {
			return false;
		}

		BigDecimalEntryCacheModel bigDecimalEntryCacheModel =
			(BigDecimalEntryCacheModel)object;

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
		StringBundler sb = new StringBundler(7);

		sb.append("{bigDecimalEntryId=");
		sb.append(bigDecimalEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", bigDecimalValue=");
		sb.append(bigDecimalValue);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BigDecimalEntry toEntityModel() {
		BigDecimalEntryImpl bigDecimalEntryImpl = new BigDecimalEntryImpl();

		bigDecimalEntryImpl.setBigDecimalEntryId(bigDecimalEntryId);
		bigDecimalEntryImpl.setCompanyId(companyId);
		bigDecimalEntryImpl.setBigDecimalValue(bigDecimalValue);

		bigDecimalEntryImpl.resetOriginalValues();

		return bigDecimalEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		bigDecimalEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
		bigDecimalValue = (BigDecimal)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(bigDecimalEntryId);

		objectOutput.writeLong(companyId);
		objectOutput.writeObject(bigDecimalValue);
	}

	public long bigDecimalEntryId;
	public long companyId;
	public BigDecimal bigDecimalValue;

}