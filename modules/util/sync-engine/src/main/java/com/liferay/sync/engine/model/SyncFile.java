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

package com.liferay.sync.engine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import com.liferay.sync.engine.service.persistence.BasePersistenceImpl;
import com.liferay.sync.engine.util.JSONUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shinn Lok
 */
@DatabaseTable(daoClass = BasePersistenceImpl.class, tableName = "SyncFile")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncFile extends StateAwareModel {

	public static final String EVENT_ADD = "add";

	public static final String EVENT_DELETE = "delete";

	public static final String EVENT_GET = "get";

	public static final String EVENT_MOVE = "move";

	public static final String EVENT_RESTORE = "restore";

	public static final String EVENT_TRASH = "trash";

	public static final String EVENT_UPDATE = "update";

	public static final int STATE_ERROR = 2;

	public static final int STATE_IN_PROGRESS = 1;

	public static final int STATE_SYNCED = 0;

	public static final int STATE_UNSYNCED = 3;

	public static final String TYPE_FILE = "file";

	public static final String TYPE_FOLDER = "folder";

	public static final String TYPE_PRIVATE_WORKING_COPY = "privateWorkingCopy";

	public static final String TYPE_SYSTEM = "system";

	public static final int UI_EVENT_ACCESS_DENIED_LOCAL = 28;

	public static final int UI_EVENT_ADDED_LOCAL = 1;

	public static final int UI_EVENT_ADDED_REMOTE = 2;

	public static final int UI_EVENT_DELETED_LOCAL = 3;

	public static final int UI_EVENT_DELETED_REMOTE = 4;

	public static final int UI_EVENT_DOWNLOADED_NEW = 5;

	public static final int UI_EVENT_DOWNLOADED_UPDATE = 6;

	public static final int UI_EVENT_DOWNLOADING = 7;

	public static final int UI_EVENT_DUPLICATE_LOCK = 8;

	public static final int UI_EVENT_EXCEEDED_SIZE_LIMIT = 9;

	public static final int UI_EVENT_FILE_NAME_TOO_LONG = 10;

	public static final int UI_EVENT_INVALID_FILE_EXTENSION = 23;

	public static final int UI_EVENT_INVALID_FILE_NAME = 11;

	public static final int UI_EVENT_INVALID_PERMISSIONS = 12;

	public static final int UI_EVENT_MOVED_LOCAL = 13;

	public static final int UI_EVENT_MOVED_REMOTE = 14;

	public static final int UI_EVENT_PARENT_MISSING = 24;

	public static final int UI_EVENT_RENAMED_LOCAL = 21;

	public static final int UI_EVENT_RENAMED_REMOTE = 22;

	public static final int UI_EVENT_RESTORED_LOCAL = 26;

	public static final int UI_EVENT_RESTORED_REMOTE = 27;

	public static final int UI_EVENT_RESYNCING = 28;

	public static final int UI_EVENT_TRASHED_LOCAL = 15;

	public static final int UI_EVENT_TRASHED_REMOTE = 16;

	public static final int UI_EVENT_UPDATED_LOCAL = 17;

	public static final int UI_EVENT_UPDATED_REMOTE = 18;

	public static final int UI_EVENT_UPLOAD_EXCEPTION = 25;

	public static final int UI_EVENT_UPLOADED = 19;

	public static final int UI_EVENT_UPLOADING = 20;

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof SyncFile)) {
			return false;
		}

		SyncFile syncFile = (SyncFile)object;

		if (syncFile.getSyncFileId() == syncFileId) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getChangeLog() {
		return changeLog;
	}

	public String getChecksum() {
		return checksum;
	}

	public long getCompanyId() {
		return companyId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public String getDescription() {
		return description;
	}

	public String getEvent() {
		return event;
	}

	public String getExtension() {
		return extension;
	}

	public String getExtraSettings() {
		return extraSettings;
	}

	public String getFilePathName() {
		return filePathName;
	}

	public String getLocalExtraSettings() {
		return localExtraSettings;
	}

	public String getLocalExtraSettingValue(String key) {
		if (localExtraSettings == null) {
			return null;
		}

		try {
			JsonNode jsonNode = JSONUtil.readTree(localExtraSettings);

			JsonNode valueJsonNode = jsonNode.get(key);

			return valueJsonNode.asText();
		}
		catch (Exception e) {
			return null;
		}
	}

	public long getLocalSyncTime() {
		return localSyncTime;
	}

	public long getLockExpirationDate() {
		return lockExpirationDate;
	}

	public long getLockUserId() {
		return lockUserId;
	}

	public String getLockUserName() {
		return lockUserName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public long getModifiedTime() {
		return modifiedTime;
	}

	public String getName() {
		return name;
	}

	public long getParentFolderId() {
		return parentFolderId;
	}

	public long getPreviousModifiedTime() {
		return previousModifiedTime;
	}

	public long getRepositoryId() {
		return repositoryId;
	}

	public long getSize() {
		return size;
	}

	public long getSyncAccountId() {
		return syncAccountId;
	}

	public long getSyncFileId() {
		return syncFileId;
	}

	public String getType() {
		return type;
	}

	public long getTypePK() {
		return typePK;
	}

	public String getTypeUuid() {
		return typeUuid;
	}

	public long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getVersion() {
		return version;
	}

	public long getVersionId() {
		return versionId;
	}

	@Override
	public int hashCode() {
		return (int)(syncFileId ^ (syncFileId >>> 32));
	}

	public boolean isFile() {
		return type.equals(TYPE_FILE);
	}

	public boolean isFolder() {
		if (!isFile() && !isPrivateWorkingCopy()) {
			return true;
		}

		return false;
	}

	public boolean isPrivateWorkingCopy() {
		return type.equals(TYPE_PRIVATE_WORKING_COPY);
	}

	public boolean isSystem() {
		return type.equals(TYPE_SYSTEM);
	}

	public boolean isUnsynced() {
		if (state == STATE_UNSYNCED) {
			return true;
		}

		return false;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setExtraSettings(String extraSettings) {
		this.extraSettings = extraSettings;
	}

	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}

	public void setLocalExtraSetting(String key, Object value)
		throws IOException {

		Map<String, Object> map = new HashMap();

		if (localExtraSettings != null) {
			map = JSONUtil.readValue(localExtraSettings, Map.class);
		}

		map.put(key, value);

		localExtraSettings = JSONUtil.writeValueAsString(map);
	}

	public void setLocalExtraSettings(String localExtraSettings) {
		this.localExtraSettings = localExtraSettings;
	}

	public void setLocalSyncTime(long localSyncTime) {
		this.localSyncTime = localSyncTime;
	}

	public void setLockExpirationDate(long lockExpirationDate) {
		this.lockExpirationDate = lockExpirationDate;
	}

	public void setLockUserId(long lockUserId) {
		this.lockUserId = lockUserId;
	}

	public void setLockUserName(String lockUserName) {
		this.lockUserName = lockUserName;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public void setModifiedTime(long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentFolderId(long parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public void setPreviousModifiedTime(long previousModifiedTime) {
		this.previousModifiedTime = previousModifiedTime;
	}

	public void setRepositoryId(long repositoryId) {
		this.repositoryId = repositoryId;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setSyncAccountId(long syncAccountId) {
		this.syncAccountId = syncAccountId;
	}

	public void setSyncFileId(long syncFileId) {
		this.syncFileId = syncFileId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypePK(long typePK) {
		this.typePK = typePK;
	}

	public void setTypeUuid(String typeUuid) {
		this.typeUuid = typeUuid;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setVersionId(long versionId) {
		this.versionId = versionId;
	}

	public void unsetLocalExtraSetting(String key) throws IOException {
		if (localExtraSettings == null) {
			return;
		}

		Map map = JSONUtil.readValue(localExtraSettings, Map.class);

		map.remove(key);

		if (map.isEmpty()) {
			localExtraSettings = null;
		}
		else {
			localExtraSettings = JSONUtil.writeValueAsString(map);
		}
	}

	@DatabaseField(defaultValue = "", useGetSet = true)
	protected String changeLog;

	@DatabaseField(index = true, useGetSet = true)
	protected String checksum;

	@DatabaseField(useGetSet = true)
	protected long companyId;

	@DatabaseField(useGetSet = true)
	protected long createTime;

	@DatabaseField(defaultValue = "", useGetSet = true, width = 16777216)
	protected String description;

	@DatabaseField(persisted = false)
	protected String event;

	@DatabaseField(useGetSet = true)
	protected String extension;

	@DatabaseField(useGetSet = true, width = 16777216)
	protected String extraSettings;

	@DatabaseField(uniqueIndex = true, useGetSet = true, width = 16777216)
	protected String filePathName;

	@DatabaseField(useGetSet = true, width = 16777216)
	protected String localExtraSettings;

	@DatabaseField(useGetSet = true)
	protected long localSyncTime;

	@DatabaseField(useGetSet = true)
	protected long lockExpirationDate;

	@DatabaseField(useGetSet = true)
	protected long lockUserId;

	@DatabaseField(useGetSet = true)
	protected String lockUserName;

	@DatabaseField(useGetSet = true)
	protected String mimeType;

	@DatabaseField(useGetSet = true)
	protected long modifiedTime;

	@DatabaseField(useGetSet = true)
	protected String name;

	@DatabaseField(useGetSet = true)
	protected long parentFolderId;

	@DatabaseField(useGetSet = true)
	protected long previousModifiedTime;

	@DatabaseField(useGetSet = true)
	protected long repositoryId;

	@DatabaseField(useGetSet = true)
	protected long size;

	@DatabaseField(index = true, useGetSet = true)
	protected long syncAccountId;

	@DatabaseField(generatedId = true, useGetSet = true)
	protected long syncFileId;

	@DatabaseField(useGetSet = true)
	protected String type;

	@DatabaseField(index = true, useGetSet = true)
	protected long typePK;

	@DatabaseField(useGetSet = true)
	protected String typeUuid;

	@DatabaseField(useGetSet = true)
	protected long userId;

	@DatabaseField(useGetSet = true)
	protected String userName;

	@DatabaseField(useGetSet = true)
	protected String version;

	@DatabaseField(useGetSet = true)
	protected long versionId;

}