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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.dynamicdatamapping.model.DDMContent;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing DDMContent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDMContent
 * @generated
 */
public class DDMContentCacheModel implements CacheModel<DDMContent>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", contentId=");
		sb.append(contentId);
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
		sb.append(", description=");
		sb.append(description);
		sb.append(", xml=");
		sb.append(xml);
		sb.append("}");

		return sb.toString();
	}

	public DDMContent toEntityModel() {
		DDMContentImpl ddmContentImpl = new DDMContentImpl();

		if (uuid == null) {
			ddmContentImpl.setUuid(StringPool.BLANK);
		}
		else {
			ddmContentImpl.setUuid(uuid);
		}

		ddmContentImpl.setContentId(contentId);
		ddmContentImpl.setGroupId(groupId);
		ddmContentImpl.setCompanyId(companyId);
		ddmContentImpl.setUserId(userId);

		if (userName == null) {
			ddmContentImpl.setUserName(StringPool.BLANK);
		}
		else {
			ddmContentImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmContentImpl.setCreateDate(null);
		}
		else {
			ddmContentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddmContentImpl.setModifiedDate(null);
		}
		else {
			ddmContentImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			ddmContentImpl.setName(StringPool.BLANK);
		}
		else {
			ddmContentImpl.setName(name);
		}

		if (description == null) {
			ddmContentImpl.setDescription(StringPool.BLANK);
		}
		else {
			ddmContentImpl.setDescription(description);
		}

		if (xml == null) {
			ddmContentImpl.setXml(StringPool.BLANK);
		}
		else {
			ddmContentImpl.setXml(xml);
		}

		ddmContentImpl.resetOriginalValues();

		return ddmContentImpl;
	}

	public String uuid;
	public long contentId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String xml;
}