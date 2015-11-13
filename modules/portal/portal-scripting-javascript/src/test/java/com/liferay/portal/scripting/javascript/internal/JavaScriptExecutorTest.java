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

package com.liferay.portal.scripting.javascript.internal;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.scripting.ScriptingExecutorTestCase;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Bruno Basto
 */
@RunWith(PowerMockRunner.class)
public class JavaScriptExecutorTest extends ScriptingExecutorTestCase {

	@Override
	public String getScriptExtension() {
		return ".js";
	}

	@Override
	public ScriptingExecutor getScriptingExecutor() {
		JavaScriptExecutor javaScriptExecutor = new JavaScriptExecutor();

		PortalCache portalCache = Mockito.mock(PortalCache.class);

		Mockito.when(
			portalCache.get(Mockito.any(Serializable.class))
		).thenReturn(
			null
		);

		SingleVMPool singleVMPool = Mockito.mock(SingleVMPool.class);

		Mockito.when(
			singleVMPool.getPortalCache(Mockito.anyString())
		).thenReturn(
			portalCache
		);

		javaScriptExecutor.setSingleVMPool(singleVMPool);

		return javaScriptExecutor;
	}

	@Test
	public void testReturnValue() throws Exception {
		Map<String, Object> inputObjects = Collections.emptyMap();

		Set<String> outputNames = new HashSet<>();

		outputNames.add("returnValue");

		Map<String, Object> returnValue = execute(
			inputObjects, outputNames, "return-value");

		Assert.assertEquals("returnValue", returnValue.get("returnValue"));
	}

	@Test(expected = ScriptingException.class)
	public void testRuntimeError() throws Exception {
		Map<String, Object> inputObjects = Collections.emptyMap();
		Set<String> outputNames = Collections.emptySet();

		execute(inputObjects, outputNames, "runtime-error");
	}

	@Test(expected = ScriptingException.class)
	public void testSyntaxError() throws Exception {
		Map<String, Object> inputObjects = Collections.emptyMap();
		Set<String> outputNames = Collections.emptySet();

		execute(inputObjects, outputNames, "syntax-error");
	}

}