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

import com.liferay.layout.seo.model.LayoutSEOOpenGraphEntry;
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
 * The cache model class for representing LayoutSEOOpenGraphEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutSEOOpenGraphEntryCacheModel
	implements CacheModel<LayoutSEOOpenGraphEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSEOOpenGraphEntryCacheModel)) {
			return false;
		}

		LayoutSEOOpenGraphEntryCacheModel layoutSEOOpenGraphEntryCacheModel =
			(LayoutSEOOpenGraphEntryCacheModel)obj;

		if ((layoutSEOEntryId ==
				layoutSEOOpenGraphEntryCacheModel.layoutSEOEntryId) &&
			(mvccVersion == layoutSEOOpenGraphEntryCacheModel.mvccVersion)) {

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
		StringBundler sb = new StringBundler(29);

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
		sb.append(", enabled=");
		sb.append(enabled);
		sb.append(", canonicalURL=");
		sb.append(canonicalURL);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSEOOpenGraphEntry toEntityModel() {
		LayoutSEOOpenGraphEntryImpl layoutSEOOpenGraphEntryImpl =
			new LayoutSEOOpenGraphEntryImpl();

		layoutSEOOpenGraphEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			layoutSEOOpenGraphEntryImpl.setUuid("");
		}
		else {
			layoutSEOOpenGraphEntryImpl.setUuid(uuid);
		}

		layoutSEOOpenGraphEntryImpl.setLayoutSEOEntryId(layoutSEOEntryId);
		layoutSEOOpenGraphEntryImpl.setGroupId(groupId);
		layoutSEOOpenGraphEntryImpl.setCompanyId(companyId);
		layoutSEOOpenGraphEntryImpl.setUserId(userId);

		if (userName == null) {
			layoutSEOOpenGraphEntryImpl.setUserName("");
		}
		else {
			layoutSEOOpenGraphEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutSEOOpenGraphEntryImpl.setCreateDate(null);
		}
		else {
			layoutSEOOpenGraphEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutSEOOpenGraphEntryImpl.setModifiedDate(null);
		}
		else {
			layoutSEOOpenGraphEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutSEOOpenGraphEntryImpl.setPrivateLayout(privateLayout);
		layoutSEOOpenGraphEntryImpl.setLayoutId(layoutId);
		layoutSEOOpenGraphEntryImpl.setEnabled(enabled);

		if (canonicalURL == null) {
			layoutSEOOpenGraphEntryImpl.setCanonicalURL("");
		}
		else {
			layoutSEOOpenGraphEntryImpl.setCanonicalURL(canonicalURL);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutSEOOpenGraphEntryImpl.setLastPublishDate(null);
		}
		else {
			layoutSEOOpenGraphEntryImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		layoutSEOOpenGraphEntryImpl.resetOriginalValues();

		return layoutSEOOpenGraphEntryImpl;
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

		enabled = objectInput.readBoolean();
		canonicalURL = objectInput.readUTF();
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

		objectOutput.writeBoolean(enabled);

		if (canonicalURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(canonicalURL);
		}

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
	public boolean enabled;
	public String canonicalURL;
	public long lastPublishDate;

}