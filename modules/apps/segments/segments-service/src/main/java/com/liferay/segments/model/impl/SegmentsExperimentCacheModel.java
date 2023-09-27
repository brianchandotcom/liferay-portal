/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.segments.model.SegmentsExperiment;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SegmentsExperiment in entity cache.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsExperimentCacheModel
	implements CacheModel<SegmentsExperiment>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SegmentsExperimentCacheModel)) {
			return false;
		}

		SegmentsExperimentCacheModel segmentsExperimentCacheModel =
			(SegmentsExperimentCacheModel)object;

		if ((segmentsExperimentId ==
				segmentsExperimentCacheModel.segmentsExperimentId) &&
			(mvccVersion == segmentsExperimentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, segmentsExperimentId);

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
		sb.append(", segmentsExperimentId=");
		sb.append(segmentsExperimentId);
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
		sb.append(", segmentsExperienceId=");
		sb.append(segmentsExperienceId);
		sb.append(", segmentsExperimentKey=");
		sb.append(segmentsExperimentKey);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SegmentsExperiment toEntityModel() {
		SegmentsExperimentImpl segmentsExperimentImpl =
			new SegmentsExperimentImpl();

		segmentsExperimentImpl.setMvccVersion(mvccVersion);
		segmentsExperimentImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			segmentsExperimentImpl.setUuid("");
		}
		else {
			segmentsExperimentImpl.setUuid(uuid);
		}

		segmentsExperimentImpl.setSegmentsExperimentId(segmentsExperimentId);
		segmentsExperimentImpl.setGroupId(groupId);
		segmentsExperimentImpl.setCompanyId(companyId);
		segmentsExperimentImpl.setUserId(userId);

		if (userName == null) {
			segmentsExperimentImpl.setUserName("");
		}
		else {
			segmentsExperimentImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			segmentsExperimentImpl.setCreateDate(null);
		}
		else {
			segmentsExperimentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			segmentsExperimentImpl.setModifiedDate(null);
		}
		else {
			segmentsExperimentImpl.setModifiedDate(new Date(modifiedDate));
		}

		segmentsExperimentImpl.setSegmentsEntryId(segmentsEntryId);
		segmentsExperimentImpl.setSegmentsExperienceId(segmentsExperienceId);

		if (segmentsExperimentKey == null) {
			segmentsExperimentImpl.setSegmentsExperimentKey("");
		}
		else {
			segmentsExperimentImpl.setSegmentsExperimentKey(
				segmentsExperimentKey);
		}

		segmentsExperimentImpl.setPlid(plid);

		if (name == null) {
			segmentsExperimentImpl.setName("");
		}
		else {
			segmentsExperimentImpl.setName(name);
		}

		if (description == null) {
			segmentsExperimentImpl.setDescription("");
		}
		else {
			segmentsExperimentImpl.setDescription(description);
		}

		if (typeSettings == null) {
			segmentsExperimentImpl.setTypeSettings("");
		}
		else {
			segmentsExperimentImpl.setTypeSettings(typeSettings);
		}

		segmentsExperimentImpl.setStatus(status);

		segmentsExperimentImpl.resetOriginalValues();

		return segmentsExperimentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		segmentsExperimentId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		segmentsEntryId = objectInput.readLong();

		segmentsExperienceId = objectInput.readLong();
		segmentsExperimentKey = objectInput.readUTF();

		plid = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		typeSettings = (String)objectInput.readObject();

		status = objectInput.readInt();
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

		objectOutput.writeLong(segmentsExperimentId);

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

		objectOutput.writeLong(segmentsExperienceId);

		if (segmentsExperimentKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(segmentsExperimentKey);
		}

		objectOutput.writeLong(plid);

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

		if (typeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(typeSettings);
		}

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long segmentsExperimentId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long segmentsEntryId;
	public long segmentsExperienceId;
	public String segmentsExperimentKey;
	public long plid;
	public String name;
	public String description;
	public String typeSettings;
	public int status;

}