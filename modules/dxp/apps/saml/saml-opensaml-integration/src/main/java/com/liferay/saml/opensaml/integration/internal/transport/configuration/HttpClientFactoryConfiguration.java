/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.transport.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "sso")
@Meta.OCD(
	id = "com.liferay.saml.opensaml.integration.internal.transport.configuration.HttpClientFactoryConfiguration",
	localization = "content/Language",
	name = "http-client-factory-configuration-name"
)
public interface HttpClientFactoryConfiguration {

	@Meta.AD(
		deflt = "20", name = "default-max-connections-per-route",
		required = false
	)
	public int defaultMaxConnectionsPerRoute();

	@Meta.AD(deflt = "20", name = "max-total-connections", required = false)
	public int maxTotalConnections();

	@Meta.AD(
		deflt = "60000", name = "connection-manager-timeout", required = false
	)
	public int connectionManagerTimeout();

	@Meta.AD(deflt = "60000", name = "so-timeout", required = false)
	public int soTimeout();

}