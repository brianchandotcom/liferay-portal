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
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class NPMResolverImpl implements NPMResolver {

	public NPMResolverImpl(
		Bundle bundle, JSONFactory jsonFactory, NPMRegistry npmRegistry) {

		_jsonFactory = jsonFactory;
		_npmRegistry = npmRegistry;

		_jsPackageIdentifier = _resolveJSPackageIndentifier(bundle);
	}

	@Override
	public JSPackage getDependencyJSPackage(String packageName) {
		JSPackage jsPackage = getJSPackage();

		JSPackageDependency jsPackageDependency =
			jsPackage.getJSPackageDependency(packageName);

		if (jsPackageDependency == null) {
			return null;
		}

		return _npmRegistry.resolveJSPackageDependency(jsPackageDependency);
	}

	@Override
	public JSPackage getJSPackage() {
		return _npmRegistry.getJSPackage(_jsPackageIdentifier);
	}

	@Override
	public String resolveModuleName(String moduleName) {
		String packageName = ModuleNameUtil.getPackageName(moduleName);

		JSPackage jsPackage = getJSPackage();

		if (!packageName.equals(jsPackage.getName())) {
			jsPackage = getDependencyJSPackage(packageName);

			if (jsPackage == null) {
				return null;
			}
		}

		StringBundler sb = new StringBundler(3);

		sb.append(jsPackage.getResolvedId());

		String packagePath = ModuleNameUtil.getPackagePath(moduleName);

		if (packagePath != null) {
			sb.append(StringPool.SLASH);
			sb.append(packagePath);
		}

		return sb.toString();
	}

	private String _resolveJSPackageIndentifier(Bundle bundle) {
		try {
			URL url = bundle.getResource("META-INF/resources/package.json");

			String content = StringUtil.read(url.openStream());

			JSONObject jsonObject = _jsonFactory.createJSONObject(content);

			String name = jsonObject.getString("name");
			String version = jsonObject.getString("version");

			StringBundler sb = new StringBundler(5);

			sb.append(bundle.getBundleId());
			sb.append(StringPool.SLASH);
			sb.append(name);
			sb.append(StringPool.AT);
			sb.append(version);

			return sb.toString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private final JSONFactory _jsonFactory;
	private final String _jsPackageIdentifier;
	private final NPMRegistry _npmRegistry;

}