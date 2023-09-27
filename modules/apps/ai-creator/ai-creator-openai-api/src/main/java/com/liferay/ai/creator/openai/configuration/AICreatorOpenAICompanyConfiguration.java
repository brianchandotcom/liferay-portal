/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Lourdes Fernández Besada
 */
@ExtendedObjectClassDefinition(
	category = "ai-creator", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.ai.creator.openai.configuration.AICreatorOpenAICompanyConfiguration",
	localization = "content/Language",
	name = "ai-creator-openai-company-configuration-name"
)
public interface AICreatorOpenAICompanyConfiguration {

	@Meta.AD(
		deflt = "true", name = "enable-openai-to-create-content",
		required = false
	)
	public boolean enableOpenAIToCreateContent();

	@Meta.AD(deflt = "", name = "api-key", required = false)
	public String apiKey();

}