/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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