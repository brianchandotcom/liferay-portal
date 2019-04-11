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

package com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.constants.GCloudVisionAssetAutoTagProviderConstants;
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
	id = "com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration.GCloudVisionAssetAutoTagProviderCompanyConfiguration",
	localization = "content/Language",
	name = "google-cloud-vision-asset-auto-tag-provider-configuration-name"
)
public interface GCloudVisionAssetAutoTagProviderCompanyConfiguration {

	/**
	 * Sets the API key for the G Cloud Vision API.
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = GCloudVisionAssetAutoTagProviderConstants.API_KEY_DOCS_URL,
		requiredInput = true
	)
	@Meta.AD(
		description = "api-key-description", name = "api-key", required = false
	)
	public String apiKey();

	/**
	 * Enables auto tagging of images using the G Cloud Vision API.
	 */
	@Meta.AD(
		description = "enabled-description", name = "enabled", required = false
	)
	public boolean enabled();

}