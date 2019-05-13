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

package com.liferay.arquillian.extension.junit.bridge.connector;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Matthew Tambara
 */
@ExtendedObjectClassDefinition(category = "module-container")
@Meta.OCD(
	id = "com.liferay.arquillian.extension.junit.bridge.connector.ArquillianConnectorConfiguration",
	localization = "content/Language",
	name = "arquillian-connector-configuration-name"
)
public interface ArquillianConnectorConfiguration {

	@Meta.AD(deflt = "32763", name = "port", required = false)
	public int port();

	@Meta.AD(name = "passcode", required = false)
	public String passcode();

}