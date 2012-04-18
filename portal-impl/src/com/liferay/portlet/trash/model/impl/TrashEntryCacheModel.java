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
		StringBundler sb = new StringBundler(17);

		sb.append("{entryId=");
		sb.append(entryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", status=");
		sb.append(status);
		sb.append(", trashedDate=");
		sb.append(trashedDate);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append("}");

		return sb.toString();
	}

	public TrashEntry toEntityModel() {
		TrashEntryImpl trashEntryImpl = new TrashEntryImpl();

		trashEntryImpl.setEntryId(entryId);
		trashEntryImpl.setGroupId(groupId);
		trashEntryImpl.setCompanyId(companyId);
		trashEntryImpl.setClassNameId(classNameId);
		trashEntryImpl.setClassPK(classPK);
		trashEntryImpl.setStatus(status);

		if (trashedDate == Long.MIN_VALUE) {
			trashEntryImpl.setTrashedDate(null);
		}
		else {
			trashEntryImpl.setTrashedDate(new Date(trashedDate));
		}

		if (typeSettings == null) {
			trashEntryImpl.setTypeSettings(StringPool.BLANK);
		}
		else {
			trashEntryImpl.setTypeSettings(typeSettings);
		}

		trashEntryImpl.resetOriginalValues();

		return trashEntryImpl;
	}

	public long entryId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public int status;
	public long trashedDate;
	public String typeSettings;
}