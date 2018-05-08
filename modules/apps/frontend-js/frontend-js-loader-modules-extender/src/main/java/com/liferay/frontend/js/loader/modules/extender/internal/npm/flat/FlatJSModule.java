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

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.builtin.BuiltInJSModule;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Collection;

/**
 * Provides a complete implementation of {@link
 * com.liferay.frontend.js.loader.modules.extender.npm.JSModule}.
 *
 * @author Iván Zaera
 */
public class FlatJSModule extends BuiltInJSModule {

	/**
	 * Constructs a <code>FlatJSModule</code> with the module's package, name,
	 * and dependencies.
	 *
	 * @param jsPackage the module's package
	 * @param name the module's name
	 * @param dependencies the module names this module depends on
	 */
	public FlatJSModule(
		JSPackage jsPackage, String name, Collection<String> dependencies) {

		super(jsPackage, name, dependencies);

		String fileName = ModuleNameUtil.toFileName(getName());

		_jsURL = jsPackage.getResourceURL(fileName);
		_sourceMapURL = jsPackage.getResourceURL(fileName + ".map");
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return _jsURL.openStream();
	}

	@Override
	public InputStream getSourceMapInputStream() throws IOException {
		return _sourceMapURL.openStream();
	}

	@Override
	public String toString() {
		return getId();
	}

	private final URL _jsURL;
	private final URL _sourceMapURL;

}