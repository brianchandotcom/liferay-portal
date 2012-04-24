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

package com.liferay.portal.json.jabsorb.serializer;

import org.jabsorb.JSONSerializer;
import org.jabsorb.serializer.ObjectMatch;
import org.jabsorb.serializer.SerializerState;
import org.jabsorb.serializer.UnmarshallException;

/**
 * @author Tomas Polesovsky
 */
public class LiferayJSONSerializer extends JSONSerializer {

	@Override
	public Object unmarshall(
			SerializerState serializerState, Class clazz, Object json)
		throws UnmarshallException {

		if (!(serializerState instanceof LiferaySerializerState)) {
			serializerState = new LiferaySerializerState();
		}

		return super.unmarshall(serializerState, clazz, json);
	}

	@Override
	public ObjectMatch tryUnmarshall(
			SerializerState serializerState, Class clazz, Object json)
		throws UnmarshallException {

		if (!(serializerState instanceof LiferaySerializerState)) {
			serializerState = new LiferaySerializerState();
		}

		return super.tryUnmarshall(serializerState, clazz, json);
	}
}