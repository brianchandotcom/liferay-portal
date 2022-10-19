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

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException.InvalidScriptLanguage;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class ScriptingLanguageTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testParseScriptingLanguages() throws Exception {
		for (String value :
				new String[] {
					"beanshell", "drl", "groovy", "java", "javascript",
					"python", "ruby", "function#foo", "function#foo-bar"
				}) {

			ScriptLanguage scriptLanguage = ScriptLanguage.parse(value);

			Assert.assertEquals(value, scriptLanguage.getValue());
		}

		for (String value :
				new String[] {
					"beanshellV", "something", "function#-foo",
					"function#Foo-bar", "function#foo-bar-"
				}) {

			try {
				ScriptLanguage.parse(value);

				Assert.fail(value);
			}
			catch (InvalidScriptLanguage invalidScriptLanguage) {
				Assert.assertNotNull(invalidScriptLanguage);
			}
		}
	}

}