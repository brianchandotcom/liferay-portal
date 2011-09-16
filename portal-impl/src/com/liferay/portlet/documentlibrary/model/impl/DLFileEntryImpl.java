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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFileEntryImpl extends DLFileEntryBaseImpl {

	public DLFileEntryImpl() {
	}

	public InputStream getContentStream()
		throws PortalException, SystemException {

		return getContentStream(getVersion());
	}

	public InputStream getContentStream(String version)
		throws PortalException, SystemException {

		return DLFileEntryServiceUtil.getFileAsStream(
			getFileEntryId(), version);
	}

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

	public Map<String, Fields> getFileEntryMetadataFields(
		long fileEntryTypeId, long fileVersionId, long ddmStructureId)
		throws PortalException, SystemException {

		Map<String, Fields> fieldsMap = new HashMap<String, Fields>();
		List<DDMStructure> ddmStructures = null;

		if (fileEntryTypeId > 0) {
			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeServiceUtil.getFileEntryType(fileEntryTypeId);

			ddmStructures = dlFileEntryType.getDDMStructures();

			for (DDMStructure ddmStructure : ddmStructures) {
				DLFileEntryMetadata dlFileEntryMetadata =
					DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
						ddmStructure.getStructureId(), fileVersionId);
				Fields fields =
					StorageEngineUtil.getFields(dlFileEntryMetadata.getDDMStorageId());
				fieldsMap.put(ddmStructure.getStructureKey(), fields);
			}
		}
		return fieldsMap;
	}
	
	public DLFileVersion getFileVersion()
		throws PortalException, SystemException {

		return getFileVersion(getVersion());
	}

	public DLFileVersion getFileVersion(String version)
		throws PortalException, SystemException {

		return DLFileVersionLocalServiceUtil.getFileVersion(
			getFileEntryId(), version);
	}

	public List<DLFileVersion> getFileVersions(int status)
		throws SystemException {

		return DLFileVersionLocalServiceUtil.getFileVersions(
			getFileEntryId(), status);
	}

	public DLFolder getFolder() {
		DLFolder dlFolder = null;

		if (getFolderId() > 0) {
			try {
				dlFolder = DLFolderLocalServiceUtil.getFolder(getFolderId());
			}
			catch (Exception e) {
				dlFolder = new DLFolderImpl();

				_log.error(e, e);
			}
		}
		else {
			dlFolder = new DLFolderImpl();
		}

		return dlFolder;
	}

	public String getIcon() {
		return DLUtil.getFileIcon(getExtension());
	}

	public String getImageType() {
		if (_imageType == null) {
			try {
				Image largeImage = ImageLocalServiceUtil.getImage(
					getLargeImageId());

				if (largeImage != null) {
					_imageType = largeImage.getType();
				}
				else {
					_imageType = StringPool.BLANK;
				}
			}
			catch (Exception e) {
				_imageType = StringPool.BLANK;

				_log.error(e);
			}
		}

		return _imageType;
	}

	public DLFileVersion getLatestFileVersion()
		throws PortalException, SystemException {

		return DLFileVersionServiceUtil.getLatestFileVersion(getFileEntryId());
	}

	public Lock getLock() {
		try {
			return LockLocalServiceUtil.getLock(
				DLFileEntry.class.getName(), getFileEntryId());
		}
		catch (Exception e) {
		}

		return null;
	}

	public String getLuceneProperties() {
		UnicodeProperties extraSettingsProps = getExtraSettingsProperties();

		Iterator<Map.Entry<String, String>> itr =
			extraSettingsProps.entrySet().iterator();

		StringBundler sb = new StringBundler(
			extraSettingsProps.entrySet().size() + 4);

		sb.append(FileUtil.stripExtension(getTitle()));
		sb.append(StringPool.SPACE);
		sb.append(getDescription());
		sb.append(StringPool.SPACE);

		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();

			String value = GetterUtil.getString(entry.getValue());

			sb.append(value);
		}

		return sb.toString();
	}

	public String getNameWithExtension() {
		String nameWithExtension = getName();

		if (Validator.isNull(nameWithExtension)) {
			nameWithExtension = String.valueOf(getFileEntryId());
		}

		String type = getImageType();

		return getNameWithExtension(nameWithExtension, type);
	}

	public static String getNameWithExtension(String name, String type) {
		if (Validator.isNotNull(type)) {
			name = name.concat(StringPool.PERIOD).concat(type);
		}

		return name;
	}

	public boolean hasLock() {
		try {
			return DLFileEntryServiceUtil.hasFileEntryLock(getFileEntryId());
		}
		catch (Exception e) {
		}

		return false;
	}

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
	public void setExtraSettings(String extraSettings) {
		_extraSettingsProperties = null;

		super.setExtraSettings(extraSettings);
	}

	public void setExtraSettingsProperties(
		UnicodeProperties extraSettingsProperties) {

		_extraSettingsProperties = extraSettingsProperties;

		super.setExtraSettings(_extraSettingsProperties.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileEntryImpl.class);

	private UnicodeProperties _extraSettingsProperties = null;
	private String _imageType;

}