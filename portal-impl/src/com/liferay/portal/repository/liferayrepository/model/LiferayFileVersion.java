/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class LiferayFileVersion extends LiferayModel implements FileVersion {

	public LiferayFileVersion(DLFileVersion dlFileVersion) {
		_dlFileVersion = dlFileVersion;
	}

	public LiferayFileVersion(
		DLFileVersion dlFileVersion, boolean escapedModel) {

		_dlFileVersion = dlFileVersion;
		_escapedModel = escapedModel;
	}

	@Override
	public Object clone() {
		LiferayFileVersion liferayFileVersion = new LiferayFileVersion(
			_dlFileVersion, _escapedModel);

		liferayFileVersion.setCompanyId(getCompanyId());
		liferayFileVersion.setCreateDate(getCreateDate());
		liferayFileVersion.setGroupId(getGroupId());
		liferayFileVersion.setPrimaryKey(getPrimaryKey());
		liferayFileVersion.setUserId(getUserId());
		liferayFileVersion.setUserName(getUserName());

		try {
			liferayFileVersion.setUserUuid(getUserUuid());
		}
		catch (Exception e) {
		}

		liferayFileVersion.setUuid(getUuid());

		return liferayFileVersion;
	}

	public Map<String, Serializable> getAttributes() {
		ExpandoBridge expandoBridge = _dlFileVersion.getExpandoBridge();

		return expandoBridge.getAttributes();
	}

	public String getChangeLog() {
		return _dlFileVersion.getChangeLog();
	}

	@Override
	public long getCompanyId() {
		return _dlFileVersion.getCompanyId();
	}

	public InputStream getContentStream(boolean incrementCounter)
		throws PortalException, SystemException {

		return _dlFileVersion.getContentStream(incrementCounter);
	}

	public Date getCreateDate() {
		return _dlFileVersion.getCreateDate();
	}

	public String getDescription() {
		return _dlFileVersion.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlFileVersion.getExpandoBridge();
	}

	public String getExtension() {
		return _dlFileVersion.getExtension();
	}

	public String getExtraSettings() {
		return _dlFileVersion.getExtraSettings();
	}

	public File getFile(boolean incrementCounter)
		throws PortalException, SystemException {

		return DLFileEntryLocalServiceUtil.getFile(
			PrincipalThreadLocal.getUserId(), _dlFileVersion.getFileEntryId(),
			_dlFileVersion.getVersion(), incrementCounter);
	}

	public FileEntry getFileEntry() throws PortalException, SystemException {
		return new LiferayFileEntry(_dlFileVersion.getFileEntry());
	}

	public long getFileEntryId() {
		return _dlFileVersion.getFileEntryId();
	}

	public long getFileVersionId() {
		return _dlFileVersion.getFileVersionId();
	}

	public long getGroupId() {
		return _dlFileVersion.getGroupId();
	}

	public String getIcon() {
		return _dlFileVersion.getIcon();
	}

	public String getMimeType() {
		return _dlFileVersion.getMimeType();
	}

	public Object getModel() {
		return _dlFileVersion;
	}

	public Class<?> getModelClass() {
		return LiferayFileVersion.class;
	}

	@Override
	public String getModelClassName() {
		return LiferayFileVersion.class.getName();
	}

	public Date getModifiedDate() {
		return _dlFileVersion.getModifiedDate();
	}

	@Override
	public long getPrimaryKey() {
		return _dlFileVersion.getPrimaryKey();
	}

	public Serializable getPrimaryKeyObj() {
		return getPrimaryKey();
	}

	public long getRepositoryId() {
		return _dlFileVersion.getRepositoryId();
	}

	public long getSize() {
		return _dlFileVersion.getSize();
	}

	public int getStatus() {
		return _dlFileVersion.getStatus();
	}

	public long getStatusByUserId() {
		return _dlFileVersion.getStatusByUserId();
	}

	public String getStatusByUserName() {
		return _dlFileVersion.getStatusByUserName();
	}

	public String getStatusByUserUuid() throws SystemException {
		return _dlFileVersion.getStatusByUserUuid();
	}

	public Date getStatusDate() {
		return _dlFileVersion.getStatusDate();
	}

	public String getTitle() {
		return _dlFileVersion.getTitle();
	}

	public DLFolder getTrashContainer() {
		return _dlFileVersion.getTrashContainer();
	}

	public long getUserId() {
		return _dlFileVersion.getUserId();
	}

	public String getUserName() {
		return _dlFileVersion.getUserName();
	}

	public String getUserUuid() throws SystemException {
		return _dlFileVersion.getUserUuid();
	}

	public String getUuid() {
		return _dlFileVersion.getUuid();
	}

	public String getVersion() {
		return _dlFileVersion.getVersion();
	}

	public boolean isApproved() {
		return _dlFileVersion.isApproved();
	}

	public boolean isDefaultRepository() {
		if (_dlFileVersion.getGroupId() == _dlFileVersion.getRepositoryId()) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDraft() {
		return _dlFileVersion.isDraft();
	}

	public boolean isEscapedModel() {
		return _escapedModel;
	}

	public boolean isExpired() {
		return _dlFileVersion.isExpired();
	}

	public boolean isInTrash() {
		return _dlFileVersion.isInTrash();
	}

	public boolean isInTrashContainer() {
		return _dlFileVersion.isInTrashContainer();
	}

	public boolean isPending() {
		return _dlFileVersion.isPending();
	}

	public void setCompanyId(long companyId) {
		_dlFileVersion.setCompanyId(companyId);
	}

	public void setCreateDate(Date date) {
		_dlFileVersion.setCreateDate(date);
	}

	public void setGroupId(long groupId) {
		_dlFileVersion.setGroupId(groupId);
	}

	public void setModifiedDate(Date date) {
	}

	public void setPrimaryKey(long primaryKey) {
		_dlFileVersion.setPrimaryKey(primaryKey);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public void setUserId(long userId) {
		_dlFileVersion.setUserId(userId);
	}

	public void setUserName(String userName) {
		_dlFileVersion.setUserName(userName);
	}

	public void setUserUuid(String userUuid) {
		_dlFileVersion.setUserUuid(userUuid);
	}

	public void setUuid(String uuid) {
		_dlFileVersion.setUuid(uuid);
	}

	public FileVersion toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return new LiferayFileVersion(
				_dlFileVersion.toEscapedModel(), true);
		}
	}

	@Override
	public String toString() {
		return _dlFileVersion.toString();
	}

	public FileVersion toUnescapedModel() {
		if (isEscapedModel()) {
			return new LiferayFileVersion(
				_dlFileVersion.toUnescapedModel(), true);
		}
		else {
			return this;
		}
	}

	private DLFileVersion _dlFileVersion;
	private boolean _escapedModel;

}