/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing TimestampTypeEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TimestampTypeEntryCacheModel
	implements CacheModel<TimestampTypeEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TimestampTypeEntryCacheModel)) {
			return false;
		}

		TimestampTypeEntryCacheModel timestampTypeEntryCacheModel =
			(TimestampTypeEntryCacheModel)object;

		if (timestampTypeEntryId ==
				timestampTypeEntryCacheModel.timestampTypeEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, timestampTypeEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{timestampTypeEntryId=");
		sb.append(timestampTypeEntryId);
		sb.append(", dateValue=");
		sb.append(dateValue);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public TimestampTypeEntry toEntityModel() {
		TimestampTypeEntryImpl timestampTypeEntryImpl =
			new TimestampTypeEntryImpl();

		timestampTypeEntryImpl.setTimestampTypeEntryId(timestampTypeEntryId);

		if (dateValue == Long.MIN_VALUE) {
			timestampTypeEntryImpl.setDateValue(null);
		}
		else {
			timestampTypeEntryImpl.setDateValue(new Date(dateValue));
		}

		timestampTypeEntryImpl.resetOriginalValues();

		return timestampTypeEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		timestampTypeEntryId = objectInput.readLong();
		dateValue = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(timestampTypeEntryId);
		objectOutput.writeLong(dateValue);
	}

	public long timestampTypeEntryId;
	public long dateValue;

}
// LIFERAY-SERVICE-BUILDER-HASH:451398599