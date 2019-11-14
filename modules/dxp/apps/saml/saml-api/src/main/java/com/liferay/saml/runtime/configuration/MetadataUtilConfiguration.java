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

package com.liferay.saml.runtime.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author     Carlos Sierra Andrés
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.saml.opensaml.integration.internal.transport.configuration.HttpClientFactoryConfiguration}
 */
@Deprecated
@ExtendedObjectClassDefinition(category = "sso")
@Meta.OCD(
	id = "com.liferay.saml.runtime.configuration.MetadataUtilConfiguration",
	localization = "content/Language", name = "metadata-util-configuration-name"
)
public interface MetadataUtilConfiguration {

	@Meta.AD(
		deflt = "60000", name = "connection-manager-timeout", required = false
	)
	public int getConnectionManagerTimeout();

	@Meta.AD(deflt = "60000", name = "so-timeout", required = false)
	public int getSoTimeout();

}