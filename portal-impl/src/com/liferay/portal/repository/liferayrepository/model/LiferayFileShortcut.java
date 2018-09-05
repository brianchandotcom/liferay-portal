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

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
public class LiferayFileShortcut extends LiferayModel implements FileShortcut {

	public LiferayFileShortcut(DLFileShortcut dlFileShortcut) {
		this (dlFileShortcut, dlFileShortcut.isEscapedModel());
	}

	public LiferayFileShortcut(
		DLFileShortcut dlFileShortcut, boolean escapedModel) {

		_dlFileShortcut = dlFileShortcut;
		_escapedModel = escapedModel;
	}

	@Override
	public Object clone() {
		return new LiferayFileShortcut(_dlFileShortcut);
	}

	@Override
	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		return _dlFileShortcutModelResourcePermission.contains(
			permissionChecker, _dlFileShortcut, actionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LiferayFileShortcut)) {
			return false;
		}

		LiferayFileShortcut liferayFileShortcut = (LiferayFileShortcut)obj;

		if (Objects.equals(
				_dlFileShortcut, liferayFileShortcut._dlFileShortcut)) {

			return true;
		}

		return false;
	}

	@Override
	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		repositoryModelOperation.execute(this);
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		ExpandoBridge expandoBridge = getExpandoBridge();

		return expandoBridge.getAttributes();
	}

	@Override
	public long getCompanyId() {
		return _dlFileShortcut.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _dlFileShortcut.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlFileShortcut.getExpandoBridge();
	}

	@Override
	public long getFileShortcutId() {
		return _dlFileShortcut.getFileShortcutId();
	}

	@Override
	public FileVersion getFileVersion() throws PortalException {
		return _dlFileShortcut.getFileVersion();
	}

	@Override
	public Folder getFolder() throws PortalException {
		return _dlFileShortcut.getFolder();
	}

	@Override
	public long getFolderId() {
		return _dlFileShortcut.getFolderId();
	}

	@Override
	public long getGroupId() {
		return _dlFileShortcut.getGroupId();
	}

	@Override
	public Date getLastPublishDate() {
		return _dlFileShortcut.getLastPublishDate();
	}

	@Override
	public Object getModel() {
		return _dlFileShortcut;
	}

	@Override
	public Class<?> getModelClass() {
		return LiferayFileShortcut.class;
	}

	@Override
	public String getModelClassName() {
		return LiferayFileShortcut.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return _dlFileShortcut.getModifiedDate();
	}

	@Override
	public long getPrimaryKey() {
		return _dlFileShortcut.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return getPrimaryKey();
	}

	@Override
	public long getRepositoryId() {
		return _dlFileShortcut.getRepositoryId();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(DLFileShortcutConstants.getClassName());
	}

	@Override
	public long getToFileEntryId() {
		return _dlFileShortcut.getToFileEntryId();
	}

	@Override
	public String getToTitle() {
		return _dlFileShortcut.getToTitle();
	}

	@Override
	public long getUserId() {
		return _dlFileShortcut.getUserId();
	}

	@Override
	public String getUserName() {
		return _dlFileShortcut.getUserName();
	}

	@Override
	public String getUserUuid() {
		return _dlFileShortcut.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _dlFileShortcut.getUuid();
	}

	@Override
	public int hashCode() {
		return _dlFileShortcut.hashCode();
	}

	@Override
	public boolean isEscapedModel() {
		return _escapedModel;
	}

	@Override
	public void setCompanyId(long companyId) {
		_dlFileShortcut.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date createDate) {
		_dlFileShortcut.setCreateDate(createDate);
	}

	@Override
	public void setGroupId(long groupId) {
		_dlFileShortcut.setGroupId(groupId);
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_dlFileShortcut.setLastPublishDate(lastPublishDate);
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_dlFileShortcut.setModifiedDate(modifiedDate);
	}

	public void setPrimaryKey(long primaryKey) {
		_dlFileShortcut.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public void setUserId(long userId) {
		_dlFileShortcut.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_dlFileShortcut.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_dlFileShortcut.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_dlFileShortcut.setUuid(uuid);
	}

	@Override
	public FileShortcut toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}

		return new LiferayFileShortcut(_dlFileShortcut.toEscapedModel(), true);
	}

	@Override
	public FileShortcut toUnescapedModel() {
		if (isEscapedModel()) {
			return new LiferayFileShortcut(
				_dlFileShortcut.toUnescapedModel(), true);
		}

		return this;
	}

	private static volatile ModelResourcePermission<DLFileShortcut>
		_dlFileShortcutModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, LiferayFileShortcut.class,
				"_dlFileShortcutModelResourcePermission",
				"(model.class.name=" + DLFileShortcut.class.getName() + ")",
				true);

	private final DLFileShortcut _dlFileShortcut;
	private final boolean _escapedModel;

}