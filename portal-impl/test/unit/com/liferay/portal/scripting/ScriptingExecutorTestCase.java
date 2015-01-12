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

package com.liferay.portal.scripting;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Miguel Pastor
 */
@PrepareForTest(SingleVMPoolUtil.class)
public abstract class ScriptingExecutorTestCase extends PowerMockito {

	public abstract String getScriptExtension();

	public abstract ScriptingExecutor getScriptingExecutor();

	@Before
	public void setUp() {
		mockStatic(SingleVMPoolUtil.class);

		when(
			SingleVMPoolUtil.getCache(Mockito.anyString())
		).thenReturn(
			_portalCache
		);

		_scriptingExecutor = getScriptingExecutor();
	}

	@After
	public void tearDown() {
		verifyStatic();
	}

	@Test
	public void testBindingInputVariables() throws ScriptingException {
		Map<String, Object> inputObjects = new HashMap<>();

		inputObjects.put("variable", "string");

		Set<String> outputNames = Collections.emptySet();

		execute(inputObjects, outputNames, "binding-input");
	}

	@Test
	public void testSimpleScript() throws ScriptingException {
		Map<String, Object> inputObjects = Collections.emptyMap();
		Set<String> outputNames = Collections.emptySet();

		execute(inputObjects, outputNames, "simple");
	}

	protected Map<String, Object> execute(
			Map<String, Object> inputObjects, Set<String> outputNames,
			String fileName)
		throws ScriptingException {

		String script = getScript(fileName + getScriptExtension());

		return _scriptingExecutor.eval(null, inputObjects, outputNames, script);
	}

	protected String getScript(String name) {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + name);

		String script = null;

		try {
			script = StringUtil.read(inputStream);
		}
		catch (IOException ioe) {
			Assert.fail("Unable to read " + name);
		}

		return script;
	}

	@Mock
	private PortalCache<Serializable, Object> _portalCache;

	private ScriptingExecutor _scriptingExecutor;

}