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

/**
 * Provides a common interface for all the processors of the document library.
 * All document library processors must implement this interface.
 *
 * A DLProcessor (processor) is responsible for extracting additional metadata
 * or assets from
 * a Documents and Media file entry. Here are a couple examples of such metadata
 * and assets:
 *
 * <ul>
 * <li>
 * Metadata stored in JPEG images or video files.
 * </li>
 * <li>
 * Images to use as previews for PDF or Word documents.
 * </li>
 * </ul>
 *
 * Processors can be defined as OSGi components. To do that, annotate your
 * processor and register it under the type <code>DLProcessor</code>:
 * <pre>
 * {@literal @}Component(service = DLProcessor.class)
 * public class MyDLProcessor implements DLProcessor {
 *
 * }
 * </pre>Implementing classes are responsible for managing any storage required by the
 * generated resources and for providing access to any generated assets. See
 * current implementations for examples.
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
	 * Cleans up any resources that the processor created for the file entry.
	 * Note that all resources for all file versions of this file entry are
	 * permanently deleted.
	 *
	 * @param fileEntry the file entry for which resources are cleaned up
	 */
	public void cleanUp(FileEntry fileEntry);

	/**
	 * Cleans up any resources that the processor created for the given file
	 * version. Note that other resources associated with other file versions
	 * for the same file entry aren't affected; use {@link #cleanUp(FileEntry)}
	 * if you want to clean up everything.
	 *
	 * @param fileVersion the file version for which resources will be cleaned
	 *        up
	 */
	public void cleanUp(FileVersion fileVersion);

	/**
	 * Copies all resources generated for the source file version, reusing them
	 * for destination file version. Note that resources are literally copied,
	 * making the resulting resources independent (i.e., if afterwards the
	 * source file version is deleted, the destination file version resources
	 * aren't affected).
	 *
	 * @param sourceFileVersion the file version to copy resources from
	 * @param destinationFileVersion the file version to copy resources to
	 */
	public void copy(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion);

	public default void destroy() throws Exception {
	}

	/**
	 * Exports any resources generated for the file entry into file entry
	 * element.
	 *
	 * @param  portletDataContext the portlet data context to use during this
	 *         export operation
	 * @param  fileEntry the file entry for which resources are exported
	 * @param  fileEntryElement the file entry element to save resources into
	 * @throws Exception if an error occurred while exporting the file entry
	 *         resources
	 */
	public void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception;

	/**
	 * Returns the processor's type. See {@link
	 * com.liferay.portlet.documentlibrary.model.DLProcessorConstants} for the
	 * set of predefined processor types.
	 *
	 * @return the type of this processor
	 */
	public String getType();

	/**
	 * Imports any existing resources from the file entry or file entry element.
	 * If the portlet data context supports direct binary import (see {@link
	 * PortletDataContext#isPerformDirectBinaryImport()}), the resources are
	 * directly copied from the file entry; otherwise, they're extracted from
	 * the file entry element.
	 *
	 * @param  portletDataContext the portlet data context to use during this
	 *         import operation
	 * @param  fileEntry the file entry to import resources from, if direct
	 *         binary import is supported
	 * @param  importedFileEntry the file entry for which resources are imported
	 * @param  fileEntryElement the file entry element to import resources from,
	 *         if direct binary import is not supported
	 * @throws Exception if an error occurred while importing the file entry
	 *         resources
	 */
	public void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception;

	/**
	 * Returns <code>true</code> if the file version is supported by this
	 * processor.
	 *
	 * @param  fileVersion the file version
	 * @return <code>true</code> if this processor supports the file version;
	 *         <code>false</code> otherwise
	 */
	public boolean isSupported(FileVersion fileVersion);

	/**
	 * Returns <code>true</code> if the given file MIME type is supported by
	 * this processor.
	 *
	 * @param  mimeType the MIME type
	 * @return <code>true</code> if this processor supports the MIME type;
	 *         <code>false</code> otherwise
	 */
	public boolean isSupported(String mimeType);

	/**
	 * Launches the processor's work with respect to the destination file
	 * version.
	 *
	 * @param sourceFileVersion the file version to copy previews and thumbnails
	 *        from (optionally <code>null</code>)
	 * @param destinationFileVersion the latest file version to process
	 */
	public void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion);

}