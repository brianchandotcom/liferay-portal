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

package com.liferay.message.boards.model.impl;

import com.liferay.message.boards.model.MBStatsUser;
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
 * The cache model class for representing MBStatsUser in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBStatsUserCacheModel
	implements CacheModel<MBStatsUser>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MBStatsUserCacheModel)) {
			return false;
		}

		MBStatsUserCacheModel mbStatsUserCacheModel =
			(MBStatsUserCacheModel)object;

		if ((statsUserId == mbStatsUserCacheModel.statsUserId) &&
			(mvccVersion == mbStatsUserCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, statsUserId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", statsUserId=");
		sb.append(statsUserId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", messageCount=");
		sb.append(messageCount);
		sb.append(", lastPostDate=");
		sb.append(lastPostDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MBStatsUser toEntityModel() {
		MBStatsUserImpl mbStatsUserImpl = new MBStatsUserImpl();

		mbStatsUserImpl.setMvccVersion(mvccVersion);
		mbStatsUserImpl.setCtCollectionId(ctCollectionId);
		mbStatsUserImpl.setStatsUserId(statsUserId);
		mbStatsUserImpl.setGroupId(groupId);
		mbStatsUserImpl.setCompanyId(companyId);
		mbStatsUserImpl.setUserId(userId);
		mbStatsUserImpl.setMessageCount(messageCount);

		if (lastPostDate == Long.MIN_VALUE) {
			mbStatsUserImpl.setLastPostDate(null);
		}
		else {
			mbStatsUserImpl.setLastPostDate(new Date(lastPostDate));
		}

		mbStatsUserImpl.resetOriginalValues();

		return mbStatsUserImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		statsUserId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		messageCount = objectInput.readInt();
		lastPostDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(statsUserId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeInt(messageCount);
		objectOutput.writeLong(lastPostDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long statsUserId;
	public long groupId;
	public long companyId;
	public long userId;
	public int messageCount;
	public long lastPostDate;

}