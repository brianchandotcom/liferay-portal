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

import com.liferay.client.extension.type.JSImportmapsEntryCET;
import com.liferay.frontend.js.importmaps.extender.JSImportmapsContributor;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportmapsEntryCETJSImportmapsContributor
	implements JSImportmapsContributor {

	public JSImportmapsEntryCETJSImportmapsContributor(
		JSImportmapsEntryCET jsImportmapsEntryCET, JSONFactory jsonFactory) {

		_importmapsJSONObject = jsonFactory.createJSONObject();

		_importmapsJSONObject.put(
			jsImportmapsEntryCET.getBareSpecifier(),
			jsImportmapsEntryCET.getURL());
	}

	@Override
	public JSONObject getImportmapsJSONObject() {
		return _importmapsJSONObject;
	}

	private final JSONObject _importmapsJSONObject;

}