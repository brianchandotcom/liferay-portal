/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

/**
 * Represents the original image, from which renditions are generated,
 * independently of where it is stored: file entries, OSGi modules, the legacy
 * portal image path, or on a completely different host.
 *
 * <p>
 * It carries nothing about cache freshness. That is defined by the
 * <code>Cache-Control</code> the origin returns and by the CDN's own
 * invalidation.
 * </p>
 *
 * <p>
 * It carries no intrinsic dimensions, as they are not cheaply obtainable
 * in general.
 * </p>
 *
 * @author Daniel Sanz
 */
public interface ImageResource {

	/**
	 * Returns the image's mime type, or <code>null</code> if it is not known.
	 */
	public String getMimeType();

	/**
	 * Returns the image's original URL, before any transformation. It may be
	 * portal relative or absolute.
	 */
	public String getURL();

}