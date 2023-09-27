/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @review
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