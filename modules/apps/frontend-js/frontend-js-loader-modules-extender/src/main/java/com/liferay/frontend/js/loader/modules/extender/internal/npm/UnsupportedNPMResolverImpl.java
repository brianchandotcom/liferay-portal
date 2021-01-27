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

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;

import org.osgi.framework.Bundle;

/**
 * This implementation of {@Link NPMResolver} throws a exception in every
 * method.
 *
 * It is instantiated when an OSGi bundle has a `package.json` file but the
 * `manifest.json` is missing or empty (which is a sign that the OSGi bundle has
 * not been built by liferay-npm-bundler but by npm-scripts, thus it uses
 * webpack federation to load modules).
 *
 * @author Iván Zaera Avellón
 */
public class UnsupportedNPMResolverImpl implements NPMResolver {

	public UnsupportedNPMResolverImpl(Bundle bundle) {
		_bundle = bundle;
	}

	@Override
	public JSPackage getDependencyJSPackage(String packageName) {
		_throwUnsupportedOperationException();

		return null;
	}

	@Override
	public JSPackage getJSPackage() {
		_throwUnsupportedOperationException();

		return null;
	}

	@Override
	public String resolveModuleName(String moduleName) {
		_throwUnsupportedOperationException();

		return null;
	}

	private void _throwUnsupportedOperationException() {
		throw new UnsupportedOperationException(
			"Bundle has no AMD Loader support: " + _bundle.getSymbolicName());
	}

	private final Bundle _bundle;

}