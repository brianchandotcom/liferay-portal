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

package com.liferay.frontend.js.loader.modules.extender.internal.config.generator;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class JSConfigGeneratorModule {

	public JSConfigGeneratorModule(
		JSConfigGeneratorPackage jsConfigGeneratorPackage, String name,
		List<String> dependencies, String contextPath) {

		_jsConfigGeneratorPackage = jsConfigGeneratorPackage;
		_name = name;
		_dependencies = dependencies;

		_resolvedURL = StringBundler.concat(
			contextPath, StringPool.SLASH, _name);
	}

	public List<String> getDependencies() {
		return _dependencies;
	}

	public JSConfigGeneratorPackage getJSConfigGeneratorPackage() {
		return _jsConfigGeneratorPackage;
	}

	public String getName() {
		return _name;
	}

	public String getResolvedURL() {
		return _resolvedURL;
	}

	private final List<String> _dependencies;
	private final JSConfigGeneratorPackage _jsConfigGeneratorPackage;
	private final String _name;
	private final String _resolvedURL;

}