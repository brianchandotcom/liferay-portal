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

package com.liferay.journal.asset.auto.tagger.google.cloud.natural.language.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.asset.auto.tagger.google.cloud.natural.language.api.constants.GCloudNaturalLanguageAssetAutoTagProviderConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Cristina González
 * @author Alicia García
 */
@ExtendedObjectClassDefinition(
	category = "web-content",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "google-cloud-natural-language-asset-auto-tag-provider-description",
	id = "com.liferay.journal.asset.auto.tagger.google.cloud.natural.language.internal.configuration.GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration",
	localization = "content/Language",
	name = "google-cloud-natural-language-asset-auto-tag-provider-configuration-name"
)
public interface GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
	extends com.liferay.asset.auto.tagger.google.cloud.natural.language.api.
		configuration.GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration {

	@ExtendedAttributeDefinition(
		descriptionArguments = GCloudNaturalLanguageAssetAutoTagProviderConstants.API_KEY_DOCS_URL
	)
	@Meta.AD(
		description = "api-key-description", name = "api-key", required = false
	)
	@Override
	public String apiKey();

	@Meta.AD(
		description = "classification-endpoint-enabled-description",
		name = "classification-endpoint-enabled", required = false
	)
	@Override
	public boolean classificationEndpointEnabled();

	@Meta.AD(
		description = "entity-endpoint-enabled-description",
		name = "entity-endpoint-enabled", required = false
	)
	@Override
	public boolean entityEndpointEnabled();

	@Meta.AD(
		deflt = "0.02", description = "salience-description", name = "salience",
		required = false
	)
	@Override
	public float salience();

	@Meta.AD(
		deflt = "0.5", description = "confidence-description",
		name = "confidence", required = false
	)
	@Override
	public float confidence();

}