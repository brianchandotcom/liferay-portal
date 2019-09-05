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

package com.liferay.adaptive.media.image.model.impl;

import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AMImageEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AMImageEntryCacheModel
	implements CacheModel<AMImageEntry>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AMImageEntryCacheModel)) {
			return false;
		}

		AMImageEntryCacheModel amImageEntryCacheModel =
			(AMImageEntryCacheModel)obj;

		if (amImageEntryId == amImageEntryCacheModel.amImageEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, amImageEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", amImageEntryId=");
		sb.append(amImageEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", configurationUuid=");
		sb.append(configurationUuid);
		sb.append(", fileVersionId=");
		sb.append(fileVersionId);
		sb.append(", mimeType=");
		sb.append(mimeType);
		sb.append(", height=");
		sb.append(height);
		sb.append(", width=");
		sb.append(width);
		sb.append(", size=");
		sb.append(size);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AMImageEntry toEntityModel() {
		AMImageEntryImpl amImageEntryImpl = new AMImageEntryImpl();

		if (uuid == null) {
			amImageEntryImpl.setUuid("");
		}
		else {
			amImageEntryImpl.setUuid(uuid);
		}

		amImageEntryImpl.setAmImageEntryId(amImageEntryId);
		amImageEntryImpl.setGroupId(groupId);
		amImageEntryImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			amImageEntryImpl.setCreateDate(null);
		}
		else {
			amImageEntryImpl.setCreateDate(new Date(createDate));
		}

		if (configurationUuid == null) {
			amImageEntryImpl.setConfigurationUuid("");
		}
		else {
			amImageEntryImpl.setConfigurationUuid(configurationUuid);
		}

		amImageEntryImpl.setFileVersionId(fileVersionId);

		if (mimeType == null) {
			amImageEntryImpl.setMimeType("");
		}
		else {
			amImageEntryImpl.setMimeType(mimeType);
		}

		amImageEntryImpl.setHeight(height);
		amImageEntryImpl.setWidth(width);
		amImageEntryImpl.setSize(size);

		amImageEntryImpl.resetOriginalValues();

		return amImageEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		amImageEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		configurationUuid = objectInput.readUTF();

		fileVersionId = objectInput.readLong();
		mimeType = objectInput.readUTF();

		height = objectInput.readInt();

		width = objectInput.readInt();

		size = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(amImageEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);

		if (configurationUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(configurationUuid);
		}

		objectOutput.writeLong(fileVersionId);

		if (mimeType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(mimeType);
		}

		objectOutput.writeInt(height);

		objectOutput.writeInt(width);

		objectOutput.writeLong(size);
	}

	public String uuid;
	public long amImageEntryId;
	public long groupId;
	public long companyId;
	public long createDate;
	public String configurationUuid;
	public long fileVersionId;
	public String mimeType;
	public int height;
	public int width;
	public long size;

}