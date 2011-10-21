/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.ratings.model.RatingsEntry;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing RatingsEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntry
 * @generated
 */
public class RatingsEntryCacheModel implements CacheModel<RatingsEntry>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{entryId=");
		sb.append(entryId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", score=");
		sb.append(score);
		sb.append("}");

		return sb.toString();
	}

	public RatingsEntry toEntityModel() {
		RatingsEntryImpl ratingsEntryImpl = new RatingsEntryImpl();

		ratingsEntryImpl.setEntryId(entryId);
		ratingsEntryImpl.setCompanyId(companyId);
		ratingsEntryImpl.setUserId(userId);

		if (userName == null) {
			ratingsEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			ratingsEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ratingsEntryImpl.setCreateDate(null);
		}
		else {
			ratingsEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ratingsEntryImpl.setModifiedDate(null);
		}
		else {
			ratingsEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		ratingsEntryImpl.setClassNameId(classNameId);
		ratingsEntryImpl.setClassPK(classPK);
		ratingsEntryImpl.setScore(score);

		ratingsEntryImpl.resetOriginalValues();

		return ratingsEntryImpl;
	}

	public long entryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public double score;
}