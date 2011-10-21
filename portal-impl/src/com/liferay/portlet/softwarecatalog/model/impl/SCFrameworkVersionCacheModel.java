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

package com.liferay.portlet.softwarecatalog.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing SCFrameworkVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersion
 * @generated
 */
public class SCFrameworkVersionCacheModel implements CacheModel<SCFrameworkVersion>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{frameworkVersionId=");
		sb.append(frameworkVersionId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", url=");
		sb.append(url);
		sb.append(", active=");
		sb.append(active);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	public SCFrameworkVersion toEntityModel() {
		SCFrameworkVersionImpl scFrameworkVersionImpl = new SCFrameworkVersionImpl();

		scFrameworkVersionImpl.setFrameworkVersionId(frameworkVersionId);
		scFrameworkVersionImpl.setGroupId(groupId);
		scFrameworkVersionImpl.setCompanyId(companyId);
		scFrameworkVersionImpl.setUserId(userId);

		if (userName == null) {
			scFrameworkVersionImpl.setUserName(StringPool.BLANK);
		}
		else {
			scFrameworkVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			scFrameworkVersionImpl.setCreateDate(null);
		}
		else {
			scFrameworkVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			scFrameworkVersionImpl.setModifiedDate(null);
		}
		else {
			scFrameworkVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			scFrameworkVersionImpl.setName(StringPool.BLANK);
		}
		else {
			scFrameworkVersionImpl.setName(name);
		}

		if (url == null) {
			scFrameworkVersionImpl.setUrl(StringPool.BLANK);
		}
		else {
			scFrameworkVersionImpl.setUrl(url);
		}

		scFrameworkVersionImpl.setActive(active);
		scFrameworkVersionImpl.setPriority(priority);

		scFrameworkVersionImpl.resetOriginalValues();

		return scFrameworkVersionImpl;
	}

	public long frameworkVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String url;
	public boolean active;
	public int priority;
}