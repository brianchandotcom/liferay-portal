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

package com.liferay.portal.repository.liferayrepository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class LiferayFileEntry extends LiferayModel implements FileEntry {

	public LiferayFileEntry(DLFileEntry dlFileEntry) {
		_dlFileEntry = dlFileEntry;
	}

	public LiferayFileEntry(DLFileEntry fileEntry, boolean escapedModel) {
		_dlFileEntry = fileEntry;
		_escapedModel = escapedModel;
	}

	@Override
	public Object clone() {
		LiferayFileEntry liferayFileEntry = new LiferayFileEntry(
			_dlFileEntry, _escapedModel);

		FileVersion cachedFileVersion = getCachedFileVersion();

		if (cachedFileVersion != null) {
			liferayFileEntry.setCachedFileVersion(cachedFileVersion);
		}

		liferayFileEntry.setCompanyId(getCompanyId());
		liferayFileEntry.setCreateDate(getCreateDate());
		liferayFileEntry.setGroupId(getGroupId());
		liferayFileEntry.setModifiedDate(getModifiedDate());
		liferayFileEntry.setPrimaryKey(getPrimaryKey());
		liferayFileEntry.setUserId(getUserId());
		liferayFileEntry.setUserName(getUserName());

		try {
			liferayFileEntry.setUserUuid(getUserUuid());
		}
		catch (SystemException se) {
		}

		liferayFileEntry.setUuid(getUuid());

		return liferayFileEntry;
	}

	@Override
	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException {

		return DLFileEntryPermission.contains(
			permissionChecker, _dlFileEntry, actionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LiferayFileEntry)) {
			return false;
		}

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)obj;

		if (Validator.equals(_dlFileEntry, liferayFileEntry._dlFileEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		ExpandoBridge expandoBridge = getExpandoBridge();

		return expandoBridge.getAttributes();
	}

	public FileVersion getCachedFileVersion() {
		if (_dlFileVersion == null) {
			return null;
		}

		return new LiferayFileVersion(_dlFileVersion);
	}

	@Override
	public long getCompanyId() {
		return _dlFileEntry.getCompanyId();
	}

	@Override
	public InputStream getContentStream()
		throws PortalException, SystemException {

		InputStream contentStream = _dlFileEntry.getContentStream();

		try {
			DLAppHelperLocalServiceUtil.getFileAsStream(
				PrincipalThreadLocal.getUserId(), this, true);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return contentStream;
	}

	@Override
	public InputStream getContentStream(String version)
		throws PortalException, SystemException {

		InputStream contentStream = _dlFileEntry.getContentStream(version);

		try {
			DLAppHelperLocalServiceUtil.getFileAsStream(
				PrincipalThreadLocal.getUserId(), this, true);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return contentStream;
	}

	@Override
	public Date getCreateDate() {
		return _dlFileEntry.getCreateDate();
	}

	@Override
	public String getDescription() {
		return _dlFileEntry.getDescription();
	}

	public DLFileEntry getDLFileEntry() {
		return _dlFileEntry;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlFileEntry.getExpandoBridge();
	}

	@Override
	public String getExtension() {
		return _dlFileEntry.getExtension();
	}

	@Override
	public long getFileEntryId() {
		return _dlFileEntry.getFileEntryId();
	}

	@Override
	public FileVersion getFileVersion()
		throws PortalException, SystemException {

		DLFileVersion dlFileVersion = _dlFileVersion;

		if (dlFileVersion == null) {
			dlFileVersion = _dlFileEntry.getFileVersion();
		}

		return new LiferayFileVersion(dlFileVersion);
	}

	@Override
	public FileVersion getFileVersion(String version)
		throws PortalException, SystemException {

		return new LiferayFileVersion(_dlFileEntry.getFileVersion(version));
	}

	@Override
	public List<FileVersion> getFileVersions(int status)
		throws SystemException {

		return toFileVersions(_dlFileEntry.getFileVersions(status));
	}

	@Override
	public Folder getFolder() {
		Folder folder = null;

		try {
			folder = new LiferayFolder(_dlFileEntry.getFolder());
		}
		catch (Exception e) {
			return null;
		}

		return folder;
	}

	@Override
	public long getFolderId() {
		return _dlFileEntry.getFolderId();
	}

	@Override
	public long getGroupId() {
		return _dlFileEntry.getGroupId();
	}

	@Override
	public String getIcon() {
		return _dlFileEntry.getIcon();
	}

	@Override
	public FileVersion getLatestFileVersion()
		throws PortalException, SystemException {

		return getLatestFileVersion(false);
	}

	@Override
	public FileVersion getLatestFileVersion(boolean trusted)
		throws PortalException, SystemException {

		return new LiferayFileVersion(
			_dlFileEntry.getLatestFileVersion(trusted));
	}

	@Override
	public Lock getLock() {
		return _dlFileEntry.getLock();
	}

	@Override
	public String getMimeType() {
		return _dlFileEntry.getMimeType();
	}

	@Override
	public String getMimeType(String version) {
		try {
			DLFileVersion dlFileVersion =
				DLFileVersionLocalServiceUtil.getFileVersion(
					_dlFileEntry.getFileEntryId(), version);

			return dlFileVersion.getMimeType();
		}
		catch (Exception e) {
		}

		return ContentTypes.APPLICATION_OCTET_STREAM;
	}

	@Override
	public Object getModel() {
		return _dlFileEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return LiferayFileEntry.class;
	}

	@Override
	public String getModelClassName() {
		return LiferayFileEntry.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return _dlFileEntry.getModifiedDate();
	}

	@Override
	public long getPrimaryKey() {
		return _dlFileEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return getPrimaryKey();
	}

	@Override
	public int getReadCount() {
		return _dlFileEntry.getReadCount();
	}

	@Override
	public long getRepositoryId() {
		return _dlFileEntry.getRepositoryId();
	}

	@Override
	public long getSize() {
		return _dlFileEntry.getSize();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(DLFileEntryConstants.getClassName());
	}

	@Override
	public String getTitle() {
		return _dlFileEntry.getTitle();
	}

	@Override
	public long getUserId() {
		return _dlFileEntry.getUserId();
	}

	@Override
	public String getUserName() {
		return _dlFileEntry.getUserName();
	}

	@Override
	public String getUserUuid() throws SystemException {
		return _dlFileEntry.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _dlFileEntry.getUuid();
	}

	@Override
	public String getVersion() {
		return _dlFileEntry.getVersion();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link DLFileVersion#getUserId()}
	 */
	@Deprecated
	@Override
	public long getVersionUserId() {
		long versionUserId = 0;

		try {
			DLFileVersion dlFileVersion = _dlFileEntry.getFileVersion();

			versionUserId = dlFileVersion.getUserId();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return versionUserId;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link DLFileVersion#getUserName()}
	 */
	@Deprecated
	@Override
	public String getVersionUserName() {
		String versionUserName = StringPool.BLANK;

		try {
			DLFileVersion dlFileVersion = _dlFileEntry.getFileVersion();

			versionUserName = dlFileVersion.getUserName();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return versionUserName;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link DLFileVersion#getUserUuid()}
	 */
	@Deprecated
	@Override
	public String getVersionUserUuid() {
		String versionUserUuid = StringPool.BLANK;

		try {
			DLFileVersion dlFileVersion = _dlFileEntry.getFileVersion();

			versionUserUuid = dlFileVersion.getUserUuid();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return versionUserUuid;
	}

	@Override
	public int hashCode() {
		return _dlFileEntry.hashCode();
	}

	@Override
	public boolean hasLock() {
		return _dlFileEntry.hasLock();
	}

	@Override
	public boolean isCheckedOut() {
		return _dlFileEntry.isCheckedOut();
	}

	@Override
	public boolean isDefaultRepository() {
		if (_dlFileEntry.getGroupId() == _dlFileEntry.getRepositoryId()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isEscapedModel() {
		return _escapedModel;
	}

	@Override
	public boolean isInTrash() {
		return _dlFileEntry.isInTrash();
	}

	@Override
	public boolean isInTrashContainer() {
		try {
			return _dlFileEntry.isInTrashContainer();
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean isManualCheckInRequired() {
		return _dlFileEntry.isManualCheckInRequired();
	}

	@Override
	public boolean isSupportsLocking() {
		return true;
	}

	@Override
	public boolean isSupportsMetadata() {
		return true;
	}

	@Override
	public boolean isSupportsSocial() {
		return true;
	}

	public void setCachedFileVersion(FileVersion fileVersion) {
		_dlFileVersion = (DLFileVersion)fileVersion.getModel();
	}

	@Override
	public void setCompanyId(long companyId) {
		_dlFileEntry.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
		_dlFileEntry.setCreateDate(date);
	}

	@Override
	public void setGroupId(long groupId) {
		_dlFileEntry.setGroupId(groupId);
	}

	@Override
	public void setModifiedDate(Date date) {
		_dlFileEntry.setModifiedDate(date);
	}

	public void setPrimaryKey(long primaryKey) {
		_dlFileEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public void setUserId(long userId) {
		_dlFileEntry.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_dlFileEntry.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_dlFileEntry.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_dlFileEntry.setUuid(uuid);
	}

	@Override
	public FileEntry toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return new LiferayFileEntry(_dlFileEntry.toEscapedModel(), true);
		}
	}

	@Override
	public String toString() {
		return _dlFileEntry.toString();
	}

	@Override
	public FileEntry toUnescapedModel() {
		if (isEscapedModel()) {
			return new LiferayFileEntry(_dlFileEntry.toUnescapedModel(), true);
		}
		else {
			return this;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LiferayFileEntry.class);

	private DLFileEntry _dlFileEntry;
	private DLFileVersion _dlFileVersion;
	private boolean _escapedModel;

}