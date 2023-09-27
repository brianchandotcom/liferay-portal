/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONTransformer;

import jodd.json.JsonContext;
import jodd.json.TypeJsonSerializer;

/**
 * @author Igor Spasic
 */
public class JoddJsonTransformer implements TypeJsonSerializer<Object> {

	public JoddJsonTransformer(JSONTransformer jsonTransformer) {
		_jsonTransformer = jsonTransformer;
	}

	@Override
	public boolean serialize(JsonContext jsonContext, Object object) {
		_jsonTransformer.transform(new JoddJSONContext(jsonContext), object);

		return true;
	}

	private final JSONTransformer _jsonTransformer;

}