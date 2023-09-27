/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Petteri Karttunen
 */
@ExtendedObjectClassDefinition(
	category = "search-experiences",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "semantic-search-configuration-description",
	id = "com.liferay.search.experiences.configuration.SemanticSearchConfiguration",
	localization = "content/Language",
	name = "semantic-search-configuration-name"
)
public interface SemanticSearchConfiguration {

	@Meta.AD(
		deflt = "false", name = "text-embeddings-enabled", required = false
	)
	public boolean textEmbeddingsEnabled();

	@Meta.AD(
		deflt = "", name = "text-embedding-provider-configuration-jsons",
		required = false
	)
	public String[] textEmbeddingProviderConfigurationJSONs();

	@Meta.AD(
		deflt = "604800", name = "text-embedding-cache-timeout",
		required = false
	)
	public int textEmbeddingCacheTimeout();

}