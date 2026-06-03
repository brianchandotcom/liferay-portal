/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.configuration;

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
	id = "com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration",
	localization = "content/Language", name = "ai-hub-cell-configuration-name"
)
public interface AIHubCellConfiguration {

	@Meta.AD(name = "client-id")
	public String clientId();

	@Meta.AD(name = "client-secret", type = Meta.Type.Password)
	public String clientSecret();

	@Meta.AD(name = "service-url")
	public String serviceURL();

}