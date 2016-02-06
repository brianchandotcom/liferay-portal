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

package com.liferay.document.library.kernel.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.xml.Element;

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public interface ImageProcessor {

	public void cleanUp(FileEntry fileEntry);

	public void cleanUp(FileVersion fileVersion);

	public void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception;

	public void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception;

	public Set<String> getImageMimeTypes();

	public InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception;

	public long getPreviewFileSize(FileVersion fileVersion) throws Exception;

	public String getPreviewType(FileVersion fileVersion);

	public InputStream getThumbnailAsStream(FileVersion fileVersion, int index)
		throws Exception;

	public long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception;

	public String getThumbnailType(FileVersion fileVersion);

	public boolean hasImages(FileVersion fileVersion);

	public void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception;

	public boolean isImageSupported(FileVersion fileVersion);

	public boolean isImageSupported(String mimeType);

	public boolean isSupported(String mimeType);

	public void storeThumbnail(
			long companyId, long groupId, long fileEntryId, long fileVersionId,
			long custom1ImageId, long custom2ImageId, InputStream is,
			String type)
		throws Exception;

	public void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion);

}