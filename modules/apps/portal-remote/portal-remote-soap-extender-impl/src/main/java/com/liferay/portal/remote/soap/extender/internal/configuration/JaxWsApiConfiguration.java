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

package com.liferay.portal.remote.soap.extender.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Carlos Sierra Andrés
 */
@ExtendedObjectClassDefinition(category = "web-api")
@Meta.OCD(
	id = "com.liferay.portal.remote.soap.extender.internal.configuration.JaxWsApiConfiguration",
	localization = "content/Language", name = "jax-ws-api-configuration-name"
)
public interface JaxWsApiConfiguration {

	@Meta.AD(name = "context-path")
	public String contextPath();

	@Meta.AD(deflt = "10000", name = "timeout")
	public long timeout();

}