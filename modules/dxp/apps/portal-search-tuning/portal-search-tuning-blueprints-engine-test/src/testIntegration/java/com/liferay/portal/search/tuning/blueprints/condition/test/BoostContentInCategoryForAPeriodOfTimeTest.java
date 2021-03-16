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
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.EvaluationType;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.test.BaseBlueprintsTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class BoostContentInCategoryForAPeriodOfTimeTest
	extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testInRangeCondition() throws Exception {
		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				group.getGroupId());

		User user = UserTestUtil.addUser();

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			user.getUserId(), group.getGroupId(), "Promoted",
			assetVocabulary.getVocabularyId(), serviceContext);

		addJournalArticle("Coca Cola", "cola cola");

		serviceContext.setAssetCategoryIds(
			new long[] {assetCategory.getCategoryId()});

		addJournalArticle("Pepsi Cola", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString(null), "", 1);

		assertSearch(blueprint, null, "[coca cola, pepsi cola]", "cola", null);

		Date startDate = new Date(System.currentTimeMillis());

		Date endDate = _getNextDay();

		String configurationString = getConfigurationString(
			_getQueryElementJSONObject(
				1000, assetCategory.getCategoryId(), endDate,
				EvaluationType.IN_RANGE.getjsonValue(), startDate));

		String selectedElementString = _getSelectedElementString(
			1000, assetCategory.getCategoryId(), endDate,
			EvaluationType.IN_RANGE.getjsonValue(), startDate);

		assertSearch(
			blueprint, configurationString, "[pepsi cola, coca cola]", "cola",
			selectedElementString);
	}

	private Date _getNextDay() {
		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.add(Calendar.DAY_OF_YEAR, 1);

		return cal.getTime();
	}

	private JSONObject _getQueryElementJSONObject(
		int boost, long categoryId, Date endDate, String evaluationType,
		Date startDate) {

		JSONArray jsonArray = createJSONArray().put(
			DateUtil.getDate(startDate, "yyyyMMdd", LocaleUtil.US));

		jsonArray.put(DateUtil.getDate(endDate, "yyyyMMdd", LocaleUtil.US));

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
							"term",
							JSONUtil.put(
								"assetCategoryIds",
								JSONUtil.put(
									"boost", boost
								).put(
									"value", categoryId
								))))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions",
			createJSONArray().put(
				JSONUtil.put(
					"configuration",
					JSONUtil.put(
						"date_format", "yyyyMMdd"
					).put(
						"evaluation_type", evaluationType
					).put(
						"parameter_name", "${time.current_date}"
					).put(
						"value", jsonArray
					)))
		).put(
			"description",
			JSONUtil.put(
				"en_US",
				"Boost contents in a category for the given period of time")
		).put(
			"enabled", true
		).put(
			"icon", "thumbs-up"
		).put(
			"title",
			JSONUtil.put(
				"en_US", "Boost Contents in a Category for a Period of Time")
		);
	}

	private String _getSelectedElementString(
			int boost, long categoryId, Date endDate, String evaluationType,
			Date startDate)
		throws Exception {

		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/boost-contents-in-a-category-for-a-period-of-time-" +
				"test.json");

		JSONArray jsonArray = createJSONArray().put(
			DateUtil.getDate(startDate, "yyyyMMdd", LocaleUtil.US));

		jsonArray.put(DateUtil.getDate(endDate, "yyyyMMdd", LocaleUtil.US));

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
										"term",
										JSONUtil.put(
											"assetCategoryIds",
											JSONUtil.put(
												"boost", boost
											).put(
												"value", categoryId
											))))
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
									"parameter_name", "${time.current_date}"
								).put(
									"value", jsonArray
								)))
					).put(
						"description",
						JSONUtil.put(
							"en_US",
							"Boost contents in a category for the given " +
								"period of time")
					).put(
						"enabled", true
					).put(
						"icon", "thumbs-up"
					).put(
						"title",
						JSONUtil.put(
							"en_US",
							"Boost Contents in a Category for a Period of Time")
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
						categoryId, boost, endDate.getTime(),
						startDate.getTime())
				))
		).toString();
	}

	private JSONObject _getUIConfigurationValuesJSONObject(
		long categoryId, int boost, long endDate, long startDate) {

		return JSONUtil.put(
			"asset_category_id", categoryId
		).put(
			"boost", boost
		).put(
			"end_date", endDate
		).put(
			"start_date", startDate
		);
	}

}