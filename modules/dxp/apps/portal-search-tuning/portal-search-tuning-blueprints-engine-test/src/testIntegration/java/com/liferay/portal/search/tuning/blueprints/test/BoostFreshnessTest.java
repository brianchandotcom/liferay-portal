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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class BoostFreshnessTest extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchBoostFreshness() throws Exception {
		addJournalArticle("coca cola", "cola cola");

		TimeUnit.SECONDS.sleep(5);

		addJournalArticle("pepsi cola", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString((JSONObject[])null), "");

		assertSearch(blueprint, null, "[coca cola, pepsi cola]", "cola", null);

		assertSearch(
			blueprint,
			getConfigurationString(
				_getFunctionScoreElementJSONObject(1000, 0.5, "0s", "2s")),
			"[pepsi cola, coca cola]", "cola",
			_getSelectedElementString(0.5, 0, 2));
	}

	private JSONObject _getFunctionScoreElementJSONObject(
		int boost, double decay, String offset, String scale) {

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
							"function_score",
							JSONUtil.put(
								"boost", boost
							).put(
								"gauss",
								JSONUtil.put(
									"modified",
									JSONUtil.put(
										"decay", decay
									).put(
										"offset", offset
									).put(
										"origin",
										"${time.current_date|dateFormat=" +
											"yyyyMMddHHmmss}"
									).put(
										"scale", scale
									))
							)))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions", createJSONArray()
		).put(
			"description",
			JSONUtil.put(
				"en_US", "Give a gaussian boost to contents modified recently")
		).put(
			"enabled", true
		).put(
			"icon", "thumbs-up"
		).put(
			"title", JSONUtil.put("en_US", "Boost Freshness")
		);
	}

	private String _getSelectedElementString(
			double decay, int offset, int scale)
		throws Exception {

		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/boost-freshness-test.json");

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
					"uiConfigurationValues",
					JSONUtil.put(
						"decay", decay
					).put(
						"offset", offset
					).put(
						"scale", scale
					)
				))
		).toString();
	}

}