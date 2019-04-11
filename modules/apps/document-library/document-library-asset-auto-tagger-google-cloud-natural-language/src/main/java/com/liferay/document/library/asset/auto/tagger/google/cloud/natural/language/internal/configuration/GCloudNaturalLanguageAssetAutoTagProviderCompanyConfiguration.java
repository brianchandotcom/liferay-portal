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

package com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Cristina González
 * @review
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "google-cloud-natural-language-asset-auto-tag-provider-description",
	id = "com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.configuration.GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration",
	localization = "content/Language",
	name = "google-cloud-natural-language-asset-auto-tag-provider-configuration-name"
)
public interface GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration {

	/**
	 * Enables auto tagging of documents and media documents using a pre-trained
	 * opennlp model.
	 *
	 * @review
	 */
	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

}