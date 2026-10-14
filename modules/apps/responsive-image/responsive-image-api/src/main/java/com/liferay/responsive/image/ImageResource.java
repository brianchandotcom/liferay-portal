/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

/**
 * An image resource carries nothing about cache freshness, which the
 * <code>Cache-Control</code> header from the origin and the CDN's own
 * invalidation define. It carries no intrinsic dimensions either because they
 * are not cheaply obtainable in general.
 *
 * @author Daniel Sanz
 */
public interface ImageResource {

	/**
	 * Returns the image's MIME type, or <code>null</code> if it is not known.
	 */
	public String getMimeType();

	/**
	 * Returns the image's original URL before any transformation, which may be
	 * relative to the portal or absolute.
	 */
	public String getURL();

}