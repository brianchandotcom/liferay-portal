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

package com.liferay.portal.kernel.servlet.taglib.aui;

import com.liferay.portal.kernel.io.DummyWriter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ScriptDataTest {

	@Test
	public void testGenerateVariableStripsReplacesFirstCharacter()
		throws Exception {

		ScriptData scriptData = new ScriptData();

		scriptData.append(
			"portletId", "content", "_Var,1Var,*Var,/Var",
			ScriptData.ModulesType.ES6);

		TestScriptDataWriter testScriptDataWriter = new TestScriptDataWriter();

		scriptData.writeTo(null, testScriptDataWriter);

		testScriptDataWriter.assertStrings("_VAR", "_VAR1", "_VAR2", "_VAR3");
	}

	@Test
	public void testGenerateVariableStripsSpecialCharacters() throws Exception {
		ScriptData scriptData = new ScriptData();

		scriptData.append(
			"portletId", "content", "Var,V ar,Va*r,Var/",
			ScriptData.ModulesType.ES6);

		TestScriptDataWriter testScriptDataWriter = new TestScriptDataWriter();

		scriptData.writeTo(null, testScriptDataWriter);

		testScriptDataWriter.assertStrings("VAR", "VAR1", "VAR2", "VAR3");
	}

	private static class TestScriptDataWriter extends DummyWriter {

		public void assertStrings(String... expectedStrings) {
			for (String expected : expectedStrings) {
				Assert.assertTrue(
					expected + " was not found.",
					_writtenStrings.contains(expected));
			}
		}

		@Override
		public void write(String s) {
			_writtenStrings.add(s);
		}

		private final List<String> _writtenStrings = new ArrayList<>();

	}

}