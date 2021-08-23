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

package com.liferay.custom.elements.model.impl;

import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CustomElementsSource in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CustomElementsSourceCacheModel
	implements CacheModel<CustomElementsSource>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CustomElementsSourceCacheModel)) {
			return false;
		}

		CustomElementsSourceCacheModel customElementsSourceCacheModel =
			(CustomElementsSourceCacheModel)object;

		if ((customElementsSourceId ==
				customElementsSourceCacheModel.customElementsSourceId) &&
			(mvccVersion == customElementsSourceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, customElementsSourceId);

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
		sb.append(", customElementsSourceId=");
		sb.append(customElementsSourceId);
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
		sb.append(", htmlElementName=");
		sb.append(htmlElementName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", urls=");
		sb.append(urls);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CustomElementsSource toEntityModel() {
		CustomElementsSourceImpl customElementsSourceImpl =
			new CustomElementsSourceImpl();

		customElementsSourceImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			customElementsSourceImpl.setUuid("");
		}
		else {
			customElementsSourceImpl.setUuid(uuid);
		}

		customElementsSourceImpl.setCustomElementsSourceId(
			customElementsSourceId);
		customElementsSourceImpl.setCompanyId(companyId);
		customElementsSourceImpl.setUserId(userId);

		if (userName == null) {
			customElementsSourceImpl.setUserName("");
		}
		else {
			customElementsSourceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			customElementsSourceImpl.setCreateDate(null);
		}
		else {
			customElementsSourceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			customElementsSourceImpl.setModifiedDate(null);
		}
		else {
			customElementsSourceImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (htmlElementName == null) {
			customElementsSourceImpl.setHTMLElementName("");
		}
		else {
			customElementsSourceImpl.setHTMLElementName(htmlElementName);
		}

		if (name == null) {
			customElementsSourceImpl.setName("");
		}
		else {
			customElementsSourceImpl.setName(name);
		}

		if (urls == null) {
			customElementsSourceImpl.setURLs("");
		}
		else {
			customElementsSourceImpl.setURLs(urls);
		}

		customElementsSourceImpl.resetOriginalValues();

		return customElementsSourceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		customElementsSourceId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		htmlElementName = objectInput.readUTF();
		name = objectInput.readUTF();
		urls = (String)objectInput.readObject();
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

		objectOutput.writeLong(customElementsSourceId);

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

		if (htmlElementName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(htmlElementName);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (urls == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(urls);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long customElementsSourceId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String htmlElementName;
	public String name;
	public String urls;

}