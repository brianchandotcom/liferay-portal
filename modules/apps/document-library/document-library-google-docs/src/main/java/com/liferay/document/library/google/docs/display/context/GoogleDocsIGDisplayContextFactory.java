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

package com.liferay.document.library.google.docs.display.context;

import com.liferay.document.library.google.docs.util.GoogleDocsMetadataHelper;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService;
import com.liferay.portlet.imagegallerydisplay.display.context.IGDisplayContextFactory;
import com.liferay.portlet.imagegallerydisplay.display.context.IGViewFileVersionDisplayContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = IGDisplayContextFactory.class)
public class GoogleDocsIGDisplayContextFactory
	implements IGDisplayContextFactory {

	@Override
	public IGViewFileVersionDisplayContext getIGViewFileVersionDisplayContext(
		IGViewFileVersionDisplayContext parentIGViewFileVersionDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileShortcut fileShortcut) {

		try {
			long fileEntryId = fileShortcut.getToFileEntryId();

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			FileVersion fileVersion = fileEntry.getFileVersion();

			return getIGViewFileVersionDisplayContext(
				parentIGViewFileVersionDisplayContext, request, response,
				fileVersion);
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to build GoogleDocsDLViewFileVersionDisplayContext " +
					"for shortcut " + fileShortcut.getPrimaryKey(),
				pe);
		}
	}

	@Override
	public IGViewFileVersionDisplayContext getIGViewFileVersionDisplayContext(
		IGViewFileVersionDisplayContext parentIGViewFileVersionDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		GoogleDocsMetadataHelper googleDocsMetadataHelper =
			new GoogleDocsMetadataHelper(
				_ddmStructureLocalService,
				(DLFileVersion)fileVersion.getModel(),
				_dlFileEntryMetadataLocalService, _storageEngine);

		if (googleDocsMetadataHelper.isGoogleDocs()) {
			return new GoogleDocsIGViewFileVersionDisplayContext(
				parentIGViewFileVersionDisplayContext, request, response,
				fileVersion, googleDocsMetadataHelper);
		}

		return parentIGViewFileVersionDisplayContext;
	}

	@Reference(unbind = "-")
	public void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	public void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	@Reference(unbind = "-")
	public void setDLFileEntryMetadataLocalService(
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService) {

		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
	}

	@Reference(unbind = "-")
	public void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	private volatile DDMStructureLocalService _ddmStructureLocalService;
	private volatile DLAppService _dlAppService;
	private volatile DLFileEntryMetadataLocalService
		_dlFileEntryMetadataLocalService;
	private volatile StorageEngine _storageEngine;

}