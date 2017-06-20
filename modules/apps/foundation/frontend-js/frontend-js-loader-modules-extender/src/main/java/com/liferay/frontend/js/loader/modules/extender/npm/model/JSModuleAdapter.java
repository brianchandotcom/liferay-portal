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

package com.liferay.frontend.js.loader.modules.extender.npm.model;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides an incomplete implementation of {@link
 * com.liferay.frontend.js.loader.modules.extender.npm.JSModule} that can be
 * reused by existing implementations.
 *
 * @author Iván Zaera
 */
public abstract class JSModuleAdapter implements JSModule {

	/**
	 * Constucts a <code>JSModuleAdapter</code> with the JS package, name, URL,
	 * resolved URL, resolved ID, and dependencies.
	 *
	 * @param jsPackage the module's package
	 * @param name the module's name
	 * @param url the module's canonical URL
	 * @param resolvedURL the URL that gives access to the module to which this
	 *        module is resolved; the module can be this module or another copy
	 *        of this module contained in a different bundle
	 * @param resolvedId the resolved module's ID
	 * @param dependencies the dependencies declared by the module
	 */
	public JSModuleAdapter(
		JSPackage jsPackage, String name, String url, String resolvedURL,
		String resolvedId, Collection<String> dependencies) {

		_jsPackage = jsPackage;
		_name = name;
		_url = url;
		_resolvedURL = resolvedURL;
		_resolvedId = resolvedId;
		_dependencies = dependencies;

		_id = ModuleNameUtil.getModuleId(jsPackage, name);

		for (String dependency : dependencies) {
			_dependencyPackageNames.add(
				ModuleNameUtil.getPackageName(dependency));
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

	private final Collection<String> _dependencies;
	private final List<String> _dependencyPackageNames = new ArrayList<>();
	private final String _id;
	private final JSPackage _jsPackage;
	private final String _name;
	private final String _resolvedId;
	private final String _resolvedURL;
	private final String _url;

}