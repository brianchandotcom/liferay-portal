/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPBlueprintBoostPhraseMatchTest
	extends BaseSXPBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		addJournalArticle(
			group.getGroupId(), "This coca looks like a kind of drink",
			"coca coca");
		addJournalArticle(
			group.getGroupId(), "This looks like a kind of coca drink", "");

		setUpSXPBlueprint(getClass());
	}

	@Test
	public void testSearchWithMultiAllKeywordsMatch() throws Exception {
		updateSXPBlueprint(
			getConfigurationJSONString(getClass(), testName.getMethodName()));

		assertSearch(
			"[this looks like a kind of coca drink, this coca looks like a " +
				"kind of drink]",
			"coca drink");
	}

	@Test
	public void testSearchWithMultiMatch() throws Exception {
		updateSXPBlueprint(
			getConfigurationJSONString(getClass(), testName.getMethodName()));

		assertSearchIgnoreRelevance(
			"[this coca looks like a kind of drink, this looks like a kind " +
				"of coca drink]",
			"coca drink");
	}

}