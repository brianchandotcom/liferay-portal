/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.segments.model.SegmentsEntryRole;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SegmentsEntryRole in entity cache.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsEntryRoleCacheModel
	implements CacheModel<SegmentsEntryRole>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SegmentsEntryRoleCacheModel)) {
			return false;
		}

		SegmentsEntryRoleCacheModel segmentsEntryRoleCacheModel =
			(SegmentsEntryRoleCacheModel)object;

		if ((segmentsEntryRoleId ==
				segmentsEntryRoleCacheModel.segmentsEntryRoleId) &&
			(mvccVersion == segmentsEntryRoleCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, segmentsEntryRoleId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", segmentsEntryRoleId=");
		sb.append(segmentsEntryRoleId);
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
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SegmentsEntryRole toEntityModel() {
		SegmentsEntryRoleImpl segmentsEntryRoleImpl =
			new SegmentsEntryRoleImpl();

		segmentsEntryRoleImpl.setMvccVersion(mvccVersion);
		segmentsEntryRoleImpl.setCtCollectionId(ctCollectionId);
		segmentsEntryRoleImpl.setSegmentsEntryRoleId(segmentsEntryRoleId);
		segmentsEntryRoleImpl.setCompanyId(companyId);
		segmentsEntryRoleImpl.setUserId(userId);

		if (userName == null) {
			segmentsEntryRoleImpl.setUserName("");
		}
		else {
			segmentsEntryRoleImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			segmentsEntryRoleImpl.setCreateDate(null);
		}
		else {
			segmentsEntryRoleImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			segmentsEntryRoleImpl.setModifiedDate(null);
		}
		else {
			segmentsEntryRoleImpl.setModifiedDate(new Date(modifiedDate));
		}

		segmentsEntryRoleImpl.setSegmentsEntryId(segmentsEntryId);
		segmentsEntryRoleImpl.setRoleId(roleId);

		segmentsEntryRoleImpl.resetOriginalValues();

		return segmentsEntryRoleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		segmentsEntryRoleId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		segmentsEntryId = objectInput.readLong();

		roleId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(segmentsEntryRoleId);

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

		objectOutput.writeLong(roleId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long segmentsEntryRoleId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long segmentsEntryId;
	public long roleId;

}