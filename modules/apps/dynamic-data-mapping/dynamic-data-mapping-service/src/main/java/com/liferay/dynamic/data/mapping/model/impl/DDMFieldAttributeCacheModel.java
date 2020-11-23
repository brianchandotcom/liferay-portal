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

import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DDMFieldAttribute in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFieldAttributeCacheModel
	implements CacheModel<DDMFieldAttribute>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFieldAttributeCacheModel)) {
			return false;
		}

		DDMFieldAttributeCacheModel ddmFieldAttributeCacheModel =
			(DDMFieldAttributeCacheModel)object;

		if ((fieldAttributeId ==
				ddmFieldAttributeCacheModel.fieldAttributeId) &&
			(mvccVersion == ddmFieldAttributeCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fieldAttributeId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", fieldAttributeId=");
		sb.append(fieldAttributeId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", fieldId=");
		sb.append(fieldId);
		sb.append(", storageId=");
		sb.append(storageId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", attributeName=");
		sb.append(attributeName);
		sb.append(", smallAttributeValue=");
		sb.append(smallAttributeValue);
		sb.append(", largeAttributeValue=");
		sb.append(largeAttributeValue);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMFieldAttribute toEntityModel() {
		DDMFieldAttributeImpl ddmFieldAttributeImpl =
			new DDMFieldAttributeImpl();

		ddmFieldAttributeImpl.setMvccVersion(mvccVersion);
		ddmFieldAttributeImpl.setCtCollectionId(ctCollectionId);
		ddmFieldAttributeImpl.setFieldAttributeId(fieldAttributeId);
		ddmFieldAttributeImpl.setCompanyId(companyId);
		ddmFieldAttributeImpl.setFieldId(fieldId);
		ddmFieldAttributeImpl.setStorageId(storageId);

		if (languageId == null) {
			ddmFieldAttributeImpl.setLanguageId("");
		}
		else {
			ddmFieldAttributeImpl.setLanguageId(languageId);
		}

		if (attributeName == null) {
			ddmFieldAttributeImpl.setAttributeName("");
		}
		else {
			ddmFieldAttributeImpl.setAttributeName(attributeName);
		}

		if (smallAttributeValue == null) {
			ddmFieldAttributeImpl.setSmallAttributeValue("");
		}
		else {
			ddmFieldAttributeImpl.setSmallAttributeValue(smallAttributeValue);
		}

		if (largeAttributeValue == null) {
			ddmFieldAttributeImpl.setLargeAttributeValue("");
		}
		else {
			ddmFieldAttributeImpl.setLargeAttributeValue(largeAttributeValue);
		}

		ddmFieldAttributeImpl.resetOriginalValues();

		return ddmFieldAttributeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		fieldAttributeId = objectInput.readLong();

		companyId = objectInput.readLong();

		fieldId = objectInput.readLong();

		storageId = objectInput.readLong();
		languageId = objectInput.readUTF();
		attributeName = objectInput.readUTF();
		smallAttributeValue = objectInput.readUTF();
		largeAttributeValue = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(fieldAttributeId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(fieldId);

		objectOutput.writeLong(storageId);

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (attributeName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(attributeName);
		}

		if (smallAttributeValue == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(smallAttributeValue);
		}

		if (largeAttributeValue == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(largeAttributeValue);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long fieldAttributeId;
	public long companyId;
	public long fieldId;
	public long storageId;
	public String languageId;
	public String attributeName;
	public String smallAttributeValue;
	public String largeAttributeValue;

}