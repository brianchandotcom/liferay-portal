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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing LayoutSEOEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutSEOEntryCacheModel
	implements CacheModel<LayoutSEOEntry>, Externalizable {

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

		if (layoutSEOEntryId == layoutSEOEntryCacheModel.layoutSEOEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, layoutSEOEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
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
		sb.append(", canonicalURL=");
		sb.append(canonicalURL);
		sb.append(", enabled=");
		sb.append(enabled);
		sb.append(", privateLayout=");
		sb.append(privateLayout);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", layoutId=");
		sb.append(layoutId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSEOEntry toEntityModel() {
		LayoutSEOEntryImpl layoutSEOEntryImpl = new LayoutSEOEntryImpl();

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

		if (canonicalURL == null) {
			layoutSEOEntryImpl.setCanonicalURL("");
		}
		else {
			layoutSEOEntryImpl.setCanonicalURL(canonicalURL);
		}

		layoutSEOEntryImpl.setEnabled(enabled);
		layoutSEOEntryImpl.setPrivateLayout(privateLayout);

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutSEOEntryImpl.setLastPublishDate(null);
		}
		else {
			layoutSEOEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		layoutSEOEntryImpl.setLayoutId(layoutId);

		layoutSEOEntryImpl.resetOriginalValues();

		return layoutSEOEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		layoutSEOEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		canonicalURL = objectInput.readUTF();

		enabled = objectInput.readBoolean();

		privateLayout = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();

		layoutId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
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

		if (canonicalURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(canonicalURL);
		}

		objectOutput.writeBoolean(enabled);

		objectOutput.writeBoolean(privateLayout);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeLong(layoutId);
	}

	public String uuid;
	public long layoutSEOEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String canonicalURL;
	public boolean enabled;
	public boolean privateLayout;
	public long lastPublishDate;
	public long layoutId;

}