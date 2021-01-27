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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class NPMResolverImpl implements NPMResolver {

	public NPMResolverImpl(
		Bundle bundle, NPMRegistry npmRegistry, JSONObject packageJSONObject,
		JSONObject packagesJSONObject) {

		_npmRegistry = npmRegistry;

		_jsPackageIdentifier = _resolveJSPackageIdentifier(
			bundle, packageJSONObject);
		_packageNamesMap = _loadPackageNamesMap(packagesJSONObject);
	}

	@Override
	public JSPackage getDependencyJSPackage(String packageName) {
		JSPackage jsPackage = getJSPackage();

		String destPackageName = _packageNamesMap.get(packageName);

		if (Validator.isNull(destPackageName)) {
			destPackageName = packageName;
		}

		JSPackageDependency jsPackageDependency =
			jsPackage.getJSPackageDependency(destPackageName);

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

	private Map<String, String> _loadPackageNamesMap(
		JSONObject packagesJSONObject) {

		try {
			Map<String, String> map = new HashMap<>();

			Iterator<String> iterator = packagesJSONObject.keys();

			while (iterator.hasNext()) {
				String packageId = iterator.next();

				JSONObject packageJSONObject = packagesJSONObject.getJSONObject(
					packageId);

				JSONObject srcJSONObject = packageJSONObject.getJSONObject(
					"src");
				JSONObject destJSONObject = packageJSONObject.getJSONObject(
					"dest");

				map.put(
					srcJSONObject.getString("name"),
					destJSONObject.getString("name"));
			}

			return map;
		}
		catch (IllegalStateException illegalStateException) {
			throw illegalStateException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private String _resolveJSPackageIdentifier(
		Bundle bundle, JSONObject packageJSONObject) {

		try {
			StringBundler sb = new StringBundler(5);

			sb.append(bundle.getBundleId());
			sb.append(StringPool.SLASH);

			String name = packageJSONObject.getString("name");

			sb.append(name);

			sb.append(StringPool.AT);

			String version = packageJSONObject.getString("version");

			sb.append(version);

			return sb.toString();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private final String _jsPackageIdentifier;
	private final NPMRegistry _npmRegistry;
	private final Map<String, String> _packageNamesMap;

}