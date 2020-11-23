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

import com.liferay.dynamic.data.mapping.model.DDMField;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DDMField in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFieldCacheModel
	implements CacheModel<DDMField>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFieldCacheModel)) {
			return false;
		}

		DDMFieldCacheModel ddmFieldCacheModel = (DDMFieldCacheModel)object;

		if ((fieldId == ddmFieldCacheModel.fieldId) &&
			(mvccVersion == ddmFieldCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fieldId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", fieldId=");
		sb.append(fieldId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", structureVersionId=");
		sb.append(structureVersionId);
		sb.append(", parentFieldId=");
		sb.append(parentFieldId);
		sb.append(", storageId=");
		sb.append(storageId);
		sb.append(", fieldName=");
		sb.append(fieldName);
		sb.append(", fieldType=");
		sb.append(fieldType);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", instanceId=");
		sb.append(instanceId);
		sb.append(", localizable=");
		sb.append(localizable);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMField toEntityModel() {
		DDMFieldImpl ddmFieldImpl = new DDMFieldImpl();

		ddmFieldImpl.setMvccVersion(mvccVersion);
		ddmFieldImpl.setCtCollectionId(ctCollectionId);
		ddmFieldImpl.setFieldId(fieldId);
		ddmFieldImpl.setCompanyId(companyId);
		ddmFieldImpl.setStructureVersionId(structureVersionId);
		ddmFieldImpl.setParentFieldId(parentFieldId);
		ddmFieldImpl.setStorageId(storageId);

		if (fieldName == null) {
			ddmFieldImpl.setFieldName("");
		}
		else {
			ddmFieldImpl.setFieldName(fieldName);
		}

		if (fieldType == null) {
			ddmFieldImpl.setFieldType("");
		}
		else {
			ddmFieldImpl.setFieldType(fieldType);
		}

		ddmFieldImpl.setPriority(priority);

		if (instanceId == null) {
			ddmFieldImpl.setInstanceId("");
		}
		else {
			ddmFieldImpl.setInstanceId(instanceId);
		}

		ddmFieldImpl.setLocalizable(localizable);

		ddmFieldImpl.resetOriginalValues();

		return ddmFieldImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		fieldId = objectInput.readLong();

		companyId = objectInput.readLong();

		structureVersionId = objectInput.readLong();

		parentFieldId = objectInput.readLong();

		storageId = objectInput.readLong();
		fieldName = objectInput.readUTF();
		fieldType = objectInput.readUTF();

		priority = objectInput.readInt();
		instanceId = objectInput.readUTF();

		localizable = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(fieldId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(structureVersionId);

		objectOutput.writeLong(parentFieldId);

		objectOutput.writeLong(storageId);

		if (fieldName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fieldName);
		}

		if (fieldType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fieldType);
		}

		objectOutput.writeInt(priority);

		if (instanceId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(instanceId);
		}

		objectOutput.writeBoolean(localizable);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long fieldId;
	public long companyId;
	public long structureVersionId;
	public long parentFieldId;
	public long storageId;
	public String fieldName;
	public String fieldType;
	public int priority;
	public String instanceId;
	public boolean localizable;

}