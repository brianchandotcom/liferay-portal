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
import com.liferay.portal.model.PortletItem;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing PortletItem in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PortletItem
 * @generated
 */
public class PortletItemCacheModel implements CacheModel<PortletItem>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{portletItemId=");
		sb.append(portletItemId);
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
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append("}");

		return sb.toString();
	}

	public PortletItem toEntityModel() {
		PortletItemImpl portletItemImpl = new PortletItemImpl();

		portletItemImpl.setPortletItemId(portletItemId);
		portletItemImpl.setGroupId(groupId);
		portletItemImpl.setCompanyId(companyId);
		portletItemImpl.setUserId(userId);

		if (userName == null) {
			portletItemImpl.setUserName(StringPool.BLANK);
		}
		else {
			portletItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			portletItemImpl.setCreateDate(null);
		}
		else {
			portletItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			portletItemImpl.setModifiedDate(null);
		}
		else {
			portletItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			portletItemImpl.setName(StringPool.BLANK);
		}
		else {
			portletItemImpl.setName(name);
		}

		if (portletId == null) {
			portletItemImpl.setPortletId(StringPool.BLANK);
		}
		else {
			portletItemImpl.setPortletId(portletId);
		}

		portletItemImpl.setClassNameId(classNameId);

		portletItemImpl.resetOriginalValues();

		return portletItemImpl;
	}

	public long portletItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String portletId;
	public long classNameId;
}