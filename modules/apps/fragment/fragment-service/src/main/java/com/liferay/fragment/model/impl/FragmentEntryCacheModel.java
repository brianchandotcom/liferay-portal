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

package com.liferay.fragment.model.impl;

import com.liferay.fragment.model.FragmentEntry;
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
 * The cache model class for representing FragmentEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FragmentEntryCacheModel
	implements CacheModel<FragmentEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FragmentEntryCacheModel)) {
			return false;
		}

		FragmentEntryCacheModel fragmentEntryCacheModel =
			(FragmentEntryCacheModel)obj;

		if ((fragmentEntryId == fragmentEntryCacheModel.fragmentEntryId) &&
			(mvccVersion == fragmentEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fragmentEntryId);

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
		StringBundler sb = new StringBundler(47);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);
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
		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);
		sb.append(", fragmentEntryKey=");
		sb.append(fragmentEntryKey);
		sb.append(", name=");
		sb.append(name);
		sb.append(", css=");
		sb.append(css);
		sb.append(", html=");
		sb.append(html);
		sb.append(", js=");
		sb.append(js);
		sb.append(", configuration=");
		sb.append(configuration);
		sb.append(", previewFileEntryId=");
		sb.append(previewFileEntryId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FragmentEntry toEntityModel() {
		FragmentEntryImpl fragmentEntryImpl = new FragmentEntryImpl();

		fragmentEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			fragmentEntryImpl.setUuid("");
		}
		else {
			fragmentEntryImpl.setUuid(uuid);
		}

		fragmentEntryImpl.setFragmentEntryId(fragmentEntryId);
		fragmentEntryImpl.setGroupId(groupId);
		fragmentEntryImpl.setCompanyId(companyId);
		fragmentEntryImpl.setUserId(userId);

		if (userName == null) {
			fragmentEntryImpl.setUserName("");
		}
		else {
			fragmentEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			fragmentEntryImpl.setCreateDate(null);
		}
		else {
			fragmentEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fragmentEntryImpl.setModifiedDate(null);
		}
		else {
			fragmentEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		fragmentEntryImpl.setFragmentCollectionId(fragmentCollectionId);

		if (fragmentEntryKey == null) {
			fragmentEntryImpl.setFragmentEntryKey("");
		}
		else {
			fragmentEntryImpl.setFragmentEntryKey(fragmentEntryKey);
		}

		if (name == null) {
			fragmentEntryImpl.setName("");
		}
		else {
			fragmentEntryImpl.setName(name);
		}

		if (css == null) {
			fragmentEntryImpl.setCss("");
		}
		else {
			fragmentEntryImpl.setCss(css);
		}

		if (html == null) {
			fragmentEntryImpl.setHtml("");
		}
		else {
			fragmentEntryImpl.setHtml(html);
		}

		if (js == null) {
			fragmentEntryImpl.setJs("");
		}
		else {
			fragmentEntryImpl.setJs(js);
		}

		if (configuration == null) {
			fragmentEntryImpl.setConfiguration("");
		}
		else {
			fragmentEntryImpl.setConfiguration(configuration);
		}

		fragmentEntryImpl.setPreviewFileEntryId(previewFileEntryId);
		fragmentEntryImpl.setType(type);

		if (lastPublishDate == Long.MIN_VALUE) {
			fragmentEntryImpl.setLastPublishDate(null);
		}
		else {
			fragmentEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		fragmentEntryImpl.setStatus(status);
		fragmentEntryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			fragmentEntryImpl.setStatusByUserName("");
		}
		else {
			fragmentEntryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			fragmentEntryImpl.setStatusDate(null);
		}
		else {
			fragmentEntryImpl.setStatusDate(new Date(statusDate));
		}

		fragmentEntryImpl.resetOriginalValues();

		return fragmentEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		fragmentEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		fragmentCollectionId = objectInput.readLong();
		fragmentEntryKey = objectInput.readUTF();
		name = objectInput.readUTF();
		css = objectInput.readUTF();
		html = objectInput.readUTF();
		js = objectInput.readUTF();
		configuration = objectInput.readUTF();

		previewFileEntryId = objectInput.readLong();

		type = objectInput.readInt();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		objectOutput.writeLong(fragmentEntryId);

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

		objectOutput.writeLong(fragmentCollectionId);

		if (fragmentEntryKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fragmentEntryKey);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (css == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(css);
		}

		if (html == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(html);
		}

		if (js == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(js);
		}

		if (configuration == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(configuration);
		}

		objectOutput.writeLong(previewFileEntryId);

		objectOutput.writeInt(type);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public String uuid;
	public long fragmentEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long fragmentCollectionId;
	public String fragmentEntryKey;
	public String name;
	public String css;
	public String html;
	public String js;
	public String configuration;
	public long previewFileEntryId;
	public int type;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}