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
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.test.util.AssetTestUtil;
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
public class HideTaggedContentTest extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchHideContent() throws Exception {
		addJournalArticle("do not hide me", "");

		AssetTag assetTag = AssetTestUtil.addTag(group.getGroupId(), "hide");

		serviceContext.setAssetTagNames(new String[] {assetTag.getName()});

		addJournalArticle("hide me", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString((JSONObject[])null), "");

		assertSearch(
			blueprint, null, "[hide me, do not hide me]", "hide me", null);

		assertSearch(
			blueprint, getConfigurationString(_getQueryElementJSONObject()),
			"[do not hide me]", "hide me", _getSelectedElementString("hide"));
	}

	private JSONObject _getQueryElementJSONObject() {
		return JSONUtil.put(
			"category", "hide"
		).put(
			"clauses",
			createJSONArray().put(
				JSONUtil.put(
					"context", "query"
				).put(
					"occur", "filter"
				).put(
					"query",
					JSONUtil.put(
						"query",
						JSONUtil.put(
							"bool",
							JSONUtil.put(
								"must_not",
								createJSONArray().put(
									JSONUtil.put(
										"term",
										JSONUtil.put(
											"assetTagNames.raw",
											JSONUtil.put("value", "hide")))))))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions", createJSONArray()
		).put(
			"description", JSONUtil.put("en_US", "Hide contents tagged with")
		).put(
			"enabled", true
		).put(
			"icon", "hidden"
		).put(
			"title", JSONUtil.put("en_US", "Hide Tagged Contents")
		);
	}

	private String _getSelectedElementString(String assetTag) throws Exception {
		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/hide-tagged-contents-test.json");

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
					"uiConfigurationValues", JSONUtil.put("asset_tag", assetTag)
				))
		).toString();
	}

}