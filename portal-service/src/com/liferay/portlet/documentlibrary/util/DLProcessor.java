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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * Common interface for all the processors of the document library. All
 * document library processors must implement this interface.
 *
 * @author Alexander Chow
 * @author Mika Koivisto
 * @see    AudioProcessor
 * @see    DLPreviewableProcessor
 * @see    com.liferay.portal.kernel.image.ImageProcessor
 * @see    PDFProcessor
 * @see    com.liferay.portal.kernel.metadata.RawMetadataProcessor
 * @see    VideoProcessor
 */
public interface DLProcessor {

	public void cleanUp(FileEntry fileEntry);

	public void cleanUp(FileVersion fileVersion);

	public boolean isSupported(FileVersion fileVersion);

	public boolean isSupported(String mimeType);

	/**
	 * Launches the processor's work with respect to the given file version.
	 *
	 * @param fileVersion the latest file version to process
	 */
	public void trigger(FileVersion fileVersion);

}