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
	public void serialize(JsonContext jsonContext, Object object) {
		_jsonTransformer.transform(new JoddJSONContext(jsonContext), object);
	}

	private final JSONTransformer _jsonTransformer;

}