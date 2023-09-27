/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * not been built by liferay-npm-bundler but by npm-scripts, thus it does not
 * use AMD infrastructure).
 *
 * @author Iván Zaera Avellón
 * @review
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