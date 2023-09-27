/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.model.impl;

import com.liferay.data.engine.model.DEDataDefinitionFieldLink;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DEDataDefinitionFieldLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DEDataDefinitionFieldLinkCacheModel
	implements CacheModel<DEDataDefinitionFieldLink>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DEDataDefinitionFieldLinkCacheModel)) {
			return false;
		}

		DEDataDefinitionFieldLinkCacheModel
			deDataDefinitionFieldLinkCacheModel =
				(DEDataDefinitionFieldLinkCacheModel)object;

		if ((deDataDefinitionFieldLinkId ==
				deDataDefinitionFieldLinkCacheModel.
					deDataDefinitionFieldLinkId) &&
			(mvccVersion == deDataDefinitionFieldLinkCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, deDataDefinitionFieldLinkId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", deDataDefinitionFieldLinkId=");
		sb.append(deDataDefinitionFieldLinkId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", ddmStructureId=");
		sb.append(ddmStructureId);
		sb.append(", fieldName=");
		sb.append(fieldName);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DEDataDefinitionFieldLink toEntityModel() {
		DEDataDefinitionFieldLinkImpl deDataDefinitionFieldLinkImpl =
			new DEDataDefinitionFieldLinkImpl();

		deDataDefinitionFieldLinkImpl.setMvccVersion(mvccVersion);
		deDataDefinitionFieldLinkImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			deDataDefinitionFieldLinkImpl.setUuid("");
		}
		else {
			deDataDefinitionFieldLinkImpl.setUuid(uuid);
		}

		deDataDefinitionFieldLinkImpl.setDeDataDefinitionFieldLinkId(
			deDataDefinitionFieldLinkId);
		deDataDefinitionFieldLinkImpl.setGroupId(groupId);
		deDataDefinitionFieldLinkImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			deDataDefinitionFieldLinkImpl.setCreateDate(null);
		}
		else {
			deDataDefinitionFieldLinkImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			deDataDefinitionFieldLinkImpl.setModifiedDate(null);
		}
		else {
			deDataDefinitionFieldLinkImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		deDataDefinitionFieldLinkImpl.setClassNameId(classNameId);
		deDataDefinitionFieldLinkImpl.setClassPK(classPK);
		deDataDefinitionFieldLinkImpl.setDdmStructureId(ddmStructureId);

		if (fieldName == null) {
			deDataDefinitionFieldLinkImpl.setFieldName("");
		}
		else {
			deDataDefinitionFieldLinkImpl.setFieldName(fieldName);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			deDataDefinitionFieldLinkImpl.setLastPublishDate(null);
		}
		else {
			deDataDefinitionFieldLinkImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		deDataDefinitionFieldLinkImpl.resetOriginalValues();

		return deDataDefinitionFieldLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		deDataDefinitionFieldLinkId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		ddmStructureId = objectInput.readLong();
		fieldName = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(deDataDefinitionFieldLinkId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(ddmStructureId);

		if (fieldName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fieldName);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long deDataDefinitionFieldLinkId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long ddmStructureId;
	public String fieldName;
	public long lastPublishDate;

}