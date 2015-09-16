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

package com.liferay.portlet.softwarecatalog.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SCFrameworkVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersion
 * @generated
 */
@ProviderType
public class SCFrameworkVersionCacheModel implements CacheModel<SCFrameworkVersion>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SCFrameworkVersionCacheModel)) {
			return false;
		}

		SCFrameworkVersionCacheModel scFrameworkVersionCacheModel = (SCFrameworkVersionCacheModel)obj;

		if (frameworkVersionId == scFrameworkVersionCacheModel.frameworkVersionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, frameworkVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{frameworkVersionId=");
		sb.append(frameworkVersionId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", active=");
		sb.append(active);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SCFrameworkVersion toEntityModel() {
		SCFrameworkVersionImpl scFrameworkVersionImpl = new SCFrameworkVersionImpl();

		scFrameworkVersionImpl.setFrameworkVersionId(frameworkVersionId);
		scFrameworkVersionImpl.setGroupId(groupId);
		scFrameworkVersionImpl.setUserId(userId);

		if (userName == null) {
			scFrameworkVersionImpl.setUserName(StringPool.BLANK);
		}
		else {
			scFrameworkVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			scFrameworkVersionImpl.setCreateDate(null);
		}
		else {
			scFrameworkVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			scFrameworkVersionImpl.setModifiedDate(null);
		}
		else {
			scFrameworkVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			scFrameworkVersionImpl.setName(StringPool.BLANK);
		}
		else {
			scFrameworkVersionImpl.setName(name);
		}

		if (url == null) {
			scFrameworkVersionImpl.setUrl(StringPool.BLANK);
		}
		else {
			scFrameworkVersionImpl.setUrl(url);
		}

		scFrameworkVersionImpl.setActive(active);
		scFrameworkVersionImpl.setPriority(priority);
		scFrameworkVersionImpl.setCompanyId(companyId);

		scFrameworkVersionImpl.resetOriginalValues();

		return scFrameworkVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		frameworkVersionId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		url = objectInput.readUTF();
		active = objectInput.readBoolean();
		priority = objectInput.readInt();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(frameworkVersionId);
		objectOutput.writeLong(groupId);
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

		if (url == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(url);
		}

		objectOutput.writeBoolean(active);
		objectOutput.writeInt(priority);
		objectOutput.writeLong(companyId);
	}

	public long frameworkVersionId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String url;
	public boolean active;
	public int priority;
	public long companyId;
}