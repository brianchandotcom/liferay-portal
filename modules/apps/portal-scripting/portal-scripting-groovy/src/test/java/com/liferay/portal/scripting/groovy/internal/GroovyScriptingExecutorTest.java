/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void testBindingInput() throws Exception {
		_execute(
			Collections.singletonMap("variable", "string"),
			Collections.emptySet(), "binding-input");
	}

	@Test
	public void testMissingMethod() throws Exception {
		try {
			_execute(
				Collections.emptyMap(), Collections.emptySet(),
				"missing-method");

			Assert.fail();
		}
		catch (ScriptingException scriptingException) {
			Assert.assertEquals(
				"No signature of method: static Test.missingMethod() is " +
					"applicable for argument types: () values: []",
				scriptingException.getMessage());

			_writeAndReadObject(scriptingException);
		}
	}

	@Test
	public void testRuntimeError() throws Exception {
		try {
			_execute(
				Collections.emptyMap(), Collections.emptySet(),
				"runtime-error");

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
		}
	}

	@Test
	public void testSimple() throws Exception {
		_execute(Collections.emptyMap(), Collections.emptySet(), "simple");
	}

	@Test
	public void testSyntaxError() throws Exception {
		try {
			_execute(
				Collections.emptyMap(), Collections.emptySet(), "syntax-error");

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
		}
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

	private void _writeAndReadObject(Exception exception) throws Exception {
		Serializer serializer = new Serializer();

		serializer.writeObject(exception);

		Deserializer deserializer = new Deserializer(serializer.toByteBuffer());

		deserializer.readObject();
	}

}