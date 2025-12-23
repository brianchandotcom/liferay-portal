/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Feliphe Marinho
 * @author Rafael Praxedes
 */
@ExtendedObjectClassDefinition(
	category = "ai-hub", featureFlagKey = "LPD-62272",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.ai.hub.configuration.AIHubConfiguration",
	localization = "content/Language", name = "ai-hub-configuration-name"
)
public interface AIHubConfiguration {

	@Meta.AD(name = "client-id")
	public String clientId();

	@Meta.AD(name = "client-secret")
	public String clientSecret();

	@Meta.AD(name = "service-url")
	public String serviceURL();

}