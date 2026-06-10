/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.segments.model.SegmentsExperienceAudienceEntryRel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SegmentsExperienceAudienceEntryRel in entity cache.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsExperienceAudienceEntryRelCacheModel
	implements CacheModel<SegmentsExperienceAudienceEntryRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SegmentsExperienceAudienceEntryRelCacheModel)) {
			return false;
		}

		SegmentsExperienceAudienceEntryRelCacheModel
			segmentsExperienceAudienceEntryRelCacheModel =
				(SegmentsExperienceAudienceEntryRelCacheModel)object;

		if ((segmentsExperienceAudienceEntryRelId ==
				segmentsExperienceAudienceEntryRelCacheModel.
					segmentsExperienceAudienceEntryRelId) &&
			(mvccVersion ==
				segmentsExperienceAudienceEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, segmentsExperienceAudienceEntryRelId);

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
		sb.append(", segmentsExperienceAudienceEntryRelId=");
		sb.append(segmentsExperienceAudienceEntryRelId);
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
		sb.append(", audienceEntryERC=");
		sb.append(audienceEntryERC);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", segmentsExperienceERC=");
		sb.append(segmentsExperienceERC);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SegmentsExperienceAudienceEntryRel toEntityModel() {
		SegmentsExperienceAudienceEntryRelImpl
			segmentsExperienceAudienceEntryRelImpl =
				new SegmentsExperienceAudienceEntryRelImpl();

		segmentsExperienceAudienceEntryRelImpl.setMvccVersion(mvccVersion);
		segmentsExperienceAudienceEntryRelImpl.setCtCollectionId(
			ctCollectionId);

		if (uuid == null) {
			segmentsExperienceAudienceEntryRelImpl.setUuid("");
		}
		else {
			segmentsExperienceAudienceEntryRelImpl.setUuid(uuid);
		}

		segmentsExperienceAudienceEntryRelImpl.
			setSegmentsExperienceAudienceEntryRelId(
				segmentsExperienceAudienceEntryRelId);
		segmentsExperienceAudienceEntryRelImpl.setGroupId(groupId);
		segmentsExperienceAudienceEntryRelImpl.setCompanyId(companyId);
		segmentsExperienceAudienceEntryRelImpl.setUserId(userId);

		if (userName == null) {
			segmentsExperienceAudienceEntryRelImpl.setUserName("");
		}
		else {
			segmentsExperienceAudienceEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			segmentsExperienceAudienceEntryRelImpl.setCreateDate(null);
		}
		else {
			segmentsExperienceAudienceEntryRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			segmentsExperienceAudienceEntryRelImpl.setModifiedDate(null);
		}
		else {
			segmentsExperienceAudienceEntryRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (audienceEntryERC == null) {
			segmentsExperienceAudienceEntryRelImpl.setAudienceEntryERC("");
		}
		else {
			segmentsExperienceAudienceEntryRelImpl.setAudienceEntryERC(
				audienceEntryERC);
		}

		segmentsExperienceAudienceEntryRelImpl.setPriority(priority);

		if (segmentsExperienceERC == null) {
			segmentsExperienceAudienceEntryRelImpl.setSegmentsExperienceERC("");
		}
		else {
			segmentsExperienceAudienceEntryRelImpl.setSegmentsExperienceERC(
				segmentsExperienceERC);
		}

		segmentsExperienceAudienceEntryRelImpl.resetOriginalValues();

		return segmentsExperienceAudienceEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		segmentsExperienceAudienceEntryRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		audienceEntryERC = objectInput.readUTF();

		priority = objectInput.readInt();
		segmentsExperienceERC = objectInput.readUTF();
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

		objectOutput.writeLong(segmentsExperienceAudienceEntryRelId);

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

		if (audienceEntryERC == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(audienceEntryERC);
		}

		objectOutput.writeInt(priority);

		if (segmentsExperienceERC == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(segmentsExperienceERC);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long segmentsExperienceAudienceEntryRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String audienceEntryERC;
	public int priority;
	public String segmentsExperienceERC;

}
// LIFERAY-SERVICE-BUILDER-HASH:828588627