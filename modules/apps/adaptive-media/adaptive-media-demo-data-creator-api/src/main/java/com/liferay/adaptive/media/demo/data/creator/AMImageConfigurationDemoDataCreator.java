/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.demo.data.creator;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;

import java.io.IOException;

import java.util.Collection;

/**
 * Provides a class for creating adaptive media configurations.
 *
 * @author Alejandro Hernández
 */
public interface AMImageConfigurationDemoDataCreator {

	/**
	 * Creates a collection of {@link AMImageConfigurationEntry} configurations
	 * based on the {@link DemoAMImageConfigurationVariant} enum
	 *
	 * @param  companyId ID of the company where the configurations will be
	 *         created
	 * @return a list of the configurations
	 */
	public Collection<AMImageConfigurationEntry> create(long companyId)
		throws IOException;

	/**
	 * Creates a {@link AMImageConfigurationEntry} for a company
	 *
	 * @param  companyId ID of the company where the configuration will be
	 *         created
	 * @param  demoAMImageConfigurationVariant the configuration to create
	 * @return the configuration
	 */
	public AMImageConfigurationEntry create(
			long companyId,
			DemoAMImageConfigurationVariant demoAMImageConfigurationVariant)
		throws IOException;

	/**
	 * Deletes the configurations created by this demo data creator
	 */
	public void delete() throws IOException;

}