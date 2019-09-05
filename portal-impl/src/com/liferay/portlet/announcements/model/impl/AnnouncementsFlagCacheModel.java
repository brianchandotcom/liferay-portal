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

package com.liferay.portlet.announcements.model.impl;

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AnnouncementsFlag in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnnouncementsFlagCacheModel
	implements CacheModel<AnnouncementsFlag>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AnnouncementsFlagCacheModel)) {
			return false;
		}

		AnnouncementsFlagCacheModel announcementsFlagCacheModel =
			(AnnouncementsFlagCacheModel)obj;

		if (flagId == announcementsFlagCacheModel.flagId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, flagId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{flagId=");
		sb.append(flagId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", entryId=");
		sb.append(entryId);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AnnouncementsFlag toEntityModel() {
		AnnouncementsFlagImpl announcementsFlagImpl =
			new AnnouncementsFlagImpl();

		announcementsFlagImpl.setFlagId(flagId);
		announcementsFlagImpl.setCompanyId(companyId);
		announcementsFlagImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			announcementsFlagImpl.setCreateDate(null);
		}
		else {
			announcementsFlagImpl.setCreateDate(new Date(createDate));
		}

		announcementsFlagImpl.setEntryId(entryId);
		announcementsFlagImpl.setValue(value);

		announcementsFlagImpl.resetOriginalValues();

		return announcementsFlagImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		flagId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();

		entryId = objectInput.readLong();

		value = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(flagId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);

		objectOutput.writeLong(entryId);

		objectOutput.writeInt(value);
	}

	public long flagId;
	public long companyId;
	public long userId;
	public long createDate;
	public long entryId;
	public int value;

}