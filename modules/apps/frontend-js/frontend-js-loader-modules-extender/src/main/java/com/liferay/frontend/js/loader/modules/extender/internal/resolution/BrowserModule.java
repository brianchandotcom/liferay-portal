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

package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a JS module as it is seen by the AMD loader.
 *
 * The loader has no notion of package names or versions, it just uses module
 * names and paths for fetching them, thus a module name in the loader is the
 * same as a module ID in the server's domain (see
 * {@link JSModule#getResolvedId()} and {@link JSConfigGeneratorModule#getId()}
 * ).
 *
 * @author Rodolfo Roza Miranda
 * @review
 */
public interface BrowserModule {

	/**
	 * The list of module dependencies, which are unversioned module names that
	 * must be resolved to a fully versioned module name by means of the
	 * package.json file of the package containing the module.
	 *
	 * For example, if a JS module calls:
	 *
	 * var isObject = require('is-object);
	 *
	 * The dependencies of that module equates to:
	 *
	 * ["is-object"]
	 *
	 * @return
	 */
	public Collection<String> getDependencies();

	/**
	 * The list of module dependency mapping, so that we can convert an
	 * unversioned module name to a versioned one.
	 *
	 * For example, if a JS module has a dependencies array like:
	 *
	 * ["is-object/util/checker"]
	 *
	 * The dependencies map will be something similar to:
	 *
	 * {
	 * "is-object": "is-object@1.1.0"
	 * }
	 *
	 * @return a map or <code>null</code>
	 */
	public Map<String, String> getDependenciesMap();

	public JSONObject getFlagsJSONObject();

	public String getName();

	public String getPath();

}