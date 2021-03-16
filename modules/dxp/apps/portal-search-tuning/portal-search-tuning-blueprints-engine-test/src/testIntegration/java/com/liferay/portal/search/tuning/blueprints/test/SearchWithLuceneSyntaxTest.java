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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
public class SearchWithLuceneSyntaxTest extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchWithLuceneSyntax() throws Exception {
		addJournalArticle("Coca Cola", "");
		addJournalArticle("Pepsi Cola", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString(null), "", 1);

		assertSearch(
			blueprint, null, "[coca cola, pepsi cola]", "cola +coca", null);

		String configurationString = getConfigurationString(
			_getQueryElementJSONObject(1, 2, "and", 2));

		String selectedElementString = _getSelectedElementString(
			1, 2, "and", 2);

		assertSearch(
			blueprint, configurationString, "[coca cola]", "cola +coca",
			selectedElementString);

		assertSearch(
			blueprint, configurationString, "[pepsi cola]", "cola -coca",
			selectedElementString);
	}

	private JSONObject _getQueryElementJSONObject(
		int boost, int contentBoost, String operator, int titleBoost) {

		JSONArray fieldsJSONArray = createJSONArray();

		return JSONUtil.put(
			"category", "match"
		).put(
			"clauses",
			createJSONArray().put(
				JSONUtil.put(
					"context", "query"
				).put(
					"occur", "must"
				).put(
					"query",
					JSONUtil.put(
						"query",
						JSONUtil.put(
							"simple_query_string",
							JSONUtil.put(
								"boost", boost
							).put(
								"default_operator", operator
							).put(
								"fields",
								fieldsJSONArray.put(
									"localized_title${context.language_id}^" +
										titleBoost
								).put(
									"content${context.language_id}^" +
										contentBoost
								)
							).put(
								"query", "${keywords}"
							)))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions", createJSONArray()
		).put(
			"description",
			JSONUtil.put("en_US", "Enable searching using the Lucene syntax")
		).put(
			"enabled", true
		).put(
			"icon", "picture"
		).put(
			"title", JSONUtil.put("en_US", "Search with the Lucene Syntax")
		);
	}

	private String _getSelectedElementString(
			int boost, int contentBoost, String operator, int titleBoost)
		throws Exception {

		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/search-with-lucene-syntax-test.json");

		JSONArray uiConfigurationValuesFieldsJSONArray = createJSONArray();

		return JSONUtil.put(
			"query_configuration",
			createJSONArray().put(
				JSONUtil.put(
					"elementOutput",
					JSONUtil.put(
						"category", "match"
					).put(
						"clauses",
						createJSONArray().put(
							JSONUtil.put(
								"context", "query"
							).put(
								"occur", "must"
							).put(
								"query",
								JSONUtil.put(
									"query",
									JSONUtil.put(
										"simple_query_string",
										JSONUtil.put(
											"boost", boost
										).put(
											"default_operator", operator
										).put(
											"fields",
											_getSimpleQueryStringFieldsJSONArray(
												contentBoost, titleBoost)
										).put(
											"query", "${keywords}"
										))
								).put(
									"type", "wrapper"
								)
							))
					).put(
						"conditions", createJSONArray()
					).put(
						"description",
						JSONUtil.put(
							"en_US", "Enable searching using the Lucene syntax")
					).put(
						"enabled", true
					).put(
						"icon", "picture"
					).put(
						"title",
						JSONUtil.put("en_US", "Search with the Lucene Syntax")
					)
				).put(
					"elementTemplateJSON",
					elementTemplateJSONObject.get("elementTemplateJSON")
				).put(
					"uiConfigurationJSON",
					elementTemplateJSONObject.get("uiConfigurationJSON")
				).put(
					"uiConfigurationValues",
					JSONUtil.put(
						"boost", boost
					).put(
						"fields",
						uiConfigurationValuesFieldsJSONArray.put(
							JSONUtil.put(
								"boost", titleBoost
							).put(
								"field", "localized_title"
							).put(
								"locale", "${context.language_id}"
							)
						).put(
							JSONUtil.put(
								"boost", contentBoost
							).put(
								"field", "content"
							).put(
								"locale", "${context.language_id}"
							)
						)
					).put(
						"operator", operator
					)
				))
		).toString();
	}

	private JSONArray _getSimpleQueryStringFieldsJSONArray(
		int contentBoost, int titleBoost) {

		JSONArray jsonArray = createJSONArray();

		return jsonArray.put(
			"localized_title${context.language_id}^" + titleBoost
		).put(
			"content${context.language_id}^" + contentBoost
		);
	}

}