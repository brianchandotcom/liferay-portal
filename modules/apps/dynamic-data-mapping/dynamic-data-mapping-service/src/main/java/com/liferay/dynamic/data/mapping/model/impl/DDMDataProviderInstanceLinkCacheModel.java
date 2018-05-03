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

package com.liferay.dynamic.data.mapping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DDMDataProviderInstanceLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLink
 * @generated
 */
@ProviderType
public class DDMDataProviderInstanceLinkCacheModel implements CacheModel<DDMDataProviderInstanceLink>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMDataProviderInstanceLinkCacheModel)) {
			return false;
		}

		DDMDataProviderInstanceLinkCacheModel ddmDataProviderInstanceLinkCacheModel =
			(DDMDataProviderInstanceLinkCacheModel)obj;

		if (dataProviderInstanceLinkId == ddmDataProviderInstanceLinkCacheModel.dataProviderInstanceLinkId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, dataProviderInstanceLinkId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{dataProviderInstanceLinkId=");
		sb.append(dataProviderInstanceLinkId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", dataProviderInstanceId=");
		sb.append(dataProviderInstanceId);
		sb.append(", structureId=");
		sb.append(structureId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMDataProviderInstanceLink toEntityModel() {
		DDMDataProviderInstanceLinkImpl ddmDataProviderInstanceLinkImpl = new DDMDataProviderInstanceLinkImpl();

		ddmDataProviderInstanceLinkImpl.setDataProviderInstanceLinkId(dataProviderInstanceLinkId);
		ddmDataProviderInstanceLinkImpl.setCompanyId(companyId);
		ddmDataProviderInstanceLinkImpl.setDataProviderInstanceId(dataProviderInstanceId);
		ddmDataProviderInstanceLinkImpl.setStructureId(structureId);

		ddmDataProviderInstanceLinkImpl.resetOriginalValues();

		return ddmDataProviderInstanceLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		dataProviderInstanceLinkId = objectInput.readLong();

		companyId = objectInput.readLong();

		dataProviderInstanceId = objectInput.readLong();

		structureId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(dataProviderInstanceLinkId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(dataProviderInstanceId);

		objectOutput.writeLong(structureId);
	}

	public long dataProviderInstanceLinkId;
	public long companyId;
	public long dataProviderInstanceId;
	public long structureId;
}