/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import java.util.Map;

/**
 * The caller already holds a usable URL and the image transformations it wants
 * applied. A transformer only rewrites what it is given, deciding how the
 * provider spells each transformation and which ones the provider understands
 * at all.
 *
 * <p>
 * Implementations must be deterministic. Two calls with equal image
 * transformations must produce byte identical URLs. Otherwise, every render
 * creates a new CDN cache object for the same image.
 * </p>
 *
 * @author Daniel Sanz
 */
public interface ResponsiveImageURLTransformer {

	/**
	 * Returns the URL with the image transformations applied, or the URL
	 * unchanged if the provider can apply none of them. The image
	 * transformations are keyed by the provider's own parameter names.
	 */
	public String transform(
		String url, Map<String, String> imageTransformations);

}