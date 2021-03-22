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
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.framework.FrameworkConfigurationKeys;
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
public class MatchFromScratchTest extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchWithLimitSearchToMyGroups() throws Exception {
		addJournalArticle("Cafe Rio", "Los Angeles");
		addJournalArticle("Starbucks Cafe", "Los Angeles");
		addJournalArticle("Cloud Cafe", "Orange County");
		addJournalArticle("Denny's", "Los Angeles");

		String configurationString = getConfigurationString(
			_getMatchQueryElementJSONObject(200, "orange county"),
			_getMultiMatchQueryElementJSONObject(1, "or"));

		String selectedElementString = _getSelectedElementString(
			_getPasteESQueryJSONObject(200, "orange county"),
			_getTextMatchOverMultipleFieldJSONObject(1, 2, 1, "or"));

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""), configurationString,
			selectedElementString, 1);

		assertSearchIgnoreRelevance(
			blueprint, null, "[cloud cafe]", "cafe", null);

		configurationString = getConfigurationString(
			_getMatchQueryElementJSONObject(200, "los angeles"),
			_getMultiMatchQueryElementJSONObject(1, "or"));

		selectedElementString = _getSelectedElementString(
			_getPasteESQueryJSONObject(200, "los angeles"),
			_getTextMatchOverMultipleFieldJSONObject(1, 2, 1, "or"));

		assertSearchIgnoreRelevance(
			blueprint, configurationString, "[cafe rio, starbucks cafe]",
			"cafe", selectedElementString);
	}

	@Override
	protected JSONObject getFrameworkConfiguration() {
		JSONArray fieldsJSONArray = createJSONArray();

		return JSONUtil.put(
			FrameworkConfigurationKeys.APPLY_INDEXER_CLAUSES.getJsonKey(), false
		).put(
			"searchable_asset_types",
			fieldsJSONArray.put(
				"com.liferay.wiki.model.WikiPage"
			).put(
				"com.liferay.journal.model.JournalArticle"
			)
		);
	}

	private JSONObject _getMatchQueryElementJSONObject(
		int boost, String queryValue) {

		return JSONUtil.put(
			"category", "custom"
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
							"match",
							JSONUtil.put(
								"content_en_US",
								JSONUtil.put(
									"boost", boost
								).put(
									"query", queryValue
								))))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions", createJSONArray()
		).put(
			"description",
			JSONUtil.put(
				"en_US",
				"Paste any Elasticsearch query body in the element as is")
		).put(
			"enabled", true
		).put(
			"icon", "custom-field"
		).put(
			"title", JSONUtil.put("en_US", "Paste any Elasticsearch query")
		);
	}

	private JSONObject _getMultiMatchJSONObject(int boost, String operator) {
		JSONArray fieldsJSONArray = createJSONArray();

		return JSONUtil.put(
			"boost", boost
		).put(
			"fields",
			fieldsJSONArray.put(
				"localized_title${context.language_id}^2"
			).put(
				"content${context.language_id}^1"
			)
		).put(
			"fuzziness", "AUTO"
		).put(
			"operator", operator
		).put(
			"query", "${keywords}"
		).put(
			"type", "best_fields"
		);
	}

	private JSONObject _getMultiMatchQueryElementJSONObject(
		int boost, String operator) {

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
							"multi_match",
							_getMultiMatchJSONObject(boost, operator)))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions", createJSONArray()
		).put(
			"description",
			JSONUtil.put(
				"en_US", "Search for a text match over multiple text fields")
		).put(
			"enabled", true
		).put(
			"icon", "picture"
		).put(
			"title", JSONUtil.put("en_US", "Text Match Over Multiple Fields")
		);
	}

	private JSONObject _getPasteESQueryJSONObject(int boost, String queryValue)
		throws Exception {

		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/paste-an-elasticsearch-query.json");

		return JSONUtil.put(
			"elementOutput",
			JSONUtil.put(
				"category", "custom"
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
								"match",
								JSONUtil.put(
									"content_en_US",
									JSONUtil.put(
										"boost", boost
									).put(
										"query", queryValue
									))))
					).put(
						"type", "wrapper"
					))
			).put(
				"conditions", createJSONArray()
			).put(
				"description",
				JSONUtil.put(
					"en_US",
					"Paste any Elasticsearch query body in the element as is")
			).put(
				"enabled", true
			).put(
				"icon", "custom-field"
			).put(
				"title", JSONUtil.put("en_US", "Paste any Elasticsearch query")
			)
		).put(
			"elementTemplateJSON",
			elementTemplateJSONObject.get("elementTemplateJSON")
		).put(
			"uiConfigurationJSON",
			elementTemplateJSONObject.get("uiConfigurationJSON")
		).put(
			"uiConfigurationValues",
			_getPasteESQueryUIConfigValuesJSONObject("los angeles", boost)
		);
	}

	private JSONObject _getPasteESQueryUIConfigValuesJSONObject(
		String queryValue, int boost) {

		return JSONUtil.put(
			"occur", "must"
		).put(
			"query",
			JSONUtil.put(
				"match",
				JSONUtil.put(
					"content_en_US",
					JSONUtil.put(
						"boost", boost
					).put(
						"query", queryValue
					)))
		);
	}

	private String _getSelectedElementString(JSONObject... jsonObjects)
		throws Exception {

		JSONArray jsonArray = createJSONArray();

		for (JSONObject jsonObject : jsonObjects) {
			jsonArray.put(jsonObject);
		}

		return JSONUtil.put(
			"query_configuration", jsonArray
		).toString();
	}

	private JSONObject _getTextMatchOverMultipleFieldJSONObject(
			int boost, int contentBoost, int localizedTitleBoost,
			String operator)
		throws Exception {

		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/text-match-over-multiple-fields.json");

		return JSONUtil.put(
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
								"multi_match",
								_getMultiMatchJSONObject(boost, operator)))
					).put(
						"type", "wrapper"
					))
			).put(
				"conditions", createJSONArray()
			).put(
				"description",
				JSONUtil.put(
					"en_US",
					"Search for a text match over multiple text fields")
			).put(
				"enabled", true
			).put(
				"icon", "picture"
			).put(
				"title",
				JSONUtil.put("en_US", "Text Match Over Multiple Fields")
			)
		).put(
			"elementTemplateJSON",
			elementTemplateJSONObject.get("elementTemplateJSON")
		).put(
			"uiConfigurationJSON",
			elementTemplateJSONObject.get("uiConfigurationJSON")
		).put(
			"uiConfigurationValues",
			_getTextMatchOverMultipleFieldUIConfigValuesJSONObject(
				boost, contentBoost, localizedTitleBoost, operator)
		);
	}

	private JSONObject _getTextMatchOverMultipleFieldUIConfigValuesJSONObject(
		int boost, int contentBoost, int localizedTitleBoost, String operator) {

		JSONArray fieldsJSONArray = createJSONArray();

		return JSONUtil.put(
			"boost", boost
		).put(
			"fields",
			fieldsJSONArray.put(
				JSONUtil.put(
					"boost", localizedTitleBoost
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
			"fuzziness", "AUTO"
		).put(
			"operator", operator
		).put(
			"type", "best_fields"
		);
	}

}