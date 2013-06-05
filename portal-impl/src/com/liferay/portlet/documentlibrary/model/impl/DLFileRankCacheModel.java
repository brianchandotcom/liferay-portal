/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.documentlibrary.model.DLFileRank;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DLFileRank in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileRank
 * @generated
 */
public class DLFileRankCacheModel implements CacheModel<DLFileRank>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{fileRankId=");
		sb.append(fileRankId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLFileRank toEntityModel() {
		DLFileRankImpl dlFileRankImpl = new DLFileRankImpl();

		dlFileRankImpl.setFileRankId(fileRankId);
		dlFileRankImpl.setGroupId(groupId);
		dlFileRankImpl.setCompanyId(companyId);
		dlFileRankImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			dlFileRankImpl.setCreateDate(null);
		}
		else {
			dlFileRankImpl.setCreateDate(new Date(createDate));
		}

		dlFileRankImpl.setFileEntryId(fileEntryId);
		dlFileRankImpl.setActive(active);

		dlFileRankImpl.resetOriginalValues();

		return dlFileRankImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		fileRankId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		fileEntryId = objectInput.readLong();
		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(fileRankId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(fileEntryId);
		objectOutput.writeBoolean(active);
	}

	public long fileRankId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long fileEntryId;
	public boolean active;
}