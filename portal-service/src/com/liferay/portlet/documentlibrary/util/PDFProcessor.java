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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.image.ImageProcessor;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

/**
 * @author Sergio González
 */
public interface PDFProcessor {

	public static final String PREVIEW_TYPE = ImageProcessor.TYPE_PNG;

	public static final String THUMBNAIL_TYPE = ImageProcessor.TYPE_PNG;

	public String getGlobalSearchPath() throws Exception;

	public void generateImages(FileVersion fileVersion)
		throws Exception;

	public InputStream getPreviewAsStream(FileVersion fileVersion, int index)
		throws Exception;

	public int getPreviewFileCount(FileVersion fileVersion);

	public long getPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception;

	public InputStream getThumbnailAsStream(
			FileVersion fileVersion, int thumbnailIndex)
		throws Exception;

	public long getThumbnailFileSize(
			FileVersion fileVersion, int thumbnailIndex)
		throws Exception;

	public boolean hasImages(FileVersion fileVersion);

	public boolean isDocumentSupported(FileVersion fileVersion);

	public boolean isDocumentSupported(String mimeType);

	public boolean isImageMagickEnabled() throws Exception;

	public void reset() throws Exception;

	public boolean isSupported(String mimeType);

	public void trigger(FileVersion fileVersion);

}