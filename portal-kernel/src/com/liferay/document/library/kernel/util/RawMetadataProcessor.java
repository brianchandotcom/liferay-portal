/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

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
public interface RawMetadataProcessor {

	public void cleanUp(FileEntry fileEntry);

	public void cleanUp(FileVersion fileVersion);

	/**
	 * Generates the raw metadata associated with the file entry.
	 *
	 * @param fileVersion the file version from which the raw metatada is to be
	 *        generated
	 */
	public void generateMetadata(FileVersion fileVersion)
		throws PortalException;

	public boolean isSupported(FileVersion fileVersion);

	public boolean isSupported(String mimeType);

	/**
	 * Saves the raw metadata present in the file version.
	 *
	 * @param fileVersion the file version from which the raw metatada is to be
	 *        extracted and persisted
	 */
	public void saveMetadata(FileVersion fileVersion) throws PortalException;

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
	public void trigger(FileVersion fileVersion);

}