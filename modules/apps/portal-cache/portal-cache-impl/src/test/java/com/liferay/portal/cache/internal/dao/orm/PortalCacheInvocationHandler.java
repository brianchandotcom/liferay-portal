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

package com.liferay.portal.cache.internal.dao.orm;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.nio.ByteBuffer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tina Tian
 * @author Preston Crary
 */
public class PortalCacheInvocationHandler implements InvocationHandler {

	public PortalCacheInvocationHandler(
		String portalCacheName, boolean serialized) {

		_portalCacheName = portalCacheName;
		_serialized = serialized;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		String methodName = method.getName();

		if (methodName.equals("get")) {
			if (_serialized) {
				byte[] bytes = (byte[])_map.get(args[0]);

				Deserializer deserializer = new Deserializer(
					ByteBuffer.wrap(bytes));

				return deserializer.readObject();
			}

			return _map.get(args[0]);
		}

		if (methodName.equals("getPortalCacheName")) {
			return _portalCacheName;
		}

		if (methodName.equals("put")) {
			if (_serialized) {
				Serializer serializer = new Serializer();

				serializer.writeObject((Serializable)args[1]);

				ByteBuffer byteBuffer = serializer.toByteBuffer();

				_map.put((Serializable)args[0], byteBuffer.array());
			}
			else {
				_map.put((Serializable)args[0], args[1]);
			}
		}

		if (methodName.equals("remove")) {
			return _map.remove(args[0]);
		}

		return null;
	}

	private final Map<Serializable, Object> _map = new HashMap<>();
	private final String _portalCacheName;
	private final boolean _serialized;

}