/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.auto.tagger.google.cloud.natural.language.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.asset.auto.tagger.google.cloud.natural.language.internal.contants.GCloudNaturalLanguageDocumentAssetAutoTaggerConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * Models a Google Cloud Natural Language Document Asset Auto Tagger Company
 * Configuration.
 *
 * @author Cristina González
 * @author Alicia García
 * @review
 */
@ExtendedObjectClassDefinition(
	category = "assets", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "google-cloud-natural-language-asset-auto-tag-configuration-description",
	id = "com.liferay.asset.auto.tagger.google.cloud.natural.language.internal.configuration.GCloudNaturalLanguageAssetAutoTaggerCompanyConfiguration",
	localization = "content/Language",
	name = "google-cloud-natural-language-asset-auto-tag-configuration-name"
)
public interface GCloudNaturalLanguageAssetAutoTaggerCompanyConfiguration {

	/**
	 * Sets the API key for the Google Cloud Vision API.
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = GCloudNaturalLanguageDocumentAssetAutoTaggerConstants.API_KEY_DOCS_URL,
		requiredInput = true
	)
	@Meta.AD(
		description = "api-key-description", name = "api-key", required = false
	)
	public String apiKey();

	/**
	 * Enables auto tagging of documents using the Google Cloud Natural Language
	 * API.
	 */
	@Meta.AD(
		description = "classification-endpoint-enabled-description",
		name = "classification-endpoint-enabled", required = false
	)
	public boolean classificationEndpointEnabled();

	/**
	 * Sets the classifier's confidence of the category. This number represents
	 * how certain the classifier is that this category represents the given
	 * text.
	 */
	@Meta.AD(
		deflt = "0.5", description = "confidence-description",
		name = "confidence", required = false
	)
	public float confidence();

	/**
	 * Enables auto tagging of documents using the Google Cloud Natural Language
	 * API.
	 */
	@Meta.AD(
		description = "entity-endpoint-enabled-description",
		name = "entity-endpoint-enabled", required = false
	)
	public boolean entityEndpointEnabled();

	/**
	 * Sets the salience score for an entity. The salience provides information
	 * about the importance or centrality of that entity to the entire document
	 * text. Scores closer to 0 are less salient, while scores closer to 1.0 are
	 * highly salient
	 */
	@Meta.AD(
		deflt = "0.02", description = "salience-description", name = "salience",
		required = false
	)
	public float salience();

	/**
	 * Sets the class names to enable auto tagging of documents using the Google
	 * Cloud Natural Language API.
	 *
	 * @review
	 */
	@Meta.AD(deflt = "", name = "enabled-class-names", required = false)
	public String[] enabledClassNames();

}