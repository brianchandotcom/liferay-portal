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

import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DDMStructureLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMStructureLinkCacheModel
	implements CacheModel<DDMStructureLink>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStructureLinkCacheModel)) {
			return false;
		}

		DDMStructureLinkCacheModel ddmStructureLinkCacheModel =
			(DDMStructureLinkCacheModel)obj;

		if ((structureLinkId == ddmStructureLinkCacheModel.structureLinkId) &&
			(mvccVersion == ddmStructureLinkCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, structureLinkId);

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
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", structureLinkId=");
		sb.append(structureLinkId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", structureId=");
		sb.append(structureId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMStructureLink toEntityModel() {
		DDMStructureLinkImpl ddmStructureLinkImpl = new DDMStructureLinkImpl();

		ddmStructureLinkImpl.setMvccVersion(mvccVersion);
		ddmStructureLinkImpl.setStructureLinkId(structureLinkId);
		ddmStructureLinkImpl.setCompanyId(companyId);
		ddmStructureLinkImpl.setClassNameId(classNameId);
		ddmStructureLinkImpl.setClassPK(classPK);
		ddmStructureLinkImpl.setStructureId(structureId);

		ddmStructureLinkImpl.resetOriginalValues();

		return ddmStructureLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		structureLinkId = objectInput.readLong();

		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		structureId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(structureLinkId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(structureId);
	}

	public long mvccVersion;
	public long structureLinkId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public long structureId;

}