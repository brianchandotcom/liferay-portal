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

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpSession;

/**
 * @author Shuyang Zhou
 */
public class PortletSessionImplTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		_scopePrefix = _portletSessionImpl.scopePrefix;

		_mockHttpSession.setAttribute(_scopePrefix.concat(_KEY_1), _value1);
		_mockHttpSession.setAttribute(_scopePrefix.concat(_KEY_2), _value2);
		_mockHttpSession.setAttribute(_scopePrefix.concat(_KEY_3), _value3);

		_mockHttpSession.setAttribute(_KEY_4, _value4);
		_mockHttpSession.setAttribute(_KEY_5, _value5);
	}

	@Test
	public void testConstructor() {
		Assert.assertSame(_mockHttpSession, _portletSessionImpl.session);
		Assert.assertSame(_portletContext, _portletSessionImpl.portletContext);

		StringBundler sb = new StringBundler(5);

		sb.append(PORTLET_SCOPE_NAMESPACE);
		sb.append(_PORTLET_NAME);
		sb.append(LAYOUT_SEPARATOR);
		sb.append(_PLID);
		sb.append(StringPool.QUESTION);

		Assert.assertEquals(sb.toString(), _portletSessionImpl.scopePrefix);
	}

	@Test
	public void testDirectDelegateMethods() {
		Assert.assertEquals(
			_mockHttpSession.getCreationTime(),
			_portletSessionImpl.getCreationTime());
		Assert.assertSame(
			_mockHttpSession, _portletSessionImpl.getHttpSession());
		Assert.assertEquals(
			_mockHttpSession.getId(), _portletSessionImpl.getId());
		Assert.assertEquals(
			_mockHttpSession.getLastAccessedTime(),
			_portletSessionImpl.getLastAccessedTime());
		Assert.assertEquals(
			_mockHttpSession.getMaxInactiveInterval(),
			_portletSessionImpl.getMaxInactiveInterval());
		Assert.assertSame(
			_portletContext, _portletSessionImpl.getPortletContext());
		Assert.assertEquals(
			_mockHttpSession.isNew(), _portletSessionImpl.isNew());
		Assert.assertFalse(_mockHttpSession.isInvalid());

		_portletSessionImpl.invalidate();

		Assert.assertTrue(_mockHttpSession.isInvalid());

		_portletSessionImpl.setMaxInactiveInterval(Integer.MAX_VALUE);

		Assert.assertEquals(
			Integer.MAX_VALUE, _mockHttpSession.getMaxInactiveInterval());

		HttpSession session = new MockHttpSession();

		_portletSessionImpl.setHttpSession(session);

		Assert.assertSame(session, _portletSessionImpl.session);
	}

	@Test
	public void testGetAttribute() {
		try {
			Assert.assertNull(_portletSessionImpl.getAttribute(null));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			Assert.assertNull(
				_portletSessionImpl.getAttribute(
					null, PortletSession.APPLICATION_SCOPE));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		Assert.assertSame(_value1, _portletSessionImpl.getAttribute(_KEY_1));
		Assert.assertSame(_value2, _portletSessionImpl.getAttribute(_KEY_2));
		Assert.assertSame(_value3, _portletSessionImpl.getAttribute(_KEY_3));
		Assert.assertNull(_portletSessionImpl.getAttribute(_KEY_4));
		Assert.assertNull(_portletSessionImpl.getAttribute(_KEY_5));
		Assert.assertNull(_portletSessionImpl.getAttribute(_KEY_6));
		Assert.assertNull(
			_portletSessionImpl.getAttribute(
				_KEY_1, PortletSession.APPLICATION_SCOPE));
		Assert.assertNull(
			_portletSessionImpl.getAttribute(
				_KEY_2, PortletSession.APPLICATION_SCOPE));
		Assert.assertNull(
			_portletSessionImpl.getAttribute(
				_KEY_3, PortletSession.APPLICATION_SCOPE));
		Assert.assertSame(
			_value4,
			_portletSessionImpl.getAttribute(
				_KEY_4, PortletSession.APPLICATION_SCOPE));
		Assert.assertSame(
			_value5,
			_portletSessionImpl.getAttribute(
				_KEY_5, PortletSession.APPLICATION_SCOPE));
		Assert.assertNull(
			_portletSessionImpl.getAttribute(
				_KEY_6, PortletSession.APPLICATION_SCOPE));
	}

	@Test
	public void testGetAttributeMap() {
		Map<String, Object> attributeMap =
			_portletSessionImpl.getAttributeMap();

		Assert.assertEquals(attributeMap.toString(), 3, attributeMap.size());
		Assert.assertSame(_value1, attributeMap.get(_KEY_1));
		Assert.assertSame(_value2, attributeMap.get(_KEY_2));
		Assert.assertSame(_value3, attributeMap.get(_KEY_3));
		Assert.assertEquals(
			attributeMap,
			_portletSessionImpl.getAttributeMap(PortletSession.PORTLET_SCOPE));

		attributeMap = _portletSessionImpl.getAttributeMap(
			PortletSession.APPLICATION_SCOPE);

		Assert.assertEquals(attributeMap.toString(), 5, attributeMap.size());
		Assert.assertSame(
			_value1, attributeMap.get(_scopePrefix.concat(_KEY_1)));
		Assert.assertSame(
			_value2, attributeMap.get(_scopePrefix.concat(_KEY_2)));
		Assert.assertSame(
			_value3, attributeMap.get(_scopePrefix.concat(_KEY_3)));
		Assert.assertSame(_value4, attributeMap.get(_KEY_4));
		Assert.assertSame(_value5, attributeMap.get(_KEY_5));
	}

	@Test
	public void testGetAttributeNames() {
		Enumeration<String> enumeration =
			_portletSessionImpl.getAttributeNames();

		Assert.assertEquals(_KEY_1, enumeration.nextElement());
		Assert.assertEquals(_KEY_2, enumeration.nextElement());
		Assert.assertEquals(_KEY_3, enumeration.nextElement());
		Assert.assertFalse(enumeration.hasMoreElements());

		enumeration = _portletSessionImpl.getAttributeNames(
			PortletSession.APPLICATION_SCOPE);

		Assert.assertEquals(
			_scopePrefix.concat(_KEY_1), enumeration.nextElement());
		Assert.assertEquals(
			_scopePrefix.concat(_KEY_2), enumeration.nextElement());
		Assert.assertEquals(
			_scopePrefix.concat(_KEY_3), enumeration.nextElement());
		Assert.assertEquals(_KEY_4, enumeration.nextElement());
		Assert.assertEquals(_KEY_5, enumeration.nextElement());
		Assert.assertFalse(enumeration.hasMoreElements());
	}

	@Test
	public void testRemoveAttribute() {
		try {
			_portletSessionImpl.removeAttribute(null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		_portletSessionImpl.removeAttribute(_KEY_1);

		Assert.assertNull(
			_mockHttpSession.getAttribute(_scopePrefix.concat(_KEY_1)));

		_portletSessionImpl.removeAttribute(_KEY_2);

		Assert.assertNull(
			_mockHttpSession.getAttribute(_scopePrefix.concat(_KEY_2)));

		_portletSessionImpl.removeAttribute(_KEY_3);

		Assert.assertNull(
			_mockHttpSession.getAttribute(_scopePrefix.concat(_KEY_3)));

		_portletSessionImpl.removeAttribute(
			_KEY_4, PortletSession.APPLICATION_SCOPE);

		Assert.assertNull(_mockHttpSession.getAttribute(_KEY_4));

		_portletSessionImpl.removeAttribute(
			_KEY_5, PortletSession.APPLICATION_SCOPE);

		Assert.assertNull(_mockHttpSession.getAttribute(_KEY_5));

		Enumeration<String> enumeration = _mockHttpSession.getAttributeNames();

		Assert.assertFalse(enumeration.hasMoreElements());
	}

	@Test
	public void testSetAttribute() {
		try {
			_portletSessionImpl.setAttribute(null, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		String key7 = "key7";
		Object value7 = new Object();

		_portletSessionImpl.setAttribute(key7, value7);

		Assert.assertSame(
			value7, _mockHttpSession.getAttribute(_scopePrefix.concat(key7)));

		String key8 = "key8";
		Object value8 = new Object();

		_portletSessionImpl.setAttribute(
			key8, value8, PortletSession.APPLICATION_SCOPE);

		Assert.assertSame(value8, _mockHttpSession.getAttribute(key8));
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
	private final PortletSessionImpl _portletSessionImpl =
		new PortletSessionImpl(
			_mockHttpSession, _portletContext, _PORTLET_NAME, _PLID);
	private String _scopePrefix;
	private final Object _value1 = new Object();
	private final Object _value2 = new Object();
	private final Object _value3 = new Object();
	private final Object _value4 = new Object();
	private final Object _value5 = new Object();

}