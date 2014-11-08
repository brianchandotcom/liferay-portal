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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Alexander Chow
 */
public class DLFileVersionImpl extends DLFileVersionBaseImpl {

	@Override
	public String buildTreePath() throws PortalException {
		if (getFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return StringPool.SLASH;
		}

		DLFolder dlFolder = getFolder();

		return dlFolder.buildTreePath();
	}

	@Override
	public InputStream getContentStream(boolean incrementCounter)
		throws PortalException {

		return DLFileEntryLocalServiceUtil.getFileAsStream(
			getFileEntryId(), getVersion(), incrementCounter);
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				getFileEntryTypeId());

		return dlFileEntryType.getDDMStructures();
	}

	@Override
	public DLFileEntryType getDLFileEntryType() throws PortalException {
		return DLFileEntryTypeLocalServiceUtil.getFileEntryType(
			getFileEntryTypeId());
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
				getCompanyId(), DLFileEntry.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
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
	public DLFileEntry getFileEntry() throws PortalException {
		return DLFileEntryLocalServiceUtil.getFileEntry(getFileEntryId());
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

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileVersionImpl.class);

	private transient ExpandoBridge _expandoBridge;
	private UnicodeProperties _extraSettingsProperties;

}