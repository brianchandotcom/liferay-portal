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
public class HideFromScratchTest extends BaseQueryElementsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchWithPasteESQueryMustNot() throws Exception {
		addJournalArticle("Cafe Rio", "Los Angeles");
		addJournalArticle("Starbucks Cafe", "Los Angeles");
		addJournalArticle("Cloud Cafe", "Orange County");
		addJournalArticle("Denny's", "Los Angeles");

		String configurationString = getConfigurationString(
			getMatchQueryElementJSONObject(200, "must_not", "los angeles"),
			getMultiMatchQueryElementJSONObject(1, "or"),
			getHideHiddenContentsElementJSONObject());

		String selectedElementString = getSelectedElementString(
			getPasteESQueryJSONObject(200, "must_not", "los angeles"),
			getTextMatchOverMultipleFieldJSONObject(1, 1, 2, "or"),
			getHideHiddenContentsJSONObject());

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""), configurationString,
			selectedElementString);

		assertSearchIgnoreRelevance(
			blueprint, null, "[cloud cafe]", "cafe", null);

		configurationString = getConfigurationString(
			getMatchQueryElementJSONObject(200, "must_not", "orange county"),
			getMultiMatchQueryElementJSONObject(1, "or"),
			getHideHiddenContentsElementJSONObject());

		selectedElementString = getSelectedElementString(
			getPasteESQueryJSONObject(200, "must_not", "orange county", null),
			getTextMatchOverMultipleFieldJSONObject(1, 1, 2, "or"),
			getHideHiddenContentsJSONObject());

		assertSearchIgnoreRelevance(
			blueprint, configurationString, "[cafe rio, starbucks cafe]",
			"cafe", selectedElementString);
	}

}