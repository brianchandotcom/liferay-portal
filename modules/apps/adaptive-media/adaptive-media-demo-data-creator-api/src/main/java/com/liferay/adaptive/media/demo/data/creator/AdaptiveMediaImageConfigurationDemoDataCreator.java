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

package com.liferay.adaptive.media.demo.data.creator;

import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;

import java.io.IOException;

import java.util.Collection;

/**
 * Provides a class for creating adaptive media configurations.
 *
 * @author Alejandro Hernández
 */
public interface AdaptiveMediaImageConfigurationDemoDataCreator {

	/**
	 * Creates a collection of {@link AdaptiveMediaImageConfigurationEntry}
	 * configurations based on the {@link
	 * DemoAdaptiveMediaImageConfigurationVariant} enum
	 *
	 * @param  companyId ID of the company where the configurations will be
	 *         created
	 * @return a list of the configurations
	 */
	public Collection<AdaptiveMediaImageConfigurationEntry> create(
			long companyId)
		throws IOException;

	/**
	 * Creates a {@link AdaptiveMediaImageConfigurationEntry} for a company
	 *
	 * @param  companyId ID of the company where the configuration will be
	 *         created
	 * @param  demoAdaptiveMediaImageConfigurationVariant the configuration to
	 *         create
	 * @return the configuration
	 */
	public AdaptiveMediaImageConfigurationEntry create(
			long companyId,
			DemoAdaptiveMediaImageConfigurationVariant
				demoAdaptiveMediaImageConfigurationVariant)
		throws IOException;

	/**
	 * Deletes the configurations created by this demo data creator
	 */
	public void delete() throws IOException;

}