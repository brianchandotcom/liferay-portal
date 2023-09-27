/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.mime.type;

/**
 * Provides the supported Adaptive Media image MIME types.
 *
 * @author Sergio González
 */
public interface AMImageMimeTypeProvider {

	/**
	 * Returns the supported MIME types that generate Adaptive Media images.
	 *
	 * @return the supported MIME types that generate Adaptive Media images
	 */
	public String[] getSupportedMimeTypes();

	/**
	 * Returns <code>true</code> if the provided MIME type generates Adaptive
	 * Media images.
	 *
	 * @param  mimeType the MIME type
	 * @return <code>true</code> if the MIME type generates Adaptive Media
	 *         images; <code>false</code> otherwise
	 */
	public boolean isMimeTypeSupported(String mimeType);

}