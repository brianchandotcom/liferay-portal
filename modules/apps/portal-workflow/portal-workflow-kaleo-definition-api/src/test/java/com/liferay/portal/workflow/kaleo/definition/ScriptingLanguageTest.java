/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					"drl", "groovy", "java", "function#foo", "function#foo-bar"
				}) {

			ScriptLanguage scriptLanguage = ScriptLanguage.parse(value);

			Assert.assertEquals(value, scriptLanguage.getValue());
		}

		for (String value :
				new String[] {
					"beanshell", "beanshellV", "javascript", "python", "ruby",
					"something", "function#-foo", "function#Foo-bar",
					"function#foo-bar-"
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