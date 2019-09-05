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

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoDefinitionVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoDefinitionVersionCacheModel
	implements CacheModel<KaleoDefinitionVersion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoDefinitionVersionCacheModel)) {
			return false;
		}

		KaleoDefinitionVersionCacheModel kaleoDefinitionVersionCacheModel =
			(KaleoDefinitionVersionCacheModel)obj;

		if ((kaleoDefinitionVersionId ==
				kaleoDefinitionVersionCacheModel.kaleoDefinitionVersionId) &&
			(mvccVersion == kaleoDefinitionVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoDefinitionVersionId);

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
		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", content=");
		sb.append(content);
		sb.append(", version=");
		sb.append(version);
		sb.append(", startKaleoNodeId=");
		sb.append(startKaleoNodeId);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoDefinitionVersion toEntityModel() {
		KaleoDefinitionVersionImpl kaleoDefinitionVersionImpl =
			new KaleoDefinitionVersionImpl();

		kaleoDefinitionVersionImpl.setMvccVersion(mvccVersion);
		kaleoDefinitionVersionImpl.setKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
		kaleoDefinitionVersionImpl.setGroupId(groupId);
		kaleoDefinitionVersionImpl.setCompanyId(companyId);
		kaleoDefinitionVersionImpl.setUserId(userId);

		if (userName == null) {
			kaleoDefinitionVersionImpl.setUserName("");
		}
		else {
			kaleoDefinitionVersionImpl.setUserName(userName);
		}

		kaleoDefinitionVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			kaleoDefinitionVersionImpl.setStatusByUserName("");
		}
		else {
			kaleoDefinitionVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			kaleoDefinitionVersionImpl.setStatusDate(null);
		}
		else {
			kaleoDefinitionVersionImpl.setStatusDate(new Date(statusDate));
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoDefinitionVersionImpl.setCreateDate(null);
		}
		else {
			kaleoDefinitionVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoDefinitionVersionImpl.setModifiedDate(null);
		}
		else {
			kaleoDefinitionVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			kaleoDefinitionVersionImpl.setName("");
		}
		else {
			kaleoDefinitionVersionImpl.setName(name);
		}

		if (title == null) {
			kaleoDefinitionVersionImpl.setTitle("");
		}
		else {
			kaleoDefinitionVersionImpl.setTitle(title);
		}

		if (description == null) {
			kaleoDefinitionVersionImpl.setDescription("");
		}
		else {
			kaleoDefinitionVersionImpl.setDescription(description);
		}

		if (content == null) {
			kaleoDefinitionVersionImpl.setContent("");
		}
		else {
			kaleoDefinitionVersionImpl.setContent(content);
		}

		if (version == null) {
			kaleoDefinitionVersionImpl.setVersion("");
		}
		else {
			kaleoDefinitionVersionImpl.setVersion(version);
		}

		kaleoDefinitionVersionImpl.setStartKaleoNodeId(startKaleoNodeId);
		kaleoDefinitionVersionImpl.setStatus(status);

		kaleoDefinitionVersionImpl.resetOriginalValues();

		return kaleoDefinitionVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		content = objectInput.readUTF();
		version = objectInput.readUTF();

		startKaleoNodeId = objectInput.readLong();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(kaleoDefinitionVersionId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (content == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(content);
		}

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeLong(startKaleoNodeId);

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long kaleoDefinitionVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String title;
	public String description;
	public String content;
	public String version;
	public long startKaleoNodeId;
	public int status;

}