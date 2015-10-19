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

package com.liferay.portal.verify.test;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.verify.VerifyProcess;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Connection;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.naming.NamingException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 * @author Preston Crary
 */
public abstract class BaseVerifyProcessTestCase {

	@Before
	public void setUp() throws Exception {
		Field field = ReflectionTestUtil.getField(DataAccess.class, "_pacl");

		_pacl = (DataAccess.PACL)field.get(null);

		field.set(null, new PACLWrapper(_pacl));
	}

	@After
	public void tearDown() throws Exception {
		if (_pacl == null) {
			throw new NullPointerException();
		}

		Field field = ReflectionTestUtil.getField(DataAccess.class, "_pacl");

		field.set(null, _pacl);
	}

	@Test
	public void testVerify() throws Exception {
		doVerify();

		assertAllConnectionsClosed();
	}

	protected void assertAllConnectionsClosed() throws Exception {
		for (Connection con : _connections) {
			Assert.assertTrue("A connection was not closed", con.isClosed());
		}
	}

	protected void doVerify() throws Exception {
		VerifyProcess verifyProcess = getVerifyProcess();

		verifyProcess.verify();
	}

	protected abstract VerifyProcess getVerifyProcess();

	private final List<Connection> _connections = new CopyOnWriteArrayList<>();
	private DataAccess.PACL _pacl;

	private class DataSourceInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			try {
				Object result = method.invoke(_instance, args);

				if (result instanceof Connection) {
					_connections.add((Connection)result);
				}

				return result;
			}
			catch (InvocationTargetException ite) {
				throw ite.getTargetException();
			}
		}

		private DataSourceInvocationHandler(Object instance) {
			_instance = instance;
		}

		private final Object _instance;

	}

	private class PACLWrapper implements DataAccess.PACL {

		@Override
		public DataSource getDataSource() {
			return _wrapDataSource(_pacl.getDataSource());
		}

		@Override
		public DataSource getDataSource(String location)
			throws NamingException {

			return _wrapDataSource(_pacl.getDataSource(location));
		}

		private PACLWrapper(DataAccess.PACL pacl) {
			_pacl = pacl;
		}

		private DataSource _wrapDataSource(DataSource dataSource) {
			return (DataSource)ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class<?>[] {DataSource.class},
				new DataSourceInvocationHandler(dataSource));
		}

		private final DataAccess.PACL _pacl;

	}

}