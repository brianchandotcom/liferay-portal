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

package com.liferay.message.boards.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sergio González
 */
@ExtendedObjectClassDefinition(category = "collaboration")
@Meta.OCD(
	id = "com.liferay.message.boards.configuration.MBConfiguration",
	localization = "content/Language", name = "mb-configuration-name"
)
public interface MBConfiguration {

	/**
	 * Enter time in minutes on how often this job is run. If a user's ban is
	 * set to expire at 12:05 PM and the job runs at 2 PM, the expire will occur
	 * during the 2 PM run.
	 */
	@Meta.AD(deflt = "120", required = false)
	public int expireBanJobInterval();

}