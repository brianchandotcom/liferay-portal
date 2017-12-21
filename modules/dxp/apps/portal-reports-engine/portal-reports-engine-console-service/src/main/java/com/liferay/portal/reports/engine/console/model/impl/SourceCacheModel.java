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

package com.liferay.portal.reports.engine.console.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.reports.engine.console.model.Source;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Source in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Source
 * @generated
 */
@ProviderType
public class SourceCacheModel implements CacheModel<Source>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SourceCacheModel)) {
			return false;
		}

		SourceCacheModel sourceCacheModel = (SourceCacheModel)obj;

		if (sourceId == sourceCacheModel.sourceId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, sourceId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", sourceId=");
		sb.append(sourceId);
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
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", driverClassName=");
		sb.append(driverClassName);
		sb.append(", driverUrl=");
		sb.append(driverUrl);
		sb.append(", driverUserName=");
		sb.append(driverUserName);
		sb.append(", driverPassword=");
		sb.append(driverPassword);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Source toEntityModel() {
		SourceImpl sourceImpl = new SourceImpl();

		if (uuid == null) {
			sourceImpl.setUuid("");
		}
		else {
			sourceImpl.setUuid(uuid);
		}

		sourceImpl.setSourceId(sourceId);
		sourceImpl.setGroupId(groupId);
		sourceImpl.setCompanyId(companyId);
		sourceImpl.setUserId(userId);

		if (userName == null) {
			sourceImpl.setUserName("");
		}
		else {
			sourceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			sourceImpl.setCreateDate(null);
		}
		else {
			sourceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			sourceImpl.setModifiedDate(null);
		}
		else {
			sourceImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			sourceImpl.setLastPublishDate(null);
		}
		else {
			sourceImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		if (name == null) {
			sourceImpl.setName("");
		}
		else {
			sourceImpl.setName(name);
		}

		if (driverClassName == null) {
			sourceImpl.setDriverClassName("");
		}
		else {
			sourceImpl.setDriverClassName(driverClassName);
		}

		if (driverUrl == null) {
			sourceImpl.setDriverUrl("");
		}
		else {
			sourceImpl.setDriverUrl(driverUrl);
		}

		if (driverUserName == null) {
			sourceImpl.setDriverUserName("");
		}
		else {
			sourceImpl.setDriverUserName(driverUserName);
		}

		if (driverPassword == null) {
			sourceImpl.setDriverPassword("");
		}
		else {
			sourceImpl.setDriverPassword(driverPassword);
		}

		sourceImpl.resetOriginalValues();

		return sourceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		sourceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
		name = objectInput.readUTF();
		driverClassName = objectInput.readUTF();
		driverUrl = objectInput.readUTF();
		driverUserName = objectInput.readUTF();
		driverPassword = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(sourceId);

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
		objectOutput.writeLong(lastPublishDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (driverClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(driverClassName);
		}

		if (driverUrl == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(driverUrl);
		}

		if (driverUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(driverUserName);
		}

		if (driverPassword == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(driverPassword);
		}
	}

	public String uuid;
	public long sourceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long lastPublishDate;
	public String name;
	public String driverClassName;
	public String driverUrl;
	public String driverUserName;
	public String driverPassword;
}