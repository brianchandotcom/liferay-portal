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
public class BoostContentInCategoryByKeywordMatchTest
	extends BaseBoostContentInCategoryTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testAnyWordInCondition() throws Exception {
		_assetCategory = getAssetCategory("Promoted", "employee");

		addJournalArticle("Coca Cola", "cola cola");

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		addJournalArticle("Pepsi Cola", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString((JSONObject[])null), "", 1);

		assertSearch(blueprint, null, "[coca cola, pepsi cola]", "cola", null);

		String configurationString = getConfigurationString(
			getQueryElementJSONObject(100, _assetCategory.getCategoryId()));

		String selectedElementString = getSelectedElementString(
			100, _assetCategory.getCategoryId());

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
					"evaluation_type", EvaluationType.ANY_WORD_IN.getjsonValue()
				).put(
					"parameter_name", "${keywords}"
				).put(
					"value", createJSONArray().put("cola")
				)));
	}

	@Override
	protected JSONObject getDescription() {
		return JSONUtil.put(
			"en_US",
			"Show Web Contents in a category higher in the results, if " +
				"searchphrase contains any of the given keywords");
	}

	@Override
	protected JSONObject getElementTemplateJSONObject() throws Exception {
		return getElementTemplateJSONObject(
			"/elements/boost-contents-in-a-category-by-keyword-match-" +
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
			"boost", 100
		).put(
			"keywords",
			createJSONArray().put(
				JSONUtil.put(
					"label", "cola"
				).put(
					"value", "cola"
				))
		);
	}

	private AssetCategory _assetCategory;

}