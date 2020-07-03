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

package com.liferay.style.book.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.style.book.model.StyleBookEntryVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing StyleBookEntryVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class StyleBookEntryVersionCacheModel
	implements CacheModel<StyleBookEntryVersion>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StyleBookEntryVersionCacheModel)) {
			return false;
		}

		StyleBookEntryVersionCacheModel styleBookEntryVersionCacheModel =
			(StyleBookEntryVersionCacheModel)object;

		if (styleBookEntryVersionId ==
				styleBookEntryVersionCacheModel.styleBookEntryVersionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, styleBookEntryVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{styleBookEntryVersionId=");
		sb.append(styleBookEntryVersionId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", styleBookEntryId=");
		sb.append(styleBookEntryId);
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
		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);
		sb.append(", name=");
		sb.append(name);
		sb.append(", previewFileEntryId=");
		sb.append(previewFileEntryId);
		sb.append(", styleBookEntryKey=");
		sb.append(styleBookEntryKey);
		sb.append(", tokensValues=");
		sb.append(tokensValues);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public StyleBookEntryVersion toEntityModel() {
		StyleBookEntryVersionImpl styleBookEntryVersionImpl =
			new StyleBookEntryVersionImpl();

		styleBookEntryVersionImpl.setStyleBookEntryVersionId(
			styleBookEntryVersionId);
		styleBookEntryVersionImpl.setVersion(version);
		styleBookEntryVersionImpl.setStyleBookEntryId(styleBookEntryId);
		styleBookEntryVersionImpl.setGroupId(groupId);
		styleBookEntryVersionImpl.setCompanyId(companyId);
		styleBookEntryVersionImpl.setUserId(userId);

		if (userName == null) {
			styleBookEntryVersionImpl.setUserName("");
		}
		else {
			styleBookEntryVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			styleBookEntryVersionImpl.setCreateDate(null);
		}
		else {
			styleBookEntryVersionImpl.setCreateDate(new Date(createDate));
		}

		styleBookEntryVersionImpl.setDefaultStyleBookEntry(
			defaultStyleBookEntry);

		if (name == null) {
			styleBookEntryVersionImpl.setName("");
		}
		else {
			styleBookEntryVersionImpl.setName(name);
		}

		styleBookEntryVersionImpl.setPreviewFileEntryId(previewFileEntryId);

		if (styleBookEntryKey == null) {
			styleBookEntryVersionImpl.setStyleBookEntryKey("");
		}
		else {
			styleBookEntryVersionImpl.setStyleBookEntryKey(styleBookEntryKey);
		}

		if (tokensValues == null) {
			styleBookEntryVersionImpl.setTokensValues("");
		}
		else {
			styleBookEntryVersionImpl.setTokensValues(tokensValues);
		}

		styleBookEntryVersionImpl.resetOriginalValues();

		return styleBookEntryVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		styleBookEntryVersionId = objectInput.readLong();

		version = objectInput.readInt();

		styleBookEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		defaultStyleBookEntry = objectInput.readBoolean();
		name = objectInput.readUTF();

		previewFileEntryId = objectInput.readLong();
		styleBookEntryKey = objectInput.readUTF();
		tokensValues = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(styleBookEntryVersionId);

		objectOutput.writeInt(version);

		objectOutput.writeLong(styleBookEntryId);

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

		objectOutput.writeBoolean(defaultStyleBookEntry);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(previewFileEntryId);

		if (styleBookEntryKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(styleBookEntryKey);
		}

		if (tokensValues == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(tokensValues);
		}
	}

	public long styleBookEntryVersionId;
	public int version;
	public long styleBookEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public boolean defaultStyleBookEntry;
	public String name;
	public long previewFileEntryId;
	public String styleBookEntryKey;
	public String tokensValues;

}