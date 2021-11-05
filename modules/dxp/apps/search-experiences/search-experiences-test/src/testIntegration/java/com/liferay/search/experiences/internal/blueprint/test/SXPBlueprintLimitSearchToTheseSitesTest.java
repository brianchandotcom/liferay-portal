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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPBlueprintLimitSearchToTheseSitesTest
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

		Group groupA = addGroup("SiteA");
		Group groupB = addGroup("SiteB");
		Group groupC = addGroup("SiteC");

		addJournalArticle(groupA.getGroupId(), "cola coca", "");
		addJournalArticle(groupB.getGroupId(), "cola pepsi", "");
		addJournalArticle(groupC.getGroupId(), "cola sprite", "");

		_groups.add(groupA);
		_groups.add(groupB);
		_groups.add(groupC);

		setUpSXPBlueprint(getClass());
	}

	@Test
	public void testSearch() throws Exception {
		updateSXPBlueprint(getEmptyConfigurationJSONString());

		assertSearchIgnoreRelevance(
			"[cola coca, cola pepsi, cola sprite]", "cola");
	}

	@Test
	public void testSearchWithLimitSearchToTheseSites() throws Exception {
		updateSXPBlueprint(_getConfigurationJSONString());

		assertSearchIgnoreRelevance("[cola coca, cola pepsi]", "cola");
	}

	private String _getConfigurationJSONString() {
		Group groupA = _groups.get(0);
		Group groupB = _groups.get(1);

		String configurationJSONS = StringUtil.replace(
			getConfigurationJSONString(getClass()),
			"${configuration.scope_group_id_1}",
			String.valueOf(groupA.getGroupId()));

		return StringUtil.replace(
			configurationJSONS, "${configuration.scope_group_id_2}",
			String.valueOf(groupB.getGroupId()));
	}

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

}