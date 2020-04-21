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

package com.liferay.portal.search.significance.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.significance.SignificanceHeuristics;
import com.liferay.portal.search.test.util.SearchTestRule;
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
public class SignificanceHeuristicsInstantiationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testFactories() {
		Assert.assertNotNull(_significanceHeuristics.chiSquare(false, false));
		Assert.assertNotNull(_significanceHeuristics.gnd(false));
		Assert.assertNotNull(_significanceHeuristics.jlhScore());
		Assert.assertNotNull(
			_significanceHeuristics.mutualInformation(false, false));
		Assert.assertNotNull(_significanceHeuristics.percentageScore());
		Assert.assertNotNull(
			_significanceHeuristics.script(_scripts.script("script_id")));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Inject
	private static Scripts _scripts;

	@Inject
	private static SignificanceHeuristics _significanceHeuristics;

}