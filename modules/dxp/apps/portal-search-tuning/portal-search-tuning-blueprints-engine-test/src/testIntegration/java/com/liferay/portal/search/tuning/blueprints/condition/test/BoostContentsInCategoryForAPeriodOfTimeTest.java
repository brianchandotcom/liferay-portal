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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.EvaluationType;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class BoostContentsInCategoryForAPeriodOfTimeTest
	extends BaseBoostContentsInCategoryTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_startDate = new Date(System.currentTimeMillis());

		_endDate = _getNextDay();
	}

	@Test
	public void testInRangeCondition() throws Exception {
		_assetCategory = getAssetCategory("Promoted", "Custmers");

		addJournalArticle("Coca Cola", "cola cola");

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		addJournalArticle("Pepsi Cola", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString((JSONObject[])null), "");

		assertSearch(blueprint, null, "[coca cola, pepsi cola]", "cola", null);

		String configurationString = getConfigurationString(
			getQueryElementJSONObject(1000, _assetCategory.getCategoryId()));

		String selectedElementString = getSelectedElementString(
			1000, _assetCategory.getCategoryId());

		assertSearch(
			blueprint, configurationString, "[pepsi cola, coca cola]", "cola",
			selectedElementString);
	}

	@Override
	protected JSONArray getConditions() {
		return createJSONArray().put(
			JSONUtil.put(
				"configuration",
				JSONUtil.put(
					"date_format", "yyyyMMdd"
				).put(
					"evaluation_type", EvaluationType.IN_RANGE.getjsonValue()
				).put(
					"parameter_name", "${time.current_date}"
				).put(
					"value", _getStartDateEndDateJSONArray()
				)));
	}

	@Override
	protected JSONObject getDescription() {
		return JSONUtil.put(
			"en_US",
			"Boost contents in a category for the given period of time");
	}

	@Override
	protected JSONObject getElementTemplateJSONObject() throws Exception {
		return getElementTemplateJSONObject(
			"/elements/boost-contents-in-a-category-for-a-period-of-time-" +
				"test.json");
	}

	@Override
	protected JSONObject getTitle() {
		return JSONUtil.put(
			"en_US", "Boost Contents in a Category for a Period of Time");
	}

	@Override
	protected JSONObject getUIConfigurationValuesJSONObject() {
		return JSONUtil.put(
			"asset_category_id", _assetCategory.getCategoryId()
		).put(
			"boost", 1000
		).put(
			"end_date", _endDate.getTime()
		).put(
			"start_date", _startDate.getTime()
		);
	}

	private Date _getNextDay() {
		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.add(Calendar.DAY_OF_YEAR, 1);

		return cal.getTime();
	}

	private JSONArray _getStartDateEndDateJSONArray() {
		JSONArray jsonArray = createJSONArray().put(
			DateUtil.getDate(_startDate, "yyyyMMdd", LocaleUtil.US));

		jsonArray.put(DateUtil.getDate(_endDate, "yyyyMMdd", LocaleUtil.US));

		return jsonArray;
	}

	private AssetCategory _assetCategory;
	private Date _endDate;
	private Date _startDate;

}