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
		descriptionArguments = MSCognitiveServicesAssetAutoTagProviderConstants.API_KEY_DOCS_URL
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
		}
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