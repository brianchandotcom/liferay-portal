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

package com.liferay.adaptive.media.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sergio González
 */
@ExtendedObjectClassDefinition(category = "collaboration")
@Meta.OCD(
	id = "com.liferay.adaptive.media.configuration.AMConfiguration",
	localization = "content/Language",
	name = "adaptive-media-configuration-name"
)
public interface AMConfiguration {

	/**
	 * Set the maximum size of workers that will process adaptive media.
	 *
	 * @review
	 */
	@Meta.AD(
		deflt = "10", description = "workers-max-size-key-description",
		required = false
	)
	public int workersMaxSize();

	/**
	 * Set the size of core workers that will process adaptive media.
	 *
	 * @review
	 */
	@Meta.AD(
		deflt = "5", description = "workers-core-size-key-description",
		required = false
	)
	public int workersCoreSize();

}