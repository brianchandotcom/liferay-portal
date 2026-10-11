/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import java.util.Map;

/**
 * Transforms an image URL into the one an image optimization provider
 * understands, typically a CDN.
 *
 * <p>
 * The caller already holds a usable URL and the image transformations it wants
 * applied, so a transformer only rewrites what it is given. It decides how the
 * provider spells each transformation, and which of them the provider
 * understands at all.
 * </p>
 *
 * <p>
 * Implementations must be deterministic. Two calls with equal image
 * transformations must produce byte identical URLs, otherwise every render
 * creates a new CDN cache object for the same image.
 * </p>
 *
 * @author Daniel Sanz
 */
public interface ResponsiveImageURLTransformer {

	/**
	 * Returns the URL with the given image transformations applied.
	 *
	 * @param  url the image URL to transform
	 * @param  imageTransformations the image transformations to apply, keyed
	 *         by the provider's own parameter names
	 * @return the transformed URL, or <code>url</code> unchanged if the
	 *         provider cannot apply any of the image transformations, which
	 *         includes the case where it recognizes none of them
	 */
	public String transform(
		String url, Map<String, String> imageTransformations);

}