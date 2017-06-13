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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.flat;

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.model.JSPackageAdapter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.net.URL;

/**
 * Provides a complete implementation of {@link
 * com.liferay.frontend.js.loader.modules.extender.npm.JSPackage}.
 *
 * @author Iván Zaera
 */
public class FlatJSPackage extends JSPackageAdapter {

	/**
	 * Constructs a <code>FlatJSPackage</code> with the package's bundle, name,
	 * version, and default module name.
	 *
	 * @param flatJSBundle the package's bundle
	 * @param name the package's name
	 * @param version the package's version
	 * @param mainModuleName the default module name
	 * @param root whether the package is the root package of the bundle;
	 *        otherwise, the package is an NPM package contained in the
	 *        <code>node_modules</code> folder
	 */
	public FlatJSPackage(
		FlatJSBundle flatJSBundle, String name, String version,
		String mainModuleName, boolean root) {

		super(flatJSBundle, name, version, mainModuleName);

		if (root) {
			_basePath = "META-INF/resources/";
		}
		else {
			StringBundler basePath = new StringBundler(5);

			basePath.append("META-INF/resources/node_modules/");

			if (name.startsWith(StringPool.AT)) {
				basePath.append(name.replace(StringPool.SLASH, "%2F"));
			}
			else {
				basePath.append(name);
			}

			basePath.append(StringPool.AT);
			basePath.append(getVersion());
			basePath.append(StringPool.SLASH);

			_basePath = basePath.toString();
		}
	}

	@Override
	public FlatJSBundle getJSBundle() {
		return (FlatJSBundle)super.getJSBundle();
	}

	@Override
	public URL getResourceURL(String location) {
		JSBundle jsBundle = getJSBundle();

		return jsBundle.getResourceURL(_basePath + location);
	}

	@Override
	public String toString() {
		return getId();
	}

	private final String _basePath;

}