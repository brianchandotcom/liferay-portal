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

package com.liferay.document.library.document.conversion.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pei-Jung Lan
 */
@ExtendedObjectClassDefinition(category = "other")
@Meta.OCD(
	id = "com.liferay.document.library.document.conversion.configuration.OpenOfficeConfiguration",
	localization = "content/Language", name = "openoffice-configuration-name"
)
public interface OpenOfficeConfiguration {

	@Meta.AD(deflt = "true", required = false)
	public boolean cacheEnabled();

	@Meta.AD(
		deflt = "false", description = "openoffice-server-enabled-help",
		required = false
	)
	public boolean serverEnabled();

	@Meta.AD(deflt = "127.0.0.1", required = false)
	public String serverHost();

	@Meta.AD(deflt = "8100", required = false)
	public int serverPort();

}