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

import com.liferay.fragment.model.FragmentComposition;
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
 * The cache model class for representing FragmentComposition in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FragmentCompositionCacheModel
	implements CacheModel<FragmentComposition>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentCompositionCacheModel)) {
			return false;
		}

		FragmentCompositionCacheModel fragmentCompositionCacheModel =
			(FragmentCompositionCacheModel)object;

		if ((fragmentCompositionId ==
				fragmentCompositionCacheModel.fragmentCompositionId) &&
			(mvccVersion == fragmentCompositionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fragmentCompositionId);

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
		StringBundler sb = new StringBundler(43);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", fragmentCompositionId=");
		sb.append(fragmentCompositionId);
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
		sb.append(", fragmentCompositionKey=");
		sb.append(fragmentCompositionKey);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", data=");
		sb.append(data);
		sb.append(", previewFileEntryId=");
		sb.append(previewFileEntryId);
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
	public FragmentComposition toEntityModel() {
		FragmentCompositionImpl fragmentCompositionImpl =
			new FragmentCompositionImpl();

		fragmentCompositionImpl.setMvccVersion(mvccVersion);
		fragmentCompositionImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			fragmentCompositionImpl.setUuid("");
		}
		else {
			fragmentCompositionImpl.setUuid(uuid);
		}

		fragmentCompositionImpl.setFragmentCompositionId(fragmentCompositionId);
		fragmentCompositionImpl.setGroupId(groupId);
		fragmentCompositionImpl.setCompanyId(companyId);
		fragmentCompositionImpl.setUserId(userId);

		if (userName == null) {
			fragmentCompositionImpl.setUserName("");
		}
		else {
			fragmentCompositionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			fragmentCompositionImpl.setCreateDate(null);
		}
		else {
			fragmentCompositionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fragmentCompositionImpl.setModifiedDate(null);
		}
		else {
			fragmentCompositionImpl.setModifiedDate(new Date(modifiedDate));
		}

		fragmentCompositionImpl.setFragmentCollectionId(fragmentCollectionId);

		if (fragmentCompositionKey == null) {
			fragmentCompositionImpl.setFragmentCompositionKey("");
		}
		else {
			fragmentCompositionImpl.setFragmentCompositionKey(
				fragmentCompositionKey);
		}

		if (name == null) {
			fragmentCompositionImpl.setName("");
		}
		else {
			fragmentCompositionImpl.setName(name);
		}

		if (description == null) {
			fragmentCompositionImpl.setDescription("");
		}
		else {
			fragmentCompositionImpl.setDescription(description);
		}

		if (data == null) {
			fragmentCompositionImpl.setData("");
		}
		else {
			fragmentCompositionImpl.setData(data);
		}

		fragmentCompositionImpl.setPreviewFileEntryId(previewFileEntryId);

		if (lastPublishDate == Long.MIN_VALUE) {
			fragmentCompositionImpl.setLastPublishDate(null);
		}
		else {
			fragmentCompositionImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		fragmentCompositionImpl.setStatus(status);
		fragmentCompositionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			fragmentCompositionImpl.setStatusByUserName("");
		}
		else {
			fragmentCompositionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			fragmentCompositionImpl.setStatusDate(null);
		}
		else {
			fragmentCompositionImpl.setStatusDate(new Date(statusDate));
		}

		fragmentCompositionImpl.resetOriginalValues();

		return fragmentCompositionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		fragmentCompositionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		fragmentCollectionId = objectInput.readLong();
		fragmentCompositionKey = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		data = (String)objectInput.readObject();

		previewFileEntryId = objectInput.readLong();
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

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(fragmentCompositionId);

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

		if (fragmentCompositionKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fragmentCompositionKey);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (data == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(data);
		}

		objectOutput.writeLong(previewFileEntryId);
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
	public String uuid;
	public long fragmentCompositionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long fragmentCollectionId;
	public String fragmentCompositionKey;
	public String name;
	public String description;
	public String data;
	public long previewFileEntryId;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}