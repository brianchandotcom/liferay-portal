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

package com.liferay.adaptive.media.image.validator;

import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * Validates image size for Adaptive Media.
 *
 * @author Sergio González
 */
public interface AMImageValidator {

	/**
	 * Returns <code>true</code> if the provided file version supports image
	 * processing. Adaptive Media works with some media types where the content
	 * doesn't need to be processed or doesn't support processing at all (e.g.
	 * SVG).
	 *
	 * @return <code>true</code> if the file version supports image processing.
	 * @review
	 */
	public default boolean isProcessingSupported(FileVersion fileVersion) {
		return isValid(fileVersion);
	}

	/**
	 * Returns <code>true</code> if the provided mimeType supports image
	 * processing. Adaptive Media works with some media types where the content
	 * doesn't need to be processed or doesn't support processing at all (e.g.
	 * SVG).
	 *
	 * @return <code>true</code> if the file version supports image processing.
	 * @review
	 */
	public boolean isProcessingSupported(String mimeType);

	/**
	 * Returns <code>true</code> if the provided file version is valid for
	 * Adaptive Media.
	 *
	 * @return <code>true</code> if the file version is valid for Adaptive Media
	 */
	public boolean isValid(FileVersion fileVersion);

}