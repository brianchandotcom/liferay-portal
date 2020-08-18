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

import com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin.BaseBuiltInJSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Collection;

/**
 * Provides a complete implementation of {@link JSModule}.
 *
 * @author Iván Zaera
 */
public class FlatJSModule extends BaseBuiltInJSModule implements JSModule {

	/**
	 * Constructs a <code>FlatJSModule</code> with the module's package, name,
	 * and dependencies.
	 *
	 * @param flatJSPackage the module's package
	 * @param name the module's name
	 * @param dependencies the module names this module depends on
	 */
	public FlatJSModule(
		FlatJSPackage flatJSPackage, String name,
		Collection<String> dependencies, JSONObject flagsJSONObject) {

		super(flatJSPackage, name, dependencies, flagsJSONObject);

		_flatJSPackage = flatJSPackage;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		String fileName = ModuleNameUtil.toFileName(getName());

		URL url = _flatJSPackage.getResourceURL(fileName);

		if (url == null) {
			return null;
		}

		return url.openStream();
	}

	@Override
	public InputStream getSourceMapInputStream() throws IOException {
		String fileName = ModuleNameUtil.toFileName(getName());

		URL url = _flatJSPackage.getResourceURL(fileName + ".map");

		if (url == null) {
			return null;
		}

		return url.openStream();
	}

	private final FlatJSPackage _flatJSPackage;

}