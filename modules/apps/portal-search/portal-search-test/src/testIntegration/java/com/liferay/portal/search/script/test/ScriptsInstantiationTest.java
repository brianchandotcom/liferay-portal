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

package com.liferay.portal.search.script.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptField;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.test.rule.Inject;
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
		Script script = _scripts.builder(
		).idOrCode(
			"Math.min(1, 1)"
		).language(
			"painless"
		).build();

		Assert.assertNotNull(script);
	}

	@Test
	public void testFieldBuilder() {
		ScriptField scriptField = _scripts.fieldBuilder(
		).field(
			"field"
		).script(
			_scripts.script("Math.min(1, 1)")
		).build();

		Assert.assertNotNull(scriptField);
	}

	@Test
	public void testInline() {
		Script script = _scripts.inline("painless", "Math.min(1, 1)");

		Assert.assertNotNull(script);
	}

	@Test
	public void testScript() {
		Script script = _scripts.script("Math.min(1, 1)");

		Assert.assertNotNull(script);
	}

	@Test
	public void testScriptField() {
		ScriptField scriptField = _scripts.scriptField(
			"field", _scripts.script("Math.min(1, 1)"));

		Assert.assertNotNull(scriptField);
	}

	@Test
	public void testStored() {
		Script script = _scripts.stored("script_id");

		Assert.assertNotNull(script);
	}

	@Inject
	private static Scripts _scripts;

}