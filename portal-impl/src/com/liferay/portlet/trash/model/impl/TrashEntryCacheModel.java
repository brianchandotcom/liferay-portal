/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.trash.model.TrashEntry;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing TrashEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntry
 * @generated
 */
public class TrashEntryCacheModel implements CacheModel<TrashEntry>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{entryId=");
		sb.append(entryId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	public TrashEntry toEntityModel() {
		TrashEntryImpl trashEntryImpl = new TrashEntryImpl();

		trashEntryImpl.setEntryId(entryId);
		trashEntryImpl.setGroupId(groupId);
		trashEntryImpl.setCompanyId(companyId);
		trashEntryImpl.setUserId(userId);

		if (userName == null) {
			trashEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			trashEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			trashEntryImpl.setCreateDate(null);
		}
		else {
			trashEntryImpl.setCreateDate(new Date(createDate));
		}

		trashEntryImpl.setClassNameId(classNameId);
		trashEntryImpl.setClassPK(classPK);

		if (typeSettings == null) {
			trashEntryImpl.setTypeSettings(StringPool.BLANK);
		}
		else {
			trashEntryImpl.setTypeSettings(typeSettings);
		}

		trashEntryImpl.setStatus(status);

		trashEntryImpl.resetOriginalValues();

		return trashEntryImpl;
	}

	public long entryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long classNameId;
	public long classPK;
	public String typeSettings;
	public int status;
}