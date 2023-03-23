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