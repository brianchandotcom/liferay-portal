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

import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

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

	public static void cleanUp(FileEntry fileEntry) {
		RawMetadataProcessor rawMetadataProcessor = getRawMetadataProcessor();

		if (rawMetadataProcessor != null) {
			rawMetadataProcessor.cleanUp(fileEntry);
		}
	}

	public static void cleanUp(FileVersion fileVersion) {
		RawMetadataProcessor rawMetadataProcessor = getRawMetadataProcessor();

		if (rawMetadataProcessor != null) {
			rawMetadataProcessor.cleanUp(fileVersion);
		}
	}

	/**
	 * Generates the raw metadata associated with the file entry.
	 *
	 * @param fileVersion the file version from which the raw metatada is to be
	 *        generated
	 */
	public static void generateMetadata(FileVersion fileVersion)
		throws PortalException {

		RawMetadataProcessor rawMetadataProcessor = getRawMetadataProcessor();

		if (rawMetadataProcessor != null) {
			rawMetadataProcessor.generateMetadata(fileVersion);
		}
	}

	public static RawMetadataProcessor getRawMetadataProcessor() {
		return (RawMetadataProcessor)DLProcessorRegistryUtil.getDLProcessor(
			DLProcessorConstants.RAW_METADATA_PROCESSOR);
	}

	public static boolean isSupported(FileVersion fileVersion) {
		RawMetadataProcessor rawMetadataProcessor = getRawMetadataProcessor();

		if (rawMetadataProcessor == null) {
			return false;
		}

		return rawMetadataProcessor.isSupported(fileVersion);
	}

	public static boolean isSupported(String mimeType) {
		RawMetadataProcessor rawMetadataProcessor = getRawMetadataProcessor();

		if (rawMetadataProcessor == null) {
			return false;
		}

		return rawMetadataProcessor.isSupported(mimeType);
	}

	/**
	 * Saves the raw metadata present in the file version.
	 *
	 * <p>
	 * The raw metadata present in the file version is extracted and persisted
	 * using {@link com.liferay.portal.metadata.TikaRawMetadataProcessor}.
	 * </p>
	 *
	 * @param fileVersion the file version from which the raw metatada is to be
	 *        extracted and persisted
	 */
	public static void saveMetadata(FileVersion fileVersion)
		throws PortalException {

		RawMetadataProcessor rawMetadataProcessor = getRawMetadataProcessor();

		if (rawMetadataProcessor != null) {
			rawMetadataProcessor.saveMetadata(fileVersion);
		}
	}

	/**
	 * Launches extraction of raw metadata from the file version.
	 *
	 * <p>
	 * The raw metadata extraction is done asynchronously.
	 * </p>
	 *
	 * @param fileVersion the latest file version from which the raw metadata is
	 *        to be generated
	 */
	public static void trigger(FileVersion fileVersion) {
		RawMetadataProcessor rawMetadataProcessor = getRawMetadataProcessor();

		if (rawMetadataProcessor != null) {
			rawMetadataProcessor.trigger(fileVersion);
		}
	}

}