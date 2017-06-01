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
 * A {@link JSBundleAsset} which can be resolved from a pool of objects of the
 * same type.
 *
 * For example: several {@link JSModule} objects may point to the same physical
 * asset living in different bundles and each one of them can be requested using
 * its {@link JSBundleAsset} URL.
 *
 * But only the resolved instance chosen by a predefined algorithm from among
 * all is the one that can be requested through the
 * {@link JSResolvableBundleAsset} URL.
 *
 * This is useful to disambiguate duplicated assets.
 * @author Iván Zaera
 */
public interface JSResolvableBundleAsset extends JSBundleAsset {

	public String getResolvedId();

	public String getResolvedURL();

}