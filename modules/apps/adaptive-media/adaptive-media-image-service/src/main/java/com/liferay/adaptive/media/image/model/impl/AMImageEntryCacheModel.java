/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.model.impl;

import com.liferay.adaptive.media.image.model.AMImageEntry;
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
 * The cache model class for representing AMImageEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AMImageEntryCacheModel
	implements CacheModel<AMImageEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AMImageEntryCacheModel)) {
			return false;
		}

		AMImageEntryCacheModel amImageEntryCacheModel =
			(AMImageEntryCacheModel)object;

		if ((amImageEntryId == amImageEntryCacheModel.amImageEntryId) &&
			(mvccVersion == amImageEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, amImageEntryId);

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

		amImageEntryImpl.setMvccVersion(mvccVersion);
		amImageEntryImpl.setCtCollectionId(ctCollectionId);

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
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
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
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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

	public long mvccVersion;
	public long ctCollectionId;
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