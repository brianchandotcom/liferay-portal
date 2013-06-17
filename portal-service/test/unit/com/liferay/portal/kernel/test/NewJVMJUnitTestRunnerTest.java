/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewJVMJUnitTestRunner.class)
public class NewJVMJUnitTestRunnerTest {

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
	}

	@Test
	public void testNewJVM2() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertProcessId();
	}

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

	private AtomicInteger _counter = new AtomicInteger();
	private Integer _processId;

}