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

package com.liferay.document.library.asset.auto.tagger.opennlp.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.asset.auto.tagger.opennlp.api.configuration.OpenNLPDocumentAssetAutoTagCompanyConfiguration;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Cristina González
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "opennlp-auto-tag-provider-configuration-description",
	id = "com.liferay.document.library.asset.auto.tagger.opennlp.internal.configuration.OpenNLPDocumentAssetAutoTagProviderCompanyConfiguration",
	localization = "content/Language",
	name = "opennlp-auto-tag-provider-configuration-name"
)
public interface OpenNLPDocumentAssetAutoTagProviderCompanyConfiguration
	extends OpenNLPDocumentAssetAutoTagCompanyConfiguration {

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(
		deflt = "0.1", description = "confidence-threshold-description",
		name = "confidence-threshold", required = false
	)
	public float confidenceThreshold();

}