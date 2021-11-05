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
import com.liferay.portal.kernel.util.StringUtil;
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
public class SXPBlueprintHideFromScratchTest extends BaseSXPBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		addJournalArticle(group.getGroupId(), "Cafe Rio", "Los Angeles");
		addJournalArticle(group.getGroupId(), "Cloud Cafe", "Orange County");
		addJournalArticle(group.getGroupId(), "Denny's", "Los Angeles");
		addJournalArticle(group.getGroupId(), "Starbucks Cafe", "Los Angeles");

		setUpSXPBlueprint(getClass());
	}

	@Test
	public void testSearch() throws Exception {
		updateSXPBlueprint(getEmptyConfigurationJSONString());

		assertSearchIgnoreRelevance(
			"[cafe rio, cloud cafe, starbucks cafe]", "cafe");
	}

	@Test
	public void testSearchWithPasteESQueryMustNot() throws Exception {
		updateSXPBlueprint(
			_getConfigurationJSONString("los angeles", "must_not"));

		assertSearchIgnoreRelevance("[cloud cafe]", "cafe");

		updateSXPBlueprint(
			_getConfigurationJSONString("orange county", "must_not"));

		assertSearchIgnoreRelevance("[cafe rio, starbucks cafe]", "cafe");
	}

	private String _getConfigurationJSONString(String query, String occur) {
		String configurationJSONString = StringUtil.replace(
			getConfigurationJSONString(getClass()), "${configuration.query}",
			query);

		return StringUtil.replace(
			configurationJSONString, "${configuration.occur}", occur);
	}

}