/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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