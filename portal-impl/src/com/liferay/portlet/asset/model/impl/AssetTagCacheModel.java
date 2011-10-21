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

import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing AssetTag in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTag
 * @generated
 */
public class AssetTagCacheModel implements CacheModel<AssetTag>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{tagId=");
		sb.append(tagId);
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
		sb.append(", assetCount=");
		sb.append(assetCount);
		sb.append("}");

		return sb.toString();
	}

	public AssetTag toEntityModel() {
		AssetTagImpl assetTagImpl = new AssetTagImpl();

		assetTagImpl.setTagId(tagId);
		assetTagImpl.setGroupId(groupId);
		assetTagImpl.setCompanyId(companyId);
		assetTagImpl.setUserId(userId);

		if (userName == null) {
			assetTagImpl.setUserName(StringPool.BLANK);
		}
		else {
			assetTagImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetTagImpl.setCreateDate(null);
		}
		else {
			assetTagImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetTagImpl.setModifiedDate(null);
		}
		else {
			assetTagImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			assetTagImpl.setName(StringPool.BLANK);
		}
		else {
			assetTagImpl.setName(name);
		}

		assetTagImpl.setAssetCount(assetCount);

		assetTagImpl.resetOriginalValues();

		return assetTagImpl;
	}

	public long tagId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public int assetCount;
}