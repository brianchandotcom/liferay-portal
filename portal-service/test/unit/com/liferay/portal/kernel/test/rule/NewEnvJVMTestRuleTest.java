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

import com.liferay.portal.kernel.test.rule.NewEnv.JVMArgsLine;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@JVMArgsLine("-Dkey1=default1 -Dkey2=default2")
@NewEnv(type = NewEnv.Type.JVM)
public class NewEnvJVMTestRuleTest {

	@Before
	public void setUp() {
		Assert.assertEquals(0, _counter.getAndIncrement());
		Assert.assertNull(_processId);

		_processId = getProcessId();
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

	private final AtomicInteger _counter = new AtomicInteger();
	private Integer _processId;

}