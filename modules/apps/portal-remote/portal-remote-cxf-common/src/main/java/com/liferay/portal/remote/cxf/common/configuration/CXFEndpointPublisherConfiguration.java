/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.cxf.common.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Carlos Sierra Andrés
 */
@ExtendedObjectClassDefinition(
	category = "web-api", factoryInstanceLabelAttribute = "contextPath"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.remote.cxf.common.configuration.CXFEndpointPublisherConfiguration",
	localization = "content/Language", name = "cxf-endpoint-configuration-name"
)
public interface CXFEndpointPublisherConfiguration {

	@Meta.AD(name = "context-path")
	public String contextPath();

	@Meta.AD(
		deflt = "auth.verifier.PortalSessionAuthVerifier.urls.includes=*",
		name = "auth-verifier-properties", required = false
	)
	public String[] authVerifierProperties();

	@Meta.AD(name = "required-extensions", required = false)
	public String[] extensions();

}