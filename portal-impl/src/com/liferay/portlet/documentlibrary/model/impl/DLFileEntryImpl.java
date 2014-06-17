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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.Repository;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFileEntryImpl extends DLFileEntryBaseImpl {

	public DLFileEntryImpl() {
	}

	@Override
	public String buildTreePath() throws PortalException {
		DLFolder dlFolder = getFolder();

		return dlFolder.buildTreePath();
	}

	@Override
	public InputStream getContentStream() throws PortalException {
		return getContentStream(getVersion());
	}

	@Override
	public InputStream getContentStream(String version) throws PortalException {
		return DLFileEntryServiceUtil.getFileAsStream(
			getFileEntryId(), version);
	}

	@Override
	public long getDataRepositoryId() {
		return DLFolderConstants.getDataRepositoryId(
			getGroupId(), getFolderId());
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		try {
			DLFileVersion dlFileVersion = getFileVersion();

			return dlFileVersion.getExpandoBridge();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public String getExtraSettings() {
		if (_extraSettingsProperties == null) {
			return super.getExtraSettings();
		}
		else {
			return _extraSettingsProperties.toString();
		}
	}

	@Override
	public UnicodeProperties getExtraSettingsProperties() {
		if (_extraSettingsProperties == null) {
			_extraSettingsProperties = new UnicodeProperties(true);

			try {
				_extraSettingsProperties.load(super.getExtraSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _extraSettingsProperties;
	}

	@Override
	public Map<String, Fields> getFieldsMap(long fileVersionId)
		throws PortalException {

		Map<String, Fields> fieldsMap = new HashMap<String, Fields>();

		DLFileVersion dlFileVersion =
			DLFileVersionLocalServiceUtil.getFileVersion(fileVersionId);

		long fileEntryTypeId = dlFileVersion.getFileEntryTypeId();

		if (fileEntryTypeId <= 0) {
			return fieldsMap;
		}

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			DLFileEntryMetadata dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
					ddmStructure.getStructureId(), fileVersionId);

			Fields fields = StorageEngineUtil.getFields(
				dlFileEntryMetadata.getDDMStorageId());

			fieldsMap.put(ddmStructure.getStructureKey(), fields);
		}

		return fieldsMap;
	}

	@Override
	public DLFileVersion getFileVersion() throws PortalException {
		return getFileVersion(getVersion());
	}

	@Override
	public DLFileVersion getFileVersion(String version) throws PortalException {
		return DLFileVersionLocalServiceUtil.getFileVersion(
			getFileEntryId(), version);
	}

	@Override
	public List<DLFileVersion> getFileVersions(int status) {
		return DLFileVersionLocalServiceUtil.getFileVersions(
			getFileEntryId(), status);
	}

	@Override
	public int getFileVersionsCount(int status) {
		return DLFileVersionLocalServiceUtil.getFileVersionsCount(
			getFileEntryId(), status);
	}

	@Override
	public DLFolder getFolder() throws PortalException {
		if (getFolderId() <= 0) {
			return new DLFolderImpl();
		}

		return DLFolderLocalServiceUtil.getFolder(getFolderId());
	}

	@Override
	public String getIcon() {
		return DLUtil.getFileIcon(getExtension());
	}

	@Override
	public String getIconCssClass() {
		return DLUtil.getFileIconCssClass(getExtension());
	}

	@Override
	public DLFileVersion getLatestFileVersion(boolean trusted)
		throws PortalException {

		if (trusted) {
			return DLFileVersionLocalServiceUtil.getLatestFileVersion(
				getFileEntryId(), false);
		}
		else {
			return DLFileVersionServiceUtil.getLatestFileVersion(
				getFileEntryId());
		}
	}

	@Override
	public Lock getLock() {
		try {
			return LockLocalServiceUtil.getLock(
				DLFileEntry.class.getName(), getFileEntryId());
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getLuceneProperties() {
		UnicodeProperties extraSettingsProps = getExtraSettingsProperties();

		StringBundler sb = new StringBundler(
			extraSettingsProps.entrySet().size() + 4);

		sb.append(FileUtil.stripExtension(getTitle()));
		sb.append(StringPool.SPACE);
		sb.append(getDescription());
		sb.append(StringPool.SPACE);

		for (Map.Entry<String, String> entry : extraSettingsProps.entrySet()) {
			String value = GetterUtil.getString(entry.getValue());

			sb.append(value);
		}

		return sb.toString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(DLFileEntryConstants.getClassName());
	}

	@Override
	public int getStatus() {
		try {
			DLFileVersion dlFileVersion = getFileVersion();

			return dlFileVersion.getStatus();
		}
		catch (Exception e) {
			return WorkflowConstants.STATUS_APPROVED;
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link DLFileVersion#getUserId()}
	 */
	@Deprecated
	@Override
	public long getVersionUserId() {
		long versionUserId = 0;

		try {
			DLFileVersion dlFileVersion = getFileVersion();

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
			DLFileVersion dlFileVersion = getFileVersion();

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
			DLFileVersion dlFileVersion = getFileVersion();

			versionUserUuid = dlFileVersion.getUserUuid();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return versionUserUuid;
	}

	@Override
	public boolean hasLock() {
		try {
			return DLFileEntryServiceUtil.hasFileEntryLock(getFileEntryId());
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isCheckedOut() {
		try {
			return DLFileEntryServiceUtil.isFileEntryCheckedOut(
				getFileEntryId());
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isInHiddenFolder() {
		try {
			long repositoryId = getRepositoryId();

			Repository repository = RepositoryLocalServiceUtil.getRepository(
				repositoryId);

			long dlFolderId = repository.getDlFolderId();

			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(dlFolderId);

			return dlFolder.isHidden();
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isInTrash() {
		if (getStatus() == WorkflowConstants.STATUS_IN_TRASH) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void setExtraSettings(String extraSettings) {
		_extraSettingsProperties = null;

		super.setExtraSettings(extraSettings);
	}

	@Override
	public void setExtraSettingsProperties(
		UnicodeProperties extraSettingsProperties) {

		_extraSettingsProperties = extraSettingsProperties;

		super.setExtraSettings(_extraSettingsProperties.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileEntryImpl.class);

	private UnicodeProperties _extraSettingsProperties;

}