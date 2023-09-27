/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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