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

package com.liferay.portal.search.query.function.score.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.query.function.score.ScoreFunctions;
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
public class ScoreFunctionsInstantiationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExponentialDecay1() {
		Assert.assertNotNull(
			_scoreFunctions.exponentialDecay(
				"field", "origin", "scale", "offset"));
	}

	@Test
	public void testExponentialDecay2() {
		Assert.assertNotNull(
			_scoreFunctions.exponentialDecay(
				"field", "origin", "scale", "offset", 42.0));
	}

	@Test
	public void testField() {
		Assert.assertNotNull(_scoreFunctions.fieldValueFactor("field"));
	}

	@Test
	public void testGaussianDecay1() {
		Assert.assertNotNull(
			_scoreFunctions.gaussianDecay(
				"field", "origin", "scale", "offset"));
	}

	@Test
	public void testGaussianDecay2() {
		Assert.assertNotNull(
			_scoreFunctions.gaussianDecay(
				"field", "origin", "scale", "offset", 42.0));
	}

	@Test
	public void testLinearDecay1() {
		Assert.assertNotNull(
			_scoreFunctions.linearDecay("field", "origin", "scale", "offset"));
	}

	@Test
	public void testLinearDecay2() {
		Assert.assertNotNull(
			_scoreFunctions.linearDecay(
				"field", "origin", "scale", "offset", 42.0));
	}

	@Test
	public void testRandom() {
		Assert.assertNotNull(_scoreFunctions.random());
	}

	@Test
	public void testScript() {
		Assert.assertNotNull(
			_scoreFunctions.script(_scripts.script("script_id")));
	}

	@Test
	public void testWeight() {
		Assert.assertNotNull(_scoreFunctions.weight(42));
	}

	@Inject
	private static ScoreFunctions _scoreFunctions;

	@Inject
	private static Scripts _scripts;

}