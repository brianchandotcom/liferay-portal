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

import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.constants.MSCognitiveServicesAssetAutoTagProviderConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "microsoft-cognitive-services-asset-auto-tag-provider-description",
	id = "com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration.MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration",
	localization = "content/Language",
	name = "microsoft-cognitive-services-asset-auto-tag-provider-configuration-name"
)
public interface MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration {

	/**
	 * Sets the API Key for the Computer Vision API V2.
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = MSCognitiveServicesAssetAutoTagProviderConstants.API_KEY_DOCS_URL,
		uiRequired = true
	)
	@Meta.AD(
		description = "api-key-description", name = "api-key", required = false
	)
	public String apiKey();

	/**
	 * Sets the Computer Vision API V2 endpoint.
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = {
			MSCognitiveServicesAssetAutoTagProviderConstants.
				SAMPLE_API_ENDPOINT,
			MSCognitiveServicesAssetAutoTagProviderConstants.API_KEY_DOCS_URL
		},
		requiredInput = true
	)
	@Meta.AD(
		description = "api-endpoint-description", name = "api-endpoint",
		required = false
	)
	public String apiEndpoint();

	/**
	 * Enables auto tagging of images using the Microsoft Cognitive Services
	 * API.
	 */
	@Meta.AD(name = "enabled", required = false)
	public boolean enabled();

}