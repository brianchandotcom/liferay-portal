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

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(category = "documents-and-media")
@Meta.OCD(
	id = "com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration.GoogleCloudVisionAssetAutoTagProviderConfiguration",
	localization = "content/Language",
	name = "google-cloud-vision-auto-tag-provider-configuration-name"
)
public interface GoogleCloudVisionAssetAutoTagProviderConfiguration {

	/**
	 * Sets the API Key for the cloud vision API.
	 *
	 * Billing must be enabled for this API to work.
	 *
	 * See https://cloud.google.com/docs/authentication/api-keys
	 */
	@Meta.AD(description = "api-key-description", name = "api-key")
	public String apiKey();

	/**
	 * Enables auto tagging of images using Google Cloud Vision API
	 */
	@Meta.AD(
		description = "enabled-description", name = "enabled", required = false
	)
	public boolean enabled();

}