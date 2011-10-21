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

package com.liferay.portlet.wiki.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.wiki.model.WikiNode;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing WikiNode in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see WikiNode
 * @generated
 */
public class WikiNodeCacheModel implements CacheModel<WikiNode>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", nodeId=");
		sb.append(nodeId);
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
		sb.append(", lastPostDate=");
		sb.append(lastPostDate);
		sb.append("}");

		return sb.toString();
	}

	public WikiNode toEntityModel() {
		WikiNodeImpl wikiNodeImpl = new WikiNodeImpl();

		if (uuid == null) {
			wikiNodeImpl.setUuid(StringPool.BLANK);
		}
		else {
			wikiNodeImpl.setUuid(uuid);
		}

		wikiNodeImpl.setNodeId(nodeId);
		wikiNodeImpl.setGroupId(groupId);
		wikiNodeImpl.setCompanyId(companyId);
		wikiNodeImpl.setUserId(userId);

		if (userName == null) {
			wikiNodeImpl.setUserName(StringPool.BLANK);
		}
		else {
			wikiNodeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			wikiNodeImpl.setCreateDate(null);
		}
		else {
			wikiNodeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			wikiNodeImpl.setModifiedDate(null);
		}
		else {
			wikiNodeImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			wikiNodeImpl.setName(StringPool.BLANK);
		}
		else {
			wikiNodeImpl.setName(name);
		}

		if (description == null) {
			wikiNodeImpl.setDescription(StringPool.BLANK);
		}
		else {
			wikiNodeImpl.setDescription(description);
		}

		if (lastPostDate == Long.MIN_VALUE) {
			wikiNodeImpl.setLastPostDate(null);
		}
		else {
			wikiNodeImpl.setLastPostDate(new Date(lastPostDate));
		}

		wikiNodeImpl.resetOriginalValues();

		return wikiNodeImpl;
	}

	public String uuid;
	public long nodeId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public long lastPostDate;
}