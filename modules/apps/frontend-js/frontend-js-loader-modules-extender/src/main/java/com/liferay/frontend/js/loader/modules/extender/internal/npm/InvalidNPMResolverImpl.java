/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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