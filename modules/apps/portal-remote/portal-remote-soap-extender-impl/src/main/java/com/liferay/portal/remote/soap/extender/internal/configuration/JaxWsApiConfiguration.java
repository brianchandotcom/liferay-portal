/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

}