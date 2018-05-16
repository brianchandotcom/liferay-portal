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

package com.liferay.media.object.apio.internal.helper;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.media.object.apio.internal.architect.form.MediaObjectCreatorForm;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides helper method for creating fileEntries from different Apio forms.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true, service = MediaObjectHelper.class)
public class MediaObjectHelper {

	public FileEntry addFileEntry(
			long repositoryId, long folderId,
			MediaObjectCreatorForm mediaObjectCreatorForm)
		throws PortalException {

		String sourceFileName = mediaObjectCreatorForm.getName();
		BinaryFile binaryFile = mediaObjectCreatorForm.getBinaryFile();

		String mimeType = binaryFile.getMimeType();

		String title = mediaObjectCreatorForm.getTitle();
		String description = mediaObjectCreatorForm.getDescription();
		String changelog = mediaObjectCreatorForm.getChangelog();

		InputStream inputStream = binaryFile.getInputStream();
		long size = binaryFile.getSize();

		return _dlAppService.addFileEntry(
			repositoryId, folderId, sourceFileName, mimeType, title,
			description, changelog, inputStream, size, new ServiceContext());
	}

	@Reference
	private DLAppService _dlAppService;

}