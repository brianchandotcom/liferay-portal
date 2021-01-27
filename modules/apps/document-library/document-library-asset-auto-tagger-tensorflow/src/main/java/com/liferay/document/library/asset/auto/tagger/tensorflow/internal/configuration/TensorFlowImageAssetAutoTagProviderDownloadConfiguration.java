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
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderDownloadConfiguration"
)
public interface TensorFlowImageAssetAutoTagProviderDownloadConfiguration {

	@Meta.AD(
		deflt = "https://repository.liferay.com/nexus/service/local/artifact/maven/redirect?r=third-party&g=com.liferay&a=org.tensorflow.models.inception&v=5h&e=jar",
		required = false
	)
	public String modelDownloadURL();

}