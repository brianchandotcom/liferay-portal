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

package com.liferay.asset.list.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Ricardo Couso
 * @review
 */
@ExtendedObjectClassDefinition(category = "assets")
@Meta.OCD(
	id = "com.liferay.asset.list.internal.configuration.AssetListConfiguration",
	localization = "content/Language", name = "asset-list-configuration-name"
)
public interface AssetListConfiguration {

	/**
	 * Set this to <code>true</code> to combine and display in an Asset
	 * Publisher the assets from all personalized views/segments that the
	 * viewing user belongs to for a dynamic content set.
	 *
	 * @return default display style.
	 */
	@Meta.AD(
		deflt = "false",
		description = "combine-assets-from-all-segments-in-asset-publisher-dynamic-description",
		name = "combine-assets-from-all-segments-in-asset-publisher-dynamic",
		required = false
	)
	public boolean combineAssetsFromAllSegmentsDynamic();

	/**
	 * Set this to <code>true</code> to combine and display in an Asset
	 * Publisher the assets from all personalized views/segments that the
	 * viewing user belongs to for a manual content set.
	 *
	 * @return default display style.
	 */
	@Meta.AD(
		deflt = "false",
		description = "combine-assets-from-all-segments-in-asset-publisher-manual-description",
		name = "combine-assets-from-all-segments-in-asset-publisher-manual",
		required = false
	)
	public boolean combineAssetsFromAllSegmentsManual();

}