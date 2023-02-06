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

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class JSModulesCache {

	public JSModulesCache() {
		exactMatchMap = Collections.emptyMap();
		jsModules = Collections.emptyMap();
		jsPackages = Collections.emptyMap();
		jsPackageVersions = Collections.emptyList();
		resolvedJSModules = Collections.emptyMap();
		resolvedJSPackages = Collections.emptyMap();
	}

	public JSModulesCache(
		Map<String, String> exactMatchMap, Map<String, JSModule> jsModules,
		Map<String, JSPackage> jsPackages,
		List<JSPackageVersion> jsPackageVersions,
		Map<String, JSModule> resolvedJSModules,
		Map<String, JSPackage> resolvedJSPackages) {

		this.exactMatchMap = Collections.unmodifiableMap(exactMatchMap);
		this.jsModules = Collections.unmodifiableMap(jsModules);
		this.jsPackages = Collections.unmodifiableMap(jsPackages);
		this.jsPackageVersions = Collections.unmodifiableList(
			jsPackageVersions);
		this.resolvedJSModules = Collections.unmodifiableMap(resolvedJSModules);
		this.resolvedJSPackages = Collections.unmodifiableMap(
			resolvedJSPackages);
	}

	public final Map<String, String> exactMatchMap;
	public final Map<String, JSModule> jsModules;
	public final Map<String, JSPackage> jsPackages;
	public final List<JSPackageVersion> jsPackageVersions;
	public final Map<String, JSModule> resolvedJSModules;
	public final Map<String, JSPackage> resolvedJSPackages;

}