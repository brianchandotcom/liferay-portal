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

package com.liferay.document.library.google.docs.context;

import com.liferay.document.library.google.docs.util.GoogleDocsMetadataHelper;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portlet.documentlibrary.context.DLEditFileEntryDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLEditFileEntryDisplayContextFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true, service = DLEditFileEntryDisplayContextFactory.class
)
public class GoogleDocsDLEditFileEntryDisplayContextFactory
	implements DLEditFileEntryDisplayContextFactory {

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {

		DDMStructure googleDocsDDMStructure =
			GoogleDocsMetadataHelper.getGoogleDocsDDMStructure(dlFileEntryType);

		if (googleDocsDDMStructure != null) {
			return new GoogleDocsDLEditFileEntryDisplayContext(
				parentDLEditFileEntryDisplayContext, request, response,
				dlFileEntryType);
		}

		return parentDLEditFileEntryDisplayContext;
	}

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry) {

		GoogleDocsMetadataHelper googleDocsMetadataHelper =
			new GoogleDocsMetadataHelper(
				(DLFileEntry)fileEntry.getModel(),
				_dlFileEntryMetadataLocalService, _storageEngine);

		if (googleDocsMetadataHelper.isGoogleDocs()) {
			return new GoogleDocsDLEditFileEntryDisplayContext(
				parentDLEditFileEntryDisplayContext, request, response,
				fileEntry);
		}

		return parentDLEditFileEntryDisplayContext;
	}

	@Reference
	public void setDLFileEntryMetadataLocalService(
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService) {

		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
	}

	@Reference
	public void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;
	private StorageEngine _storageEngine;

}