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

package com.liferay.portlet;

import static com.liferay.portal.kernel.portlet.LiferayPortletSession.LAYOUT_SEPARATOR;
import static com.liferay.portal.kernel.portlet.LiferayPortletSession.PORTLET_SCOPE_NAMESPACE;

import com.liferay.portal.kernel.io.SerializableObjectWrapper;
import com.liferay.portal.kernel.servlet.HttpSessionWrapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
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

	@AdviseWith(adviceClasses = PropsUtilAdvice.class)
	@Test
	public void testConstructor() {
		PropsUtilAdvice.setProps(
			PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		Assert.assertSame(_mockHttpSession, portletSessionImpl.session);
		Assert.assertSame(_portletContext, portletSessionImpl.portletContext);

		StringBundler sb = new StringBundler(5);

		sb.append(PORTLET_SCOPE_NAMESPACE);
		sb.append(_PORTLET_NAME);
		sb.append(LAYOUT_SEPARATOR);
		sb.append(_PLID);
		sb.append(StringPool.QUESTION);

		Assert.assertEquals(sb.toString(), portletSessionImpl.scopePrefix);
	}

	@AdviseWith(adviceClasses = PropsUtilAdvice.class)
	@Test
	public void testDirectDelegateMethods() {
		PropsUtilAdvice.setProps(
			PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

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

	@AdviseWith(adviceClasses = PropsUtilAdvice.class)
	@Test
	public void testGetAttribute() {
		PropsUtilAdvice.setProps(
			PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		try {
			Assert.assertNull(portletSessionImpl.getAttribute(null));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			Assert.assertNull(
				portletSessionImpl.getAttribute(
					null, PortletSession.APPLICATION_SCOPE));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
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

	@AdviseWith(adviceClasses = PropsUtilAdvice.class)
	@Test
	public void testGetAttributeMap() {
		PropsUtilAdvice.setProps(
			PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

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

	@AdviseWith(adviceClasses = PropsUtilAdvice.class)
	@Test
	public void testGetAttributeNames() {
		PropsUtilAdvice.setProps(
			PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

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

	@AdviseWith(adviceClasses = PropsUtilAdvice.class)
	@Test
	public void testRemoveAttribute() {
		PropsUtilAdvice.setProps(
			PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		String scopePrefix = portletSessionImpl.scopePrefix;

		try {
			portletSessionImpl.removeAttribute(null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
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

	@AdviseWith(
		adviceClasses = {
			PropsUtilAdvice.class, PortalClassLoaderUtilAdvice.class
		}
	)
	@Test
	public void testSerializableHttpSessionWrapper() {
		PropsUtilAdvice.setProps(
			PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "true");

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

		// Set/get attribute when value class is not loaded by PortalClassLoader

		String key = "key";
		String value = "value";

		PortalClassLoaderUtilAdvice.setPortalClassLoader(false);

		portletSessionImpl.setAttribute(key, value);

		Assert.assertSame(value, portletSessionImpl.getAttribute(key));
		Assert.assertTrue(
			_mockHttpSession.getAttribute(scopePrefix.concat(key)) instanceof
				SerializableObjectWrapper);

		// Set/get attribute when value class is loaded by PortalClassLoader

		PortalClassLoaderUtilAdvice.setPortalClassLoader(true);

		portletSessionImpl.setAttribute(key, value);

		Assert.assertSame(value, portletSessionImpl.getAttribute(key));
		Assert.assertSame(
			value, _mockHttpSession.getAttribute(scopePrefix.concat(key)));
	}

	@AdviseWith(adviceClasses = PropsUtilAdvice.class)
	@Test
	public void testSetAttribute() {
		PropsUtilAdvice.setProps(
			PropsKeys.PORTLET_SESSION_REPLICATE_ENABLED, "false");

		PortletSessionImpl portletSessionImpl = _getPortletSessionImpl();

		String scopePrefix = portletSessionImpl.scopePrefix;

		try {
			portletSessionImpl.setAttribute(null, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
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

	@Aspect
	public static class PropsUtilAdvice {

		public static void setProps(String name, String value) {
			_props.put(name, value);
		}

		@Around(
			"execution(public static String com.liferay.portal.util." +
				"PropsUtil.get(String)) && args(key)"
		)
		public Object get(String key) {
			return _props.get(key);
		}

		private static final Map<String, String> _props = new HashMap<>();

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

	private final MockHttpSession _mockHttpSession = new MockHttpSession();
	private final Object _value1 = new Object();
	private final Object _value2 = new Object();
	private final Object _value3 = new Object();
	private final Object _value4 = new Object();
	private final Object _value5 = new Object();

}