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

import com.liferay.portal.kernel.test.rule.NewEnv.Environment;
import com.liferay.portal.kernel.test.rule.NewEnv.JVMArgsLine;
import com.liferay.portal.kernel.util.CharPool;
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
		System.setProperty(_ENVIRONMENT_KEY, _toString(_getEnv()));
	}

	@Before
	public void setUp() {
		Assert.assertEquals(0, _counter.getAndIncrement());
		Assert.assertNull(_processId);

		_processId = getProcessId();

		_parentEnv = _fromString(System.getProperty(_ENVIRONMENT_KEY));
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
	@JVMArgsLine("-D" + _ENVIRONMENT_KEY + "=${" + _ENVIRONMENT_KEY + "}")
	@Test
	public void testNewJVM6() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Map<String, String> env = _getEnv();

		Assert.assertEquals("ENV_VALUE", env.get("ENV_KEY"));

		_parentEnv.put("ENV_KEY", "ENV_VALUE");

		Assert.assertEquals(_parentEnv, env);
	}

	@Environment(variables = {"USER=UNIT_TEST", "ENV_KEY=NEW_VALUE"})
	@JVMArgsLine("-D" + _ENVIRONMENT_KEY + "=${" + _ENVIRONMENT_KEY + "}")
	@Test
	public void testNewJVM7() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Map<String, String> env = _getEnv();

		Assert.assertEquals("UNIT_TEST", env.get(_USER));
		Assert.assertEquals("NEW_VALUE", env.get("ENV_KEY"));

		_parentEnv.put(_USER, "UNIT_TEST");
		_parentEnv.put("ENV_KEY", "NEW_VALUE");

		Assert.assertEquals(_parentEnv, env);
	}

	@Environment(append = false, variables = {"KEY1=VALUE1"})
	@JVMArgsLine("-D" + _ENVIRONMENT_KEY + "=${" + _ENVIRONMENT_KEY + "}")
	@Test
	public void testNewJVM8() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();

		Map<String, String> env = _getEnv();

		Assert.assertEquals("VALUE1", env.get("KEY1"));
		Assert.assertNull(env.get("ENV_KEY"));
		Assert.assertNull(env.get(_USER));

		_parentEnv.put("KEY1", "VALUE1");

		Assert.assertNotEquals(_parentEnv, env);
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

		for (String entry : StringUtil.split(s, _VARIABLE_SEPARATOR)) {
			String[] parts = StringUtil.split(entry, _KEY_VALUE_SEPARATOR);

			if (parts.length == 1) {
				map.put(parts[0], null);
			}
			else {
				map.put(parts[0], parts[1]);
			}
		}

		return map;
	}

	private static Map<String, String> _getEnv() {
		Map<String, String> env = new HashMap<>(System.getenv());

		env.remove("TERMCAP");

		return env;
	}

	private static String _toString(Map<String, String> map) {
		StringBundler sb = new StringBundler();

		for (Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey());
			sb.append(_KEY_VALUE_SEPARATOR);
			sb.append(entry.getValue());
			sb.append(_VARIABLE_SEPARATOR);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static final String _ENVIRONMENT_KEY = "ENVIRONMENT_KEY";

	private static final String _KEY_VALUE_SEPARATOR = "_KEY_VALUE_SEPARATOR_";

	private static final String _USER = "USER";

	private static final String _VARIABLE_SEPARATOR = "_VARIABLE_SEPARATOR_";

	private final AtomicInteger _counter = new AtomicInteger();
	private Map<String, String> _parentEnv;
	private Integer _processId;

}