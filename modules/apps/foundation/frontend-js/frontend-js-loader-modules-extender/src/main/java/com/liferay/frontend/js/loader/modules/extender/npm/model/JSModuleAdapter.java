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
 * <p>
 * You can have several copies of the same <code>JSModule</code> in different
 * modules of the portal. For example, suppose you have a module named
 * <code>p@1.0.0/m</code> (the module <code>m</code> residing in package
 * <code>p</code> with version 1.0.0) containing three OSGi bundles:
 * <code>b1.jar</code>, <code>b2.jar</code>, and <code>b3.jar</code>. In this
 * scenario, you would have three JS modules in the {@link
 * com.liferay.frontend.js.loader.modules.extender.internal.npm.NPMRegistry}
 * (one per bundle) and one JS resolved module (depending on the algorithm used,
 * points to one of the three JS modules). The JS module is served to the
 * browser using its JS resolved module (i.e., a virtual entity passed to the
 * browser to avoid using any of the other three JS modules).
 * </p>
 *
 * <p>
 * The modules would look something like this:
 * </p>
 *
 * <p>
 * <b>Modules:</b>
 * <ul>
 * <li>
 * <code>b1.jar:p@1.0.0/m</code> with URL <code>.../b1.jar/p@1.0.0/m</code>
 * </li>
 * <li>
 * <code>b2.jar:p@1.0.0/m</code> with URL <code>.../b2.jar/p@1.0.0/m</code>
 * </li>
 * <li>
 * <code>b3.jar:p@1.0.0/m</code> with URL <code>.../b3.jar/p@1.0.0/m</code>
 * </li>
 * </ul>
 * <b>Resolved Module:</b>
 * <ul>
 * <li>
 * <code>p@1.0.0/m</code> with URL <code>.../p@1.0.0/m</code>
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * The URL of the resolved module does not show any reference to a bundle. Also,
 * when the resolved module is requested, it's internally resolved to one of the
 * other three modules. Therefore, the requester would receive something like
 * <code>.../b2.jar/p@1.0.0/m</code>, but wouldn't notice it since it's
 * transparent to the requester.
 * </p>
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