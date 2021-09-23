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
		StringBundler sb = new StringBundler(35);

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
		sb.append(", customElementCSSURLs=");
		sb.append(customElementCSSURLs);
		sb.append(", customElementHTMLElementName=");
		sb.append(customElementHTMLElementName);
		sb.append(", customElementURLs=");
		sb.append(customElementURLs);
		sb.append(", iFrameURL=");
		sb.append(iFrameURL);
		sb.append(", instanceable=");
		sb.append(instanceable);
		sb.append(", name=");
		sb.append(name);
		sb.append(", portletCategoryName=");
		sb.append(portletCategoryName);
		sb.append(", properties=");
		sb.append(properties);
		sb.append(", type=");
		sb.append(type);
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

		if (customElementCSSURLs == null) {
			remoteAppEntryImpl.setCustomElementCSSURLs("");
		}
		else {
			remoteAppEntryImpl.setCustomElementCSSURLs(customElementCSSURLs);
		}

		if (customElementHTMLElementName == null) {
			remoteAppEntryImpl.setCustomElementHTMLElementName("");
		}
		else {
			remoteAppEntryImpl.setCustomElementHTMLElementName(
				customElementHTMLElementName);
		}

		if (customElementURLs == null) {
			remoteAppEntryImpl.setCustomElementURLs("");
		}
		else {
			remoteAppEntryImpl.setCustomElementURLs(customElementURLs);
		}

		if (iFrameURL == null) {
			remoteAppEntryImpl.setIFrameURL("");
		}
		else {
			remoteAppEntryImpl.setIFrameURL(iFrameURL);
		}

		remoteAppEntryImpl.setInstanceable(instanceable);

		if (name == null) {
			remoteAppEntryImpl.setName("");
		}
		else {
			remoteAppEntryImpl.setName(name);
		}

		if (portletCategoryName == null) {
			remoteAppEntryImpl.setPortletCategoryName("");
		}
		else {
			remoteAppEntryImpl.setPortletCategoryName(portletCategoryName);
		}

		if (properties == null) {
			remoteAppEntryImpl.setProperties("");
		}
		else {
			remoteAppEntryImpl.setProperties(properties);
		}

		if (type == null) {
			remoteAppEntryImpl.setType("");
		}
		else {
			remoteAppEntryImpl.setType(type);
		}

		remoteAppEntryImpl.resetOriginalValues();

		return remoteAppEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		remoteAppEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		customElementCSSURLs = (String)objectInput.readObject();
		customElementHTMLElementName = objectInput.readUTF();
		customElementURLs = (String)objectInput.readObject();
		iFrameURL = objectInput.readUTF();

		instanceable = objectInput.readBoolean();
		name = objectInput.readUTF();
		portletCategoryName = objectInput.readUTF();
		properties = (String)objectInput.readObject();
		type = objectInput.readUTF();
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

		if (customElementCSSURLs == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(customElementCSSURLs);
		}

		if (customElementHTMLElementName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(customElementHTMLElementName);
		}

		if (customElementURLs == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(customElementURLs);
		}

		if (iFrameURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(iFrameURL);
		}

		objectOutput.writeBoolean(instanceable);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (portletCategoryName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletCategoryName);
		}

		if (properties == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(properties);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
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
	public String customElementCSSURLs;
	public String customElementHTMLElementName;
	public String customElementURLs;
	public String iFrameURL;
	public boolean instanceable;
	public String name;
	public String portletCategoryName;
	public String properties;
	public String type;

}