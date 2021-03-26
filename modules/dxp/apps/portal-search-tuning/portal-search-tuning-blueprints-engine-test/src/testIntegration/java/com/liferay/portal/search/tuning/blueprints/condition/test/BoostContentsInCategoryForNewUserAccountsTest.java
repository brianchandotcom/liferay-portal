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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.framework.FrameworkConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.EvaluationType;
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
public class BoostContentsInCategoryForNewUserAccountsTest
	extends BaseBoostContentsInCategoryTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testInRangeCondition() throws Exception {
		_assetCategory = getAssetCategory("For New Recruits", "Employee");

		addJournalArticle(
			"Company policies for All Employees Recruits", "policies policies");

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		addJournalArticle("Company Policies for New Recruits", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString((JSONObject[])null), "", 1);

		assertSearch(
			blueprint, null,
			"[company policies for all employees recruits, company policies " +
				"for new recruits]",
			"policies", null);

		String configurationString = getConfigurationString(
			getQueryElementJSONObject(1000, _assetCategory.getCategoryId()));

		String selectedElementString = getSelectedElementString(
			1000, _assetCategory.getCategoryId());

		assertSearch(
			blueprint, configurationString,
			"[company policies for new recruits, company policies for all " +
				"employees recruits]",
			"policies", selectedElementString);
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
					"value", _getCurrentDateModifierDateJSONArray()
				)));
	}

	@Override
	protected JSONObject getDescription() {
		return JSONUtil.put(
			"en_US",
			"Boost contents in a category for user accounts created withing " +
				"the given time");
	}

	@Override
	protected JSONObject getElementTemplateJSONObject() throws Exception {
		return getElementTemplateJSONObject(
			"/elements/boost-contents-in-a-category-for-new-user-accounts-" +
				"test.json");
	}

	@Override
	protected JSONObject getFrameworkConfiguration() {
		JSONArray fieldsJSONArray = createJSONArray();

		return JSONUtil.put(
			FrameworkConfigurationKeys.APPLY_INDEXER_CLAUSES.getJsonKey(), true
		).put(
			"searchable_asset_types",
			fieldsJSONArray.put(
				"com.liferay.wiki.model.WikiPage"
			).put(
				"com.liferay.journal.model.JournalArticle"
			)
		);
	}

	@Override
	protected JSONObject getTitle() {
		return JSONUtil.put(
			"en_US", "Boost Contents in a Category for New User Accounts");
	}

	@Override
	protected JSONObject getUIConfigurationValuesJSONObject() {
		return JSONUtil.put(
			"asset_category_id", _assetCategory.getCategoryId()
		).put(
			"boost", 1000
		).put(
			"time_range", 30
		);
	}

	private JSONArray _getCurrentDateModifierDateJSONArray() {
		JSONArray jsonArray = createJSONArray().put(
			"${time.current_date|modifier=-30d,dateFormat=yyyyMMdd}");

		jsonArray.put("${time.current_date|modifier=+1d,dateFormat=yyyyMMdd}");

		return jsonArray;
	}

	private AssetCategory _assetCategory;

}