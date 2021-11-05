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
import com.liferay.portal.kernel.test.util.UserTestUtil;
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
public class SXPBlueprintLimitSearchToMySitesTest
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

		_groups.add(addGroup("SiteA"));
		_groups.add(addGroup("SiteB"));

		setUpSXPBlueprint(getClass());
	}

	@Test
	public void testSearchWithLimitSearchInOneGroup() throws Exception {
		Group groupA = _groups.get(0);
		Group groupB = _groups.get(1);

		user = UserTestUtil.addUser(groupA.getGroupId());

		serviceContext.setUserId(user.getUserId());

		addJournalArticle(groupA.getGroupId(), "cola coca", "");
		addJournalArticle(groupB.getGroupId(), "cola pepsi", "");

		updateSXPBlueprint(getConfigurationJSONString(getClass()));

		assertSearchIgnoreRelevance("[cola coca]", "cola");
	}

	@Test
	public void testSearchWithLimitSearchInTwoGroups() throws Exception {
		Group groupA = _groups.get(0);
		Group groupB = _groups.get(1);

		user = UserTestUtil.addUser(groupA.getGroupId(), groupB.getGroupId());

		serviceContext.setUserId(user.getUserId());

		addJournalArticle(groupA.getGroupId(), "cola coca", "");
		addJournalArticle(groupB.getGroupId(), "cola pepsi", "");

		updateSXPBlueprint(getConfigurationJSONString(getClass()));

		assertSearchIgnoreRelevance("[cola coca, cola pepsi]", "cola");
	}

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

}