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

package com.liferay.remote.web.app.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.remote.web.app.model.RemoteWebAppEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RemoteWebAppEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RemoteWebAppEntryCacheModel
	implements CacheModel<RemoteWebAppEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RemoteWebAppEntryCacheModel)) {
			return false;
		}

		RemoteWebAppEntryCacheModel remoteWebAppEntryCacheModel =
			(RemoteWebAppEntryCacheModel)object;

		if ((entryId == remoteWebAppEntryCacheModel.entryId) &&
			(mvccVersion == remoteWebAppEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, entryId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", entryId=");
		sb.append(entryId);
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
		sb.append(", url=");
		sb.append(url);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RemoteWebAppEntry toEntityModel() {
		RemoteWebAppEntryImpl remoteWebAppEntryImpl =
			new RemoteWebAppEntryImpl();

		remoteWebAppEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			remoteWebAppEntryImpl.setUuid("");
		}
		else {
			remoteWebAppEntryImpl.setUuid(uuid);
		}

		remoteWebAppEntryImpl.setEntryId(entryId);
		remoteWebAppEntryImpl.setCompanyId(companyId);
		remoteWebAppEntryImpl.setUserId(userId);

		if (userName == null) {
			remoteWebAppEntryImpl.setUserName("");
		}
		else {
			remoteWebAppEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			remoteWebAppEntryImpl.setCreateDate(null);
		}
		else {
			remoteWebAppEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			remoteWebAppEntryImpl.setModifiedDate(null);
		}
		else {
			remoteWebAppEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			remoteWebAppEntryImpl.setName("");
		}
		else {
			remoteWebAppEntryImpl.setName(name);
		}

		if (url == null) {
			remoteWebAppEntryImpl.setUrl("");
		}
		else {
			remoteWebAppEntryImpl.setUrl(url);
		}

		remoteWebAppEntryImpl.resetOriginalValues();

		return remoteWebAppEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		entryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		url = objectInput.readUTF();
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

		objectOutput.writeLong(entryId);

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

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long entryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String url;

}