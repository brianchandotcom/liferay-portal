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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Repository;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing Repository in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Repository
 * @generated
 */
public class RepositoryCacheModel implements CacheModel<Repository>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{repositoryId=");
		sb.append(repositoryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", dlFolderId=");
		sb.append(dlFolderId);
		sb.append("}");

		return sb.toString();
	}

	public Repository toEntityModel() {
		RepositoryImpl repositoryImpl = new RepositoryImpl();

		repositoryImpl.setRepositoryId(repositoryId);
		repositoryImpl.setGroupId(groupId);
		repositoryImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			repositoryImpl.setCreateDate(null);
		}
		else {
			repositoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			repositoryImpl.setModifiedDate(null);
		}
		else {
			repositoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		repositoryImpl.setClassNameId(classNameId);

		if (name == null) {
			repositoryImpl.setName(StringPool.BLANK);
		}
		else {
			repositoryImpl.setName(name);
		}

		if (description == null) {
			repositoryImpl.setDescription(StringPool.BLANK);
		}
		else {
			repositoryImpl.setDescription(description);
		}

		if (portletId == null) {
			repositoryImpl.setPortletId(StringPool.BLANK);
		}
		else {
			repositoryImpl.setPortletId(portletId);
		}

		if (typeSettings == null) {
			repositoryImpl.setTypeSettings(StringPool.BLANK);
		}
		else {
			repositoryImpl.setTypeSettings(typeSettings);
		}

		repositoryImpl.setDlFolderId(dlFolderId);

		repositoryImpl.resetOriginalValues();

		return repositoryImpl;
	}

	public long repositoryId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public String name;
	public String description;
	public String portletId;
	public String typeSettings;
	public long dlFolderId;
}