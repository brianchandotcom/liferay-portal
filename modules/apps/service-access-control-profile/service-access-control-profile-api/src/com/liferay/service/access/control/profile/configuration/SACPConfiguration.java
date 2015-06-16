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

package com.liferay.service.access.control.profile.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Mika Koivisto
 */
@Meta.OCD(
	id = "com.liferay.service.access.control.profile.configuration.SACPConfiguration",
	localization = "content/Language"
)
public interface SACPConfiguration {

	@Meta.AD(deflt = "Default Service Access Profile", required = false)
	public String defaultProfileDescription();

	@Meta.AD(deflt = "DEFAULT", required = false)
	public String defaultProfileName();

	@Meta.AD(deflt = "", required = false)
	public String defaultProfileServiceSignatures();

}