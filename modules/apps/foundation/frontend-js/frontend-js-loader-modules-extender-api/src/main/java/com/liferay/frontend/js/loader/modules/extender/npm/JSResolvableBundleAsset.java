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

package com.liferay.frontend.js.loader.modules.extender.npm;

/**
 * Represents a {@link JSBundleAsset} that is selected/resolved from a pool of
 * equivalent objects of the same type.
 *
 * <p>
 * For example, several {@link JSModule} objects may point to the same physical
 * asset living in different bundles; each of those objects can be requested
 * using its {@link JSBundleAsset} URL. Only the resolved instance chosen by a
 * predefined algorithm, however, is eligible to be requested via the {@link
 * JSResolvableBundleAsset} URL. This is useful to disambiguate duplicated
 * assets.
 * </p>
 *
 * @author Iván Zaera
 */
public interface JSResolvableBundleAsset extends JSBundleAsset {

	/**
	 * Returns the asset's resolved ID; this differs from the canonical ID
	 * defined in the {@link JSBundleAsset}. See this class's package summary
	 * for more information about resolved assets.
	 *
	 * @return the asset's resolved ID
	 */
	public String getResolvedId();

	/**
	 * Returns the asset's resolved URL; this differs from the canonical URL
	 * defined in the {@link JSBundleAsset}. See this class's package summary
	 * for more information about resolved assets.
	 *
	 * @return the asset's resolved URL
	 */
	public String getResolvedURL();

}