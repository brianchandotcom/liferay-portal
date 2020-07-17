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

import com.liferay.fragment.model.FragmentEntryVersion;
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
 * The cache model class for representing FragmentEntryVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FragmentEntryVersionCacheModel
	implements CacheModel<FragmentEntryVersion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentEntryVersionCacheModel)) {
			return false;
		}

		FragmentEntryVersionCacheModel fragmentEntryVersionCacheModel =
			(FragmentEntryVersionCacheModel)object;

		if ((fragmentEntryVersionId ==
				fragmentEntryVersionCacheModel.fragmentEntryVersionId) &&
			(mvccVersion == fragmentEntryVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fragmentEntryVersionId);

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
		StringBundler sb = new StringBundler(57);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", fragmentEntryVersionId=");
		sb.append(fragmentEntryVersionId);
		sb.append(", version=");
		sb.append(version);
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
		sb.append(", cacheable=");
		sb.append(cacheable);
		sb.append(", configuration=");
		sb.append(configuration);
		sb.append(", previewFileEntryId=");
		sb.append(previewFileEntryId);
		sb.append(", readOnly=");
		sb.append(readOnly);
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
	public FragmentEntryVersion toEntityModel() {
		FragmentEntryVersionImpl fragmentEntryVersionImpl =
			new FragmentEntryVersionImpl();

		fragmentEntryVersionImpl.setMvccVersion(mvccVersion);
		fragmentEntryVersionImpl.setCtCollectionId(ctCollectionId);
		fragmentEntryVersionImpl.setFragmentEntryVersionId(
			fragmentEntryVersionId);
		fragmentEntryVersionImpl.setVersion(version);

		if (uuid == null) {
			fragmentEntryVersionImpl.setUuid("");
		}
		else {
			fragmentEntryVersionImpl.setUuid(uuid);
		}

		fragmentEntryVersionImpl.setFragmentEntryId(fragmentEntryId);
		fragmentEntryVersionImpl.setGroupId(groupId);
		fragmentEntryVersionImpl.setCompanyId(companyId);
		fragmentEntryVersionImpl.setUserId(userId);

		if (userName == null) {
			fragmentEntryVersionImpl.setUserName("");
		}
		else {
			fragmentEntryVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			fragmentEntryVersionImpl.setCreateDate(null);
		}
		else {
			fragmentEntryVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fragmentEntryVersionImpl.setModifiedDate(null);
		}
		else {
			fragmentEntryVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		fragmentEntryVersionImpl.setFragmentCollectionId(fragmentCollectionId);

		if (fragmentEntryKey == null) {
			fragmentEntryVersionImpl.setFragmentEntryKey("");
		}
		else {
			fragmentEntryVersionImpl.setFragmentEntryKey(fragmentEntryKey);
		}

		if (name == null) {
			fragmentEntryVersionImpl.setName("");
		}
		else {
			fragmentEntryVersionImpl.setName(name);
		}

		if (css == null) {
			fragmentEntryVersionImpl.setCss("");
		}
		else {
			fragmentEntryVersionImpl.setCss(css);
		}

		if (html == null) {
			fragmentEntryVersionImpl.setHtml("");
		}
		else {
			fragmentEntryVersionImpl.setHtml(html);
		}

		if (js == null) {
			fragmentEntryVersionImpl.setJs("");
		}
		else {
			fragmentEntryVersionImpl.setJs(js);
		}

		fragmentEntryVersionImpl.setCacheable(cacheable);

		if (configuration == null) {
			fragmentEntryVersionImpl.setConfiguration("");
		}
		else {
			fragmentEntryVersionImpl.setConfiguration(configuration);
		}

		fragmentEntryVersionImpl.setPreviewFileEntryId(previewFileEntryId);
		fragmentEntryVersionImpl.setReadOnly(readOnly);
		fragmentEntryVersionImpl.setType(type);

		if (lastPublishDate == Long.MIN_VALUE) {
			fragmentEntryVersionImpl.setLastPublishDate(null);
		}
		else {
			fragmentEntryVersionImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		fragmentEntryVersionImpl.setStatus(status);
		fragmentEntryVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			fragmentEntryVersionImpl.setStatusByUserName("");
		}
		else {
			fragmentEntryVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			fragmentEntryVersionImpl.setStatusDate(null);
		}
		else {
			fragmentEntryVersionImpl.setStatusDate(new Date(statusDate));
		}

		fragmentEntryVersionImpl.resetOriginalValues();

		return fragmentEntryVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		fragmentEntryVersionId = objectInput.readLong();

		version = objectInput.readInt();
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
		css = (String)objectInput.readObject();
		html = (String)objectInput.readObject();
		js = (String)objectInput.readObject();

		cacheable = objectInput.readBoolean();
		configuration = (String)objectInput.readObject();

		previewFileEntryId = objectInput.readLong();

		readOnly = objectInput.readBoolean();

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

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(fragmentEntryVersionId);

		objectOutput.writeInt(version);

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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(css);
		}

		if (html == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(html);
		}

		if (js == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(js);
		}

		objectOutput.writeBoolean(cacheable);

		if (configuration == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(configuration);
		}

		objectOutput.writeLong(previewFileEntryId);

		objectOutput.writeBoolean(readOnly);

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
	public long ctCollectionId;
	public long fragmentEntryVersionId;
	public int version;
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
	public boolean cacheable;
	public String configuration;
	public long previewFileEntryId;
	public boolean readOnly;
	public int type;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}