/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.model.impl;

import com.liferay.layout.seo.model.LayoutSEOSite;
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
 * The cache model class for representing LayoutSEOSite in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutSEOSiteCacheModel
	implements CacheModel<LayoutSEOSite>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutSEOSiteCacheModel)) {
			return false;
		}

		LayoutSEOSiteCacheModel layoutSEOSiteCacheModel =
			(LayoutSEOSiteCacheModel)object;

		if ((layoutSEOSiteId == layoutSEOSiteCacheModel.layoutSEOSiteId) &&
			(mvccVersion == layoutSEOSiteCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutSEOSiteId);

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
		sb.append(", layoutSEOSiteId=");
		sb.append(layoutSEOSiteId);
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
		sb.append(", openGraphImageAlt=");
		sb.append(openGraphImageAlt);
		sb.append(", openGraphImageFileEntryId=");
		sb.append(openGraphImageFileEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSEOSite toEntityModel() {
		LayoutSEOSiteImpl layoutSEOSiteImpl = new LayoutSEOSiteImpl();

		layoutSEOSiteImpl.setMvccVersion(mvccVersion);
		layoutSEOSiteImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			layoutSEOSiteImpl.setUuid("");
		}
		else {
			layoutSEOSiteImpl.setUuid(uuid);
		}

		layoutSEOSiteImpl.setLayoutSEOSiteId(layoutSEOSiteId);
		layoutSEOSiteImpl.setGroupId(groupId);
		layoutSEOSiteImpl.setCompanyId(companyId);
		layoutSEOSiteImpl.setUserId(userId);

		if (userName == null) {
			layoutSEOSiteImpl.setUserName("");
		}
		else {
			layoutSEOSiteImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutSEOSiteImpl.setCreateDate(null);
		}
		else {
			layoutSEOSiteImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutSEOSiteImpl.setModifiedDate(null);
		}
		else {
			layoutSEOSiteImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutSEOSiteImpl.setOpenGraphEnabled(openGraphEnabled);

		if (openGraphImageAlt == null) {
			layoutSEOSiteImpl.setOpenGraphImageAlt("");
		}
		else {
			layoutSEOSiteImpl.setOpenGraphImageAlt(openGraphImageAlt);
		}

		layoutSEOSiteImpl.setOpenGraphImageFileEntryId(
			openGraphImageFileEntryId);

		layoutSEOSiteImpl.resetOriginalValues();

		return layoutSEOSiteImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		layoutSEOSiteId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		openGraphEnabled = objectInput.readBoolean();
		openGraphImageAlt = objectInput.readUTF();

		openGraphImageFileEntryId = objectInput.readLong();
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

		objectOutput.writeLong(layoutSEOSiteId);

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

		if (openGraphImageAlt == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(openGraphImageAlt);
		}

		objectOutput.writeLong(openGraphImageFileEntryId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long layoutSEOSiteId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean openGraphEnabled;
	public String openGraphImageAlt;
	public long openGraphImageFileEntryId;

}