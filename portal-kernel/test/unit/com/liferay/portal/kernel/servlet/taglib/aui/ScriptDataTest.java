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

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		scriptData.writeTo(null, unsyncStringWriter);

		assertVariables(unsyncStringWriter, "_VAR", "_VAR1", "_VAR2", "_VAR3");
	}

	@Test
	public void testGenerateVariableStripsSpecialCharacters() throws Exception {
		ScriptData scriptData = new ScriptData();

		scriptData.append(
			"portletId", "content", "Var,V ar,Va*r,Var/",
			ScriptData.ModulesType.ES6);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		scriptData.writeTo(null, unsyncStringWriter);

		assertVariables(unsyncStringWriter, "VAR", "VAR1", "VAR2", "VAR3");
	}

	protected void assertVariables(
		UnsyncStringWriter unsyncStringWriter, String... variables) {

		StringBundler sb = unsyncStringWriter.getStringBundler();

		Set<String> strings = new HashSet<>(
			Arrays.asList(Arrays.copyOf(sb.getStrings(), sb.index())));

		for (String variable : variables) {
			Assert.assertTrue(
				variable + " was not found.", strings.contains(variable));
		}
	}

}