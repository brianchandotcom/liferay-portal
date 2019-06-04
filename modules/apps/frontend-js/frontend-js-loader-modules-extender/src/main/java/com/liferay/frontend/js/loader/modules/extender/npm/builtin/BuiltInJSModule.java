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

package com.liferay.frontend.js.loader.modules.extender.npm.builtin;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides an incomplete implementation of {@link JSModule} that lets its
 * contents be retrieved with an HTTP request to the portal.
 *
 * <p>
 * This class assumes that the {@link BuiltInJSModuleServlet} and {@link
 * BuiltInJSResolvedModuleServlet} are installed and running in the portal.
 * These servlets are responsible for exporting the contents returned by the
 * {@link JSModule#getInputStream()} method implemented by subclasses inheriting
 * from this class.
 * </p>
 *
 * @author Iván Zaera
 */
public abstract class BuiltInJSModule implements JSModule {

	/**
	 * Constructs a <code>BuiltInJSModule</code> with the module's JS package,
	 * name, and dependencies.
	 *
	 * @param jsPackage the package containing the module
	 * @param name the module's name
	 * @param dependencies the module's dependencies
	 */
	public BuiltInJSModule(
		JSPackage jsPackage, String name, Collection<String> dependencies) {

		_jsPackage = jsPackage;
		_name = name;
		_url = _getURL(jsPackage, name);
		_resolvedURL = _getResolvedURL(jsPackage, name);
		_resolvedId = _getResolvedId(jsPackage, name);
		_dependencies = dependencies;

		_id = ModuleNameUtil.getModuleId(jsPackage, name);

		for (String dependency : dependencies) {
			String packageName = ModuleNameUtil.getPackageName(dependency);

			if (packageName != null) {
				_dependencyPackageNames.add(packageName);
			}
		}
	}

	@Override
	public Collection<String> getDependencies() {
		return _dependencies;
	}

	@Override
	public Collection<String> getDependencyPackageNames() {
		return _dependencyPackageNames;
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public JSPackage getJSPackage() {
		return _jsPackage;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getResolvedId() {
		return _resolvedId;
	}

	@Override
	public String getResolvedURL() {
		return _resolvedURL;
	}

	@Override
	public String getURL() {
		return _url;
	}

	/**
	 * Composes a resolved ID given the package and module name.
	 *
	 * @param  jsPackage the NPM package
	 * @param  moduleName the module's name
	 * @return a resolved ID
	 */
	private static String _getResolvedId(
		JSPackage jsPackage, String moduleName) {

		StringBundler sb = new StringBundler(5);

		sb.append(jsPackage.getName());
		sb.append(StringPool.AT);
		sb.append(jsPackage.getVersion());
		sb.append(StringPool.SLASH);
		sb.append(moduleName);

		return sb.toString();
	}

	/**
	 * Composes a resolved URL given the package and module name.
	 *
	 * @param  jsPackage the NPM package
	 * @param  moduleName the module's name
	 * @return a resolved URL
	 */
	private static String _getResolvedURL(
		JSPackage jsPackage, String moduleName) {

		StringBundler sb = new StringBundler(2);

		sb.append("/o/js/resolved-module/");
		sb.append(_getResolvedId(jsPackage, moduleName));

		return sb.toString();
	}

	/**
	 * Composes a canonical URL given the package and module name.
	 *
	 * @param jsPackage the NPM package
	 * @param moduleName the module's name
	 */
	private static String _getURL(JSPackage jsPackage, String moduleName) {
		StringBundler sb = new StringBundler(2);

		sb.append("/o/js/module/");
		sb.append(ModuleNameUtil.getModuleId(jsPackage, moduleName));

		return sb.toString();
	}

	private final Collection<String> _dependencies;
	private final List<String> _dependencyPackageNames = new ArrayList<>();
	private final String _id;
	private final JSPackage _jsPackage;
	private final String _name;
	private final String _resolvedId;
	private final String _resolvedURL;
	private final String _url;

}