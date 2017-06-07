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
import com.liferay.frontend.js.loader.modules.extender.npm.model.JSModuleAdapter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Collection;

/**
 * <p>
 * An incomplete implementation of {@link com.liferay.frontend.js.loader.modules.extender.npm.JSModule}
 * that lets its contents be retrieved with an HTTP request to the Portal.
 * </p>
 * <p>
 * This class assumes that the {@link com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin.BuiltInJSModuleServlet}
 * and {@link com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin.BuiltInJSResolvedModuleServlet}
 * servlets are installed and running in the Portal, as they are the
 * responsibles for exporting the contents returned by the
 * {@link JSModule#getInputStream()} method implemented by subclasses
 * inheriting from this one.
 * </p>
 * @author Iván Zaera
 */
public abstract class BuiltInJSModule extends JSModuleAdapter {

	/**
	 * @param jsPackage the package containing this module
	 * @param name the name of the module
	 * @param dependencies the dependencies of the module
	 */
	public BuiltInJSModule(
		JSPackage jsPackage, String name, Collection<String> dependencies) {

		super(
			jsPackage, name, _getURL(jsPackage, name),
			_getResolvedURL(jsPackage, name), _getResolvedId(jsPackage, name),
			dependencies);
	}

	/**
	 * Compose a resolved id given a package and a module name.
	 * @param jsPackage an NPM package
	 * @param moduleName a module name
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
	 * Compose a resolved URL given a package and a module name.
	 * @param jsPackage an NPM package
	 * @param moduleName a module name
	 */
	private static String _getResolvedURL(
		JSPackage jsPackage, String moduleName) {

		StringBundler sb = new StringBundler(2);

		sb.append("/o/js/resolved-module/");
		sb.append(_getResolvedId(jsPackage, moduleName));

		return sb.toString();
	}

	/**
	 * Compose a canonical (as opposed to resolved) URL given a package and a
	 * module name.
	 * @param jsPackage an NPM package
	 * @param moduleName a module name
	 */
	private static String _getURL(JSPackage jsPackage, String moduleName) {
		StringBundler sb = new StringBundler(2);

		sb.append("/o/js/module/");
		sb.append(ModuleNameUtil.getModuleId(jsPackage, moduleName));

		return sb.toString();
	}

}