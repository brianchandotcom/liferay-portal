/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.journal.model.JournalArticle;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing JournalArticle in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticle
 * @generated
 */
@ProviderType
public class JournalArticleCacheModel implements CacheModel<JournalArticle>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(67);

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
		sb.append(", folderId=");
		sb.append(folderId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", treePath=");
		sb.append(treePath);
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
		sb.append(", DDMStructureKey=");
		sb.append(DDMStructureKey);
		sb.append(", DDMTemplateKey=");
		sb.append(DDMTemplateKey);
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

	@Override
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

		journalArticleImpl.setFolderId(folderId);
		journalArticleImpl.setClassNameId(classNameId);
		journalArticleImpl.setClassPK(classPK);

		if (treePath == null) {
			journalArticleImpl.setTreePath(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setTreePath(treePath);
		}

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

		if (DDMStructureKey == null) {
			journalArticleImpl.setDDMStructureKey(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setDDMStructureKey(DDMStructureKey);
		}

		if (DDMTemplateKey == null) {
			journalArticleImpl.setDDMTemplateKey(StringPool.BLANK);
		}
		else {
			journalArticleImpl.setDDMTemplateKey(DDMTemplateKey);
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

		journalArticleImpl.setDefaultLanguageId(_defaultLanguageId);

		journalArticleImpl.setDocument(_document);

		return journalArticleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		uuid = objectInput.readUTF();
		id = objectInput.readLong();
		resourcePrimKey = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		folderId = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		treePath = objectInput.readUTF();
		articleId = objectInput.readUTF();
		version = objectInput.readDouble();
		title = objectInput.readUTF();
		urlTitle = objectInput.readUTF();
		description = objectInput.readUTF();
		content = objectInput.readUTF();
		DDMStructureKey = objectInput.readUTF();
		DDMTemplateKey = objectInput.readUTF();
		layoutUuid = objectInput.readUTF();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		reviewDate = objectInput.readLong();
		indexable = objectInput.readBoolean();
		smallImage = objectInput.readBoolean();
		smallImageId = objectInput.readLong();
		smallImageURL = objectInput.readUTF();
		status = objectInput.readInt();
		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();

		_defaultLanguageId = (java.lang.String)objectInput.readObject();
		_document = (com.liferay.portal.kernel.xml.Document)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(id);
		objectOutput.writeLong(resourcePrimKey);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(folderId);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);

		if (treePath == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(treePath);
		}

		if (articleId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(articleId);
		}

		objectOutput.writeDouble(version);

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (urlTitle == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(urlTitle);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (content == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(content);
		}

		if (DDMStructureKey == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(DDMStructureKey);
		}

		if (DDMTemplateKey == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(DDMTemplateKey);
		}

		if (layoutUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(layoutUuid);
		}

		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(reviewDate);
		objectOutput.writeBoolean(indexable);
		objectOutput.writeBoolean(smallImage);
		objectOutput.writeLong(smallImageId);

		if (smallImageURL == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(smallImageURL);
		}

		objectOutput.writeInt(status);
		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);

		objectOutput.writeObject(_defaultLanguageId);
		objectOutput.writeObject(_document);
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
	public long folderId;
	public long classNameId;
	public long classPK;
	public String treePath;
	public String articleId;
	public double version;
	public String title;
	public String urlTitle;
	public String description;
	public String content;
	public String DDMStructureKey;
	public String DDMTemplateKey;
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
	public java.lang.String _defaultLanguageId;
	public com.liferay.portal.kernel.xml.Document _document;
}