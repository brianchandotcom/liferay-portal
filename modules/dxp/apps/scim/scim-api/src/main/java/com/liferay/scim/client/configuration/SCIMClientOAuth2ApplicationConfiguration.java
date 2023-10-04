/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.client.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Olivér Kecskeméty
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	factory = true,
	id = "com.liferay.scim.client.configuration.SCIMClientOAuth2ApplicationConfiguration"
)
public interface SCIMClientOAuth2ApplicationConfiguration {

	@Meta.AD(type = Meta.Type.String)
	public String applicationName();

	@Meta.AD(type = Meta.Type.String)
	public String matcherField();

}