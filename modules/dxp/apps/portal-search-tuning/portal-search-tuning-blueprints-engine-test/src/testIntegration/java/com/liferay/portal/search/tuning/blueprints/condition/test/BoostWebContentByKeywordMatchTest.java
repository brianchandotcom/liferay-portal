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

package com.liferay.portal.search.tuning.blueprints.condition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.EvaluationType;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.test.BaseBlueprintsTestCase;
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
public class BoostWebContentByKeywordMatchTest extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testAnyWordInCondition() throws Exception {
		addJournalArticle("Coca Cola", "cola cola");
		JournalArticle journalArticle = addJournalArticle("Pepsi Cola", "");

		String articleId = journalArticle.getArticleId();

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString(null), "", 1);

		assertSearch(blueprint, null, "[coca cola, pepsi cola]", "cola", null);

		String configurationString = getConfigurationString(
			_getQueryElementJSONObject(
				articleId, 100, EvaluationType.ANY_WORD_IN.getjsonValue(),
				"cola"));

		String selectedElementString = _getSelectedElementString(
			articleId, 100, EvaluationType.ANY_WORD_IN.getjsonValue(), "cola");

		assertSearch(
			blueprint, configurationString, "[pepsi cola, coca cola]", "cola",
			selectedElementString);

		configurationString = getConfigurationString(
			_getQueryElementJSONObject(
				articleId, 100, EvaluationType.NOT_CONTAINS.getjsonValue(),
				"cola"));

		selectedElementString = _getSelectedElementString(
			articleId, 100, EvaluationType.NOT_CONTAINS.getjsonValue(), "cola");

		assertSearch(
			blueprint, configurationString, "[coca cola, pepsi cola]", "cola",
			selectedElementString);
	}

	private JSONObject _getQueryElementJSONObject(
		String articleId, int boost, String evaluationType, String keywords) {

		return JSONUtil.put(
			"category", "conditional"
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
								"articleId_String_sortable",
								createJSONArray().put(articleId)
							).put(
								"boost", boost
							)))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions",
			createJSONArray().put(
				JSONUtil.put(
					"configuration",
					JSONUtil.put(
						"evaluation_type", evaluationType
					).put(
						"parameter_name", "${keywords}"
					).put(
						"value", createJSONArray().put(keywords)
					)))
		).put(
			"description",
			JSONUtil.put(
				"en_US", "Show selected Web Contents higher in the results")
		).put(
			"enabled", true
		).put(
			"icon", "thumbs-up"
		).put(
			"title",
			JSONUtil.put("en_US", "Boost Web Contents by Keyword Match")
		);
	}

	private String _getSelectedElementString(
			String articleId, int boost, String evaluationType, String keywords)
		throws Exception {

		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/boost-web-contents-by-keyword-match-test.json");

		return JSONUtil.put(
			"query_configuration",
			createJSONArray().put(
				JSONUtil.put(
					"elementOutput",
					JSONUtil.put(
						"category", "conditional"
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
											"articleId_String_sortable",
											createJSONArray().put(articleId)
										).put(
											"boost", boost
										)))
							).put(
								"type", "wrapper"
							))
					).put(
						"conditions",
						createJSONArray().put(
							JSONUtil.put(
								"configuration",
								JSONUtil.put(
									"evaluation_type", evaluationType
								).put(
									"parameter_name", "${keywords}"
								).put(
									"value", createJSONArray().put(keywords)
								)))
					).put(
						"description",
						JSONUtil.put(
							"en_US",
							"Show selected Web Contents higher in the results")
					).put(
						"enabled", true
					).put(
						"icon", "thumbs-up"
					).put(
						"title",
						JSONUtil.put(
							"en_US", "Boost Web Contents by Keyword Match")
					)
				).put(
					"elementTemplateJSON",
					elementTemplateJSONObject.get("elementTemplateJSON")
				).put(
					"uiConfigurationJSON",
					elementTemplateJSONObject.get("uiConfigurationJSON")
				).put(
					"uiConfigurationValues",
					_getUIConfigurationValuesJSONObject(
						articleId, boost, keywords)
				))
		).toString();
	}

	private JSONObject _getUIConfigurationValuesJSONObject(
		String articleId, int boost, String keywords) {

		return JSONUtil.put(
			"article_ids",
			createJSONArray().put(
				JSONUtil.put(
					"label", articleId
				).put(
					"value", articleId
				))
		).put(
			"boost", boost
		).put(
			"values",
			createJSONArray().put(
				JSONUtil.put(
					"label", keywords
				).put(
					"value", keywords
				))
		);
	}

}