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

import com.liferay.data.engine.model.DEDataDefinitionFieldLink;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing DEDataDefinitionFieldLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DEDataDefinitionFieldLinkCacheModel
	implements CacheModel<DEDataDefinitionFieldLink>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataDefinitionFieldLinkCacheModel)) {
			return false;
		}

		DEDataDefinitionFieldLinkCacheModel
			deDataDefinitionFieldLinkCacheModel =
				(DEDataDefinitionFieldLinkCacheModel)obj;

		if (dataDefinitionFieldLinkId ==
				deDataDefinitionFieldLinkCacheModel.dataDefinitionFieldLinkId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, dataDefinitionFieldLinkId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", dataDefinitionFieldLinkId=");
		sb.append(dataDefinitionFieldLinkId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", DDMStructureId=");
		sb.append(DDMStructureId);
		sb.append(", fieldName=");
		sb.append(fieldName);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DEDataDefinitionFieldLink toEntityModel() {
		DEDataDefinitionFieldLinkImpl deDataDefinitionFieldLinkImpl =
			new DEDataDefinitionFieldLinkImpl();

		if (uuid == null) {
			deDataDefinitionFieldLinkImpl.setUuid("");
		}
		else {
			deDataDefinitionFieldLinkImpl.setUuid(uuid);
		}

		deDataDefinitionFieldLinkImpl.setDataDefinitionFieldLinkId(
			dataDefinitionFieldLinkId);
		deDataDefinitionFieldLinkImpl.setGroupId(groupId);
		deDataDefinitionFieldLinkImpl.setClassNameId(classNameId);
		deDataDefinitionFieldLinkImpl.setClassPK(classPK);
		deDataDefinitionFieldLinkImpl.setDDMStructureId(DDMStructureId);
		deDataDefinitionFieldLinkImpl.setFieldName(fieldName);

		deDataDefinitionFieldLinkImpl.resetOriginalValues();

		return deDataDefinitionFieldLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		dataDefinitionFieldLinkId = objectInput.readLong();

		groupId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		DDMStructureId = objectInput.readLong();

		fieldName = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(dataDefinitionFieldLinkId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(DDMStructureId);

		objectOutput.writeLong(fieldName);
	}

	public String uuid;
	public long dataDefinitionFieldLinkId;
	public long groupId;
	public long classNameId;
	public long classPK;
	public long DDMStructureId;
	public long fieldName;

}