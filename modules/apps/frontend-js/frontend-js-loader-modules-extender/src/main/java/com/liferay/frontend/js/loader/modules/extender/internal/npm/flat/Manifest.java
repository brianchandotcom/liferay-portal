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

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Iván Zaera Avellón
 */
public class Manifest {

	public Manifest(JSONObject packagesJSONObject) {
		_packagesJSONObject = packagesJSONObject;
	}

	public JSONObject getFlagsJSONObject(String packageId, String fileName) {
		if (_packagesJSONObject == null) {
			return null;
		}

		JSONObject packageJSONObject = _packagesJSONObject.getJSONObject(
			packageId);

		if (packageJSONObject == null) {
			return null;
		}

		JSONObject modulesJSONObject = packageJSONObject.getJSONObject(
			"modules");

		if (modulesJSONObject == null) {
			return null;
		}

		JSONObject moduleJSONObject = modulesJSONObject.getJSONObject(fileName);

		if (moduleJSONObject == null) {
			return null;
		}

		return moduleJSONObject.getJSONObject("flags");
	}

	private final JSONObject _packagesJSONObject;

}