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

package com.liferay.portal.kernel.test.rule;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.test.rule.NewEnv.Environment;
import com.liferay.portal.kernel.test.rule.NewEnv.JVMArgsLine;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@Environment(variables = {"ENV_KEY=ENV_VALUE"})
@JVMArgsLine("-Dkey1=default1 -Dkey2=default2")
@NewEnv(type = NewEnv.Type.JVM)
public class NewEnvJVMTestRuleTest {

	@BeforeClass
	public static void setUpClass() {
		System.setProperty(
			_SYSTEM_PROPERTY_KEY_ENVIRONMENT, _toString(_getEnvironment()));
	}

	@Before
	public void setUp() {
		Assert.assertEquals(0, _counter.getAndIncrement());
		Assert.assertNull(_processId);

		_processId = getProcessId();

		_parentEnvironment = _fromString(
			System.getProperty(_SYSTEM_PROPERTY_KEY_ENVIRONMENT));
	}

	@After
	public void tearDown() {
		Assert.assertEquals(2, _counter.getAndIncrement());

		assertProcessId();
	}

	@Test
	public void testNewJVM1() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Assert.assertEquals("default1", System.getProperty("key1"));
		Assert.assertEquals("default2", System.getProperty("key2"));
		Assert.assertNull(System.getProperty("key3"));
	}

	@JVMArgsLine("-Dkey1=value1")
	@Test
	public void testNewJVM2() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Assert.assertEquals("value1", System.getProperty("key1"));
		Assert.assertEquals("default2", System.getProperty("key2"));
		Assert.assertNull(System.getProperty("key3"));
	}

	@JVMArgsLine("-Dkey2=value2")
	@Test
	public void testNewJVM3() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Assert.assertEquals("default1", System.getProperty("key1"));
		Assert.assertEquals("value2", System.getProperty("key2"));
		Assert.assertNull(System.getProperty("key3"));
	}

	@JVMArgsLine("-Dkey1=value1 -Dkey2=value2")
	@Test
	public void testNewJVM4() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Assert.assertEquals("value1", System.getProperty("key1"));
		Assert.assertEquals("value2", System.getProperty("key2"));
		Assert.assertNull(System.getProperty("key3"));
	}

	@JVMArgsLine("-Dkey1=value1 -Dkey2=value2 -Dkey3=value3")
	@Test
	public void testNewJVM5() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Assert.assertEquals("value1", System.getProperty("key1"));
		Assert.assertEquals("value2", System.getProperty("key2"));
		Assert.assertEquals("value3", System.getProperty("key3"));
	}

	@Environment(variables = {})
	@JVMArgsLine(
		"-D" + _SYSTEM_PROPERTY_KEY_ENVIRONMENT + "=${" +
			_SYSTEM_PROPERTY_KEY_ENVIRONMENT + "}"
	)
	@Test
	public void testNewJVM6() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Map<String, String> environment = _getEnvironment();

		Assert.assertEquals("ENV_VALUE", environment.get("ENV_KEY"));

		_parentEnvironment.put("ENV_KEY", "ENV_VALUE");

		Assert.assertEquals(_parentEnvironment, environment);
	}

	@Environment(variables = {"USER=UNIT_TEST", "ENV_KEY=NEW_VALUE"})
	@JVMArgsLine(
		"-D" + _SYSTEM_PROPERTY_KEY_ENVIRONMENT + "=${" +
			_SYSTEM_PROPERTY_KEY_ENVIRONMENT + "}"
	)
	@Test
	public void testNewJVM7() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Map<String, String> environment = _getEnvironment();

		Assert.assertEquals(
			"UNIT_TEST", environment.get(_ENVIRONMENT_KEY_USER));
		Assert.assertEquals("NEW_VALUE", environment.get("ENV_KEY"));

		_parentEnvironment.put(_ENVIRONMENT_KEY_USER, "UNIT_TEST");
		_parentEnvironment.put("ENV_KEY", "NEW_VALUE");

		Assert.assertEquals(_parentEnvironment, environment);
	}

	@Environment(append = false, variables = {"KEY1=VALUE1"})
	@JVMArgsLine(
		"-D" + _SYSTEM_PROPERTY_KEY_ENVIRONMENT + "=${" +
			_SYSTEM_PROPERTY_KEY_ENVIRONMENT + "}"
	)
	@Test
	public void testNewJVM8() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Map<String, String> environment = _getEnvironment();

		Assert.assertEquals("VALUE1", environment.get("KEY1"));
		Assert.assertNull(environment.get("ENV_KEY"));
		Assert.assertNull(environment.get(_ENVIRONMENT_KEY_USER));

		_parentEnvironment.put("KEY1", "VALUE1");

		Assert.assertNotEquals(_parentEnvironment, environment);
	}

	@Rule
	public final NewEnvTestRule newEnvTestRule = NewEnvTestRule.INSTANCE;

	protected void assertProcessId() {
		Assert.assertNotNull(_processId);

		Assert.assertEquals(_processId.intValue(), getProcessId());
	}

	protected int getProcessId() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		String name = runtimeMXBean.getName();

		int index = name.indexOf(CharPool.AT);

		if (index == -1) {
			throw new RuntimeException("Unable to parse process name " + name);
		}

		int pid = GetterUtil.getInteger(name.substring(0, index));

		if (pid == 0) {
			throw new RuntimeException("Unable to parse process name " + name);
		}

		return pid;
	}

	private static Map<String, String> _fromString(String s) {
		Map<String, String> map = new HashMap<>();

		for (String entry : StringUtil.split(s, _SEPARATOR_VARIABLE)) {
			String[] parts = StringUtil.split(entry, _SEPARATOR_KEY_VALUE);

			if (parts.length == 1) {
				map.put(parts[0], null);
			}
			else {
				map.put(parts[0], parts[1]);
			}
		}

		return map;
	}

	private static Map<String, String> _getEnvironment() {
		Map<String, String> environment = new HashMap<>(System.getenv());

		environment.remove("MODULES_BASE_DIR_NAMES_WITH_CHANGES");
		environment.remove("PROJECT_NAMES");
		environment.remove("SUBREPOSITORY_PACKAGE_NAMES");
		environment.remove("TERMCAP");

		return environment;
	}

	private static String _toString(Map<String, String> map) {
		StringBundler sb = new StringBundler();

		for (Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey());
			sb.append(_SEPARATOR_KEY_VALUE);
			sb.append(entry.getValue());
			sb.append(_SEPARATOR_VARIABLE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static final String _ENVIRONMENT_KEY_USER = "USER";

	private static final String _SEPARATOR_KEY_VALUE = "_SEPARATOR_KEY_VALUE_";

	private static final String _SEPARATOR_VARIABLE = "_SEPARATOR_VARIABLE_";

	private static final String _SYSTEM_PROPERTY_KEY_ENVIRONMENT =
		"KEY_ENVIRONMENT";

	private final AtomicInteger _counter = new AtomicInteger();
	private Map<String, String> _parentEnvironment;
	private Integer _processId;

}