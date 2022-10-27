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
		deflt = "false", description = "sentence-transformer-enabled-help",
		name = "sentence-transformer-enabled", required = false
	)
	public boolean sentenceTransformerEnabled();

	@Meta.AD(
		deflt = "huggingFace", name = "sentence-transform-provider",
		optionLabels = {"Hugging Face", "txtai"},
		optionValues = {"huggingFace", "txtai"}, required = false
	)
	public String sentenceTransformProvider();

	@Meta.AD(deflt = "", name = "hugging-face-access-token", required = false)
	public String huggingFaceAccessToken();

	@Meta.AD(
		deflt = "http://localhost:8000",
		description = "sentence-transformer-txtai-host-address-help",
		name = "txtai-host-address", required = false
	)
	public String txtaiHostAddress();

	@Meta.AD(
		deflt = "", description = "sentence-transformer-model-help",
		name = "model", required = false
	)
	public String model();

	@Meta.AD(
		deflt = "25", description = "sentence-transformer-model-timeout-help",
		name = "model-timeout", required = false
	)
	public int modelTimeout();

	@Meta.AD(
		deflt = "false", description = "sentence-transformer-enable-gpu-help",
		name = "enable-gpu", required = false
	)
	public boolean enableGPU();

	@Meta.AD(
		deflt = "768",
		description = "sentence-transformer-embedding-vector-dimensions-help",
		name = "embedding-vector-dimensions",
		optionLabels = {"384", "512", "768"},
		optionValues = {"384", "512", "768"}, required = false
	)
	public int embeddingVectorDimensions();

	@Meta.AD(
		deflt = "500",
		description = "sentence-transformer-max-character-count-help",
		name = "max-character-count", required = false
	)
	public int maxCharacterCount();

	@Meta.AD(
		deflt = "beginning",
		description = "sentence-transformer-text-truncation-strategy-help",
		name = "text-truncation-strategy",
		optionLabels = {"beginning", "middle", "End"},
		optionValues = {"beginning", "middle", "end"}, required = false
	)
	public String textTruncationStrategy();

	@Meta.AD(
		deflt = "com.liferay.blogs.model.BlogsEntry|com.liferay.journal.model.JournalArticle|com.liferay.knowledge.base.model.KBArticle|com.liferay.wiki.model.WikiPage",
		description = "sentence-transformer-asset-entry-class-names-help",
		name = "asset-entry-class-names", required = false
	)
	public String[] assetEntryClassNames();

	@Meta.AD(
		deflt = "en_US", description = "sentence-transformer-language-ids-help",
		name = "language-ids", required = false
	)
	public String[] languageIds();

	@Meta.AD(deflt = "604800", name = "cache-timeout", required = false)
	public int cacheTimeout();

}