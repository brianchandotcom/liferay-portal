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
 * This interface provides the number of images that can benefit of Adaptive
 * Media for a particular use case. The number of images must include both the
 * ones that already have adaptive media images and the ones that do not.
 *
 * <p>
 * This interface should be implemented by applications that stores images and
 * want to generate adaptive media images as well. By implementing this
 * interface, the images stored by that application will be considered when
 * showing the number of Adaptive Media images.
 * </p>
 *
 * <p>
 * Each use case or application that stores images and uses Adaptive Media
 * should create a new implementation of this class and register it as an OSGi
 * component with the propery adaptive.media.key and a key that represents the
 * use case or application.
 * </p>
 *
 * @review
 *
 * @author Sergio González
 */
public interface AdaptiveMediaImageCounter {

	/**
	 * Returns the number of images of an application that should have an
	 * adaptive media image in a particular company, even if the adaptive media
	 * image is not generated yet.
	 *
	 * @return the number of images of an application that should have an
	 *         adaptive media image in a particular company.
	 *
	 * @review
	 */
	public int countExpectedAdaptiveMediaImageEntries(long companyId);

}