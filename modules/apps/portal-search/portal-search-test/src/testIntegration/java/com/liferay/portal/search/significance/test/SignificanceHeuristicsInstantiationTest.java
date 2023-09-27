/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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