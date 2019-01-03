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

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides helper methods for creating file entries from different Apio forms.
 *
 * @author Eduardo Pérez
 */
@Component(immediate = true, service = MediaObjectHelper.class)
public class MediaObjectHelper {

	/**
	 * Adds a file entry and associated metadata, based on a {@link
	 * MediaObjectCreatorForm}.
	 *
	 * @param  repositoryId the ID of the repository in which to add the file
	 *         entry
	 * @param  folderId the ID of the folder in which to add the file entry
	 * @param  mediaObjectCreatorForm the form with the new file entry data
	 * @return the new file entry
	 * @throws PortalException if an error occurred while adding the file entry
	 */
	public FileEntry addFileEntry(
			long repositoryId, long folderId,
			MediaObjectCreatorForm mediaObjectCreatorForm)
		throws PortalException {

		BinaryFile binaryFile = mediaObjectCreatorForm.getBinaryFile();

		String binaryFileName = binaryFile.getName();

		Optional<String> titleOptional =
			mediaObjectCreatorForm.getTitleOptional();

		String title = titleOptional.orElse(binaryFileName);

		ServiceContext serviceContext =
			mediaObjectCreatorForm.getServiceContext(repositoryId);

		return _dlAppService.addFileEntry(
			repositoryId, folderId, binaryFileName, binaryFile.getMimeType(),
			title, mediaObjectCreatorForm.getDescription(), null,
			binaryFile.getInputStream(), binaryFile.getSize(), serviceContext);
	}

	@Reference
	private DLAppService _dlAppService;

}