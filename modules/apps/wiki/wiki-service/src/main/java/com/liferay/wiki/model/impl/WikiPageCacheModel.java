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

package com.liferay.wiki.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.wiki.model.WikiPage;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WikiPage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WikiPageCacheModel
	implements CacheModel<WikiPage>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WikiPageCacheModel)) {
			return false;
		}

		WikiPageCacheModel wikiPageCacheModel = (WikiPageCacheModel)object;

		if ((pageId == wikiPageCacheModel.pageId) &&
			(mvccVersion == wikiPageCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, pageId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(53);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
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
	public WikiPage toEntityModel() {
		WikiPageImpl wikiPageImpl = new WikiPageImpl();

		wikiPageImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			wikiPageImpl.setUuid("");
		}
		else {
			wikiPageImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			wikiPageImpl.setExternalReferenceCode("");
		}
		else {
			wikiPageImpl.setExternalReferenceCode(externalReferenceCode);
		}

		wikiPageImpl.setPageId(pageId);
		wikiPageImpl.setResourcePrimKey(resourcePrimKey);
		wikiPageImpl.setGroupId(groupId);
		wikiPageImpl.setCompanyId(companyId);
		wikiPageImpl.setUserId(userId);

		if (userName == null) {
			wikiPageImpl.setUserName("");
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
			wikiPageImpl.setTitle("");
		}
		else {
			wikiPageImpl.setTitle(title);
		}

		wikiPageImpl.setVersion(version);
		wikiPageImpl.setMinorEdit(minorEdit);

		if (content == null) {
			wikiPageImpl.setContent("");
		}
		else {
			wikiPageImpl.setContent(content);
		}

		if (summary == null) {
			wikiPageImpl.setSummary("");
		}
		else {
			wikiPageImpl.setSummary(summary);
		}

		if (format == null) {
			wikiPageImpl.setFormat("");
		}
		else {
			wikiPageImpl.setFormat(format);
		}

		wikiPageImpl.setHead(head);

		if (parentTitle == null) {
			wikiPageImpl.setParentTitle("");
		}
		else {
			wikiPageImpl.setParentTitle(parentTitle);
		}

		if (redirectTitle == null) {
			wikiPageImpl.setRedirectTitle("");
		}
		else {
			wikiPageImpl.setRedirectTitle(redirectTitle);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			wikiPageImpl.setLastPublishDate(null);
		}
		else {
			wikiPageImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		wikiPageImpl.setStatus(status);
		wikiPageImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			wikiPageImpl.setStatusByUserName("");
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

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		pageId = objectInput.readLong();

		resourcePrimKey = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		nodeId = objectInput.readLong();
		title = objectInput.readUTF();

		version = objectInput.readDouble();

		minorEdit = objectInput.readBoolean();
		content = (String)objectInput.readObject();
		summary = objectInput.readUTF();
		format = objectInput.readUTF();

		head = objectInput.readBoolean();
		parentTitle = objectInput.readUTF();
		redirectTitle = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(pageId);

		objectOutput.writeLong(resourcePrimKey);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(nodeId);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeDouble(version);

		objectOutput.writeBoolean(minorEdit);

		if (content == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(content);
		}

		if (summary == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(summary);
		}

		if (format == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(format);
		}

		objectOutput.writeBoolean(head);

		if (parentTitle == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(parentTitle);
		}

		if (redirectTitle == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(redirectTitle);
		}

		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
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
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}