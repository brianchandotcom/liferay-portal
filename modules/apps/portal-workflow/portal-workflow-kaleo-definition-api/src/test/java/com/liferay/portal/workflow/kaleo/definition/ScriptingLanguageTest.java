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

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException.InvalidScriptLanguage;

/**
 * @author Raymond Augé
 */
public class ScriptingLanguageTest {

	public static final String[] LANGUAGES = {
		"beanshell", "drl", "groovy", "java", "javascript", "python", "ruby",
		"function#foo", "function#foo-bar"
	};

	public static final String[] NOT_LANGUAGES = {
		"beanshellV", "something", "function#-foo", "function#foo-bar-",
		"function#Foo-bar"
	};

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testParseScriptingLanguages() throws Exception {
		for (String language : LANGUAGES) {
			ScriptLanguage scriptLanguage = ScriptLanguage.parse(language);

			Assert.assertEquals(language, scriptLanguage.getValue());
		}

		for (String language : NOT_LANGUAGES) {
			try {
				ScriptLanguage scriptLanguage = ScriptLanguage.parse(language);

				Assert.fail(language);
			}
			catch (InvalidScriptLanguage invalidScriptLanguage) {
			}
		}
	}

}