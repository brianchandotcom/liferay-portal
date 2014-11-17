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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

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
 * @see WikiNode
 * @generated
 */
@ProviderType
public class WikiNodeCacheModel implements CacheModel<WikiNode>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

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

		wikiNodeImpl.setStatus(status);
		wikiNodeImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			wikiNodeImpl.setStatusByUserName(StringPool.BLANK);
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
		uuid = objectInput.readUTF();
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

		objectOutput.writeLong(nodeId);
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

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(lastPostDate);
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
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}