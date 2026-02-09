/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.script.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptField;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.test.rule.SearchTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class ScriptsInstantiationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testBuilder() {
		Script script = Scripts.INSTANCE.builder(
		).idOrCode(
			"Math.min(1, 1)"
		).language(
			"painless"
		).build();

		Assert.assertNotNull(script);
	}

	@Test
	public void testFieldBuilder() {
		ScriptField scriptField = Scripts.INSTANCE.fieldBuilder(
		).field(
			"field"
		).script(
			Scripts.INSTANCE.script("Math.min(1, 1)")
		).build();

		Assert.assertNotNull(scriptField);
	}

	@Test
	public void testInline() {
		Script script = Scripts.INSTANCE.inline("painless", "Math.min(1, 1)");

		Assert.assertNotNull(script);
	}

	@Test
	public void testScript() {
		Script script = Scripts.INSTANCE.script("Math.min(1, 1)");

		Assert.assertNotNull(script);
	}

	@Test
	public void testScriptField() {
		ScriptField scriptField = Scripts.INSTANCE.scriptField(
			"field", Scripts.INSTANCE.script("Math.min(1, 1)"));

		Assert.assertNotNull(scriptField);
	}

	@Test
	public void testStored() {
		Script script = Scripts.INSTANCE.stored("script_id");

		Assert.assertNotNull(script);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

}