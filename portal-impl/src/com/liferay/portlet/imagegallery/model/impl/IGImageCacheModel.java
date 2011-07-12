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

package com.liferay.portlet.imagegallery.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.imagegallery.model.IGImage;

import java.util.Date;

/**
 * The cache model class for representing IGImage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see IGImage
 * @generated
 */
public class IGImageCacheModel implements CacheModel<IGImage> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", imageId=");
		sb.append(imageId);
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
		sb.append(", folderId=");
		sb.append(folderId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", smallImageId=");
		sb.append(smallImageId);
		sb.append(", largeImageId=");
		sb.append(largeImageId);
		sb.append(", custom1ImageId=");
		sb.append(custom1ImageId);
		sb.append(", custom2ImageId=");
		sb.append(custom2ImageId);
		sb.append("}");

		return sb.toString();
	}

	public IGImage toEntityModel() {
		IGImageImpl igImageImpl = new IGImageImpl();

		if (uuid == null) {
			igImageImpl.setUuid(StringPool.BLANK);
		}
		else {
			igImageImpl.setUuid(uuid);
		}

		igImageImpl.setImageId(imageId);
		igImageImpl.setGroupId(groupId);
		igImageImpl.setCompanyId(companyId);
		igImageImpl.setUserId(userId);

		if (userName == null) {
			igImageImpl.setUserName(StringPool.BLANK);
		}
		else {
			igImageImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			igImageImpl.setCreateDate(null);
		}
		else {
			igImageImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			igImageImpl.setModifiedDate(null);
		}
		else {
			igImageImpl.setModifiedDate(new Date(modifiedDate));
		}

		igImageImpl.setFolderId(folderId);

		if (name == null) {
			igImageImpl.setName(StringPool.BLANK);
		}
		else {
			igImageImpl.setName(name);
		}

		if (description == null) {
			igImageImpl.setDescription(StringPool.BLANK);
		}
		else {
			igImageImpl.setDescription(description);
		}

		igImageImpl.setSmallImageId(smallImageId);
		igImageImpl.setLargeImageId(largeImageId);
		igImageImpl.setCustom1ImageId(custom1ImageId);
		igImageImpl.setCustom2ImageId(custom2ImageId);

		igImageImpl.resetOriginalValues();

		return igImageImpl;
	}

	public String uuid;
	public long imageId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long folderId;
	public String name;
	public String description;
	public long smallImageId;
	public long largeImageId;
	public long custom1ImageId;
	public long custom2ImageId;
}