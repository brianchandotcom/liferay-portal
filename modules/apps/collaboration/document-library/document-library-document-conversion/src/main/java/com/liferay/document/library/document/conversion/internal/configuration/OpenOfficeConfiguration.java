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

package com.liferay.document.library.document.conversion.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pei-Jung Lan
 */
@ExtendedObjectClassDefinition(category = "connectors")
@Meta.OCD(
	id = "com.liferay.document.library.document.conversion.internal.configuration.OpenOfficeConfiguration",
	localization = "content/Language", name = "openoffice-configuration-name"
)
public interface OpenOfficeConfiguration {

	@Meta.AD(deflt = "true", name = "cache-enabled", required = false)
	public boolean cacheEnabled();

	@Meta.AD(
		deflt = "false", description = "openoffice-server-enabled-help",
		name = "server-enabled", required = false
	)
	public boolean serverEnabled();

	@Meta.AD(deflt = "127.0.0.1", name = "server-host", required = false)
	public String serverHost();

	@Meta.AD(deflt = "8100", name = "server-port", required = false)
	public int serverPort();

}