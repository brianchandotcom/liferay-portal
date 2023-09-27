/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.internal.npm.flat;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModuleAlias;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.ModifiableJSPackage;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Provides a complete implementation of {@link JSPackage}.
 *
 * @author Iván Zaera
 */
public class FlatJSPackage implements ModifiableJSPackage {

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
	@Override
	public void addJSModule(JSModule jsModule) {
		if (jsModule.getJSPackage() != this) {
			throw new IllegalArgumentException(
				"The given JS module does not belong to this JS package");
		}

		if (_jsModules.putIfAbsent(jsModule.getName(), jsModule) != null) {
			throw new IllegalStateException(
				"A JS module with the same name already exists");
		}
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
	public JSModule getJSModule(String packagePath) {
		return _jsModules.get(packagePath);
	}

	@Override
	public Collection<JSModuleAlias> getJSModuleAliases() {
		return _jsModuleAliases;
	}

	@Override
	public Collection<JSModule> getJSModules() {
		return _jsModules.values();
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
		String path = "META-INF/resources/";

		if (_root) {
			path = path.concat(location);
		}
		else {
			StringBundler sb = new StringBundler(6);

			sb.append("META-INF/resources/node_modules/");

			if (_name.startsWith(StringPool.AT)) {
				sb.append(StringUtil.replace(_name, CharPool.SLASH, "%2F"));
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

		return _flatJSBundle.getResourceURL(path);
	}

	@Override
	public String getVersion() {
		return _version;
	}

	@Override
	public void removeJSModule(JSModule jsModule) {
		if (jsModule.getJSPackage() != this) {
			throw new IllegalArgumentException(
				"The given JS module does not belong to this JS package");
		}

		_jsModules.remove(jsModule.getName());
	}

	@Override
	public void replaceJSModule(JSModule jsModule) {
		if (jsModule.getJSPackage() != this) {
			throw new IllegalArgumentException(
				"The given JS module does not belong to this JS package");
		}

		if (_jsModules.replace(jsModule.getName(), jsModule) == null) {
			throw new IllegalArgumentException(
				"No JS module with the same name exists");
		}
	}

	@Override
	public String toString() {
		return getId();
	}

	private final FlatJSBundle _flatJSBundle;
	private final List<JSModuleAlias> _jsModuleAliases = new ArrayList<>();
	private final ConcurrentMap<String, JSModule> _jsModules =
		new ConcurrentHashMap<>();
	private final Map<String, JSPackageDependency> _jsPackageDependencies =
		new HashMap<>();
	private final String _mainModuleName;
	private final String _name;
	private final boolean _root;
	private final String _version;

}