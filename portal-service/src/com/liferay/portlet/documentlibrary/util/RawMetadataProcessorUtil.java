/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLProcessorConstants;

/**
 * Document library processor responsible for the generation of raw metadata
 * associated with all of the the files stored in the document library.
 *
 * <p>
 * This processor automatically and assynchronously extracts the metadata from
 * all of the files stored in the document library. The metadata extraction is
 * done with the help of {@link
 * com.liferay.portal.metadata.TikaRawMetadataProcessor}
 * </p>
 *
 * @author Alexander Chow
 * @author Mika Koivisto
 * @author Miguel Pastor
 */
public class RawMetadataProcessorUtil {

	/**
	 * @deprecated
	 */
	public static void cleanUp(FileEntry fileEntry) {
		getRawMetadataProcessor().cleanUp(fileEntry);
	}

	/**
	 * @deprecated
	 */
	public static void cleanUp(FileVersion fileVersion) {
		getRawMetadataProcessor().cleanUp(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static void generateMetadata(FileVersion fileVersion)
		throws PortalException, SystemException {

		getRawMetadataProcessor().generateMetadata(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static RawMetadataProcessor getRawMetadataProcessor() {
		return _rawMetadataProcessor;
	}

	public static boolean isSupported(FileVersion fileVersion) {
		return getRawMetadataProcessor().isSupported(fileVersion);
	}

	public static boolean isSupported(String mimeType) {
		return getRawMetadataProcessor().isSupported(mimeType);
	}

	/**
	 * @deprecated
	 */
	public static void saveMetadata(FileVersion fileVersion)
		throws PortalException, SystemException {

		getRawMetadataProcessor().saveMetadata(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static void trigger(FileVersion fileVersion) {
		getRawMetadataProcessor().trigger(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public void setRawMetadataProcessor(
		RawMetadataProcessor rawMetadataProcessor) {

		_rawMetadataProcessor = rawMetadataProcessor;
	}

	private static RawMetadataProcessor _rawMetadataProcessor =
		(RawMetadataProcessor)DLProcessorRegistryUtil.getDLProcessor(
			DLProcessorConstants.RAW_METADATA_PROCESSOR);

}