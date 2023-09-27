/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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