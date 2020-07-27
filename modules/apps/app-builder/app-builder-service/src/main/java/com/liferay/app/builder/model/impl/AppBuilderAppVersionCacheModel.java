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

package com.liferay.app.builder.model.impl;

import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AppBuilderAppVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AppBuilderAppVersionCacheModel
	implements CacheModel<AppBuilderAppVersion>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppBuilderAppVersionCacheModel)) {
			return false;
		}

		AppBuilderAppVersionCacheModel appBuilderAppVersionCacheModel =
			(AppBuilderAppVersionCacheModel)object;

		if (appBuilderAppVersionId ==
				appBuilderAppVersionCacheModel.appBuilderAppVersionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, appBuilderAppVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", appBuilderAppVersionId=");
		sb.append(appBuilderAppVersionId);
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
		sb.append(", appBuilderAppId=");
		sb.append(appBuilderAppId);
		sb.append(", ddlRecordSetId=");
		sb.append(ddlRecordSetId);
		sb.append(", ddmStructureId=");
		sb.append(ddmStructureId);
		sb.append(", ddmStructureLayoutId=");
		sb.append(ddmStructureLayoutId);
		sb.append(", version=");
		sb.append(version);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AppBuilderAppVersion toEntityModel() {
		AppBuilderAppVersionImpl appBuilderAppVersionImpl =
			new AppBuilderAppVersionImpl();

		if (uuid == null) {
			appBuilderAppVersionImpl.setUuid("");
		}
		else {
			appBuilderAppVersionImpl.setUuid(uuid);
		}

		appBuilderAppVersionImpl.setAppBuilderAppVersionId(
			appBuilderAppVersionId);
		appBuilderAppVersionImpl.setGroupId(groupId);
		appBuilderAppVersionImpl.setCompanyId(companyId);
		appBuilderAppVersionImpl.setUserId(userId);

		if (userName == null) {
			appBuilderAppVersionImpl.setUserName("");
		}
		else {
			appBuilderAppVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			appBuilderAppVersionImpl.setCreateDate(null);
		}
		else {
			appBuilderAppVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			appBuilderAppVersionImpl.setModifiedDate(null);
		}
		else {
			appBuilderAppVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		appBuilderAppVersionImpl.setAppBuilderAppId(appBuilderAppId);
		appBuilderAppVersionImpl.setDdlRecordSetId(ddlRecordSetId);
		appBuilderAppVersionImpl.setDdmStructureId(ddmStructureId);
		appBuilderAppVersionImpl.setDdmStructureLayoutId(ddmStructureLayoutId);

		if (version == null) {
			appBuilderAppVersionImpl.setVersion("");
		}
		else {
			appBuilderAppVersionImpl.setVersion(version);
		}

		appBuilderAppVersionImpl.resetOriginalValues();

		return appBuilderAppVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		appBuilderAppVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		appBuilderAppId = objectInput.readLong();

		ddlRecordSetId = objectInput.readLong();

		ddmStructureId = objectInput.readLong();

		ddmStructureLayoutId = objectInput.readLong();
		version = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(appBuilderAppVersionId);

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

		objectOutput.writeLong(appBuilderAppId);

		objectOutput.writeLong(ddlRecordSetId);

		objectOutput.writeLong(ddmStructureId);

		objectOutput.writeLong(ddmStructureLayoutId);

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}
	}

	public String uuid;
	public long appBuilderAppVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long appBuilderAppId;
	public long ddlRecordSetId;
	public long ddmStructureId;
	public long ddmStructureLayoutId;
	public String version;

}