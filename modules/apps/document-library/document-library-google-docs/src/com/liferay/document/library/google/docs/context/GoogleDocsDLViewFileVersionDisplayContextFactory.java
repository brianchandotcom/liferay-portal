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
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.context.DLViewFileVersionDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLViewFileVersionDisplayContextFactory;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true, service = DLViewFileVersionDisplayContextFactory.class
)
public class GoogleDocsDLViewFileVersionDisplayContextFactory
	implements DLViewFileVersionDisplayContextFactory {

	@Override
	public DLViewFileVersionDisplayContext
		getDLFileVersionActionsDisplayContext(
			DLViewFileVersionDisplayContext
				parentDLFileEntryActionsDisplayContext,
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		GoogleDocsMetadataHelper googleDocsMetadataHelper =
			new GoogleDocsMetadataHelper(
				(DLFileVersion)fileVersion.getModel(),
				_dlFileEntryMetadataLocalService, _storageEngine);

		if (googleDocsMetadataHelper.isGoogleDocs()) {
			return new GoogleDocsDLViewFileVersionDisplayContext(
				parentDLFileEntryActionsDisplayContext, request, response,
				fileVersion, googleDocsMetadataHelper);
		}

		return parentDLFileEntryActionsDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			DLViewFileVersionDisplayContext
				parentDLViewFileVersionDisplayContext,
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		return getDLFileVersionActionsDisplayContext(
			parentDLViewFileVersionDisplayContext, request, response,
			fileVersion);
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