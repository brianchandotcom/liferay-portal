/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.scaler;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * Scales an image to fit the characteristics specified by the image
 * configuration entry.
 *
 * <p>
 * This interface should be implemented to let Adaptive Media work with new
 * image types that are supported by the out-of-the-box image scalers, or to
 * replace one of the existing image scalers.
 * </p>
 *
 * <p>
 * Implementations of this interface must be registed as OSGi components. The
 * <code>mime.type</code> property defines the MIME type(s) that the image
 * scaler can handle. The special MIME type <code>*</code> is used as a fallback
 * in case there's no image scaler for a specific MIME type.
 * </p>
 *
 * <p>
 * Image scalers can be replaced by registering an OSGi component with a higher
 * <code>service.ranking</code> property.
 * </p>
 *
 * @author Sergio González
 */
public interface AMImageScaler {

	/**
	 * Returns <code>true</code> if the image scaler is enabled to scale images.
	 *
	 * @return <code>true</code> if the image scaler is enabled to scale images
	 */
	public default boolean isEnabled() {
		return true;
	}

	/**
	 * Generates a scaled image for the file version that fits the
	 * characteristics specified by the image configuration entry.
	 *
	 * @param  fileVersion the file version of the image to scale
	 * @param  amImageConfigurationEntry the image configuration entry
	 * @return the scaled image
	 */
	public AMImageScaledImage scaleImage(
		FileVersion fileVersion,
		AMImageConfigurationEntry amImageConfigurationEntry);

}