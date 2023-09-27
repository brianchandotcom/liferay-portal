/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.configuration;

import java.util.Map;

/**
 * Represents a single configuration entry used by Adaptive Media to generate
 * adaptive media images. Image configuration entries are company scoped.
 *
 * <p>
 * It is recommended that you use {@link AMImageConfigurationHelper} to manage
 * and fetch image configuration entries.
 * </p>
 *
 * @author Alejandro Hernández
 */
public interface AMImageConfigurationEntry {

	/**
	 * Returns the image configuration entry's description.
	 *
	 * @return the image configuration entry's description
	 */
	public String getDescription();

	/**
	 * Returns the image configuration entry's name.
	 *
	 * @return the image configuration entry's name
	 */
	public String getName();

	/**
	 * Returns a set of properties that communicate how adaptive media images
	 * should be generated for the image configuration entry.
	 *
	 * @return a set of properties for the image configuration entry
	 */
	public Map<String, String> getProperties();

	/**
	 * Returns the image configuration entry's unique identifier.
	 *
	 * @return the image configuration entry's unique identifier
	 */
	public String getUUID();

	/**
	 * Returns <code>true</code> if the image configuration entry is enabled and
	 * used to generate adaptive media images.
	 *
	 * @return <code>true</code> if the image configuration entry is enabled and
	 *         used to generate adaptive media images; <code>false</code>
	 *         otherwise
	 */
	public boolean isEnabled();

}