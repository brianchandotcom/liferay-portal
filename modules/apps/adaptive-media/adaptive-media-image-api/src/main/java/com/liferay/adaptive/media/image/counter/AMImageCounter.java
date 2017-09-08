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

package com.liferay.adaptive.media.image.counter;

/**
 * Provides an interface that returns the number of images that can use Adaptive
 * Media for a particular use case. The number of images returned must include
 * the images that are already adapted and the ones that are not adapted.
 *
 * <p>
 * This interface should be implemented by applications that store images and
 * want to generate adaptive media images. The application's images are then
 * considered when showing the total number of Adaptive Media images.
 * </p>
 *
 * <p>
 * Each use case or application that stores images and uses Adaptive Media
 * should create a new implementation of this class and register it as an OSGi
 * component with the property <code>adaptive.media.key</code> and a key that
 * represents the use case or application.
 * </p>
 *
 * @author Sergio González
 */
public interface AMImageCounter {

	/**
	 * Returns the number of images in the application that should have an
	 * adaptive media image in a particular company, including images that are
	 * not generated yet.
	 *
	 * @return the number of images in the application that should have an
	 *         adaptive media image in a particular company
	 */
	public int countExpectedAMImageEntries(long companyId);

}