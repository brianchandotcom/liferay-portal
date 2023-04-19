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

package com.liferay.client.extension.web.internal.frontend.js.importmaps.extender;

import com.liferay.client.extension.web.internal.type.deployer.Registrable;
import com.liferay.frontend.js.importmaps.extender.JSImportmapsContributor;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Dictionary;

/**
 * @author Iván Zaera Avellón
 */
public class ClientExtensionJSImportmapsContributor
	implements JSImportmapsContributor, Registrable {

	public ClientExtensionJSImportmapsContributor(
		String bareSpecifier, JSONFactory jsonFactory, String url) {

		_importmapsJSONObject = jsonFactory.createJSONObject();

		_importmapsJSONObject.put(bareSpecifier, url);
	}

	@Override
	public Dictionary<String, Object> getDictionary() {
		return null;
	}

	@Override
	public JSONObject getImportmapsJSONObject() {
		return _importmapsJSONObject;
	}

	private final JSONObject _importmapsJSONObject;

}