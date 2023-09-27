/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.segments.model.SegmentsExperience;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SegmentsExperience in entity cache.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsExperienceCacheModel
	implements CacheModel<SegmentsExperience>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SegmentsExperienceCacheModel)) {
			return false;
		}

		SegmentsExperienceCacheModel segmentsExperienceCacheModel =
			(SegmentsExperienceCacheModel)object;

		if ((segmentsExperienceId ==
				segmentsExperienceCacheModel.segmentsExperienceId) &&
			(mvccVersion == segmentsExperienceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, segmentsExperienceId);

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
		StringBundler sb = new StringBundler(37);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", segmentsExperienceId=");
		sb.append(segmentsExperienceId);
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
		sb.append(", segmentsEntryId=");
		sb.append(segmentsEntryId);
		sb.append(", segmentsExperienceKey=");
		sb.append(segmentsExperienceKey);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", active=");
		sb.append(active);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SegmentsExperience toEntityModel() {
		SegmentsExperienceImpl segmentsExperienceImpl =
			new SegmentsExperienceImpl();

		segmentsExperienceImpl.setMvccVersion(mvccVersion);
		segmentsExperienceImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			segmentsExperienceImpl.setUuid("");
		}
		else {
			segmentsExperienceImpl.setUuid(uuid);
		}

		segmentsExperienceImpl.setSegmentsExperienceId(segmentsExperienceId);
		segmentsExperienceImpl.setGroupId(groupId);
		segmentsExperienceImpl.setCompanyId(companyId);
		segmentsExperienceImpl.setUserId(userId);

		if (userName == null) {
			segmentsExperienceImpl.setUserName("");
		}
		else {
			segmentsExperienceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			segmentsExperienceImpl.setCreateDate(null);
		}
		else {
			segmentsExperienceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			segmentsExperienceImpl.setModifiedDate(null);
		}
		else {
			segmentsExperienceImpl.setModifiedDate(new Date(modifiedDate));
		}

		segmentsExperienceImpl.setSegmentsEntryId(segmentsEntryId);

		if (segmentsExperienceKey == null) {
			segmentsExperienceImpl.setSegmentsExperienceKey("");
		}
		else {
			segmentsExperienceImpl.setSegmentsExperienceKey(
				segmentsExperienceKey);
		}

		segmentsExperienceImpl.setPlid(plid);

		if (name == null) {
			segmentsExperienceImpl.setName("");
		}
		else {
			segmentsExperienceImpl.setName(name);
		}

		segmentsExperienceImpl.setPriority(priority);
		segmentsExperienceImpl.setActive(active);

		if (typeSettings == null) {
			segmentsExperienceImpl.setTypeSettings("");
		}
		else {
			segmentsExperienceImpl.setTypeSettings(typeSettings);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			segmentsExperienceImpl.setLastPublishDate(null);
		}
		else {
			segmentsExperienceImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		segmentsExperienceImpl.resetOriginalValues();

		return segmentsExperienceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		segmentsExperienceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		segmentsEntryId = objectInput.readLong();
		segmentsExperienceKey = objectInput.readUTF();

		plid = objectInput.readLong();
		name = objectInput.readUTF();

		priority = objectInput.readInt();

		active = objectInput.readBoolean();
		typeSettings = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(segmentsExperienceId);

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

		objectOutput.writeLong(segmentsEntryId);

		if (segmentsExperienceKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(segmentsExperienceKey);
		}

		objectOutput.writeLong(plid);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(priority);

		objectOutput.writeBoolean(active);

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long segmentsExperienceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long segmentsEntryId;
	public String segmentsExperienceKey;
	public long plid;
	public String name;
	public int priority;
	public boolean active;
	public String typeSettings;
	public long lastPublishDate;

}