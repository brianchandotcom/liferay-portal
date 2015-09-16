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

package com.liferay.portlet.blogs.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.blogs.model.BlogsStatsUser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing BlogsStatsUser in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUser
 * @generated
 */
@ProviderType
public class BlogsStatsUserCacheModel implements CacheModel<BlogsStatsUser>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BlogsStatsUserCacheModel)) {
			return false;
		}

		BlogsStatsUserCacheModel blogsStatsUserCacheModel = (BlogsStatsUserCacheModel)obj;

		if (statsUserId == blogsStatsUserCacheModel.statsUserId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, statsUserId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{statsUserId=");
		sb.append(statsUserId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", entryCount=");
		sb.append(entryCount);
		sb.append(", lastPostDate=");
		sb.append(lastPostDate);
		sb.append(", ratingsTotalEntries=");
		sb.append(ratingsTotalEntries);
		sb.append(", ratingsTotalScore=");
		sb.append(ratingsTotalScore);
		sb.append(", ratingsAverageScore=");
		sb.append(ratingsAverageScore);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BlogsStatsUser toEntityModel() {
		BlogsStatsUserImpl blogsStatsUserImpl = new BlogsStatsUserImpl();

		blogsStatsUserImpl.setStatsUserId(statsUserId);
		blogsStatsUserImpl.setGroupId(groupId);
		blogsStatsUserImpl.setUserId(userId);
		blogsStatsUserImpl.setEntryCount(entryCount);

		if (lastPostDate == Long.MIN_VALUE) {
			blogsStatsUserImpl.setLastPostDate(null);
		}
		else {
			blogsStatsUserImpl.setLastPostDate(new Date(lastPostDate));
		}

		blogsStatsUserImpl.setRatingsTotalEntries(ratingsTotalEntries);
		blogsStatsUserImpl.setRatingsTotalScore(ratingsTotalScore);
		blogsStatsUserImpl.setRatingsAverageScore(ratingsAverageScore);
		blogsStatsUserImpl.setCompanyId(companyId);

		blogsStatsUserImpl.resetOriginalValues();

		return blogsStatsUserImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		statsUserId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		entryCount = objectInput.readInt();
		lastPostDate = objectInput.readLong();
		ratingsTotalEntries = objectInput.readInt();
		ratingsTotalScore = objectInput.readDouble();
		ratingsAverageScore = objectInput.readDouble();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(statsUserId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(userId);
		objectOutput.writeInt(entryCount);
		objectOutput.writeLong(lastPostDate);
		objectOutput.writeInt(ratingsTotalEntries);
		objectOutput.writeDouble(ratingsTotalScore);
		objectOutput.writeDouble(ratingsAverageScore);
		objectOutput.writeLong(companyId);
	}

	public long statsUserId;
	public long groupId;
	public long userId;
	public int entryCount;
	public long lastPostDate;
	public int ratingsTotalEntries;
	public double ratingsTotalScore;
	public double ratingsAverageScore;
	public long companyId;
}