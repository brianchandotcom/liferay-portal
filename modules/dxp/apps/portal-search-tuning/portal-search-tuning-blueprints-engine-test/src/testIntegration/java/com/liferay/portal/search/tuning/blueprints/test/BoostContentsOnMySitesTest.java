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

package com.liferay.portal.search.tuning.blueprints.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class BoostContentsOnMySitesTest extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchWithBoost() throws Exception {
		Group groupA = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, "SiteA", serviceContext);
		Group groupB = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, "SiteB", serviceContext);

		_groups.add(groupA);
		_groups.add(groupB);

		addJournalArticle(groupA.getGroupId(), "coca cola", "cola cola");
		addJournalArticle(groupB.getGroupId(), "pepsi cola", "");

		user = UserTestUtil.addUser(groupA.getGroupId());

		serviceContext.setUserId(user.getUserId());

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString((JSONObject[])null), "");

		assertSearch(blueprint, null, "[coca cola, pepsi cola]", "cola", null);

		user = UserTestUtil.addUser(groupB.getGroupId());

		serviceContext.setUserId(user.getUserId());

		assertSearch(
			blueprint, getConfigurationString(_getQueryElementJSONObject(100)),
			"[pepsi cola, coca cola]", "cola", _getSelectedElementString(100));
	}

	private JSONObject _getQueryElementJSONObject(int boost) {
		return JSONUtil.put(
			"category", "boost"
		).put(
			"clauses",
			createJSONArray().put(
				JSONUtil.put(
					"context", "query"
				).put(
					"occur", "should"
				).put(
					"query",
					JSONUtil.put(
						"query",
						JSONUtil.put(
							"terms",
							JSONUtil.put(
								"boost", boost
							).put(
								"groupId", "${user.user_group_ids}"
							)))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions", createJSONArray()
		).put(
			"description",
			JSONUtil.put("en_US", "Boost contents on sites I'm a member of")
		).put(
			"enabled", true
		).put(
			"icon", "thumbs-up"
		).put(
			"title", JSONUtil.put("en_US", "Boost Contents on My Sites")
		);
	}

	private String _getSelectedElementString(int boost) throws Exception {
		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/boost-contents-on-my-sites-test.json");

		return JSONUtil.put(
			"query_configuration",
			createJSONArray().put(
				JSONUtil.put(
					"elementTemplateJSON",
					elementTemplateJSONObject.get("elementTemplateJSON")
				).put(
					"uiConfigurationJSON",
					elementTemplateJSONObject.get("uiConfigurationJSON")
				).put(
					"uiConfigurationValues", JSONUtil.put("boost", boost)
				))
		).toString();
	}

	@DeleteAfterTestRun
	private List<Group> _groups = new ArrayList<>();

}