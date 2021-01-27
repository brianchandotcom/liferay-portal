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
 * @author Iván Zaera Avellón
 */
public class InvalidNPMResolverImpl implements NPMResolver {

	public InvalidNPMResolverImpl(Bundle bundle) {
		_bundle = bundle;
	}

	@Override
	public JSPackage getDependencyJSPackage(String packageName) {
		_throwIllegalStateException();

		return null;
	}

	@Override
	public JSPackage getJSPackage() {
		_throwIllegalStateException();

		return null;
	}

	@Override
	public String resolveModuleName(String moduleName) {
		_throwIllegalStateException();

		return null;
	}

	private void _throwIllegalStateException() {
		throw new IllegalStateException(
			"Unable to find META-INF/resources/package.json in " +
				_bundle.getSymbolicName());
	}

	private final Bundle _bundle;

}