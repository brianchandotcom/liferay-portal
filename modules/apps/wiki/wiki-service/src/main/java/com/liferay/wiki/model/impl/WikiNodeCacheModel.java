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
import com.liferay.wiki.model.WikiNode;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WikiNode in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WikiNodeCacheModel
	implements CacheModel<WikiNode>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WikiNodeCacheModel)) {
			return false;
		}

		WikiNodeCacheModel wikiNodeCacheModel = (WikiNodeCacheModel)object;

		if ((nodeId == wikiNodeCacheModel.nodeId) &&
			(mvccVersion == wikiNodeCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, nodeId);

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
		StringBundler sb = new StringBundler(37);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
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
	public WikiNode toEntityModel() {
		WikiNodeImpl wikiNodeImpl = new WikiNodeImpl();

		wikiNodeImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			wikiNodeImpl.setUuid("");
		}
		else {
			wikiNodeImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			wikiNodeImpl.setExternalReferenceCode("");
		}
		else {
			wikiNodeImpl.setExternalReferenceCode(externalReferenceCode);
		}

		wikiNodeImpl.setNodeId(nodeId);
		wikiNodeImpl.setGroupId(groupId);
		wikiNodeImpl.setCompanyId(companyId);
		wikiNodeImpl.setUserId(userId);

		if (userName == null) {
			wikiNodeImpl.setUserName("");
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
			wikiNodeImpl.setName("");
		}
		else {
			wikiNodeImpl.setName(name);
		}

		if (description == null) {
			wikiNodeImpl.setDescription("");
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

		if (lastPublishDate == Long.MIN_VALUE) {
			wikiNodeImpl.setLastPublishDate(null);
		}
		else {
			wikiNodeImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		wikiNodeImpl.setStatus(status);
		wikiNodeImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			wikiNodeImpl.setStatusByUserName("");
		}
		else {
			wikiNodeImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			wikiNodeImpl.setStatusDate(null);
		}
		else {
			wikiNodeImpl.setStatusDate(new Date(statusDate));
		}

		wikiNodeImpl.resetOriginalValues();

		return wikiNodeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		nodeId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		lastPostDate = objectInput.readLong();
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

		objectOutput.writeLong(nodeId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(lastPostDate);
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
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}