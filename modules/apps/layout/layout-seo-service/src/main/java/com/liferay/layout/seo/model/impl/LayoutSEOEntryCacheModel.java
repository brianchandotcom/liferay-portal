/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.model.impl;

import com.liferay.layout.seo.model.LayoutSEOEntry;
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
 * The cache model class for representing LayoutSEOEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutSEOEntryCacheModel
	implements CacheModel<LayoutSEOEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutSEOEntryCacheModel)) {
			return false;
		}

		LayoutSEOEntryCacheModel layoutSEOEntryCacheModel =
			(LayoutSEOEntryCacheModel)object;

		if ((layoutSEOEntryId == layoutSEOEntryCacheModel.layoutSEOEntryId) &&
			(mvccVersion == layoutSEOEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutSEOEntryId);

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
		StringBundler sb = new StringBundler(45);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", layoutSEOEntryId=");
		sb.append(layoutSEOEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", privateLayout=");
		sb.append(privateLayout);
		sb.append(", layoutId=");
		sb.append(layoutId);
		sb.append(", canonicalURL=");
		sb.append(canonicalURL);
		sb.append(", canonicalURLEnabled=");
		sb.append(canonicalURLEnabled);
		sb.append(", DDMStorageId=");
		sb.append(DDMStorageId);
		sb.append(", openGraphDescription=");
		sb.append(openGraphDescription);
		sb.append(", openGraphDescriptionEnabled=");
		sb.append(openGraphDescriptionEnabled);
		sb.append(", openGraphImageAlt=");
		sb.append(openGraphImageAlt);
		sb.append(", openGraphImageFileEntryId=");
		sb.append(openGraphImageFileEntryId);
		sb.append(", openGraphTitle=");
		sb.append(openGraphTitle);
		sb.append(", openGraphTitleEnabled=");
		sb.append(openGraphTitleEnabled);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSEOEntry toEntityModel() {
		LayoutSEOEntryImpl layoutSEOEntryImpl = new LayoutSEOEntryImpl();

		layoutSEOEntryImpl.setMvccVersion(mvccVersion);
		layoutSEOEntryImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			layoutSEOEntryImpl.setUuid("");
		}
		else {
			layoutSEOEntryImpl.setUuid(uuid);
		}

		layoutSEOEntryImpl.setLayoutSEOEntryId(layoutSEOEntryId);
		layoutSEOEntryImpl.setGroupId(groupId);
		layoutSEOEntryImpl.setCompanyId(companyId);
		layoutSEOEntryImpl.setUserId(userId);

		if (userName == null) {
			layoutSEOEntryImpl.setUserName("");
		}
		else {
			layoutSEOEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutSEOEntryImpl.setCreateDate(null);
		}
		else {
			layoutSEOEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutSEOEntryImpl.setModifiedDate(null);
		}
		else {
			layoutSEOEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutSEOEntryImpl.setPrivateLayout(privateLayout);
		layoutSEOEntryImpl.setLayoutId(layoutId);

		if (canonicalURL == null) {
			layoutSEOEntryImpl.setCanonicalURL("");
		}
		else {
			layoutSEOEntryImpl.setCanonicalURL(canonicalURL);
		}

		layoutSEOEntryImpl.setCanonicalURLEnabled(canonicalURLEnabled);
		layoutSEOEntryImpl.setDDMStorageId(DDMStorageId);

		if (openGraphDescription == null) {
			layoutSEOEntryImpl.setOpenGraphDescription("");
		}
		else {
			layoutSEOEntryImpl.setOpenGraphDescription(openGraphDescription);
		}

		layoutSEOEntryImpl.setOpenGraphDescriptionEnabled(
			openGraphDescriptionEnabled);

		if (openGraphImageAlt == null) {
			layoutSEOEntryImpl.setOpenGraphImageAlt("");
		}
		else {
			layoutSEOEntryImpl.setOpenGraphImageAlt(openGraphImageAlt);
		}

		layoutSEOEntryImpl.setOpenGraphImageFileEntryId(
			openGraphImageFileEntryId);

		if (openGraphTitle == null) {
			layoutSEOEntryImpl.setOpenGraphTitle("");
		}
		else {
			layoutSEOEntryImpl.setOpenGraphTitle(openGraphTitle);
		}

		layoutSEOEntryImpl.setOpenGraphTitleEnabled(openGraphTitleEnabled);

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutSEOEntryImpl.setLastPublishDate(null);
		}
		else {
			layoutSEOEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		layoutSEOEntryImpl.resetOriginalValues();

		return layoutSEOEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		layoutSEOEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		privateLayout = objectInput.readBoolean();

		layoutId = objectInput.readLong();
		canonicalURL = objectInput.readUTF();

		canonicalURLEnabled = objectInput.readBoolean();

		DDMStorageId = objectInput.readLong();
		openGraphDescription = objectInput.readUTF();

		openGraphDescriptionEnabled = objectInput.readBoolean();
		openGraphImageAlt = objectInput.readUTF();

		openGraphImageFileEntryId = objectInput.readLong();
		openGraphTitle = objectInput.readUTF();

		openGraphTitleEnabled = objectInput.readBoolean();
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

		objectOutput.writeLong(layoutSEOEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeBoolean(privateLayout);

		objectOutput.writeLong(layoutId);

		if (canonicalURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(canonicalURL);
		}

		objectOutput.writeBoolean(canonicalURLEnabled);

		objectOutput.writeLong(DDMStorageId);

		if (openGraphDescription == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(openGraphDescription);
		}

		objectOutput.writeBoolean(openGraphDescriptionEnabled);

		if (openGraphImageAlt == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(openGraphImageAlt);
		}

		objectOutput.writeLong(openGraphImageFileEntryId);

		if (openGraphTitle == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(openGraphTitle);
		}

		objectOutput.writeBoolean(openGraphTitleEnabled);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long layoutSEOEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean privateLayout;
	public long layoutId;
	public String canonicalURL;
	public boolean canonicalURLEnabled;
	public long DDMStorageId;
	public String openGraphDescription;
	public boolean openGraphDescriptionEnabled;
	public String openGraphImageAlt;
	public long openGraphImageFileEntryId;
	public String openGraphTitle;
	public boolean openGraphTitleEnabled;
	public long lastPublishDate;

}