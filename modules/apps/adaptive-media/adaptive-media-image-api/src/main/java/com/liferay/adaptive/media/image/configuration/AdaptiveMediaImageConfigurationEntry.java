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

package com.liferay.adaptive.media.image.configuration;

import java.util.Map;

/**
 * Represents a single configuration entry used by Adaptive Media to generate
 * adaptive media images. Image configuration entries are company scoped.
 *
 * <p>
 * It is recommened to use {@link AdaptiveMediaImageConfigurationHelper} to
 * manage and fetch image configuration entries.
 * </p>
 *
 * @review
 *
 * @author Alejandro Hernández
 */
public interface AdaptiveMediaImageConfigurationEntry {

	/**
	 * Returns the description of the image configuration entry.
	 *
	 * @return the description of the image configuration entry.
	 *
	 * @review
	 */
	public String getDescription();

	/**
	 * Returns the name of the image configuration entry.
	 *
	 * @return the name of the image configuration entry.
	 *
	 * @review
	 */
	public String getName();

	/**
	 * Returns a set of properties that contains additional information
	 * regarding how adaptive media images should be generated.
	 *
	 * @return a set of properties of the image configuration entry.
	 *
	 * @review
	 */
	public Map<String, String> getProperties();

	/**
	 * Returns the unique identifier for the image configuration entry.
	 *
	 * @return the unique identifier of the image configuration entry.
	 *
	 * @review
	 */
	public String getUUID();

	/**
	 * Returns <code>true</code> when the image configuration entry is enabled
	 * and it is used to generated adaptive media images. Returns
	 * <code>false</code> otherwise.
	 *
	 * @return <code>true</code> if the image configuration entry is enabled and
	 *         it is used to generate adaptive media images; <code>false</code>
	 *         otherwise
	 *
	 * @review
	 */
	public boolean isEnabled();

}