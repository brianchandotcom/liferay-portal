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

package com.liferay.asset.auto.tagger.google.cloud.natural.language.api.configuration;

/**
 * Models a Google Cloud Natural Language Document Asset Auto Tagger Company
 * Configuration.
 *
 * @author Cristina González
 * @author Alicia García
 * @review
 */
public interface GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration {

	/**
	 * Sets the API key for the Google Cloud Vision API.
	 */
	public String apiKey();

	/**
	 * Enables auto tagging of documents using the Google Cloud Natural Language
	 * API.
	 */
	public boolean classificationEndpointEnabled();

	/**
	 * Sets the classifier's confidence of the category. This number represents
	 * how certain the classifier is that this category represents the given
	 * text.
	 */
	public float confidence();

	/**
	 * Enables auto tagging of documents using the Google Cloud Natural Language
	 * API.
	 */
	public boolean entityEndpointEnabled();

	/**
	 * Sets the salience score for an entity. The salience provides information
	 * about the importance or centrality of that entity to the entire document
	 * text. Scores closer to 0 are less salient, while scores closer to 1.0 are
	 * highly salient
	 */
	public float salience();

}