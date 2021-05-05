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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.view.count.ViewCountManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFileEntryImpl extends DLFileEntryBaseImpl {

	@Override
	public String buildTreePath() throws PortalException {
		if (getFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return StringPool.SLASH;
		}

		DLFolder dlFolder = getFolder();

		return dlFolder.buildTreePath();
	}

	@Override
	public InputStream getContentStream() throws PortalException {
		return getContentStream(getVersion());
	}

	@Override
	public InputStream getContentStream(String version) throws PortalException {
		return DLFileEntryLocalServiceUtil.getFileAsStream(
			getFileEntryId(), version);
	}

	@Override
	public long getDataRepositoryId() {
		return DLFolderConstants.getDataRepositoryId(
			getGroupId(), getFolderId());
	}

	@Override
	public Map<String, DDMFormValues> getDDMFormValuesMap(long fileVersionId)
		throws PortalException {

		Map<String, DDMFormValues> ddmFormValuesMap = new HashMap<>();

		DLFileVersion dlFileVersion =
			DLFileVersionLocalServiceUtil.getFileVersion(fileVersionId);

		long fileEntryTypeId = dlFileVersion.getFileEntryTypeId();

		if (fileEntryTypeId <= 0) {
			return ddmFormValuesMap;
		}

		DLFileEntryType dlFileEntryType = getDLFileEntryType();

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			DLFileEntryMetadata dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
					ddmStructure.getStructureId(), fileVersionId);

			if (dlFileEntryMetadata != null) {
				DDMFormValues ddmFormValues =
					StorageEngineManagerUtil.getDDMFormValues(
						dlFileEntryMetadata.getDDMStorageId());

				ddmFormValuesMap.put(
					ddmStructure.getStructureKey(), ddmFormValues);
			}
		}

		return ddmFormValuesMap;
	}

	@Override
	public DLFileEntryType getDLFileEntryType() throws PortalException {
		return DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(
			getFileEntryTypeId());
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		try {
			DLFileVersion dlFileVersion = getFileVersion();

			return dlFileVersion.getExpandoBridge();
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return null;
	}

	@Override
	public String getExtraSettings() {
		if (_extraSettingsUnicodeProperties == null) {
			return super.getExtraSettings();
		}

		return _extraSettingsUnicodeProperties.toString();
	}

	@Override
	public UnicodeProperties getExtraSettingsProperties() {
		if (_extraSettingsUnicodeProperties == null) {
			_extraSettingsUnicodeProperties = new UnicodeProperties(true);

			try {
				_extraSettingsUnicodeProperties.load(super.getExtraSettings());
			}
			catch (IOException ioException) {
				_log.error(ioException, ioException);
			}
		}

		return _extraSettingsUnicodeProperties;
	}

	@Override
	public List<DLFileShortcut> getFileShortcuts() {
		return DLFileShortcutLocalServiceUtil.getFileShortcuts(
			getFileEntryId());
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

		return DLFileVersionServiceUtil.getLatestFileVersion(getFileEntryId());
	}

	@Override
	public Lock getLock() {
		return LockManagerUtil.fetchLock(
			DLFileEntry.class.getName(), getFileEntryId());
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public String getLuceneProperties() {
		UnicodeProperties extraSettingsUnicodeProperties =
			getExtraSettingsProperties();

		Set<Map.Entry<String, String>> entrySet =
			extraSettingsUnicodeProperties.entrySet();

		StringBundler sb = new StringBundler(entrySet.size() + 4);

		sb.append(FileUtil.stripExtension(getTitle()));
		sb.append(StringPool.SPACE);
		sb.append(getDescription());
		sb.append(StringPool.SPACE);

		for (Map.Entry<String, String> entry :
				extraSettingsUnicodeProperties.entrySet()) {

			String value = GetterUtil.getString(entry.getValue());

			sb.append(value);
		}

		return sb.toString();
	}

	@Override
	public long getReadCount() {
		return ViewCountManagerUtil.getViewCount(
			getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(DLFileEntry.class),
			getPrimaryKey());
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return WorkflowConstants.STATUS_APPROVED;
		}
	}

	@Override
	public boolean hasLock() {
		return DLFileEntryLocalServiceUtil.hasFileEntryLock(
			PrincipalThreadLocal.getUserId(), getFileEntryId(), getFolderId());
	}

	@Override
	public boolean isCheckedOut() {
		try {
			return DLFileEntryServiceUtil.isFileEntryCheckedOut(
				getFileEntryId());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return false;
	}

	@Override
	public boolean isInHiddenFolder() {
		try {
			long repositoryId = getRepositoryId();

			if (getGroupId() == repositoryId) {
				return false;
			}

			Repository repository = RepositoryLocalServiceUtil.getRepository(
				repositoryId);

			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(
				repository.getDlFolderId());

			return dlFolder.isHidden();
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return false;
	}

	@Override
	public boolean isInTrash() {
		if (getStatus() == WorkflowConstants.STATUS_IN_TRASH) {
			return true;
		}

		return false;
	}

	@Override
	public void setExtraSettings(String extraSettings) {
		_extraSettingsUnicodeProperties = null;

		super.setExtraSettings(extraSettings);
	}

	@Override
	public void setExtraSettingsProperties(
		UnicodeProperties extraSettingsUnicodeProperties) {

		_extraSettingsUnicodeProperties = extraSettingsUnicodeProperties;

		super.setExtraSettings(_extraSettingsUnicodeProperties.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryImpl.class);

	private UnicodeProperties _extraSettingsUnicodeProperties;

}