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

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.nio.ByteBuffer;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class EntityCacheImplTest {

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		_classLoader = EntityCacheImplTest.class.getClassLoader();

		_nullModel = ReflectionTestUtil.getFieldValue(
			BasePersistenceImpl.class, "nullModel");

		_props = (Props)ProxyUtil.newProxyInstance(
			_classLoader, new Class<?>[] {Props.class},
			new PropsInvocationHandler());
	}

	@Test
	public void testPutAndGetNullModel() throws Exception {
		_testPutAndGetNullModel(false);

		_testPutAndGetNullModel(true);
	}

	private void _testPutAndGetNullModel(boolean serialized) throws Exception {
		EntityCacheImpl entityCacheImpl = new EntityCacheImpl();

		entityCacheImpl.setProps(_props);

		entityCacheImpl.setMultiVMPool(
			(MultiVMPool)ProxyUtil.newProxyInstance(
				_classLoader, new Class<?>[] {MultiVMPool.class},
				new MultiVMPoolInvocationHandler(serialized)));

		entityCacheImpl.activate();

		entityCacheImpl.putResult(
			true, EntityCacheImplTest.class, 12345, _nullModel);

		Serializable result = entityCacheImpl.getResult(
			true, EntityCacheImplTest.class, 12345);

		Assert.assertSame(_nullModel, result);
	}

	private ClassLoader _classLoader;
	private Serializable _nullModel;
	private Props _props;

	private class MultiVMPoolInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			String methodName = method.getName();

			if (methodName.equals("getPortalCache")) {
				return ProxyUtil.newProxyInstance(
					_classLoader, new Class<?>[] {PortalCache.class},
					new PortalCacheInvocationHandler(_serialized));
			}

			if (methodName.equals("getPortalCacheManager")) {
				return ProxyUtil.newProxyInstance(
					_classLoader, new Class<?>[] {PortalCacheManager.class},
					new InvocationHandler() {

						@Override
						public Object invoke(
								Object proxy, Method method, Object[] args)
							throws Throwable {

							String methodName = method.getName();

							if (methodName.equals(
									"registerPortalCacheManagerListener")) {

								return true;
							}

							return null;
						}

					});
			}

			return null;
		}

		private MultiVMPoolInvocationHandler(boolean serialized) {
			_serialized = serialized;
		}

		private final boolean _serialized;

	}

	private class PortalCacheInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			String methodName = method.getName();

			if (methodName.equals("get")) {
				if (_serialized) {
					byte[] data = (byte[])_map.get(args[0]);

					Deserializer deserializer = new Deserializer(
						ByteBuffer.wrap(data));

					return deserializer.readObject();
				}

				return _map.get(args[0]);
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

			return null;
		}

		private PortalCacheInvocationHandler(boolean serialized) {
			_serialized = serialized;
		}

		private final Map<Serializable, Object> _map = new HashMap<>();
		private final boolean _serialized;

	}

	private class PropsInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			String methodName = method.getName();

			if (methodName.equals("get")) {
				String key = (String)args[0];

				if (key.equals(PropsKeys.VALUE_OBJECT_ENTITY_BLOCKING_CACHE) ||
					key.equals(PropsKeys.VALUE_OBJECT_ENTITY_CACHE_ENABLED) ||
					key.equals(
						PropsKeys.VALUE_OBJECT_MVCC_ENTITY_CACHE_ENABLED)) {

					return "true";
				}
			}

			return null;
		}

	}

}