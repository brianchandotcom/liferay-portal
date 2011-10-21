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

import com.liferay.portlet.journal.model.JournalArticle;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing JournalArticle in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticle
 * @generated
 */
public class JournalArticleCacheModel implements CacheModel<JournalArticle>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(65);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", id=");
		sb.append(id);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", articleId=");
		sb.append(articleId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", title=");
		sb.append(title);
		sb.append(", urlTitle=");
		sb.append(urlTitle);
		sb.append(", description=");
		sb.append(description);
		sb.append(", content=");
		sb.append(content);
		sb.append(", type=");
		sb.append(type);
		sb.append(", structureId=");
		sb.append(structureId);
		sb.append(", templateId=");
		sb.append(templateId);
		sb.append(", layoutUuid=");
		sb.append(layoutUuid);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", reviewDate=");
		sb.append(reviewDate);
		sb.append(", indexable=");
		sb.append(indexable);
		sb.append(", smallImage=");
		sb.append(smallImage);
		sb.append(", smallImageId=");
		sb.append(smallImageId);
		sb.append(", smallImageURL=");
		sb.append(smallImageURL);
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

	public JournalArticle toEntityModel() {
		JournalArticleImpl journalArticleImpl = new JournalArticleImpl();

		if (uuid == null) {
			journalArticleImpl.setUuid(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setUuid(uuid);
		}

		journalArticleImpl.setId(id);
		journalArticleImpl.setResourcePrimKey(resourcePrimKey);
		journalArticleImpl.setGroupId(groupId);
		journalArticleImpl.setCompanyId(companyId);
		journalArticleImpl.setUserId(userId);

		if (userName == null) {
			journalArticleImpl.setUserName(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			journalArticleImpl.setCreateDate(null);
		}
		else {
			journalArticleImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			journalArticleImpl.setModifiedDate(null);
		}
		else {
			journalArticleImpl.setModifiedDate(new Date(modifiedDate));
		}

		journalArticleImpl.setClassNameId(classNameId);
		journalArticleImpl.setClassPK(classPK);

		if (articleId == null) {
			journalArticleImpl.setArticleId(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setArticleId(articleId);
		}

		journalArticleImpl.setVersion(version);

		if (title == null) {
			journalArticleImpl.setTitle(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setTitle(title);
		}

		if (urlTitle == null) {
			journalArticleImpl.setUrlTitle(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setUrlTitle(urlTitle);
		}

		if (description == null) {
			journalArticleImpl.setDescription(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setDescription(description);
		}

		if (content == null) {
			journalArticleImpl.setContent(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setContent(content);
		}

		if (type == null) {
			journalArticleImpl.setType(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setType(type);
		}

		if (structureId == null) {
			journalArticleImpl.setStructureId(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setStructureId(structureId);
		}

		if (templateId == null) {
			journalArticleImpl.setTemplateId(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setTemplateId(templateId);
		}

		if (layoutUuid == null) {
			journalArticleImpl.setLayoutUuid(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setLayoutUuid(layoutUuid);
		}

		if (displayDate == Long.MIN_VALUE) {
			journalArticleImpl.setDisplayDate(null);
		}
		else {
			journalArticleImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			journalArticleImpl.setExpirationDate(null);
		}
		else {
			journalArticleImpl.setExpirationDate(new Date(expirationDate));
		}

		if (reviewDate == Long.MIN_VALUE) {
			journalArticleImpl.setReviewDate(null);
		}
		else {
			journalArticleImpl.setReviewDate(new Date(reviewDate));
		}

		journalArticleImpl.setIndexable(indexable);
		journalArticleImpl.setSmallImage(smallImage);
		journalArticleImpl.setSmallImageId(smallImageId);

		if (smallImageURL == null) {
			journalArticleImpl.setSmallImageURL(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setSmallImageURL(smallImageURL);
		}

		journalArticleImpl.setStatus(status);
		journalArticleImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			journalArticleImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			journalArticleImpl.setStatusDate(null);
		}
		else {
			journalArticleImpl.setStatusDate(new Date(statusDate));
		}

		journalArticleImpl.resetOriginalValues();

		return journalArticleImpl;
	}

	public String uuid;
	public long id;
	public long resourcePrimKey;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String articleId;
	public double version;
	public String title;
	public String urlTitle;
	public String description;
	public String content;
	public String type;
	public String structureId;
	public String templateId;
	public String layoutUuid;
	public long displayDate;
	public long expirationDate;
	public long reviewDate;
	public boolean indexable;
	public boolean smallImage;
	public long smallImageId;
	public String smallImageURL;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}