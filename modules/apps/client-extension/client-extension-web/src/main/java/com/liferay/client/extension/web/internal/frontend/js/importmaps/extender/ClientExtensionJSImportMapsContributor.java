/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.frontend.js.importmaps.extender;

import com.liferay.client.extension.web.internal.type.deployer.Registrable;
import com.liferay.frontend.js.importmaps.extender.JSImportMapsContributor;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Dictionary;

/**
 * @author Iván Zaera Avellón
 */
public class ClientExtensionJSImportMapsContributor
	implements JSImportMapsContributor, Registrable {

	public ClientExtensionJSImportMapsContributor(
		String bareSpecifier, JSONFactory jsonFactory, String url) {

		_importMapsJSONObject = jsonFactory.createJSONObject();

		_importMapsJSONObject.put(bareSpecifier, url);
	}

	@Override
	public Dictionary<String, Object> getDictionary() {
		return null;
	}

	@Override
	public JSONObject getImportMapsJSONObject() {
		return _importMapsJSONObject;
	}

	private final JSONObject _importMapsJSONObject;

}