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

import com.liferay.portlet.documentlibrary.model.DLSync;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing DLSync in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLSync
 * @generated
 */
public class DLSyncCacheModel implements CacheModel<DLSync>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{syncId=");
		sb.append(syncId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", fileId=");
		sb.append(fileId);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", parentFolderId=");
		sb.append(parentFolderId);
		sb.append(", event=");
		sb.append(event);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	public DLSync toEntityModel() {
		DLSyncImpl dlSyncImpl = new DLSyncImpl();

		dlSyncImpl.setSyncId(syncId);
		dlSyncImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			dlSyncImpl.setCreateDate(null);
		}
		else {
			dlSyncImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dlSyncImpl.setModifiedDate(null);
		}
		else {
			dlSyncImpl.setModifiedDate(new Date(modifiedDate));
		}

		dlSyncImpl.setFileId(fileId);
		dlSyncImpl.setRepositoryId(repositoryId);
		dlSyncImpl.setParentFolderId(parentFolderId);

		if (event == null) {
			dlSyncImpl.setEvent(StringPool.BLANK);
		}
		else {
			dlSyncImpl.setEvent(event);
		}

		if (type == null) {
			dlSyncImpl.setType(StringPool.BLANK);
		}
		else {
			dlSyncImpl.setType(type);
		}

		dlSyncImpl.resetOriginalValues();

		return dlSyncImpl;
	}

	public long syncId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long fileId;
	public long repositoryId;
	public long parentFolderId;
	public String event;
	public String type;
}