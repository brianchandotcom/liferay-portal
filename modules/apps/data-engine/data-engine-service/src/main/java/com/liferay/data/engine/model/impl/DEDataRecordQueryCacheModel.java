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

package com.liferay.data.engine.model.impl;

import com.liferay.data.engine.model.DEDataRecordQuery;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing DEDataRecordQuery in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DEDataRecordQueryCacheModel
	implements CacheModel<DEDataRecordQuery>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataRecordQueryCacheModel)) {
			return false;
		}

		DEDataRecordQueryCacheModel deDataRecordQueryCacheModel =
			(DEDataRecordQueryCacheModel)obj;

		if (deDataRecordQueryId ==
				deDataRecordQueryCacheModel.deDataRecordQueryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, deDataRecordQueryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", deDataRecordQueryId=");
		sb.append(deDataRecordQueryId);
		sb.append(", appliedFilters=");
		sb.append(appliedFilters);
		sb.append(", fieldNames=");
		sb.append(fieldNames);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DEDataRecordQuery toEntityModel() {
		DEDataRecordQueryImpl deDataRecordQueryImpl =
			new DEDataRecordQueryImpl();

		if (uuid == null) {
			deDataRecordQueryImpl.setUuid("");
		}
		else {
			deDataRecordQueryImpl.setUuid(uuid);
		}

		deDataRecordQueryImpl.setDeDataRecordQueryId(deDataRecordQueryId);

		if (appliedFilters == null) {
			deDataRecordQueryImpl.setAppliedFilters("");
		}
		else {
			deDataRecordQueryImpl.setAppliedFilters(appliedFilters);
		}

		if (fieldNames == null) {
			deDataRecordQueryImpl.setFieldNames("");
		}
		else {
			deDataRecordQueryImpl.setFieldNames(fieldNames);
		}

		deDataRecordQueryImpl.resetOriginalValues();

		return deDataRecordQueryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		deDataRecordQueryId = objectInput.readLong();
		appliedFilters = objectInput.readUTF();
		fieldNames = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(deDataRecordQueryId);

		if (appliedFilters == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(appliedFilters);
		}

		if (fieldNames == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fieldNames);
		}
	}

	public String uuid;
	public long deDataRecordQueryId;
	public String appliedFilters;
	public String fieldNames;

}