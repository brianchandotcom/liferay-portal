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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSEOEntryCacheModel)) {
			return false;
		}

		LayoutSEOEntryCacheModel layoutSEOEntryCacheModel =
			(LayoutSEOEntryCacheModel)obj;

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
		StringBundler sb = new StringBundler(39);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
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
		sb.append(", canonicalURLEnabled=");
		sb.append(canonicalURLEnabled);
		sb.append(", canonicalURL=");
		sb.append(canonicalURL);
		sb.append(", openGraphTitleEnabled=");
		sb.append(openGraphTitleEnabled);
		sb.append(", openGraphTitle=");
		sb.append(openGraphTitle);
		sb.append(", openGraphDescriptionEnabled=");
		sb.append(openGraphDescriptionEnabled);
		sb.append(", openGraphDescription=");
		sb.append(openGraphDescription);
		sb.append(", openGraphImageFileEntryId=");
		sb.append(openGraphImageFileEntryId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSEOEntry toEntityModel() {
		LayoutSEOEntryImpl layoutSEOEntryImpl = new LayoutSEOEntryImpl();

		layoutSEOEntryImpl.setMvccVersion(mvccVersion);

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
		layoutSEOEntryImpl.setCanonicalURLEnabled(canonicalURLEnabled);

		if (canonicalURL == null) {
			layoutSEOEntryImpl.setCanonicalURL("");
		}
		else {
			layoutSEOEntryImpl.setCanonicalURL(canonicalURL);
		}

		layoutSEOEntryImpl.setOpenGraphTitleEnabled(openGraphTitleEnabled);

		if (openGraphTitle == null) {
			layoutSEOEntryImpl.setOpenGraphTitle("");
		}
		else {
			layoutSEOEntryImpl.setOpenGraphTitle(openGraphTitle);
		}

		layoutSEOEntryImpl.setOpenGraphDescriptionEnabled(
			openGraphDescriptionEnabled);

		if (openGraphDescription == null) {
			layoutSEOEntryImpl.setOpenGraphDescription("");
		}
		else {
			layoutSEOEntryImpl.setOpenGraphDescription(openGraphDescription);
		}

		layoutSEOEntryImpl.setOpenGraphImageFileEntryId(
			openGraphImageFileEntryId);

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

		canonicalURLEnabled = objectInput.readBoolean();
		canonicalURL = objectInput.readUTF();

		openGraphTitleEnabled = objectInput.readBoolean();
		openGraphTitle = objectInput.readUTF();

		openGraphDescriptionEnabled = objectInput.readBoolean();
		openGraphDescription = objectInput.readUTF();

		openGraphImageFileEntryId = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		objectOutput.writeBoolean(canonicalURLEnabled);

		if (canonicalURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(canonicalURL);
		}

		objectOutput.writeBoolean(openGraphTitleEnabled);

		if (openGraphTitle == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(openGraphTitle);
		}

		objectOutput.writeBoolean(openGraphDescriptionEnabled);

		if (openGraphDescription == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(openGraphDescription);
		}

		objectOutput.writeLong(openGraphImageFileEntryId);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
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
	public boolean canonicalURLEnabled;
	public String canonicalURL;
	public boolean openGraphTitleEnabled;
	public String openGraphTitle;
	public boolean openGraphDescriptionEnabled;
	public String openGraphDescription;
	public long openGraphImageFileEntryId;
	public long lastPublishDate;

}