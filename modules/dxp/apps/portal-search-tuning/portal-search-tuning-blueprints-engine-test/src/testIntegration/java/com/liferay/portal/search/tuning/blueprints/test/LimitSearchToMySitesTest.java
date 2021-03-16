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
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class LimitSearchToMySitesTest extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchWithLimitSearchToMyGroups() throws Exception {
		Group groupA = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, "SiteA", serviceContext);
		Group groupB = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, "SiteB", serviceContext);

		user = UserTestUtil.addUser(groupA.getGroupId(), groupB.getGroupId());

		serviceContext.setUserId(user.getUserId());

		addJournalArticle(groupA.getGroupId(), "cola coca");
		addJournalArticle(groupB.getGroupId(), "cola pepsi");

		String configurationString = getConfigurationString(
			_getQueryElementJSONObject());

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""), configurationString,
			_getSelectedElementString(), 1);

		assertSearchIgnoreRelevance(
			blueprint, null, "[cola coca, cola pepsi]", "cola", null);

		user = UserTestUtil.addUser(groupA.getGroupId());

		assertSearchIgnoreRelevance(
			blueprint, null, "[cola coca]", "cola", null);
	}

	private JSONObject _getQueryElementJSONObject() {
		return JSONUtil.put(
			"category", "filter"
		).put(
			"clauses",
			createJSONArray().put(
				JSONUtil.put(
					"context", "query"
				).put(
					"occur", "filter"
				).put(
					"query",
					JSONUtil.put(
						"query",
						JSONUtil.put(
							"terms",
							JSONUtil.put(
								"scopeGroupId", "${user.user_group_ids}")))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions", createJSONArray()
		).put(
			"description",
			JSONUtil.put(
				"en_US", "Limit search scope to the sites user is member of")
		).put(
			"enabled", true
		).put(
			"icon", "thumbs-up"
		).put(
			"title", JSONUtil.put("en_US", "Limit Search to My Sites")
		);
	}

	private String _getSelectedElementString() throws Exception {
		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/limit-search-to-my-sites-test.json");

		return JSONUtil.put(
			"query_configuration",
			createJSONArray().put(
				JSONUtil.put(
					"elementOutput",
					JSONUtil.put(
						"category", "filter"
					).put(
						"clauses",
						createJSONArray().put(
							JSONUtil.put(
								"context", "query"
							).put(
								"occur", "filter"
							).put(
								"query",
								JSONUtil.put(
									"query",
									JSONUtil.put(
										"terms",
										JSONUtil.put(
											"scopeGroupId",
											"${user.user_group_ids}")))
							).put(
								"type", "wrapper"
							))
					).put(
						"conditions",
						createJSONArray().put(
							JSONUtil.put("configuration", createJSONArray()))
					).put(
						"description",
						JSONUtil.put(
							"en_US",
							"Limit search scope to the sites user is member of")
					).put(
						"enabled", true
					).put(
						"icon", "filter"
					).put(
						"title",
						JSONUtil.put("en_US", "Limit Search to My Sites")
					)
				).put(
					"elementTemplateJSON",
					elementTemplateJSONObject.get("elementTemplateJSON")
				).put(
					"uiConfigurationJSON",
					elementTemplateJSONObject.get("uiConfigurationJSON")
				).put(
					"uiConfigurationValues", JSONUtil.put(null, null)
				))
		).toString();
	}

}