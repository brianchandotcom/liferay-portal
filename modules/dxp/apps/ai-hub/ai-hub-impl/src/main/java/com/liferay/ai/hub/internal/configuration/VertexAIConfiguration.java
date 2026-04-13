/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author João Victor Alves
 */
@ExtendedObjectClassDefinition(
	category = "ai-hub", featureFlagKey = "LPD-62272",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.ai.hub.internal.configuration.VertexAIConfiguration",
	localization = "content/Language", name = "vertex-ai-configuration-name"
)
public interface VertexAIConfiguration {

	@Meta.AD(name = "location")
	public String location();

	@Meta.AD(name = "model-name")
	public String modelName();

	@Meta.AD(name = "project-id")
	public String projectId();

}