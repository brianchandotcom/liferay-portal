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
	id = "com.liferay.search.experiences.configuration.SemanticSearchConfiguration",
	localization = "content/Language",
	name = "semantic-search-configuration-name"
)
public interface SemanticSearchConfiguration {

	@Meta.AD(
		deflt = "false", description = "text-embeddings-enabled-help",
		name = "text-embeddings-enabled", required = false
	)
	public boolean textEmbeddingsEnabled();

	@Meta.AD(
		deflt = "huggingFaceInferenceAPI", name = "text-embedding-provider",
		optionLabels = {"Hugging Face Inference API", "txtai"},
		optionValues = {"huggingFaceInferenceAPI", "txtai"}, required = false
	)
	public String textEmbeddingProvider();

	@Meta.AD(deflt = "", name = "hugging-face-access-token", required = false)
	public String huggingFaceAccessToken();

	@Meta.AD(
		deflt = "http://localhost:8000",
		description = "text-embedding-provider-txtai-host-address-help",
		name = "txtai-host-address", required = false
	)
	public String txtaiHostAddress();

	@Meta.AD(
		deflt = "", description = "text-embedding-provider-txtai-username-help",
		name = "username", required = false
	)
	public String txtaiUsername();

	@Meta.AD(
		deflt = "", description = "text-embedding-provider-txtai-password-help",
		name = "password", required = false
	)
	public String txtaiPassword();

	@Meta.AD(
		deflt = "", description = "text-embedding-provider-model-help",
		name = "model", required = false
	)
	public String model();

	@Meta.AD(
		deflt = "25",
		description = "text-embedding-provider-model-timeout-help",
		name = "model-timeout", required = false
	)
	public int modelTimeout();

	@Meta.AD(
		deflt = "768",
		description = "text-embedding-provider-embedding-vector-dimensions-help",
		name = "embedding-vector-dimensions",
		optionLabels = {"384", "512", "768"},
		optionValues = {"384", "512", "768"}, required = false
	)
	public int embeddingVectorDimensions();

	@Meta.AD(
		deflt = "500",
		description = "text-embedding-provider-max-character-count-help",
		name = "max-character-count", required = false
	)
	public int maxCharacterCount();

	@Meta.AD(
		deflt = "beginning",
		description = "text-embedding-provider-text-truncation-strategy-help",
		name = "text-truncation-strategy",
		optionLabels = {"beginning", "middle", "End"},
		optionValues = {"beginning", "middle", "end"}, required = false
	)
	public String textTruncationStrategy();

	@Meta.AD(
		deflt = "com.liferay.blogs.model.BlogsEntry|com.liferay.journal.model.JournalArticle|com.liferay.knowledge.base.model.KBArticle|com.liferay.wiki.model.WikiPage",
		description = "text-embedding-provider-asset-entry-class-names-help",
		name = "asset-entry-class-names", required = false
	)
	public String[] assetEntryClassNames();

	@Meta.AD(
		deflt = "en_US",
		description = "text-embedding-provider-language-ids-help",
		name = "language-ids", required = false
	)
	public String[] languageIds();

	@Meta.AD(
		deflt = "604800",
		description = "text-embedding-provider-cache-timeout-help",
		name = "cache-timeout", required = false
	)
	public int cacheTimeout();

}