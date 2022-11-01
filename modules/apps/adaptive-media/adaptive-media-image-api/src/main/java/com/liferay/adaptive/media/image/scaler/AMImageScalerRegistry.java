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

package com.liferay.adaptive.media.image.scaler;

/**
 * Tracks the image scalers and returns the one used for a specific MIME type.
 *
 * <p>
 * Every image scaler registered in OSGi with the <code>mime.type</code>
 * property is tracked. Multiple image scalers can be registered for the same
 * MIME type, but the tracker returns only one.
 * </p>
 *
 * <p>
 * The image scalers are grouped by <code>mime.type</code> and ordered by the
 * <code>service.ranking</code> property. The image scaler returns the first
 * image scaler of the group whose {@link AMImageScaler#isEnabled()} method
 * returns <code>true</code>.
 * </p>
 *
 * <p>
 * If no enabled image scalers are registered for the specific MIME type, it
 * searches for the enabled image scaler with the highest
 * <code>service.ranking</code> that's registered with the <code>*</code> MIME
 * type. If no enabled image scalers are registered for the <code>*</code> MIME
 * type, it returns <code>null</code>.
 * </p>
 *
 * @author Sergio González
 */
public interface AMImageScalerRegistry {

	/**
	 * Returns the enabled image scaler for the specific MIME type.
	 *
	 * @param  mimeType the MIME type
	 * @return the image scaler
	 */
	public AMImageScaler getAMImageScaler(String mimeType);

}