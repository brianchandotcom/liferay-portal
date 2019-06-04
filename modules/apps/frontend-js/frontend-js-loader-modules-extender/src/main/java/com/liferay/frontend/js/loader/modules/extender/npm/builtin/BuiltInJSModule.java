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
		_dependencies = dependencies;
	}

	@Override
	public Collection<String> getDependencies() {
		return _dependencies;
	}

	@Override
	public Collection<String> getDependencyPackageNames() {
		List<String> dependencyPackageNames = new ArrayList<>();

		for (String dependency : _dependencies) {
			String packageName = ModuleNameUtil.getPackageName(dependency);

			if (packageName != null) {
				dependencyPackageNames.add(packageName);
			}
		}

		return dependencyPackageNames;
	}

	@Override
	public String getId() {
		return ModuleNameUtil.getModuleId(_jsPackage, _name);
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
		StringBundler sb = new StringBundler(5);

		sb.append(_jsPackage.getName());
		sb.append(StringPool.AT);
		sb.append(_jsPackage.getVersion());
		sb.append(StringPool.SLASH);
		sb.append(_name);

		return sb.toString();
	}

	@Override
	public String getResolvedURL() {
		StringBundler sb = new StringBundler(2);

		sb.append("/o/js/resolved-module/");
		sb.append(getResolvedId());

		return sb.toString();
	}

	@Override
	public String getURL() {
		StringBundler sb = new StringBundler(2);

		sb.append("/o/js/module/");
		sb.append(ModuleNameUtil.getModuleId(_jsPackage, _name));

		return sb.toString();
	}

	private final Collection<String> _dependencies;
	private final JSPackage _jsPackage;
	private final String _name;

}