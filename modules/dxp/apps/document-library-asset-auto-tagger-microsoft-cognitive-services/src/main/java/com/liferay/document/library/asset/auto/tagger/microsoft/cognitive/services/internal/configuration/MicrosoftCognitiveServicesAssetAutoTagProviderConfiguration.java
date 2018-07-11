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

package com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(category = "documents-and-media")
@Meta.OCD(
	description = "microsoft-cognitive-services-auto-tag-provider-description",
	id = "com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration.MicrosoftCognitiveServicesAssetAutoTagProviderConfiguration",
	localization = "content/Language",
	name = "microsoft-cognitive-services-auto-tag-provider-configuration-name"
)
public interface MicrosoftCognitiveServicesAssetAutoTagProviderConfiguration {

	/**
	 * Sets the API Key for the Computer Vision API V2.
	 *
	 * See https://azure.microsoft.com/en-us/try/cognitive-services/my-apis/?apiSlug=computer-services
	 */
	@Meta.AD(description = "api-key-description", name = "api-key")
	public String apiKey();

	/**
	 * Sets the Computer Vision API V2 endpoint.
	 *
	 * For example: https://westcentralus.api.cognitive.microsoft.com/vision/v2.0
	 *
	 * See https://azure.microsoft.com/en-us/try/cognitive-services/my-apis/?apiSlug=computer-services
	 */
	@Meta.AD(description = "api-endpoint-description", name = "api-endpoint")
	public String apiEndpoint();

	/**
	 * Enables auto tagging of images using Microsoft Cognitive Services API
	 */
	@Meta.AD(name = "enabled", required = false)
	public boolean enabled();

}