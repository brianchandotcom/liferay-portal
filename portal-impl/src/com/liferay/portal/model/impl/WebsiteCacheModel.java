/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.Website;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Website in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WebsiteCacheModel
	implements CacheModel<Website>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WebsiteCacheModel)) {
			return false;
		}

		WebsiteCacheModel websiteCacheModel = (WebsiteCacheModel)object;

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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", url=");
		sb.append(url);
		sb.append(", listTypeId=");
		sb.append(listTypeId);
		sb.append(", primary=");
		sb.append(primary);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Website toEntityModel() {
		WebsiteImpl websiteImpl = new WebsiteImpl();

		websiteImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			websiteImpl.setUuid("");
		}
		else {
			websiteImpl.setUuid(uuid);
		}

		websiteImpl.setWebsiteId(websiteId);
		websiteImpl.setCompanyId(companyId);
		websiteImpl.setUserId(userId);

		if (userName == null) {
			websiteImpl.setUserName("");
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
			websiteImpl.setUrl("");
		}
		else {
			websiteImpl.setUrl(url);
		}

		websiteImpl.setListTypeId(listTypeId);
		websiteImpl.setPrimary(primary);

		if (lastPublishDate == Long.MIN_VALUE) {
			websiteImpl.setLastPublishDate(null);
		}
		else {
			websiteImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		websiteImpl.resetOriginalValues();

		return websiteImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		websiteId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		url = objectInput.readUTF();

		listTypeId = objectInput.readLong();

		primary = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(websiteId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}

		objectOutput.writeLong(listTypeId);

		objectOutput.writeBoolean(primary);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long websiteId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String url;
	public long listTypeId;
	public boolean primary;
	public long lastPublishDate;

}