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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.dynamic;

import com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin.BaseBuiltInJSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;

/**
 * Provides an implementation of {@link JSModule} to hold in-memory modules.
 *
 * @author Iván Zaera
 */
public class DynamicJSModule extends BaseBuiltInJSModule implements JSModule {

	public DynamicJSModule(
		JSPackage jsPackage, String name, Collection<String> dependencies,
		String js, String map) {

		super(jsPackage, name, dependencies, null);

		_js = js;
		_map = map;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(_js.getBytes("UTF-8"));
	}

	@Override
	public InputStream getSourceMapInputStream() throws IOException {
		if (_map == null) {
			return null;
		}

		return new ByteArrayInputStream(_map.getBytes("UTF-8"));
	}

	private final String _js;
	private final String _map;

}