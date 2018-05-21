/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.transport.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "foundation")
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