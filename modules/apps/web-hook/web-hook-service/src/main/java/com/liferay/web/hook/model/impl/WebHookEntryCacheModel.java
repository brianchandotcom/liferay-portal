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

package com.liferay.web.hook.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.web.hook.model.WebHookEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WebHookEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WebHookEntryCacheModel
	implements CacheModel<WebHookEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WebHookEntryCacheModel)) {
			return false;
		}

		WebHookEntryCacheModel webHookEntryCacheModel =
			(WebHookEntryCacheModel)object;

		if ((webHookEntryId == webHookEntryCacheModel.webHookEntryId) &&
			(mvccVersion == webHookEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, webHookEntryId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", webHookEntryId=");
		sb.append(webHookEntryId);
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
		sb.append(", destination=");
		sb.append(destination);
		sb.append(", url=");
		sb.append(url);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WebHookEntry toEntityModel() {
		WebHookEntryImpl webHookEntryImpl = new WebHookEntryImpl();

		webHookEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			webHookEntryImpl.setUuid("");
		}
		else {
			webHookEntryImpl.setUuid(uuid);
		}

		webHookEntryImpl.setWebHookEntryId(webHookEntryId);
		webHookEntryImpl.setCompanyId(companyId);
		webHookEntryImpl.setUserId(userId);

		if (userName == null) {
			webHookEntryImpl.setUserName("");
		}
		else {
			webHookEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			webHookEntryImpl.setCreateDate(null);
		}
		else {
			webHookEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			webHookEntryImpl.setModifiedDate(null);
		}
		else {
			webHookEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			webHookEntryImpl.setName("");
		}
		else {
			webHookEntryImpl.setName(name);
		}

		if (destination == null) {
			webHookEntryImpl.setDestination("");
		}
		else {
			webHookEntryImpl.setDestination(destination);
		}

		if (url == null) {
			webHookEntryImpl.setUrl("");
		}
		else {
			webHookEntryImpl.setUrl(url);
		}

		webHookEntryImpl.resetOriginalValues();

		return webHookEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		webHookEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		destination = objectInput.readUTF();
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

		objectOutput.writeLong(webHookEntryId);

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

		if (destination == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(destination);
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
	public long webHookEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String destination;
	public String url;

}