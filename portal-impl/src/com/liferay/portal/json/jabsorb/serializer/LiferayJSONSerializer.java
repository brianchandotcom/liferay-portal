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

package com.liferay.portal.json.jabsorb.serializer;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.jabsorb.JSONSerializer;
import org.jabsorb.serializer.ObjectMatch;
import org.jabsorb.serializer.SerializerState;
import org.jabsorb.serializer.UnmarshallException;

import org.json.JSONObject;

/**
 * @author Tomas Polesovsky
 */
public class LiferayJSONSerializer extends JSONSerializer {

	@Override
	public ObjectMatch tryUnmarshall(
			SerializerState serializerState,
			@SuppressWarnings("rawtypes") Class clazz, Object jsonObj)
		throws UnmarshallException {

		if (!(serializerState instanceof LiferaySerializerState)) {
			serializerState = new LiferaySerializerState();
		}

		return super.tryUnmarshall(serializerState, clazz, jsonObj);
	}

	@Override
	public Object unmarshall(
			SerializerState serializerState,
			@SuppressWarnings("rawtypes") Class clazz, Object jsonObj)
		throws UnmarshallException {

		if (!(serializerState instanceof LiferaySerializerState)) {
			serializerState = new LiferaySerializerState();
		}

		return super.unmarshall(serializerState, clazz, jsonObj);
	}

	@Override
	protected Class getClassFromHint(Object o) throws UnmarshallException {
		if (o == null) {
			return null;
		}

		if (o instanceof JSONObject) {
			String className = "(unknown)";
			String contextName = null;
			ClassLoader loader = null;

			try {
				JSONObject jsonObject = (JSONObject)o;
				
				className = jsonObject.getString("javaClass");

				if (jsonObject.has("contextName")) {
					contextName = jsonObject.getString("contextName");
				}

				if (contextName != null) {
					loader = ClassLoaderPool.getClassLoader(contextName);

					if (loader == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to load classLoader for javaClass: " +
									className + " in contextName: " +
										contextName);
						}
					}
				}

				if (loader != null) {
					return Class.forName(className, true, loader);
				}
			}
			catch (Exception e)
			{
				throw new UnmarshallException(
					"Class specified in javaClass hint not found: " + className,
					e);
			}
		}

		return super.getClassFromHint(o);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayJSONSerializer.class);

}