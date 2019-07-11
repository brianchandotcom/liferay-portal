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

import com.liferay.data.engine.model.DERecordQuery;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing DERecordQuery in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DERecordQueryCacheModel
	implements CacheModel<DERecordQuery>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DERecordQueryCacheModel)) {
			return false;
		}

		DERecordQueryCacheModel deRecordQueryCacheModel =
			(DERecordQueryCacheModel)obj;

		if (deRecordQueryId == deRecordQueryCacheModel.deRecordQueryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, deRecordQueryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", deRecordQueryId=");
		sb.append(deRecordQueryId);
		sb.append(", appliedFilters=");
		sb.append(appliedFilters);
		sb.append(", fieldNames=");
		sb.append(fieldNames);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DERecordQuery toEntityModel() {
		DERecordQueryImpl deRecordQueryImpl = new DERecordQueryImpl();

		if (uuid == null) {
			deRecordQueryImpl.setUuid("");
		}
		else {
			deRecordQueryImpl.setUuid(uuid);
		}

		deRecordQueryImpl.setDeRecordQueryId(deRecordQueryId);

		if (appliedFilters == null) {
			deRecordQueryImpl.setAppliedFilters("");
		}
		else {
			deRecordQueryImpl.setAppliedFilters(appliedFilters);
		}

		if (fieldNames == null) {
			deRecordQueryImpl.setFieldNames("");
		}
		else {
			deRecordQueryImpl.setFieldNames(fieldNames);
		}

		deRecordQueryImpl.resetOriginalValues();

		return deRecordQueryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		deRecordQueryId = objectInput.readLong();
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

		objectOutput.writeLong(deRecordQueryId);

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
	public long deRecordQueryId;
	public String appliedFilters;
	public String fieldNames;

}