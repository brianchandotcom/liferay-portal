/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.scaler;

import java.io.InputStream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents an image scaled by Adaptive Media.
 *
 * @author Sergio González
 */
@ProviderType
public interface AMImageScaledImage {

	/**
	 * Returns the image height.
	 *
	 * @return the image height
	 */
	public int getHeight();

	/**
	 * Returns an <code>InputStream</code> with the image data.
	 *
	 * @return the <code>InputStream</code> with the image data
	 */
	public InputStream getInputStream();

	/**
	 * Returns this image's mime type
	 *
	 * @return this image's mime type
	 */
	public String getMimeType();

	/**
	 * Returns this image's size in bytes.
	 *
	 * @return this image's size in bytes
	 */
	public long getSize();

	/**
	 * Returns the image width.
	 *
	 * @return the image width
	 */
	public int getWidth();

}