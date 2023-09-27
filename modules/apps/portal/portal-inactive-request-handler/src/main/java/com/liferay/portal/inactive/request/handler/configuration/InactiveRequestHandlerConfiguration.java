/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.inactive.request.handler.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.inactive.request.handler.configuration.InactiveRequestHandlerConfiguration",
	localization = "content/Language",
	name = "inactive-request-handler-configuration-name"
)
public interface InactiveRequestHandlerConfiguration {

	@Meta.AD(
		deflt = "false", id = "show.inactive.request.message",
		name = "show-inactive-request-message", required = false
	)
	public boolean showInactiveRequestMessage();

}