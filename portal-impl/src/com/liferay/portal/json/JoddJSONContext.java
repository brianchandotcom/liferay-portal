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

import com.liferay.portal.kernel.json.JSONContext;

import jodd.json.JsonContext;

/**
 * @author Igor Spasic
 */
public class JoddJSONContext implements JSONContext {

	public JoddJSONContext(JsonContext jsonContext) {
		_jsonContext = jsonContext;
	}

	public JsonContext getImplementation() {
		return _jsonContext;
	}

	@Override
	public void write(String content) {
		_jsonContext.write(content);
	}

	@Override
	public void writeQuoted(String content) {
		_jsonContext.writeString(content);
	}

	private final JsonContext _jsonContext;

}