/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.json.JSONDeserializer;

import java.io.Reader;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONDeserializerImpl<T> implements JSONDeserializer<T> {

	public JSONDeserializerImpl() {
		_jsonDeserializer = new flexjson.JSONDeserializer<T>();

		_jsonDeserializer.use(Object.class, new PortalBeanObjectFactory());
	}

	public T deserialize(Reader input) {
		return _jsonDeserializer.deserialize(input);
	}

	public T deserialize(String input) {
		return _jsonDeserializer.deserialize(input);
	}

	public JSONDeserializer<T> use(String path, Class<?> clazz) {
		_jsonDeserializer.use(path, clazz);

		return this;
	}

	private flexjson.JSONDeserializer<T> _jsonDeserializer;

}