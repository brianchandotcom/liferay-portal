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

import com.liferay.portlet.wiki.model.WikiPage;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing WikiPage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPage
 * @generated
 */
public class WikiPageCacheModel implements CacheModel<WikiPage>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(47);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", pageId=");
		sb.append(pageId);
		sb.append(", resourcePrimKey=");
		sb.append(resourcePrimKey);
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
		sb.append(", nodeId=");
		sb.append(nodeId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", version=");
		sb.append(version);
		sb.append(", minorEdit=");
		sb.append(minorEdit);
		sb.append(", content=");
		sb.append(content);
		sb.append(", summary=");
		sb.append(summary);
		sb.append(", format=");
		sb.append(format);
		sb.append(", head=");
		sb.append(head);
		sb.append(", parentTitle=");
		sb.append(parentTitle);
		sb.append(", redirectTitle=");
		sb.append(redirectTitle);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	public WikiPage toEntityModel() {
		WikiPageImpl wikiPageImpl = new WikiPageImpl();

		if (uuid == null) {
			wikiPageImpl.setUuid(StringPool.BLANK);
		}
		else {
			wikiPageImpl.setUuid(uuid);
		}

		wikiPageImpl.setPageId(pageId);
		wikiPageImpl.setResourcePrimKey(resourcePrimKey);
		wikiPageImpl.setGroupId(groupId);
		wikiPageImpl.setCompanyId(companyId);
		wikiPageImpl.setUserId(userId);

		if (userName == null) {
			wikiPageImpl.setUserName(StringPool.BLANK);
		}
		else {
			wikiPageImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			wikiPageImpl.setCreateDate(null);
		}
		else {
			wikiPageImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			wikiPageImpl.setModifiedDate(null);
		}
		else {
			wikiPageImpl.setModifiedDate(new Date(modifiedDate));
		}

		wikiPageImpl.setNodeId(nodeId);

		if (title == null) {
			wikiPageImpl.setTitle(StringPool.BLANK);
		}
		else {
			wikiPageImpl.setTitle(title);
		}

		wikiPageImpl.setVersion(version);
		wikiPageImpl.setMinorEdit(minorEdit);

		if (content == null) {
			wikiPageImpl.setContent(StringPool.BLANK);
		}
		else {
			wikiPageImpl.setContent(content);
		}

		if (summary == null) {
			wikiPageImpl.setSummary(StringPool.BLANK);
		}
		else {
			wikiPageImpl.setSummary(summary);
		}

		if (format == null) {
			wikiPageImpl.setFormat(StringPool.BLANK);
		}
		else {
			wikiPageImpl.setFormat(format);
		}

		wikiPageImpl.setHead(head);

		if (parentTitle == null) {
			wikiPageImpl.setParentTitle(StringPool.BLANK);
		}
		else {
			wikiPageImpl.setParentTitle(parentTitle);
		}

		if (redirectTitle == null) {
			wikiPageImpl.setRedirectTitle(StringPool.BLANK);
		}
		else {
			wikiPageImpl.setRedirectTitle(redirectTitle);
		}

		wikiPageImpl.setStatus(status);
		wikiPageImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			wikiPageImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			wikiPageImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			wikiPageImpl.setStatusDate(null);
		}
		else {
			wikiPageImpl.setStatusDate(new Date(statusDate));
		}

		wikiPageImpl.resetOriginalValues();

		return wikiPageImpl;
	}

	public String uuid;
	public long pageId;
	public long resourcePrimKey;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long nodeId;
	public String title;
	public double version;
	public boolean minorEdit;
	public String content;
	public String summary;
	public String format;
	public boolean head;
	public String parentTitle;
	public String redirectTitle;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}