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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.asset.model.AssetEntry;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing AssetEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntry
 * @generated
 */
public class AssetEntryCacheModel implements CacheModel<AssetEntry>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(53);

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
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", classUuid=");
		sb.append(classUuid);
		sb.append(", classTypeId=");
		sb.append(classTypeId);
		sb.append(", visible=");
		sb.append(visible);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", publishDate=");
		sb.append(publishDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", mimeType=");
		sb.append(mimeType);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", summary=");
		sb.append(summary);
		sb.append(", url=");
		sb.append(url);
		sb.append(", layoutUuid=");
		sb.append(layoutUuid);
		sb.append(", height=");
		sb.append(height);
		sb.append(", width=");
		sb.append(width);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", viewCount=");
		sb.append(viewCount);
		sb.append("}");

		return sb.toString();
	}

	public AssetEntry toEntityModel() {
		AssetEntryImpl assetEntryImpl = new AssetEntryImpl();

		assetEntryImpl.setEntryId(entryId);
		assetEntryImpl.setGroupId(groupId);
		assetEntryImpl.setCompanyId(companyId);
		assetEntryImpl.setUserId(userId);

		if (userName == null) {
			assetEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			assetEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetEntryImpl.setCreateDate(null);
		}
		else {
			assetEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetEntryImpl.setModifiedDate(null);
		}
		else {
			assetEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		assetEntryImpl.setClassNameId(classNameId);
		assetEntryImpl.setClassPK(classPK);

		if (classUuid == null) {
			assetEntryImpl.setClassUuid(StringPool.BLANK);
		}
		else {
			assetEntryImpl.setClassUuid(classUuid);
		}

		assetEntryImpl.setClassTypeId(classTypeId);
		assetEntryImpl.setVisible(visible);

		if (startDate == Long.MIN_VALUE) {
			assetEntryImpl.setStartDate(null);
		}
		else {
			assetEntryImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			assetEntryImpl.setEndDate(null);
		}
		else {
			assetEntryImpl.setEndDate(new Date(endDate));
		}

		if (publishDate == Long.MIN_VALUE) {
			assetEntryImpl.setPublishDate(null);
		}
		else {
			assetEntryImpl.setPublishDate(new Date(publishDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			assetEntryImpl.setExpirationDate(null);
		}
		else {
			assetEntryImpl.setExpirationDate(new Date(expirationDate));
		}

		if (mimeType == null) {
			assetEntryImpl.setMimeType(StringPool.BLANK);
		}
		else {
			assetEntryImpl.setMimeType(mimeType);
		}

		if (title == null) {
			assetEntryImpl.setTitle(StringPool.BLANK);
		}
		else {
			assetEntryImpl.setTitle(title);
		}

		if (description == null) {
			assetEntryImpl.setDescription(StringPool.BLANK);
		}
		else {
			assetEntryImpl.setDescription(description);
		}

		if (summary == null) {
			assetEntryImpl.setSummary(StringPool.BLANK);
		}
		else {
			assetEntryImpl.setSummary(summary);
		}

		if (url == null) {
			assetEntryImpl.setUrl(StringPool.BLANK);
		}
		else {
			assetEntryImpl.setUrl(url);
		}

		if (layoutUuid == null) {
			assetEntryImpl.setLayoutUuid(StringPool.BLANK);
		}
		else {
			assetEntryImpl.setLayoutUuid(layoutUuid);
		}

		assetEntryImpl.setHeight(height);
		assetEntryImpl.setWidth(width);
		assetEntryImpl.setPriority(priority);
		assetEntryImpl.setViewCount(viewCount);

		assetEntryImpl.resetOriginalValues();

		return assetEntryImpl;
	}

	public long entryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String classUuid;
	public long classTypeId;
	public boolean visible;
	public long startDate;
	public long endDate;
	public long publishDate;
	public long expirationDate;
	public String mimeType;
	public String title;
	public String description;
	public String summary;
	public String url;
	public String layoutUuid;
	public int height;
	public int width;
	public double priority;
	public int viewCount;
}