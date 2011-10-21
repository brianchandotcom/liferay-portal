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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.journal.model.JournalFeed;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing JournalFeed in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeed
 * @generated
 */
public class JournalFeedCacheModel implements CacheModel<JournalFeed>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(47);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", id=");
		sb.append(id);
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
		sb.append(", feedId=");
		sb.append(feedId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", structureId=");
		sb.append(structureId);
		sb.append(", templateId=");
		sb.append(templateId);
		sb.append(", rendererTemplateId=");
		sb.append(rendererTemplateId);
		sb.append(", delta=");
		sb.append(delta);
		sb.append(", orderByCol=");
		sb.append(orderByCol);
		sb.append(", orderByType=");
		sb.append(orderByType);
		sb.append(", targetLayoutFriendlyUrl=");
		sb.append(targetLayoutFriendlyUrl);
		sb.append(", targetPortletId=");
		sb.append(targetPortletId);
		sb.append(", contentField=");
		sb.append(contentField);
		sb.append(", feedType=");
		sb.append(feedType);
		sb.append(", feedVersion=");
		sb.append(feedVersion);
		sb.append("}");

		return sb.toString();
	}

	public JournalFeed toEntityModel() {
		JournalFeedImpl journalFeedImpl = new JournalFeedImpl();

		if (uuid == null) {
			journalFeedImpl.setUuid(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setUuid(uuid);
		}

		journalFeedImpl.setId(id);
		journalFeedImpl.setGroupId(groupId);
		journalFeedImpl.setCompanyId(companyId);
		journalFeedImpl.setUserId(userId);

		if (userName == null) {
			journalFeedImpl.setUserName(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			journalFeedImpl.setCreateDate(null);
		}
		else {
			journalFeedImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			journalFeedImpl.setModifiedDate(null);
		}
		else {
			journalFeedImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (feedId == null) {
			journalFeedImpl.setFeedId(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setFeedId(feedId);
		}

		if (name == null) {
			journalFeedImpl.setName(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setName(name);
		}

		if (description == null) {
			journalFeedImpl.setDescription(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setDescription(description);
		}

		if (type == null) {
			journalFeedImpl.setType(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setType(type);
		}

		if (structureId == null) {
			journalFeedImpl.setStructureId(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setStructureId(structureId);
		}

		if (templateId == null) {
			journalFeedImpl.setTemplateId(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setTemplateId(templateId);
		}

		if (rendererTemplateId == null) {
			journalFeedImpl.setRendererTemplateId(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setRendererTemplateId(rendererTemplateId);
		}

		journalFeedImpl.setDelta(delta);

		if (orderByCol == null) {
			journalFeedImpl.setOrderByCol(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setOrderByCol(orderByCol);
		}

		if (orderByType == null) {
			journalFeedImpl.setOrderByType(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setOrderByType(orderByType);
		}

		if (targetLayoutFriendlyUrl == null) {
			journalFeedImpl.setTargetLayoutFriendlyUrl(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		}

		if (targetPortletId == null) {
			journalFeedImpl.setTargetPortletId(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setTargetPortletId(targetPortletId);
		}

		if (contentField == null) {
			journalFeedImpl.setContentField(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setContentField(contentField);
		}

		if (feedType == null) {
			journalFeedImpl.setFeedType(StringPool.BLANK);
		}
		else {
			journalFeedImpl.setFeedType(feedType);
		}

		journalFeedImpl.setFeedVersion(feedVersion);

		journalFeedImpl.resetOriginalValues();

		return journalFeedImpl;
	}

	public String uuid;
	public long id;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String feedId;
	public String name;
	public String description;
	public String type;
	public String structureId;
	public String templateId;
	public String rendererTemplateId;
	public int delta;
	public String orderByCol;
	public String orderByType;
	public String targetLayoutFriendlyUrl;
	public String targetPortletId;
	public String contentField;
	public String feedType;
	public double feedVersion;
}