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

package com.liferay.portal.scripting.groovy.internal;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class GroovyScriptingExecutorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBindingInputVariables() throws Exception {
		_execute(
			Collections.singletonMap("variable", "string"),
			Collections.emptySet(), "binding-input");
	}

	@Test
	public void testGroovyRuntimeExceptionSerialization() throws Exception {
		try {
			_execute(
				Collections.emptyMap(), Collections.emptySet(),
				"missing-method-exception");

			Assert.fail("Should throw GroovyRuntimeException");
		}
		catch (ScriptingException scriptingException) {
			Assert.assertEquals(
				"No signature of method: static Test.missingMethod() is " +
					"applicable for argument types: () values: []",
				scriptingException.getMessage());

			_checkExceptionSerialization(scriptingException);
		}
	}

	@Test
	public void testRuntimeError() throws Exception {
		try {
			_execute(
				Collections.emptyMap(), Collections.emptySet(),
				"runtime-error");

			Assert.fail("Should throw RuntimeException");
		}
		catch (RuntimeException runtimeException) {
		}
	}

	@Test
	public void testSimpleScript() throws Exception {
		_execute(Collections.emptyMap(), Collections.emptySet(), "simple");
	}

	@Test
	public void testSyntaxError() throws Exception {
		try {
			_execute(
				Collections.emptyMap(), Collections.emptySet(), "syntax-error");

			Assert.fail("Should throw UnsupportedOperationException");
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
		}
	}

	private void _checkExceptionSerialization(Exception exception)
		throws Exception {

		Serializer serializer = new Serializer();

		serializer.writeObject(exception);

		Deserializer deserializer = new Deserializer(serializer.toByteBuffer());

		deserializer.readObject();
	}

	private Map<String, Object> _execute(
			Map<String, Object> inputObjects, Set<String> outputNames,
			String fileName)
		throws Exception {

		GroovyScriptingExecutor groovyScriptingExecutor =
			new GroovyScriptingExecutor();

		return groovyScriptingExecutor.eval(
			null, inputObjects, outputNames,
			StringUtil.read(
				getClass().getResourceAsStream(
					"dependencies/" + fileName + ".groovy")));
	}

}