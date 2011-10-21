/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.dynamicdatalists.model.DDLRecord;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing DDLRecord in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecord
 * @generated
 */
public class DDLRecordCacheModel implements CacheModel<DDLRecord>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", recordId=");
		sb.append(recordId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", versionUserId=");
		sb.append(versionUserId);
		sb.append(", versionUserName=");
		sb.append(versionUserName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", DDMStorageId=");
		sb.append(DDMStorageId);
		sb.append(", recordSetId=");
		sb.append(recordSetId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", displayIndex=");
		sb.append(displayIndex);
		sb.append("}");

		return sb.toString();
	}

	public DDLRecord toEntityModel() {
		DDLRecordImpl ddlRecordImpl = new DDLRecordImpl();

		if (uuid == null) {
			ddlRecordImpl.setUuid(StringPool.BLANK);
		}
		else {
			ddlRecordImpl.setUuid(uuid);
		}

		ddlRecordImpl.setRecordId(recordId);
		ddlRecordImpl.setGroupId(groupId);
		ddlRecordImpl.setCompanyId(companyId);
		ddlRecordImpl.setUserId(userId);

		if (userName == null) {
			ddlRecordImpl.setUserName(StringPool.BLANK);
		}
		else {
			ddlRecordImpl.setUserName(userName);
		}

		ddlRecordImpl.setVersionUserId(versionUserId);

		if (versionUserName == null) {
			ddlRecordImpl.setVersionUserName(StringPool.BLANK);
		}
		else {
			ddlRecordImpl.setVersionUserName(versionUserName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddlRecordImpl.setCreateDate(null);
		}
		else {
			ddlRecordImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddlRecordImpl.setModifiedDate(null);
		}
		else {
			ddlRecordImpl.setModifiedDate(new Date(modifiedDate));
		}

		ddlRecordImpl.setDDMStorageId(DDMStorageId);
		ddlRecordImpl.setRecordSetId(recordSetId);

		if (version == null) {
			ddlRecordImpl.setVersion(StringPool.BLANK);
		}
		else {
			ddlRecordImpl.setVersion(version);
		}

		ddlRecordImpl.setDisplayIndex(displayIndex);

		ddlRecordImpl.resetOriginalValues();

		return ddlRecordImpl;
	}

	public String uuid;
	public long recordId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long versionUserId;
	public String versionUserName;
	public long createDate;
	public long modifiedDate;
	public long DDMStorageId;
	public long recordSetId;
	public String version;
	public int displayIndex;
}