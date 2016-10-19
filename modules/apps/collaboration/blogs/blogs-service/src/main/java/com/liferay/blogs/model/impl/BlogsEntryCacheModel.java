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

package com.liferay.blogs.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.blogs.model.BlogsEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing BlogsEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntry
 * @generated
 */
@ProviderType
public class BlogsEntryCacheModel implements CacheModel<BlogsEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BlogsEntryCacheModel)) {
			return false;
		}

		BlogsEntryCacheModel blogsEntryCacheModel = (BlogsEntryCacheModel)obj;

		if (entryId == blogsEntryCacheModel.entryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, entryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(59);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", entryId=");
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
		sb.append(", title=");
		sb.append(title);
		sb.append(", subtitle=");
		sb.append(subtitle);
		sb.append(", urlTitle=");
		sb.append(urlTitle);
		sb.append(", description=");
		sb.append(description);
		sb.append(", content=");
		sb.append(content);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", allowPingbacks=");
		sb.append(allowPingbacks);
		sb.append(", allowTrackbacks=");
		sb.append(allowTrackbacks);
		sb.append(", trackbacks=");
		sb.append(trackbacks);
		sb.append(", coverImageCaption=");
		sb.append(coverImageCaption);
		sb.append(", coverImageFileEntryId=");
		sb.append(coverImageFileEntryId);
		sb.append(", coverImageURL=");
		sb.append(coverImageURL);
		sb.append(", smallImage=");
		sb.append(smallImage);
		sb.append(", smallImageFileEntryId=");
		sb.append(smallImageFileEntryId);
		sb.append(", smallImageId=");
		sb.append(smallImageId);
		sb.append(", smallImageURL=");
		sb.append(smallImageURL);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
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
	public BlogsEntry toEntityModel() {
		BlogsEntryImpl blogsEntryImpl = new BlogsEntryImpl();

		if (uuid == null) {
			blogsEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setUuid(uuid);
		}

		blogsEntryImpl.setEntryId(entryId);
		blogsEntryImpl.setGroupId(groupId);
		blogsEntryImpl.setCompanyId(companyId);
		blogsEntryImpl.setUserId(userId);

		if (userName == null) {
			blogsEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			blogsEntryImpl.setCreateDate(null);
		}
		else {
			blogsEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			blogsEntryImpl.setModifiedDate(null);
		}
		else {
			blogsEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (title == null) {
			blogsEntryImpl.setTitle(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setTitle(title);
		}

		if (subtitle == null) {
			blogsEntryImpl.setSubtitle(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setSubtitle(subtitle);
		}

		if (urlTitle == null) {
			blogsEntryImpl.setUrlTitle(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setUrlTitle(urlTitle);
		}

		if (description == null) {
			blogsEntryImpl.setDescription(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setDescription(description);
		}

		if (content == null) {
			blogsEntryImpl.setContent(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setContent(content);
		}

		if (displayDate == Long.MIN_VALUE) {
			blogsEntryImpl.setDisplayDate(null);
		}
		else {
			blogsEntryImpl.setDisplayDate(new Date(displayDate));
		}

		blogsEntryImpl.setAllowPingbacks(allowPingbacks);
		blogsEntryImpl.setAllowTrackbacks(allowTrackbacks);

		if (trackbacks == null) {
			blogsEntryImpl.setTrackbacks(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setTrackbacks(trackbacks);
		}

		if (coverImageCaption == null) {
			blogsEntryImpl.setCoverImageCaption(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setCoverImageCaption(coverImageCaption);
		}

		blogsEntryImpl.setCoverImageFileEntryId(coverImageFileEntryId);

		if (coverImageURL == null) {
			blogsEntryImpl.setCoverImageURL(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setCoverImageURL(coverImageURL);
		}

		blogsEntryImpl.setSmallImage(smallImage);
		blogsEntryImpl.setSmallImageFileEntryId(smallImageFileEntryId);
		blogsEntryImpl.setSmallImageId(smallImageId);

		if (smallImageURL == null) {
			blogsEntryImpl.setSmallImageURL(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setSmallImageURL(smallImageURL);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			blogsEntryImpl.setLastPublishDate(null);
		}
		else {
			blogsEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		blogsEntryImpl.setStatus(status);
		blogsEntryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			blogsEntryImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			blogsEntryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			blogsEntryImpl.setStatusDate(null);
		}
		else {
			blogsEntryImpl.setStatusDate(new Date(statusDate));
		}

		blogsEntryImpl.resetOriginalValues();

		return blogsEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		entryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		title = objectInput.readUTF();
		subtitle = objectInput.readUTF();
		urlTitle = objectInput.readUTF();
		description = objectInput.readUTF();
		content = objectInput.readUTF();
		displayDate = objectInput.readLong();

		allowPingbacks = objectInput.readBoolean();

		allowTrackbacks = objectInput.readBoolean();
		trackbacks = objectInput.readUTF();
		coverImageCaption = objectInput.readUTF();

		coverImageFileEntryId = objectInput.readLong();
		coverImageURL = objectInput.readUTF();

		smallImage = objectInput.readBoolean();

		smallImageFileEntryId = objectInput.readLong();

		smallImageId = objectInput.readLong();
		smallImageURL = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		objectOutput.writeLong(entryId);

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

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (subtitle == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(subtitle);
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

		objectOutput.writeLong(displayDate);

		objectOutput.writeBoolean(allowPingbacks);

		objectOutput.writeBoolean(allowTrackbacks);

		if (trackbacks == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(trackbacks);
		}

		if (coverImageCaption == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(coverImageCaption);
		}

		objectOutput.writeLong(coverImageFileEntryId);

		if (coverImageURL == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(coverImageURL);
		}

		objectOutput.writeBoolean(smallImage);

		objectOutput.writeLong(smallImageFileEntryId);

		objectOutput.writeLong(smallImageId);

		if (smallImageURL == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(smallImageURL);
		}

		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public String uuid;
	public long entryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public String subtitle;
	public String urlTitle;
	public String description;
	public String content;
	public long displayDate;
	public boolean allowPingbacks;
	public boolean allowTrackbacks;
	public String trackbacks;
	public String coverImageCaption;
	public long coverImageFileEntryId;
	public String coverImageURL;
	public boolean smallImage;
	public long smallImageFileEntryId;
	public long smallImageId;
	public String smallImageURL;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}