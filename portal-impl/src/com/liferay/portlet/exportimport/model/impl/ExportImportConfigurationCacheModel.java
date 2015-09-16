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

package com.liferay.portlet.exportimport.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;

import com.liferay.portlet.exportimport.model.ExportImportConfiguration;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ExportImportConfiguration in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportConfiguration
 * @generated
 */
@ProviderType
public class ExportImportConfigurationCacheModel implements CacheModel<ExportImportConfiguration>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExportImportConfigurationCacheModel)) {
			return false;
		}

		ExportImportConfigurationCacheModel exportImportConfigurationCacheModel = (ExportImportConfigurationCacheModel)obj;

		if ((exportImportConfigurationId == exportImportConfigurationCacheModel.exportImportConfigurationId) &&
				(mvccVersion == exportImportConfigurationCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, exportImportConfigurationId);

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
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", exportImportConfigurationId=");
		sb.append(exportImportConfigurationId);
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
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ExportImportConfiguration toEntityModel() {
		ExportImportConfigurationImpl exportImportConfigurationImpl = new ExportImportConfigurationImpl();

		exportImportConfigurationImpl.setMvccVersion(mvccVersion);
		exportImportConfigurationImpl.setExportImportConfigurationId(exportImportConfigurationId);
		exportImportConfigurationImpl.setGroupId(groupId);
		exportImportConfigurationImpl.setUserId(userId);

		if (userName == null) {
			exportImportConfigurationImpl.setUserName(StringPool.BLANK);
		}
		else {
			exportImportConfigurationImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			exportImportConfigurationImpl.setCreateDate(null);
		}
		else {
			exportImportConfigurationImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			exportImportConfigurationImpl.setModifiedDate(null);
		}
		else {
			exportImportConfigurationImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			exportImportConfigurationImpl.setName(StringPool.BLANK);
		}
		else {
			exportImportConfigurationImpl.setName(name);
		}

		if (description == null) {
			exportImportConfigurationImpl.setDescription(StringPool.BLANK);
		}
		else {
			exportImportConfigurationImpl.setDescription(description);
		}

		exportImportConfigurationImpl.setType(type);

		if (settings == null) {
			exportImportConfigurationImpl.setSettings(StringPool.BLANK);
		}
		else {
			exportImportConfigurationImpl.setSettings(settings);
		}

		exportImportConfigurationImpl.setStatus(status);
		exportImportConfigurationImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			exportImportConfigurationImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			exportImportConfigurationImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			exportImportConfigurationImpl.setStatusDate(null);
		}
		else {
			exportImportConfigurationImpl.setStatusDate(new Date(statusDate));
		}

		exportImportConfigurationImpl.setCompanyId(companyId);

		exportImportConfigurationImpl.resetOriginalValues();

		return exportImportConfigurationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		exportImportConfigurationId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		type = objectInput.readInt();
		settings = objectInput.readUTF();
		status = objectInput.readInt();
		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);
		objectOutput.writeLong(exportImportConfigurationId);
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

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(type);

		if (settings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(settings);
		}

		objectOutput.writeInt(status);
		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long exportImportConfigurationId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public int type;
	public String settings;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public long companyId;
}