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

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModuleAlias;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides a complete implementation of {@link
 * JSPackage}.
 *
 * @author Iván Zaera
 */
public class FlatJSPackage implements JSPackage {

	/**
	 * Constructs a <code>FlatJSPackage</code> with the package's bundle, name,
	 * version, and default module name.
	 *
	 * @param flatJSBundle the package's bundle
	 * @param name the package's name
	 * @param version the package's version
	 * @param mainModuleName the default module name
	 * @param root whether the package is the root package of the bundle;
	 *        otherwise, the package is an NPM package contained in the
	 *        <code>node_modules</code> folder
	 */
	public FlatJSPackage(
		FlatJSBundle flatJSBundle, String name, String version,
		String mainModuleName, boolean root) {

		_flatJSBundle = flatJSBundle;
		_name = name;
		_version = version;
		_mainModuleName = mainModuleName;
		_root = root;
	}

	/**
	 * Adds the module to the package.
	 *
	 * @param jsModule the NPM module
	 */
	public void addJSModule(JSModule jsModule) {
		_jsModules.add(jsModule);
	}

	public void addJSModuleAlias(JSModuleAlias jsModuleAlias) {
		_jsModuleAliases.add(jsModuleAlias);
	}

	/**
	 * Adds the dependency to another NPM package.
	 *
	 * @param jsPackageDependency the NPM package dependency
	 */
	public void addJSPackageDependency(
		JSPackageDependency jsPackageDependency) {

		_jsPackageDependencies.put(
			jsPackageDependency.getPackageName(), jsPackageDependency);
	}

	@Override
	public String getId() {
		return StringBundler.concat(
			_flatJSBundle.getId(), StringPool.SLASH, _name, StringPool.AT,
			_version);
	}

	@Override
	public FlatJSBundle getJSBundle() {
		return _flatJSBundle;
	}

	@Override
	public Collection<JSModuleAlias> getJSModuleAliases() {
		return _jsModuleAliases;
	}

	@Override
	public Collection<JSModule> getJSModules() {
		return _jsModules;
	}

	@Override
	public Collection<JSPackageDependency> getJSPackageDependencies() {
		return _jsPackageDependencies.values();
	}

	@Override
	public JSPackageDependency getJSPackageDependency(String packageName) {
		return _jsPackageDependencies.get(packageName);
	}

	@Override
	public String getMainModuleName() {
		return _mainModuleName;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getResolvedId() {
		return StringBundler.concat(_name, StringPool.AT, _version);
	}

	@Override
	public URL getResourceURL(String location) {
		JSBundle jsBundle = getJSBundle();

		String path = "META-INF/resources/";

		if (_root) {
			path = path.concat(location);
		}
		else {
			StringBundler sb = new StringBundler(6);

			sb.append("META-INF/resources/node_modules/");

			if (_name.startsWith(StringPool.AT)) {
				sb.append(_name.replace(StringPool.SLASH, "%2F"));
			}
			else {
				sb.append(_name);
			}

			sb.append(StringPool.AT);
			sb.append(_version);
			sb.append(StringPool.SLASH);
			sb.append(location);

			path = sb.toString();
		}

		return jsBundle.getResourceURL(path);
	}

	@Override
	public String getVersion() {
		return _version;
	}

	@Override
	public String toString() {
		return getId();
	}

	private final FlatJSBundle _flatJSBundle;
	private final List<JSModuleAlias> _jsModuleAliases = new ArrayList<>();
	private final List<JSModule> _jsModules = new ArrayList<>();
	private final Map<String, JSPackageDependency> _jsPackageDependencies =
		new HashMap<>();
	private final String _mainModuleName;
	private final String _name;
	private final boolean _root;
	private final String _version;

}