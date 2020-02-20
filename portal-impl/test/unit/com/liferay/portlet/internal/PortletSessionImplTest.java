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

package com.liferay.portlet.internal;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.servlet.HttpSessionWrapper;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;
import com.liferay.portal.util.PropsUtil;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpSession;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class PortletSessionImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Before
	public void setUp() throws ClassNotFoundException {
		ClassLoader classLoader = PortletSessionImpl.class.getClassLoader();

		_lazySerializableClass = classLoader.loadClass(
			PortletSessionImpl.class.getName() + "$LazySerializable");

		_lazySerializableObjectWrapperClass = classLoader.loadClass(
			PortletSessionImpl.class.getName() +
				"$LazySerializableObjectWrapper");
	}

	@Test
	public void testConstructor() {
		PropsUtil.set(PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		Assert.assertSame(_mockHttpSession, portletSessionImpl.session);
		Assert.assertSame(_portletContext, portletSessionImpl.portletContext);

		StringBundler sb = new StringBundler(5);

		sb.append(LiferayPortletSession.PORTLET_SCOPE_NAMESPACE);
		sb.append(_PORTLET_NAME);
		sb.append(LiferayPortletSession.LAYOUT_SEPARATOR);
		sb.append(_PLID);
		sb.append(StringPool.QUESTION);

		Assert.assertEquals(sb.toString(), portletSessionImpl.scopePrefix);
	}

	@Test
	public void testDirectDelegateMethods() {
		PropsUtil.set(PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		Assert.assertEquals(
			_mockHttpSession.getCreationTime(),
			portletSessionImpl.getCreationTime());
		Assert.assertSame(
			_mockHttpSession, portletSessionImpl.getHttpSession());
		Assert.assertEquals(
			_mockHttpSession.getId(), portletSessionImpl.getId());
		Assert.assertEquals(
			_mockHttpSession.getLastAccessedTime(),
			portletSessionImpl.getLastAccessedTime());
		Assert.assertEquals(
			_mockHttpSession.getMaxInactiveInterval(),
			portletSessionImpl.getMaxInactiveInterval());
		Assert.assertSame(
			_portletContext, portletSessionImpl.getPortletContext());
		Assert.assertEquals(
			_mockHttpSession.isNew(), portletSessionImpl.isNew());

		Assert.assertFalse(_mockHttpSession.isInvalid());

		portletSessionImpl.invalidate();

		Assert.assertTrue(_mockHttpSession.isInvalid());

		portletSessionImpl.setMaxInactiveInterval(Integer.MAX_VALUE);

		Assert.assertEquals(
			Integer.MAX_VALUE, _mockHttpSession.getMaxInactiveInterval());

		HttpSession session = new MockHttpSession();

		portletSessionImpl.setHttpSession(session);

		Assert.assertSame(session, portletSessionImpl.session);
	}

	@Test
	public void testGetAttribute() {
		PropsUtil.set(PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		try {
			Assert.assertNull(portletSessionImpl.getAttribute(null));

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		try {
			Assert.assertNull(
				portletSessionImpl.getAttribute(
					null, PortletSession.APPLICATION_SCOPE));

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		Assert.assertSame(_value1, portletSessionImpl.getAttribute(_KEY_1));
		Assert.assertSame(_value2, portletSessionImpl.getAttribute(_KEY_2));
		Assert.assertSame(_value3, portletSessionImpl.getAttribute(_KEY_3));
		Assert.assertNull(portletSessionImpl.getAttribute(_KEY_4));
		Assert.assertNull(portletSessionImpl.getAttribute(_KEY_5));
		Assert.assertNull(portletSessionImpl.getAttribute(_KEY_6));
		Assert.assertNull(
			portletSessionImpl.getAttribute(
				_KEY_1, PortletSession.APPLICATION_SCOPE));
		Assert.assertNull(
			portletSessionImpl.getAttribute(
				_KEY_2, PortletSession.APPLICATION_SCOPE));
		Assert.assertNull(
			portletSessionImpl.getAttribute(
				_KEY_3, PortletSession.APPLICATION_SCOPE));
		Assert.assertSame(
			_value4,
			portletSessionImpl.getAttribute(
				_KEY_4, PortletSession.APPLICATION_SCOPE));
		Assert.assertSame(
			_value5,
			portletSessionImpl.getAttribute(
				_KEY_5, PortletSession.APPLICATION_SCOPE));
		Assert.assertNull(
			portletSessionImpl.getAttribute(
				_KEY_6, PortletSession.APPLICATION_SCOPE));
	}

	@Test
	public void testGetAttributeMap() {
		PropsUtil.set(PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		String scopePrefix = portletSessionImpl.scopePrefix;

		Map<String, Object> attributeMap = portletSessionImpl.getAttributeMap();

		Assert.assertEquals(attributeMap.toString(), 3, attributeMap.size());
		Assert.assertSame(_value1, attributeMap.get(_KEY_1));
		Assert.assertSame(_value2, attributeMap.get(_KEY_2));
		Assert.assertSame(_value3, attributeMap.get(_KEY_3));
		Assert.assertEquals(
			attributeMap,
			portletSessionImpl.getAttributeMap(PortletSession.PORTLET_SCOPE));

		attributeMap = portletSessionImpl.getAttributeMap(
			PortletSession.APPLICATION_SCOPE);

		Assert.assertEquals(attributeMap.toString(), 5, attributeMap.size());
		Assert.assertSame(
			_value1, attributeMap.get(scopePrefix.concat(_KEY_1)));
		Assert.assertSame(
			_value2, attributeMap.get(scopePrefix.concat(_KEY_2)));
		Assert.assertSame(
			_value3, attributeMap.get(scopePrefix.concat(_KEY_3)));
		Assert.assertSame(_value4, attributeMap.get(_KEY_4));
		Assert.assertSame(_value5, attributeMap.get(_KEY_5));
	}

	@Test
	public void testGetAttributeNames() {
		PropsUtil.set(PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		String scopePrefix = portletSessionImpl.scopePrefix;

		Enumeration<String> enumeration =
			portletSessionImpl.getAttributeNames();

		Assert.assertEquals(_KEY_1, enumeration.nextElement());
		Assert.assertEquals(_KEY_2, enumeration.nextElement());
		Assert.assertEquals(_KEY_3, enumeration.nextElement());
		Assert.assertFalse(enumeration.hasMoreElements());

		enumeration = portletSessionImpl.getAttributeNames(
			PortletSession.APPLICATION_SCOPE);

		Assert.assertEquals(
			scopePrefix.concat(_KEY_1), enumeration.nextElement());
		Assert.assertEquals(
			scopePrefix.concat(_KEY_2), enumeration.nextElement());
		Assert.assertEquals(
			scopePrefix.concat(_KEY_3), enumeration.nextElement());
		Assert.assertEquals(_KEY_4, enumeration.nextElement());
		Assert.assertEquals(_KEY_5, enumeration.nextElement());
		Assert.assertFalse(enumeration.hasMoreElements());
	}

	@Test
	public void testInvalidate() {
		PortletSessionImpl portletSessionImpl = new PortletSessionImpl(
			new MockHttpSession(), _portletContext, _PORTLET_NAME, _PLID);

		Assert.assertFalse(portletSessionImpl.isInvalidated());

		portletSessionImpl.invalidate();

		try {
			Assert.assertSame(_value1, portletSessionImpl.getAttribute(_KEY_1));

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
		}

		try {
			Assert.assertEquals(
				_mockHttpSession.getCreationTime(),
				portletSessionImpl.getCreationTime());

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
		}

		Assert.assertTrue(portletSessionImpl.isInvalidated());
	}

	@Test
	public void testLazySerializableObjectWrapper() throws Exception {
		Constructor<?> constructor =
			_lazySerializableObjectWrapperClass.getDeclaredConstructor(
				Serializable.class);

		constructor.setAccessible(true);

		TestSerializable testSerializable = new TestSerializable(
			"testSerializableName");

		Object lazySerializableObjectWrapperObject = constructor.newInstance(
			testSerializable);

		Assert.assertSame(
			testSerializable,
			ReflectionTestUtil.invoke(
				lazySerializableObjectWrapperObject, "getSerializable",
				new Class<?>[0]));

		Assert.assertNotSame(
			testSerializable,
			ReflectionTestUtil.invoke(
				_getDeserializedObject(lazySerializableObjectWrapperObject),
				"getSerializable", new Class<?>[0]));

		Assert.assertEquals(
			testSerializable,
			(TestSerializable)ReflectionTestUtil.invoke(
				_getDeserializedObject(lazySerializableObjectWrapperObject),
				"getSerializable", new Class<?>[0]));

		Assert.assertEquals(
			testSerializable,
			(TestSerializable)ReflectionTestUtil.invoke(
				_getDeserializedObject(
					_getDeserializedObject(
						lazySerializableObjectWrapperObject)),
				"getSerializable", new Class<?>[0]));

		// Test with broken classloader

		ClassLoaderPool.unregister(ClassLoaderPool.class.getClassLoader());

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassNotFoundException classNotFoundException =
			new ClassNotFoundException();

		currentThread.setContextClassLoader(
			new ClassLoader() {

				@Override
				public Class<?> loadClass(String name)
					throws ClassNotFoundException {

					if (name.equals(TestSerializable.class.getName())) {
						throw classNotFoundException;
					}

					return super.loadClass(name);
				}

			});

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					_lazySerializableClass.getName(), Level.ALL)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertNull(
				ReflectionTestUtil.invoke(
					_getDeserializedObject(lazySerializableObjectWrapperObject),
					"getSerializable", new Class<?>[0]));

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to deserialize object", logRecord.getMessage());
			Assert.assertSame(classNotFoundException, logRecord.getThrown());
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Test
	public void testRemoveAttribute() {
		PropsUtil.set(PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		String scopePrefix = portletSessionImpl.scopePrefix;

		try {
			portletSessionImpl.removeAttribute(null);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		portletSessionImpl.removeAttribute(_KEY_1);

		Assert.assertNull(
			_mockHttpSession.getAttribute(scopePrefix.concat(_KEY_1)));

		portletSessionImpl.removeAttribute(_KEY_2);

		Assert.assertNull(
			_mockHttpSession.getAttribute(scopePrefix.concat(_KEY_2)));

		portletSessionImpl.removeAttribute(_KEY_3);

		Assert.assertNull(
			_mockHttpSession.getAttribute(scopePrefix.concat(_KEY_3)));

		portletSessionImpl.removeAttribute(
			_KEY_4, PortletSession.APPLICATION_SCOPE);

		Assert.assertNull(_mockHttpSession.getAttribute(_KEY_4));

		portletSessionImpl.removeAttribute(
			_KEY_5, PortletSession.APPLICATION_SCOPE);

		Assert.assertNull(_mockHttpSession.getAttribute(_KEY_5));

		Enumeration<String> enumeration = _mockHttpSession.getAttributeNames();

		Assert.assertFalse(enumeration.hasMoreElements());
	}

	@AdviseWith(adviceClasses = PortalClassLoaderUtilAdvice.class)
	@Test
	public void testSerializableHttpSessionWrapper() {
		PropsUtil.set(PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "true");

		// Constructor

		PortletSessionImpl portletSessionImpl = new PortletSessionImpl(
			_mockHttpSession, _portletContext, _PORTLET_NAME, _PLID);

		String scopePrefix = portletSessionImpl.scopePrefix;

		Assert.assertTrue(
			portletSessionImpl.session instanceof HttpSessionWrapper);

		HttpSessionWrapper httpSessionWrapper =
			(HttpSessionWrapper)portletSessionImpl.session;

		Assert.assertSame(
			_mockHttpSession, httpSessionWrapper.getWrappedSession());

		// Set http session when session is not SerializableHttpSessionWrapper

		portletSessionImpl.setHttpSession(_mockHttpSession);

		Assert.assertNotSame(_mockHttpSession, portletSessionImpl.session);
		Assert.assertTrue(
			portletSessionImpl.session instanceof HttpSessionWrapper);

		// Set http session when session is SerializableHttpSessionWrapper

		portletSessionImpl.setHttpSession(httpSessionWrapper);

		Assert.assertSame(httpSessionWrapper, portletSessionImpl.session);

		// Set/get attribute when value class is loaded by the bootstrap class
		// loader

		String key = "key";
		String value = "value";

		portletSessionImpl.setAttribute(key, value);

		Assert.assertSame(value, portletSessionImpl.getAttribute(key));
		Assert.assertSame(
			value, _mockHttpSession.getAttribute(scopePrefix.concat(key)));

		// Set/get attribute when value class is not loaded by the portal class
		// loader

		TestSerializable testSerializable = new TestSerializable("name");

		PortalClassLoaderUtilAdvice.setPortalClassLoader(false);

		portletSessionImpl.setAttribute(key, testSerializable);

		Assert.assertSame(
			testSerializable, portletSessionImpl.getAttribute(key));
		Assert.assertTrue(
			_lazySerializableObjectWrapperClass.isInstance(
				_mockHttpSession.getAttribute(scopePrefix.concat(key))));

		// Set/get non-serializable attribute when value class is not loaded by
		// PortalClassLoader

		Object objectValue = new Object();

		portletSessionImpl.setAttribute(key, objectValue);

		Assert.assertSame(objectValue, portletSessionImpl.getAttribute(key));
		Assert.assertSame(
			objectValue,
			_mockHttpSession.getAttribute(scopePrefix.concat(key)));

		// Set/get attribute when value class is loaded by PortalClassLoader

		PortalClassLoaderUtilAdvice.setPortalClassLoader(true);

		portletSessionImpl.setAttribute(key, testSerializable);

		Assert.assertSame(
			testSerializable, portletSessionImpl.getAttribute(key));
		Assert.assertSame(
			testSerializable,
			_mockHttpSession.getAttribute(scopePrefix.concat(key)));
	}

	@Test
	public void testSetAttribute() {
		PropsUtil.set(PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		String scopePrefix = portletSessionImpl.scopePrefix;

		try {
			portletSessionImpl.setAttribute(null, null);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		String key7 = "key7";
		Object value7 = new Object();

		portletSessionImpl.setAttribute(key7, value7);

		Assert.assertSame(
			value7, _mockHttpSession.getAttribute(scopePrefix.concat(key7)));

		String key8 = "key8";
		Object value8 = new Object();

		portletSessionImpl.setAttribute(
			key8, value8, PortletSession.APPLICATION_SCOPE);

		Assert.assertSame(value8, _mockHttpSession.getAttribute(key8));
	}

	@Aspect
	public static class PortalClassLoaderUtilAdvice {

		public static void setPortalClassLoader(boolean portalClassLoader) {
			_portalClassLoader = portalClassLoader;
		}

		@Around(
			"execution(public static boolean com.liferay.portal.kernel.util." +
				"PortalClassLoaderUtil.isPortalClassLoader(ClassLoader)) && " +
					"args(classLoader)"
		)
		public boolean isPortalClassLoader(ClassLoader classLoader) {
			return _portalClassLoader;
		}

		private static boolean _portalClassLoader;

	}

	private Object _getDeserializedObject(Object object) throws Exception {
		try (UnsyncByteArrayOutputStream ubaos =
				new UnsyncByteArrayOutputStream()) {

			try (ObjectOutputStream oos = new ObjectOutputStream(ubaos)) {
				oos.writeObject(object);
			}

			try (UnsyncByteArrayInputStream ubais =
					new UnsyncByteArrayInputStream(
						ubaos.unsafeGetByteArray(), 0, ubaos.size());
				ObjectInputStream ois = new ObjectInputStream(ubais)) {

				return ois.readObject();
			}
		}
	}

	private PortletSessionImpl _getPortletSessionImpl() {
		PortletSessionImpl portletSessionImpl = new PortletSessionImpl(
			_mockHttpSession, _portletContext, _PORTLET_NAME, _PLID);

		String scopePrefix = portletSessionImpl.scopePrefix;

		_mockHttpSession.setAttribute(scopePrefix.concat(_KEY_1), _value1);
		_mockHttpSession.setAttribute(scopePrefix.concat(_KEY_2), _value2);
		_mockHttpSession.setAttribute(scopePrefix.concat(_KEY_3), _value3);

		_mockHttpSession.setAttribute(_KEY_4, _value4);
		_mockHttpSession.setAttribute(_KEY_5, _value5);

		return portletSessionImpl;
	}

	private static final String _KEY_1 = "key1";

	private static final String _KEY_2 = "key2";

	private static final String _KEY_3 = "key3";

	private static final String _KEY_4 = "key4";

	private static final String _KEY_5 = "key5";

	private static final String _KEY_6 = "key6";

	private static final long _PLID = 100;

	private static final String _PORTLET_NAME = "portletName";

	private static final PortletContext _portletContext =
		(PortletContext)ProxyUtil.newProxyInstance(
			PortletContext.class.getClassLoader(),
			new Class<?>[] {PortletContext.class},
			new InvocationHandler() {

				@Override
				public Object invoke(
					Object proxy, Method method, Object[] args) {

					throw new UnsupportedOperationException();
				}

			});

	private Class<?> _lazySerializableClass;
	private Class<?> _lazySerializableObjectWrapperClass;
	private final MockHttpSession _mockHttpSession = new MockHttpSession();
	private final Object _value1 = new Object();
	private final Object _value2 = new Object();
	private final Object _value3 = new Object();
	private final Object _value4 = new Object();
	private final Object _value5 = new Object();

	private static class TestSerializable implements Serializable {

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof TestSerializable)) {
				return false;
			}

			TestSerializable testSerializable = (TestSerializable)object;

			return Objects.equals(_name, testSerializable._name);
		}

		public String getName() {
			return _name;
		}

		@Override
		public int hashCode() {
			return _name.hashCode();
		}

		private TestSerializable(String name) {
			_name = name;
		}

		private final String _name;

	}

}