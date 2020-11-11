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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides an incomplete implementation of {@link JSModule} to hold modules
 * to be served through {@link BuiltInJSModuleServlet} and
 * {@link BuiltInJSResolvedModuleServlet}.
 *
 * @author Iván Zaera
 * @review
 */
public abstract class BaseBuiltInJSModule implements JSModule {

	/**
	 * Constructs a <code>BaseBuiltInJSModule</code> with the module's package,
	 * name, and dependencies.
	 *
	 * @param jsPackage the module's package
	 * @param name the module's name
	 * @param dependencies the module names this module depends on
	 */
	public BaseBuiltInJSModule(
		JSPackage jsPackage, String name, Collection<String> dependencies,
		JSONObject flagsJSONObject) {

		_jsPackage = jsPackage;
		_name = name;
		_dependencies = dependencies;
		_flagsJSONObject = flagsJSONObject;

		_id = ModuleNameUtil.getModuleId(_jsPackage, _name);

		StringBundler sb = new StringBundler(5);

		sb.append(_jsPackage.getName());
		sb.append(StringPool.AT);
		sb.append(_jsPackage.getVersion());
		sb.append(StringPool.SLASH);
		sb.append(_name);

		_resolvedId = sb.toString();
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
	public JSONObject getFlagsJSONObject() {
		return _flagsJSONObject;
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

	@Override
	public String toString() {
		return getId();
	}

	private final Collection<String> _dependencies;
	private final JSONObject _flagsJSONObject;
	private final String _id;
	private final JSPackage _jsPackage;
	private final String _name;
	private final String _resolvedId;

}