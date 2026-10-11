/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Builds the {@link ImageResource} a caller hands to the framework, from
 * whatever form the caller holds the image in.
 *
 * @author Daniel Sanz
 */
@ProviderType
public interface ImageResourceFactory {

	/**
	 * Returns a resource for a document library image.
	 */
	public ImageResource fromFileEntry(FileEntry fileEntry)
		throws PortalException;

}