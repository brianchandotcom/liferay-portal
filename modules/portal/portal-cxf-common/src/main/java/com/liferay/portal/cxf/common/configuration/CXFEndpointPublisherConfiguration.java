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

package com.liferay.portal.cxf.common.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Carlos Sierra Andrés
 */
@ConfigurationAdmin(
	category = "platform",
	factoryInstanceLabelAttribute = "contextPath"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.cxf.common.configuration.CXFEndpointPublisherConfiguration"
)
public interface CXFEndpointPublisherConfiguration {

	@Meta.AD(
		deflt = "auth.verifier.PortalSessionAuthVerifier.urls.includes=*",
		name = "auth.verifier.properties", required = false
	)
	public String[] authVerifierProperties();

	@Meta.AD(required = true)
	public String contextPath();

	@Meta.AD(name = "required.extensions", required = false)
	public String[] extensions();

}