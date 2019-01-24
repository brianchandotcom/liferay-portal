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

package com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.constants.GoogleCloudVisionAssetAutoTagProviderConstants;
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
	description = "google-cloud-vision-asset-auto-tag-provider-description",
	id = "com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration.GoogleCloudVisionAssetAutoTagProviderCompanyConfiguration",
	localization = "content/Language",
	name = "google-cloud-vision-asset-auto-tag-provider-configuration-name"
)
public interface GoogleCloudVisionAssetAutoTagProviderCompanyConfiguration {

	/**
	 * Sets the API key for the Google Cloud Vision API.
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = GoogleCloudVisionAssetAutoTagProviderConstants.API_KEY_DOCS_URL
	)
	@Meta.AD(
		description = "api-key-description", name = "api-key", required = false
	)
	public String apiKey();

	/**
	 * Enables auto tagging of images using the Google Cloud Vision API.
	 */
	@Meta.AD(
		description = "enabled-description", name = "enabled", required = false
	)
	public boolean enabled();

}