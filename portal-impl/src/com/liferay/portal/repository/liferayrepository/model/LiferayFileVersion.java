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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LiferayFileVersion)) {
			return false;
		}

		LiferayFileVersion liferayFileVersion = (LiferayFileVersion)obj;

		if (Validator.equals(
				_dlFileVersion, liferayFileVersion._dlFileVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		ExpandoBridge expandoBridge = _dlFileVersion.getExpandoBridge();

		return expandoBridge.getAttributes();
	}

	@Override
	public String getChangeLog() {
		return _dlFileVersion.getChangeLog();
	}

	@Override
	public long getCompanyId() {
		return _dlFileVersion.getCompanyId();
	}

	@Override
	public InputStream getContentStream(boolean incrementCounter)
		throws PortalException, SystemException {

		return _dlFileVersion.getContentStream(incrementCounter);
	}

	@Override
	public Date getCreateDate() {
		return _dlFileVersion.getCreateDate();
	}

	@Override
	public String getDescription() {
		return _dlFileVersion.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlFileVersion.getExpandoBridge();
	}

	@Override
	public String getExtension() {
		return _dlFileVersion.getExtension();
	}

	@Override
	public String getExtraSettings() {
		return _dlFileVersion.getExtraSettings();
	}

	public File getFile(boolean incrementCounter)
		throws PortalException, SystemException {

		return DLFileEntryLocalServiceUtil.getFile(
			_dlFileVersion.getFileEntryId(), _dlFileVersion.getVersion(),
			incrementCounter);
	}

	@Override
	public FileEntry getFileEntry() throws PortalException, SystemException {
		return new LiferayFileEntry(_dlFileVersion.getFileEntry());
	}

	@Override
	public long getFileEntryId() {
		return _dlFileVersion.getFileEntryId();
	}

	@Override
	public long getFileVersionId() {
		return _dlFileVersion.getFileVersionId();
	}

	@Override
	public long getGroupId() {
		return _dlFileVersion.getGroupId();
	}

	@Override
	public String getIcon() {
		return _dlFileVersion.getIcon();
	}

	@Override
	public String getMimeType() {
		return _dlFileVersion.getMimeType();
	}

	@Override
	public Object getModel() {
		return _dlFileVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return LiferayFileVersion.class;
	}

	@Override
	public String getModelClassName() {
		return LiferayFileVersion.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return _dlFileVersion.getModifiedDate();
	}

	@Override
	public long getPrimaryKey() {
		return _dlFileVersion.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return getPrimaryKey();
	}

	@Override
	public long getRepositoryId() {
		return _dlFileVersion.getRepositoryId();
	}

	@Override
	public long getSize() {
		return _dlFileVersion.getSize();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(FileVersion.class);
	}

	@Override
	public int getStatus() {
		return _dlFileVersion.getStatus();
	}

	@Override
	public long getStatusByUserId() {
		return _dlFileVersion.getStatusByUserId();
	}

	@Override
	public String getStatusByUserName() {
		return _dlFileVersion.getStatusByUserName();
	}

	@Override
	public String getStatusByUserUuid() throws SystemException {
		return _dlFileVersion.getStatusByUserUuid();
	}

	@Override
	public Date getStatusDate() {
		return _dlFileVersion.getStatusDate();
	}

	@Override
	public String getTitle() {
		return _dlFileVersion.getTitle();
	}

	@Override
	public long getUserId() {
		return _dlFileVersion.getUserId();
	}

	@Override
	public String getUserName() {
		return _dlFileVersion.getUserName();
	}

	@Override
	public String getUserUuid() throws SystemException {
		return _dlFileVersion.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _dlFileVersion.getUuid();
	}

	@Override
	public String getVersion() {
		return _dlFileVersion.getVersion();
	}

	@Override
	public boolean isApproved() {
		return _dlFileVersion.isApproved();
	}

	@Override
	public boolean isDefaultRepository() {
		if (_dlFileVersion.getGroupId() == _dlFileVersion.getRepositoryId()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDraft() {
		return _dlFileVersion.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _escapedModel;
	}

	@Override
	public boolean isExpired() {
		return _dlFileVersion.isExpired();
	}

	@Override
	public boolean isPending() {
		return _dlFileVersion.isPending();
	}

	@Override
	public void setCompanyId(long companyId) {
		_dlFileVersion.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
		_dlFileVersion.setCreateDate(date);
	}

	@Override
	public void setGroupId(long groupId) {
		_dlFileVersion.setGroupId(groupId);
	}

	@Override
	public void setModifiedDate(Date date) {
	}

	public void setPrimaryKey(long primaryKey) {
		_dlFileVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public void setUserId(long userId) {
		_dlFileVersion.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_dlFileVersion.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_dlFileVersion.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_dlFileVersion.setUuid(uuid);
	}

	@Override
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

	@Override
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