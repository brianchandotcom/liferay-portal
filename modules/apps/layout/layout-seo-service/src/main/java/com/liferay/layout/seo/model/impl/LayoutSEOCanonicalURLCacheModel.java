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

import com.liferay.layout.seo.model.LayoutSEOCanonicalURL;
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
 * The cache model class for representing LayoutSEOCanonicalURL in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutSEOCanonicalURLCacheModel
	implements CacheModel<LayoutSEOCanonicalURL>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSEOCanonicalURLCacheModel)) {
			return false;
		}

		LayoutSEOCanonicalURLCacheModel layoutSEOCanonicalURLCacheModel =
			(LayoutSEOCanonicalURLCacheModel)obj;

		if (layoutSEOCanonicalURLId ==
				layoutSEOCanonicalURLCacheModel.layoutSEOCanonicalURLId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, layoutSEOCanonicalURLId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", layoutSEOCanonicalURLId=");
		sb.append(layoutSEOCanonicalURLId);
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
	public LayoutSEOCanonicalURL toEntityModel() {
		LayoutSEOCanonicalURLImpl layoutSEOCanonicalURLImpl =
			new LayoutSEOCanonicalURLImpl();

		if (uuid == null) {
			layoutSEOCanonicalURLImpl.setUuid("");
		}
		else {
			layoutSEOCanonicalURLImpl.setUuid(uuid);
		}

		layoutSEOCanonicalURLImpl.setLayoutSEOCanonicalURLId(
			layoutSEOCanonicalURLId);
		layoutSEOCanonicalURLImpl.setGroupId(groupId);
		layoutSEOCanonicalURLImpl.setCompanyId(companyId);
		layoutSEOCanonicalURLImpl.setUserId(userId);

		if (userName == null) {
			layoutSEOCanonicalURLImpl.setUserName("");
		}
		else {
			layoutSEOCanonicalURLImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutSEOCanonicalURLImpl.setCreateDate(null);
		}
		else {
			layoutSEOCanonicalURLImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutSEOCanonicalURLImpl.setModifiedDate(null);
		}
		else {
			layoutSEOCanonicalURLImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (canonicalURL == null) {
			layoutSEOCanonicalURLImpl.setCanonicalURL("");
		}
		else {
			layoutSEOCanonicalURLImpl.setCanonicalURL(canonicalURL);
		}

		layoutSEOCanonicalURLImpl.setEnabled(enabled);
		layoutSEOCanonicalURLImpl.setPrivateLayout(privateLayout);

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutSEOCanonicalURLImpl.setLastPublishDate(null);
		}
		else {
			layoutSEOCanonicalURLImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		layoutSEOCanonicalURLImpl.setLayoutId(layoutId);

		layoutSEOCanonicalURLImpl.resetOriginalValues();

		return layoutSEOCanonicalURLImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		layoutSEOCanonicalURLId = objectInput.readLong();

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

		objectOutput.writeLong(layoutSEOCanonicalURLId);

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
	public long layoutSEOCanonicalURLId;
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