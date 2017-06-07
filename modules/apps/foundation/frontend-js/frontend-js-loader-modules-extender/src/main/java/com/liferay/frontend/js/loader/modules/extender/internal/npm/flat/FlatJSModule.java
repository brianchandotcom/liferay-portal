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
 * A complete implementation of {@link com.liferay.frontend.js.loader.modules.extender.npm.JSModule}.
 * @author Iván Zaera
 */
public class FlatJSModule extends BuiltInJSModule {

	/**
	 * @param jsPackage the package where this module belongs
	 * @param name the name of the module
	 * @param dependencies the names of the modules on which this one depends
	 */
	public FlatJSModule(
		JSPackage jsPackage, String name, Collection<String> dependencies) {

		super(jsPackage, name, dependencies);

		_resource = jsPackage.getResourceURL(
			ModuleNameUtil.toFileName(getName()));
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return _resource.openStream();
	}

	@Override
	public String toString() {
		return getId();
	}

	private final URL _resource;

}