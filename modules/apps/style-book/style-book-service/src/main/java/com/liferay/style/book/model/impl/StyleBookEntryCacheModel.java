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
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.style.book.model.StyleBookEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing StyleBookEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class StyleBookEntryCacheModel
	implements CacheModel<StyleBookEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StyleBookEntryCacheModel)) {
			return false;
		}

		StyleBookEntryCacheModel styleBookEntryCacheModel =
			(StyleBookEntryCacheModel)object;

		if ((styleBookEntryId == styleBookEntryCacheModel.styleBookEntryId) &&
			(mvccVersion == styleBookEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, styleBookEntryId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", styleBookEntryKey=");
		sb.append(styleBookEntryKey);
		sb.append(", previewFileEntryId=");
		sb.append(previewFileEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public StyleBookEntry toEntityModel() {
		StyleBookEntryImpl styleBookEntryImpl = new StyleBookEntryImpl();

		styleBookEntryImpl.setMvccVersion(mvccVersion);
		styleBookEntryImpl.setStyleBookEntryId(styleBookEntryId);
		styleBookEntryImpl.setGroupId(groupId);
		styleBookEntryImpl.setCompanyId(companyId);
		styleBookEntryImpl.setUserId(userId);

		if (userName == null) {
			styleBookEntryImpl.setUserName("");
		}
		else {
			styleBookEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			styleBookEntryImpl.setCreateDate(null);
		}
		else {
			styleBookEntryImpl.setCreateDate(new Date(createDate));
		}

		if (name == null) {
			styleBookEntryImpl.setName("");
		}
		else {
			styleBookEntryImpl.setName(name);
		}

		if (styleBookEntryKey == null) {
			styleBookEntryImpl.setStyleBookEntryKey("");
		}
		else {
			styleBookEntryImpl.setStyleBookEntryKey(styleBookEntryKey);
		}

		styleBookEntryImpl.setPreviewFileEntryId(previewFileEntryId);

		styleBookEntryImpl.resetOriginalValues();

		return styleBookEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		styleBookEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		name = objectInput.readUTF();
		styleBookEntryKey = objectInput.readUTF();

		previewFileEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (styleBookEntryKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(styleBookEntryKey);
		}

		objectOutput.writeLong(previewFileEntryId);
	}

	public long mvccVersion;
	public long styleBookEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public String name;
	public String styleBookEntryKey;
	public long previewFileEntryId;

}