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
 * <p>
 * A {@link JSBundleAsset} which is selected (resolved) from a pool of
 * equivalent objects of the same type.
 * </p>
 * <p>
 * For example: several {@link JSModule} objects may point to the same physical
 * asset living in different bundles and each one of them can be requested using
 * its {@link JSBundleAsset} URL.
 * </p>
 * <p>
 * But only the resolved instance chosen by a predefined algorithm from among
 * all is the one that can be requested through the
 * {@link JSResolvableBundleAsset} URL.
 * </p>
 * <p>
 * This is useful to disambiguate duplicated assets.
 * </p>
 * @author Iván Zaera
 */
public interface JSResolvableBundleAsset extends JSBundleAsset {

	/**
	 * Get the resolved id (as opposed to the canonical id defined in
	 * {@link JSBundleAsset}) of the asset.
	 * @see this package's summary to know more about resolved assets
	 */
	public String getResolvedId();

	/**
	 * Get the resolved URL (as opposed to the canonical URL defined in
	 * {@link JSBundleAsset}) of the asset.
	 * @see this package's summary to know more about resolved assets
	 */
	public String getResolvedURL();

}