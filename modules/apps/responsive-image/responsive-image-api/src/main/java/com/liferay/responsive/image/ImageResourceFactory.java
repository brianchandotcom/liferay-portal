/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Sanz
 */
@ProviderType
public interface ImageResourceFactory {

	public ImageResource fromFileEntry(FileEntry fileEntry)
		throws PortalException;

}