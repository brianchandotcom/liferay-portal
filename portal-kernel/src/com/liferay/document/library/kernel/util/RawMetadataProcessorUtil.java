/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * Document library processor responsible for the generation of raw metadata
 * associated with all of the the files stored in the document library.
 *
 * <p>
 * This processor automatically and assynchronously extracts the metadata from
 * all of the files stored in the document library.
 * </p>
 *
 * @author Alexander Chow
 * @author Mika Koivisto
 * @author Miguel Pastor
 */
public class RawMetadataProcessorUtil {

	public static void cleanUp(FileEntry fileEntry) {
		if (_rawMetadataProcessor != null) {
			_rawMetadataProcessor.cleanUp(fileEntry);
		}
	}

	public static void cleanUp(FileVersion fileVersion) {
		if (_rawMetadataProcessor != null) {
			_rawMetadataProcessor.cleanUp(fileVersion);
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

		if (_rawMetadataProcessor != null) {
			_rawMetadataProcessor.generateMetadata(fileVersion);
		}
	}

	public static RawMetadataProcessor getRawMetadataProcessor() {
		return _rawMetadataProcessor;
	}

	public static boolean isSupported(FileVersion fileVersion) {
		if (_rawMetadataProcessor == null) {
			return false;
		}

		return _rawMetadataProcessor.isSupported(fileVersion);
	}

	public static boolean isSupported(String mimeType) {
		if (_rawMetadataProcessor == null) {
			return false;
		}

		return _rawMetadataProcessor.isSupported(mimeType);
	}

	/**
	 * Saves the raw metadata present in the file version.
	 *
	 * @param fileVersion the file version from which the raw metatada is to be
	 *        extracted and persisted
	 */
	public static void saveMetadata(FileVersion fileVersion)
		throws PortalException {

		if (_rawMetadataProcessor != null) {
			_rawMetadataProcessor.saveMetadata(fileVersion);
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
		if (_rawMetadataProcessor != null) {
			_rawMetadataProcessor.trigger(fileVersion);
		}
	}

	private static volatile RawMetadataProcessor _rawMetadataProcessor =
		ServiceProxyFactory.newServiceTrackedInstance(
			RawMetadataProcessor.class, RawMetadataProcessorUtil.class,
			"_rawMetadataProcessor", false);

}