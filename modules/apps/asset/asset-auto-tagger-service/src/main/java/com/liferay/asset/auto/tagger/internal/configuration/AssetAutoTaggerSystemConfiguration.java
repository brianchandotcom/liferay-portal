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

package com.liferay.asset.auto.tagger.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(category = "assets")
@Meta.OCD(
	id = "com.liferay.asset.auto.tagger.internal.configuration.AssetAutoTaggerSystemConfiguration",
	localization = "content/Language",
	name = "asset-auto-tagger-configuration-name"
)
public interface AssetAutoTaggerSystemConfiguration {

	/**
	 * Enables asset auto tagging.
	 */
	@Meta.AD(deflt = "true", name = "enabled", required = false)
	public boolean enabled();

	/**
	 * Specifies the maximum number of tags that can be added for a given asset.
	 */
	@Meta.AD(
		description = "maximum-number-of-tags-per-asset-description",
		name = "maximum-number-of-tags-per-asset", required = false
	)
	public int maximumNumberOfTagsPerAsset();

}