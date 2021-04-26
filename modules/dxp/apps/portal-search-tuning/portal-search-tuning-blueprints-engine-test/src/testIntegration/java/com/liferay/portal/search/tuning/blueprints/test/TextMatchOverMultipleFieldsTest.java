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
public class TextMatchOverMultipleFieldsTest extends BaseQueryElementsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchMultipleFieldsTypeWithOperator() throws Exception {
		addJournalArticle("drink carbonated pepsi", "carbonated cola cola");
		addJournalArticle("drink carbonated coca", "carbonated cola");
		addJournalArticle("sprite", "carbonated cola cola");
		addJournalArticle("fruit punch", "non-carbonated cola");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString((JSONObject[])null), "");

		_testSearchMostFieldsWithOperator(blueprint);

		_testSearchBestFieldsWithOperator(blueprint);

		_testSearchPhraseWithOperator(blueprint);

		_testSearchPhrasePrefixWithOperator(blueprint);

		_testSearchBoolPrefixWithOperator(blueprint);
	}

	@Test
	public void testSearchWithCrossFields() throws Exception {
		addJournalArticle("alpha beta", "foxtrot, golf");
		addJournalArticle("alpha edison", "hotel golf");
		addJournalArticle("beta charlie", "alpha");
		addJournalArticle("edison india", "beta");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString(
				getMultiMatchQueryElementJSONObject(
					1, null, "or", "cross_fields")),
			getSelectedElementString(
				getTextMatchOverMultipleFieldJSONObject(
					1, 1, null, 2, "or", "cross_fields")));

		assertSearch(
			blueprint, null, "[alpha beta, alpha edison, beta charlie]",
			"alpha golf", "");

		String configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(
				1, null, "and", "cross_fields"));

		String selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldJSONObject(
				1, 1, null, 2, "and", "cross_fields"));

		assertSearch(
			blueprint, configurationString, "[alpha beta, alpha edison]",
			"alpha golf", selectedElementString);
	}

	private void _testSearchBestFieldsWithOperator(Blueprint blueprint)
		throws Exception {

		String configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(
				1, "AUTO", "or", "best_fields"));

		String selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldJSONObject(
				1, 1, "AUTO", 2, "or", "best_fields"));

		assertSearch(
			blueprint, configurationString,
			"[drink carbonated coca, drink carbonated pepsi, sprite, fruit " +
				"punch]",
			"carbonated cola", selectedElementString);

		configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(
				1, "AUTO", "and", "best_fields"));

		selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldJSONObject(
				1, 1, "AUTO", 2, "and", "best_fields"));

		assertSearch(
			blueprint, configurationString,
			"[drink carbonated coca, drink carbonated pepsi, sprite, fruit " +
				"punch]",
			"coca cola", selectedElementString);
	}

	private void _testSearchBoolPrefixWithOperator(Blueprint blueprint)
		throws Exception {

		String configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(1, null, "or", "bool_prefix"));

		String selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldJSONObject(
				1, 1, null, 2, "or", "bool_prefix"));

		assertSearch(
			blueprint, configurationString,
			"[drink carbonated pepsi, drink carbonated coca]", "drink",
			selectedElementString);

		configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(1, null, "and", "bool_prefix"));

		selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldJSONObject(
				1, 1, null, 2, "and", "bool_prefix"));

		assertSearch(
			blueprint, configurationString,
			"[drink carbonated pepsi, drink carbonated coca]",
			"drink carbonated", selectedElementString);
	}

	private void _testSearchMostFieldsWithOperator(Blueprint blueprint)
		throws Exception {

		String configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(
				1, "AUTO", "or", "most_fields"));

		String selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldUIConfigValuesJSONObject(
				2, 1, "AUTO", 1, "or", "most_fields"));

		assertSearch(
			blueprint, configurationString,
			"[drink carbonated coca, drink carbonated pepsi, sprite, fruit " +
				"punch]",
			"cola cola", selectedElementString);

		configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(
				1, "AUTO", "and", "most_fields"));

		selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldUIConfigValuesJSONObject(
				2, 1, "AUTO", 1, "and", "most_fields"));

		assertSearch(
			blueprint, configurationString,
			"[drink carbonated coca, drink carbonated pepsi, sprite, fruit " +
				"punch]",
			"coca carbonated", selectedElementString);
	}

	private void _testSearchPhrasePrefixWithOperator(Blueprint blueprint)
		throws Exception {

		String configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(
				1, null, "or", "phrase_prefix"));

		String selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldJSONObject(
				1, 1, null, 2, "or", "phrase_prefix"));

		assertSearch(
			blueprint, configurationString,
			"[drink carbonated pepsi, drink carbonated coca]",
			"drink carbonated", selectedElementString);

		configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(
				1, null, "and", "phrase_prefix"));

		selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldJSONObject(
				1, 1, null, 2, "and", "phrase_prefix"));

		assertSearch(
			blueprint, configurationString,
			"[drink carbonated pepsi, drink carbonated coca]", "drink",
			selectedElementString);
	}

	private void _testSearchPhraseWithOperator(Blueprint blueprint)
		throws Exception {

		String configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(1, null, "or", "phrase"));

		String selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldJSONObject(
				1, 1, null, 2, "or", "phrase"));

		assertSearch(
			blueprint, configurationString, "[drink carbonated pepsi, sprite]",
			"cola cola", selectedElementString);

		configurationString = getConfigurationString(
			getMultiMatchQueryElementJSONObject(1, null, "and", "phrase"));

		selectedElementString = getSelectedElementString(
			getTextMatchOverMultipleFieldJSONObject(
				1, 1, null, 2, "and", "phrase"));

		assertSearch(
			blueprint, configurationString, "[drink carbonated pepsi, sprite]",
			"cola cola", selectedElementString);
	}

}