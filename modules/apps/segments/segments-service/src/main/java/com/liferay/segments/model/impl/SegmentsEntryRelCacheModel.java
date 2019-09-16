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
import com.liferay.segments.model.SegmentsEntryRel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SegmentsEntryRel in entity cache.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsEntryRelCacheModel
	implements CacheModel<SegmentsEntryRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SegmentsEntryRelCacheModel)) {
			return false;
		}

		SegmentsEntryRelCacheModel segmentsEntryRelCacheModel =
			(SegmentsEntryRelCacheModel)obj;

		if ((segmentsEntryRelId ==
				segmentsEntryRelCacheModel.segmentsEntryRelId) &&
			(mvccVersion == segmentsEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, segmentsEntryRelId);

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
		sb.append(", segmentsEntryRelId=");
		sb.append(segmentsEntryRelId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SegmentsEntryRel toEntityModel() {
		SegmentsEntryRelImpl segmentsEntryRelImpl = new SegmentsEntryRelImpl();

		segmentsEntryRelImpl.setMvccVersion(mvccVersion);
		segmentsEntryRelImpl.setSegmentsEntryRelId(segmentsEntryRelId);
		segmentsEntryRelImpl.setGroupId(groupId);
		segmentsEntryRelImpl.setCompanyId(companyId);
		segmentsEntryRelImpl.setUserId(userId);

		if (userName == null) {
			segmentsEntryRelImpl.setUserName("");
		}
		else {
			segmentsEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			segmentsEntryRelImpl.setCreateDate(null);
		}
		else {
			segmentsEntryRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			segmentsEntryRelImpl.setModifiedDate(null);
		}
		else {
			segmentsEntryRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		segmentsEntryRelImpl.setSegmentsEntryId(segmentsEntryId);
		segmentsEntryRelImpl.setClassNameId(classNameId);
		segmentsEntryRelImpl.setClassPK(classPK);

		segmentsEntryRelImpl.resetOriginalValues();

		return segmentsEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		segmentsEntryRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		segmentsEntryId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(segmentsEntryRelId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
	}

	public long mvccVersion;
	public long segmentsEntryRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long segmentsEntryId;
	public long classNameId;
	public long classPK;

}