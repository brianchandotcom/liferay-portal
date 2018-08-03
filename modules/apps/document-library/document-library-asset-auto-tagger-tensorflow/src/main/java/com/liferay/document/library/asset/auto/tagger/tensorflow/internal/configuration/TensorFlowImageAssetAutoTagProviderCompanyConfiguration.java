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

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderCompanyConfiguration",
	localization = "content/Language",
	name = "tensorflow-auto-tag-provider-configuration-name"
)
public interface TensorFlowImageAssetAutoTagProviderCompanyConfiguration {

	/**
	 * Enables auto tagging of images using a pre-trained tensorflow model.
	 */
	@Meta.AD(
		deflt = "true", description = "enabled-description", name = "enabled",
		required = false
	)
	public boolean enabled();

	/**
	 * Sets the confidence threshold for the returned tags.
	 */
	@Meta.AD(
		deflt = "0.1", description = "confidence-threshold-description",
		name = "confidence-threshold", required = false
	)
	public float confidenceThreshold();

}