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

package com.liferay.portal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.Website;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Website in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Website
 * @generated
 */
@ProviderType
public class WebsiteCacheModel implements CacheModel<Website>, Externalizable,
	MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WebsiteCacheModel)) {
			return false;
		}

		WebsiteCacheModel websiteCacheModel = (WebsiteCacheModel)obj;

		if ((websiteId == websiteCacheModel.websiteId) &&
				(mvccVersion == websiteCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, websiteId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", websiteId=");
		sb.append(websiteId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", url=");
		sb.append(url);
		sb.append(", typeId=");
		sb.append(typeId);
		sb.append(", primary=");
		sb.append(primary);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Website toEntityModel() {
		WebsiteImpl websiteImpl = new WebsiteImpl();

		websiteImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			websiteImpl.setUuid(StringPool.BLANK);
		}
		else {
			websiteImpl.setUuid(uuid);
		}

		websiteImpl.setWebsiteId(websiteId);
		websiteImpl.setUserId(userId);

		if (userName == null) {
			websiteImpl.setUserName(StringPool.BLANK);
		}
		else {
			websiteImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			websiteImpl.setCreateDate(null);
		}
		else {
			websiteImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			websiteImpl.setModifiedDate(null);
		}
		else {
			websiteImpl.setModifiedDate(new Date(modifiedDate));
		}

		websiteImpl.setClassNameId(classNameId);
		websiteImpl.setClassPK(classPK);

		if (url == null) {
			websiteImpl.setUrl(StringPool.BLANK);
		}
		else {
			websiteImpl.setUrl(url);
		}

		websiteImpl.setTypeId(typeId);
		websiteImpl.setPrimary(primary);

		if (lastPublishDate == Long.MIN_VALUE) {
			websiteImpl.setLastPublishDate(null);
		}
		else {
			websiteImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		websiteImpl.setCompanyId(companyId);

		websiteImpl.resetOriginalValues();

		return websiteImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		websiteId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		url = objectInput.readUTF();
		typeId = objectInput.readLong();
		primary = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(websiteId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);

		if (url == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(url);
		}

		objectOutput.writeLong(typeId);
		objectOutput.writeBoolean(primary);
		objectOutput.writeLong(lastPublishDate);
		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public String uuid;
	public long websiteId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String url;
	public long typeId;
	public boolean primary;
	public long lastPublishDate;
	public long companyId;
}