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

package com.liferay.adaptive.media.image.optimizer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides an interface that generates missing adaptive media images for an
 * application or use case.
 *
 * <p>
 * Implementations of this interface should generate adaptive media images for
 * every image of the application or use case that doesn't have an adaptive
 * media image.
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
@ProviderType
public interface AMImageOptimizer {

	/**
	 * Generates missing adaptive media images for an application or use case in
	 * a company for every enabled image configuration entry.
	 *
	 * @param companyId the primary key of the company
	 */
	public void optimize(long companyId);

	/**
	 * Generates missing adaptive media images for an application or use case in
	 * a company for the specified image configuration entry.
	 *
	 * @param companyId the primary key of the company
	 * @param configurationEntryUuid the image configuration entry's UUID
	 */
	public void optimize(long companyId, String configurationEntryUuid);

}