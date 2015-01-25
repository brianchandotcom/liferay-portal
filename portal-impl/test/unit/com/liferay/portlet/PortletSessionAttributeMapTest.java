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

import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.lang.reflect.InvocationTargetException;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpSession;

/**
 * @author Shuyang Zhou
 */
public class PortletSessionAttributeMapTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		_session.setAttribute(_SCOPE_PREFIX.concat(_KEY_1), _value1);
		_session.setAttribute(_SCOPE_PREFIX.concat(_KEY_2), _value2);
		_session.setAttribute(_SCOPE_PREFIX.concat(_KEY_3), _value3);
	}

	@Test
	public void testConstructor() {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(_session);

		Assert.assertSame(_session, portletSessionAttributeMap.session);
		Assert.assertNull(portletSessionAttributeMap.scopePrefix);

		portletSessionAttributeMap = new PortletSessionAttributeMap(
			_session, null);

		Assert.assertSame(_session, portletSessionAttributeMap.session);
		Assert.assertNull(portletSessionAttributeMap.scopePrefix);

		String scopePrefix = "scopePrefix";

		portletSessionAttributeMap = new PortletSessionAttributeMap(
			_session, scopePrefix);

		Assert.assertSame(_session, portletSessionAttributeMap.session);
		Assert.assertSame(scopePrefix, portletSessionAttributeMap.scopePrefix);
	}

	@Test
	public void testContainsKey() {
		_doTestContainsKey(true);
		_doTestContainsKey(false);
	}

	@Test
	public void testContainsValue() {
		_doTestContainsValue(true);
		_doTestContainsValue(false);
	}

	@Test
	public void testEntrySet() {
		_doTestEntrySet(true);
		_doTestEntrySet(false);
	}

	@Test
	public void testEqualsAndHashCode() {
		_doTestEqualsAndHashCode(true);
		_doTestEqualsAndHashCode(false);
	}

	@Test
	public void testGet() {
		_doTestGet(true);
		_doTestGet(false);
	}

	@Test
	public void testIsEmpty() {
		_doTestIsEmpty(true);
		_doTestIsEmpty(false);
	}

	@Test
	public void testKeySet() {
		_doTestKeySet(true);
		_doTestKeySet(false);
	}

	@Test
	public void testPortletScopeFiltering() {
		_session.setAttribute(_KEY_4, _value4);
		_session.setAttribute(_KEY_5, _value5);

		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(_session, _SCOPE_PREFIX);

		Set<Entry<String, Object>> entrySet =
			portletSessionAttributeMap.entrySet();

		Assert.assertEquals(3, entrySet.size());
		Assert.assertTrue(
			entrySet.contains(new SimpleEntry<>(_KEY_1, _value1)));
		Assert.assertTrue(
			entrySet.contains(new SimpleEntry<>(_KEY_2, _value2)));
		Assert.assertTrue(
			entrySet.contains(new SimpleEntry<>(_KEY_3, _value3)));

		portletSessionAttributeMap = new PortletSessionAttributeMap(_session);

		entrySet = portletSessionAttributeMap.entrySet();

		Assert.assertEquals(5, entrySet.size());
		Assert.assertTrue(
			entrySet.contains(
				new SimpleEntry<>(_SCOPE_PREFIX.concat(_KEY_1), _value1)));
		Assert.assertTrue(
			entrySet.contains(
				new SimpleEntry<>(_SCOPE_PREFIX.concat(_KEY_2), _value2)));
		Assert.assertTrue(
			entrySet.contains(
				new SimpleEntry<>(_SCOPE_PREFIX.concat(_KEY_3), _value3)));
		Assert.assertTrue(
			entrySet.contains(new SimpleEntry<>(_KEY_4, _value4)));
		Assert.assertTrue(
			entrySet.contains(new SimpleEntry<>(_KEY_5, _value5)));
	}

	@Test
	public void testSize() {
		_doTestSize(true);
		_doTestSize(false);
	}

	@Test
	public void testUnsupportedMethods() {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(_session);

		try {
			portletSessionAttributeMap.clear();

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		try {
			portletSessionAttributeMap.put(null, null);

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		try {
			ReflectionTestUtil.invokeBridge(
				portletSessionAttributeMap, "put",
				new Class<?>[] {String.class, Object.class}, null, null);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertSame(InvocationTargetException.class, e.getClass());

			Throwable throwable = e.getCause();

			Assert.assertSame(
				UnsupportedOperationException.class, throwable.getClass());
		}

		try {
			portletSessionAttributeMap.putAll(null);

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		try {
			portletSessionAttributeMap.remove(null);

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		Set<String> keySet = portletSessionAttributeMap.keySet();

		try {
			keySet.clear();

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		Set<Map.Entry<String, Object>> entrySet =
			portletSessionAttributeMap.entrySet();

		try {
			entrySet.clear();

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}
	}

	@Test
	public void testValues() {
		_doTestValues(true);
		_doTestValues(false);
	}

	private void _doTestContainsKey(boolean portletScope) {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(
				_session, _getScopePrefix(portletScope));

		Assert.assertFalse(portletSessionAttributeMap.containsKey(null));
		Assert.assertTrue(
			portletSessionAttributeMap.containsKey(
				_encodeKey(portletScope, _KEY_1)));
		Assert.assertTrue(
			portletSessionAttributeMap.containsKey(
				_encodeKey(portletScope, _KEY_2)));
		Assert.assertTrue(
			portletSessionAttributeMap.containsKey(
				_encodeKey(portletScope, _KEY_3)));
		Assert.assertFalse(
			portletSessionAttributeMap.containsKey(
				_encodeKey(portletScope, _KEY_4)));
		Assert.assertFalse(
			portletSessionAttributeMap.containsKey(
				_encodeKey(portletScope, _KEY_5)));
	}

	private void _doTestContainsValue(boolean portletScope) {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(
				_session, _getScopePrefix(portletScope));

		Assert.assertFalse(portletSessionAttributeMap.containsValue(null));
		Assert.assertTrue(portletSessionAttributeMap.containsValue(_value1));
		Assert.assertTrue(portletSessionAttributeMap.containsValue(_value2));
		Assert.assertTrue(portletSessionAttributeMap.containsValue(_value3));
		Assert.assertFalse(portletSessionAttributeMap.containsValue(_value4));
		Assert.assertFalse(portletSessionAttributeMap.containsValue(_value5));
	}

	private void _doTestEntrySet(boolean portletScope) {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(
				_session, _getScopePrefix(portletScope));

		Set<Entry<String, Object>> entrySet =
			portletSessionAttributeMap.entrySet();

		Assert.assertEquals(3, entrySet.size());
		Assert.assertTrue(
			entrySet.contains(
				new SimpleEntry<>(_encodeKey(portletScope, _KEY_1), _value1)));
		Assert.assertTrue(
			entrySet.contains(
				new SimpleEntry<>(_encodeKey(portletScope, _KEY_2), _value2)));
		Assert.assertTrue(
			entrySet.contains(
				new SimpleEntry<>(_encodeKey(portletScope, _KEY_3), _value3)));
	}

	private void _doTestEqualsAndHashCode(boolean portletScope) {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(
				_session, _getScopePrefix(portletScope));

		Map<String, Object> map = new HashMap<>();

		map.put(_encodeKey(portletScope, _KEY_1), _value1);
		map.put(_encodeKey(portletScope, _KEY_2), _value2);
		map.put(_encodeKey(portletScope, _KEY_3), _value3);

		Assert.assertEquals(map, portletSessionAttributeMap);
		Assert.assertEquals(
			map.hashCode(), portletSessionAttributeMap.hashCode());
	}

	private void _doTestGet(boolean portletScope) {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(
				_session, _getScopePrefix(portletScope));

		Assert.assertNull(portletSessionAttributeMap.get(null));
		Assert.assertSame(
			_value1,
			portletSessionAttributeMap.get(_encodeKey(portletScope, _KEY_1)));
		Assert.assertSame(
			_value2,
			portletSessionAttributeMap.get(_encodeKey(portletScope, _KEY_2)));
		Assert.assertSame(
			_value3,
			portletSessionAttributeMap.get(_encodeKey(portletScope, _KEY_3)));
		Assert.assertNull(
			portletSessionAttributeMap.get(_encodeKey(portletScope, _KEY_4)));
		Assert.assertNull(
			portletSessionAttributeMap.get(_encodeKey(portletScope, _KEY_5)));
	}

	private void _doTestIsEmpty(boolean portletScope) {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(
				_session, _getScopePrefix(portletScope));

		Assert.assertFalse(portletSessionAttributeMap.isEmpty());

		portletSessionAttributeMap = new PortletSessionAttributeMap(
			new MockHttpSession(), _getScopePrefix(portletScope));

		Assert.assertTrue(portletSessionAttributeMap.isEmpty());
	}

	private void _doTestKeySet(boolean portletScope) {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(
				_session, _getScopePrefix(portletScope));

		Set<String> keySet = portletSessionAttributeMap.keySet();

		Assert.assertEquals(3, keySet.size());
		Assert.assertTrue(keySet.contains(_encodeKey(portletScope, _KEY_1)));
		Assert.assertTrue(keySet.contains(_encodeKey(portletScope, _KEY_2)));
		Assert.assertTrue(keySet.contains(_encodeKey(portletScope, _KEY_3)));
	}

	private void _doTestSize(boolean portletScope) {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(
				_session, _getScopePrefix(portletScope));

		Assert.assertEquals(3, portletSessionAttributeMap.size());

		portletSessionAttributeMap = new PortletSessionAttributeMap(
			new MockHttpSession(), _getScopePrefix(portletScope));

		Assert.assertEquals(0, portletSessionAttributeMap.size());
	}

	private void _doTestValues(boolean portletScope) {
		PortletSessionAttributeMap portletSessionAttributeMap =
			new PortletSessionAttributeMap(
				_session, _getScopePrefix(portletScope));

		Collection<Object> values = portletSessionAttributeMap.values();

		Assert.assertEquals(3, values.size());
		Assert.assertTrue(values.contains(_value1));
		Assert.assertTrue(values.contains(_value2));
		Assert.assertTrue(values.contains(_value3));
	}

	private String _encodeKey(boolean portletScope, String key) {
		if (portletScope) {
			return key;
		}

		return _SCOPE_PREFIX.concat(key);
	}

	private String _getScopePrefix(boolean portletScope) {
		if (portletScope) {
			return _SCOPE_PREFIX;
		}

		return null;
	}

	private static final String _KEY_1 = "key1";

	private static final String _KEY_2 = "key2";

	private static final String _KEY_3 = "key3";

	private static final String _KEY_4 = "key4";

	private static final String _KEY_5 = "key5";

	private static final String _SCOPE_PREFIX = "scopePrefix?";

	private static final Object _value1 = new Object();
	private static final Object _value2 = new Object();
	private static final Object _value3 = new Object();
	private static final Object _value4 = new Object();
	private static final Object _value5 = new Object();

	private final HttpSession _session = new MockHttpSession();

}