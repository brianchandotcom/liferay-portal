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

import com.liferay.layout.seo.model.LayoutSEOSiteEntry;
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
 * The cache model class for representing LayoutSEOSiteEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutSEOSiteEntryCacheModel
	implements CacheModel<LayoutSEOSiteEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSEOSiteEntryCacheModel)) {
			return false;
		}

		LayoutSEOSiteEntryCacheModel layoutSEOSiteEntryCacheModel =
			(LayoutSEOSiteEntryCacheModel)obj;

		if ((layoutSEOSiteEntryId ==
				layoutSEOSiteEntryCacheModel.layoutSEOSiteEntryId) &&
			(mvccVersion == layoutSEOSiteEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutSEOSiteEntryId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", layoutSEOSiteEntryId=");
		sb.append(layoutSEOSiteEntryId);
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
		sb.append(", openGraphEnabled=");
		sb.append(openGraphEnabled);
		sb.append(", openGraphImageFileEntryId=");
		sb.append(openGraphImageFileEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSEOSiteEntry toEntityModel() {
		LayoutSEOSiteEntryImpl layoutSEOSiteEntryImpl =
			new LayoutSEOSiteEntryImpl();

		layoutSEOSiteEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			layoutSEOSiteEntryImpl.setUuid("");
		}
		else {
			layoutSEOSiteEntryImpl.setUuid(uuid);
		}

		layoutSEOSiteEntryImpl.setLayoutSEOSiteEntryId(layoutSEOSiteEntryId);
		layoutSEOSiteEntryImpl.setGroupId(groupId);
		layoutSEOSiteEntryImpl.setCompanyId(companyId);
		layoutSEOSiteEntryImpl.setUserId(userId);

		if (userName == null) {
			layoutSEOSiteEntryImpl.setUserName("");
		}
		else {
			layoutSEOSiteEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutSEOSiteEntryImpl.setCreateDate(null);
		}
		else {
			layoutSEOSiteEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutSEOSiteEntryImpl.setModifiedDate(null);
		}
		else {
			layoutSEOSiteEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutSEOSiteEntryImpl.setOpenGraphEnabled(openGraphEnabled);
		layoutSEOSiteEntryImpl.setOpenGraphImageFileEntryId(
			openGraphImageFileEntryId);

		layoutSEOSiteEntryImpl.resetOriginalValues();

		return layoutSEOSiteEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		layoutSEOSiteEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		openGraphEnabled = objectInput.readBoolean();

		openGraphImageFileEntryId = objectInput.readLong();
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

		objectOutput.writeLong(layoutSEOSiteEntryId);

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

		objectOutput.writeBoolean(openGraphEnabled);

		objectOutput.writeLong(openGraphImageFileEntryId);
	}

	public long mvccVersion;
	public String uuid;
	public long layoutSEOSiteEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean openGraphEnabled;
	public long openGraphImageFileEntryId;

}