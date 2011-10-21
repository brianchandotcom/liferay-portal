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
import com.liferay.portal.model.Website;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing Website in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Website
 * @generated
 */
public class WebsiteCacheModel implements CacheModel<Website>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{websiteId=");
		sb.append(websiteId);
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
		sb.append(", url=");
		sb.append(url);
		sb.append(", typeId=");
		sb.append(typeId);
		sb.append(", primary=");
		sb.append(primary);
		sb.append("}");

		return sb.toString();
	}

	public Website toEntityModel() {
		WebsiteImpl websiteImpl = new WebsiteImpl();

		websiteImpl.setWebsiteId(websiteId);
		websiteImpl.setCompanyId(companyId);
		websiteImpl.setUserId(userId);

		if (userName == null) {
			websiteImpl.setUserName(StringPool.BLANK);
		}
		else {
			websiteImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			websiteImpl.setCreateDate(null);
		}
		else {
			websiteImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			websiteImpl.setModifiedDate(null);
		}
		else {
			websiteImpl.setModifiedDate(new Date(modifiedDate));
		}

		websiteImpl.setClassNameId(classNameId);
		websiteImpl.setClassPK(classPK);

		if (url == null) {
			websiteImpl.setUrl(StringPool.BLANK);
		}
		else {
			websiteImpl.setUrl(url);
		}

		websiteImpl.setTypeId(typeId);
		websiteImpl.setPrimary(primary);

		websiteImpl.resetOriginalValues();

		return websiteImpl;
	}

	public long websiteId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String url;
	public int typeId;
	public boolean primary;
}