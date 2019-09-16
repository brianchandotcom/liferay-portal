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

package com.liferay.segments.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.segments.model.SegmentsExperimentRel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SegmentsExperimentRel in entity cache.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsExperimentRelCacheModel
	implements CacheModel<SegmentsExperimentRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SegmentsExperimentRelCacheModel)) {
			return false;
		}

		SegmentsExperimentRelCacheModel segmentsExperimentRelCacheModel =
			(SegmentsExperimentRelCacheModel)obj;

		if ((segmentsExperimentRelId ==
				segmentsExperimentRelCacheModel.segmentsExperimentRelId) &&
			(mvccVersion == segmentsExperimentRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, segmentsExperimentRelId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", segmentsExperimentRelId=");
		sb.append(segmentsExperimentRelId);
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
		sb.append(", segmentsExperimentId=");
		sb.append(segmentsExperimentId);
		sb.append(", segmentsExperienceId=");
		sb.append(segmentsExperienceId);
		sb.append(", split=");
		sb.append(split);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SegmentsExperimentRel toEntityModel() {
		SegmentsExperimentRelImpl segmentsExperimentRelImpl =
			new SegmentsExperimentRelImpl();

		segmentsExperimentRelImpl.setMvccVersion(mvccVersion);
		segmentsExperimentRelImpl.setSegmentsExperimentRelId(
			segmentsExperimentRelId);
		segmentsExperimentRelImpl.setGroupId(groupId);
		segmentsExperimentRelImpl.setCompanyId(companyId);
		segmentsExperimentRelImpl.setUserId(userId);

		if (userName == null) {
			segmentsExperimentRelImpl.setUserName("");
		}
		else {
			segmentsExperimentRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			segmentsExperimentRelImpl.setCreateDate(null);
		}
		else {
			segmentsExperimentRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			segmentsExperimentRelImpl.setModifiedDate(null);
		}
		else {
			segmentsExperimentRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		segmentsExperimentRelImpl.setSegmentsExperimentId(segmentsExperimentId);
		segmentsExperimentRelImpl.setSegmentsExperienceId(segmentsExperienceId);
		segmentsExperimentRelImpl.setSplit(split);

		segmentsExperimentRelImpl.resetOriginalValues();

		return segmentsExperimentRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		segmentsExperimentRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		segmentsExperimentId = objectInput.readLong();

		segmentsExperienceId = objectInput.readLong();

		split = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(segmentsExperimentRelId);

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

		objectOutput.writeLong(segmentsExperimentId);

		objectOutput.writeLong(segmentsExperienceId);

		objectOutput.writeDouble(split);
	}

	public long mvccVersion;
	public long segmentsExperimentRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long segmentsExperimentId;
	public long segmentsExperienceId;
	public double split;

}