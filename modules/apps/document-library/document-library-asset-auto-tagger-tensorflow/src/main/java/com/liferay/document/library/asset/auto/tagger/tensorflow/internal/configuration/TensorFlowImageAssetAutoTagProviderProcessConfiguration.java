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
@ExtendedObjectClassDefinition(category = "assets")
@Meta.OCD(
	id = "com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderProcessConfiguration",
	localization = "content/Language",
	name = "tensorflow-auto-tag-provider-process-configuration-name"
)
public interface TensorFlowImageAssetAutoTagProviderProcessConfiguration {

	/**
	 * Sets the maximum number of times the process is allowed to crash before
	 * it is permanently disabled.
	 */
	@Meta.AD(
		deflt = "5", description = "maximum-number-of-relaunches-description",
		name = "maximum-number-of-relaunches", required = false
	)
	public int maximumNumberOfRelaunches();

	/**
	 * Sets the time in seconds after which the counter is reset.
	 */
	@Meta.AD(
		deflt = "60",
		description = "maximum-number-of-relaunches-timeout-description",
		name = "maximum-number-of-relaunches-timeout", required = false
	)
	public long maximumNumberOfRelaunchesTimeout();

}