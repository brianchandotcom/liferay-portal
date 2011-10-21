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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing DLFileShortcut in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcut
 * @generated
 */
public class DLFileShortcutCacheModel implements CacheModel<DLFileShortcut>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", fileShortcutId=");
		sb.append(fileShortcutId);
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
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", folderId=");
		sb.append(folderId);
		sb.append(", toFileEntryId=");
		sb.append(toFileEntryId);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	public DLFileShortcut toEntityModel() {
		DLFileShortcutImpl dlFileShortcutImpl = new DLFileShortcutImpl();

		if (uuid == null) {
			dlFileShortcutImpl.setUuid(StringPool.BLANK);
		}
		else {
			dlFileShortcutImpl.setUuid(uuid);
		}

		dlFileShortcutImpl.setFileShortcutId(fileShortcutId);
		dlFileShortcutImpl.setGroupId(groupId);
		dlFileShortcutImpl.setCompanyId(companyId);
		dlFileShortcutImpl.setUserId(userId);

		if (userName == null) {
			dlFileShortcutImpl.setUserName(StringPool.BLANK);
		}
		else {
			dlFileShortcutImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			dlFileShortcutImpl.setCreateDate(null);
		}
		else {
			dlFileShortcutImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dlFileShortcutImpl.setModifiedDate(null);
		}
		else {
			dlFileShortcutImpl.setModifiedDate(new Date(modifiedDate));
		}

		dlFileShortcutImpl.setRepositoryId(repositoryId);
		dlFileShortcutImpl.setFolderId(folderId);
		dlFileShortcutImpl.setToFileEntryId(toFileEntryId);
		dlFileShortcutImpl.setStatus(status);
		dlFileShortcutImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			dlFileShortcutImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			dlFileShortcutImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			dlFileShortcutImpl.setStatusDate(null);
		}
		else {
			dlFileShortcutImpl.setStatusDate(new Date(statusDate));
		}

		dlFileShortcutImpl.resetOriginalValues();

		return dlFileShortcutImpl;
	}

	public String uuid;
	public long fileShortcutId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long repositoryId;
	public long folderId;
	public long toFileEntryId;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}