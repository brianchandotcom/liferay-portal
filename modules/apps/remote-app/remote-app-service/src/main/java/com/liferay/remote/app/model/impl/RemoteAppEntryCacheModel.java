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

package com.liferay.remote.app.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.remote.app.model.RemoteAppEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RemoteAppEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RemoteAppEntryCacheModel
	implements CacheModel<RemoteAppEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RemoteAppEntryCacheModel)) {
			return false;
		}

		RemoteAppEntryCacheModel remoteAppEntryCacheModel =
			(RemoteAppEntryCacheModel)object;

		if ((remoteAppEntryId == remoteAppEntryCacheModel.remoteAppEntryId) &&
			(mvccVersion == remoteAppEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, remoteAppEntryId);

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
		sb.append(", remoteAppEntryId=");
		sb.append(remoteAppEntryId);
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
	public RemoteAppEntry toEntityModel() {
		RemoteAppEntryImpl remoteAppEntryImpl = new RemoteAppEntryImpl();

		remoteAppEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			remoteAppEntryImpl.setUuid("");
		}
		else {
			remoteAppEntryImpl.setUuid(uuid);
		}

		remoteAppEntryImpl.setRemoteAppEntryId(remoteAppEntryId);
		remoteAppEntryImpl.setCompanyId(companyId);
		remoteAppEntryImpl.setUserId(userId);

		if (userName == null) {
			remoteAppEntryImpl.setUserName("");
		}
		else {
			remoteAppEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			remoteAppEntryImpl.setCreateDate(null);
		}
		else {
			remoteAppEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			remoteAppEntryImpl.setModifiedDate(null);
		}
		else {
			remoteAppEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			remoteAppEntryImpl.setName("");
		}
		else {
			remoteAppEntryImpl.setName(name);
		}

		if (url == null) {
			remoteAppEntryImpl.setUrl("");
		}
		else {
			remoteAppEntryImpl.setUrl(url);
		}

		remoteAppEntryImpl.resetOriginalValues();

		return remoteAppEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		remoteAppEntryId = objectInput.readLong();

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

		objectOutput.writeLong(remoteAppEntryId);

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
	public long remoteAppEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String url;

}