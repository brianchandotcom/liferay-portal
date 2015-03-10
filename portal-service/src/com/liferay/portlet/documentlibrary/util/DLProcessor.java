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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.xml.Element;

/**
 * Common interface for all the processors of the document library. All document
 * library processors must implement this interface.
 *
 * A DLProcessor is responsible of extracting additional metadata or assets from
 * a Documents and Media file entry. For example:
 *
 * <ul>
 * <li>
 * Images to use as previews for PDF or Word documents.
 * </li>
 * <li>
 * Metadata stored in JPEG images or video files.
 * </li>
 * </ul>
 *
 * DLProcessors can be defined as OSGi components. To do that, annotate your
 * DLProcessor and register it under the type DLProcessor:
 * <pre>
 * {@literal @}Component(service = DLProcessor.class)
 * public class MyDLProcessor implements DLProcessor {
 *
 * }
 * </pre>
 * Implementing subclasses are responsible of managing any storage required
 * by the generated resources, and to provide access to any generated assets.
 * See current implementations for examples.
 *
 * @author Alexander Chow
 * @author Mika Koivisto
 * @see    AudioProcessor
 * @see    DLPreviewableProcessor
 * @see    ImageProcessor
 * @see    PDFProcessor
 * @see    RawMetadataProcessor
 * @see    VideoProcessor
 */
public interface DLProcessor {

	public void afterPropertiesSet() throws Exception;

	/**
	 * Cleans up any resources created for the given file entry by this
	 * processor. Note that all resources for all file versions of this file
	 * entry will be permanently deleted.
	 *
	 * @param fileEntry the file entry for which resources will be cleaned up
	 */
	public void cleanUp(FileEntry fileEntry);

	/**
	 * Cleans up any resources created for the given file version by this
	 * processor. Not that other resources associated with other file versions
	 * of the same file entry won't be affected; use {@link #cleanUp(FileEntry)}
	 * if you want to clean up everything.
	 *
	 * @param fileVersion the file version for which resources will be cleaned
	 *                    up
	 */
	public void cleanUp(FileVersion fileVersion);

	/**
	 * Copies all resources generated for sourceFileVersion, reusing them for
	 * destinationFileVersion. Note that resources will be literally copied, so
	 * that the resulting resources will be independent (i.e. if afterwards
	 * sourceFileVersion is deleted, destinationFileVersion resources won't be
	 * affected).
	 *
	 * @param sourceFileVersion the file version to copy resources from
	 * @param destinationFileVersion the file version to copy resources to
	 */
	public void copy(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion);

	/**
	 * Exports any resources generated for fileEntry into fileEntryElement.
	 *
	 * @param  portletDataContext the portlet data context to use during this
	 *         export operation
	 * @param  fileEntry the file entry for which resources will be exported
	 * @param  fileEntryElement the file entry element to save resources into
	 * @throws Exception if an error occurs while exporting the file entry
	 *         resources
	 */
	public void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception;

	/**
	 * Returns the type of this processor. See {@link
	 * com.liferay.portlet.documentlibrary.model.DLProcessorConstants} for the
	 * set of predefined types.
	 *
	 * @return the type of this processor
	 */
	public String getType();

	/**
	 * Imports any existing resources from the given file entry or file entry
	 * element. If the PortletDataContext supports direct binary import (see
	 * {@link PortletDataContext#isPerformDirectBinaryImport()}), the resources
	 * will be directly copied from fileEntry; otherwise, they'll be extracted
	 * from fileEntryElement.
	 *
	 * @param  portletDataContext the portlet data context to use during this
	 *         import operation
	 * @param  fileEntry the file entry to import resources from if direct
	 *         binary import is supported
	 * @param  importedFileEntry the file entry for which resources will be
	 *         imported
	 * @param  fileEntryElement the file entry element to import resources from
	 *         if direct binary import is not supported
	 * @throws Exception if an error occurs while importing the file entry
	 *         resources
	 */
	public void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception;

	/**
	 * Returns <code>true</code> if the given file version is supported by this
	 * processor.
	 *
	 * @param  fileVersion the file version
	 * @return <code>true</code> if this processor supports the file version,
	 *         <code>false</code> otherwise.
	 */
	public boolean isSupported(FileVersion fileVersion);

	/**
	 * Returns <code>true</code> if the given file MIME type is supported by
	 * this processor.
	 *
	 * @param  mimeType the MIME type
	 * @return <code>true</code> if this processor supports the MIME type,
	 *         <code>false</code> otherwise.
	 */
	public boolean isSupported(String mimeType);

	/**
	 * Launches the processor's work with respect to the given file version.
	 *
	 * @param sourceFileVersion the file version to copy previews and thumbnails
	 *        from (optionally <code>null</code>)
	 * @param destinationFileVersion the latest file version to process
	 */
	public void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion);

}