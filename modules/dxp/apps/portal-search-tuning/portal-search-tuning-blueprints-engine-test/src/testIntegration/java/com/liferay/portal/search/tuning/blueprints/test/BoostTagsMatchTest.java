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
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class BoostTagsMatchTest extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_prefixRandomString = "cola" + RandomTestUtil.randomInt();

		serviceContext.setAssetTagNames(new String[0]);

		addJournalArticle("coca " + _prefixRandomString, _prefixRandomString);

		AssetTag assetTag = AssetTestUtil.addTag(
			group.getGroupId(), _prefixRandomString);

		serviceContext.setAssetTagNames(new String[] {assetTag.getName()});

		addJournalArticle("pepsi " + _prefixRandomString, "");
	}

	@Test
	public void testKeywoardMatchToAssetTagName() throws Exception {
		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString(_getQueryElementJSONObject(100)),
			_getSelectedElementString(100), 1);

		assertSearch(
			blueprint, null,
			StringBundler.concat(
				"[pepsi ", _prefixRandomString, ", coca ", _prefixRandomString,
				"]"),
			_prefixRandomString, null);
	}

	@Test
	public void testKeywoardMatchWithoutAssetTagName() throws Exception {
		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString((JSONObject[])null), "", 1);

		assertSearch(
			blueprint, null,
			StringBundler.concat(
				"[coca ", _prefixRandomString, ", pepsi ", _prefixRandomString,
				"]"),
			_prefixRandomString, null);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private JSONObject _getQueryElementJSONObject(int boost) {
		return JSONUtil.put(
			"category", "boost"
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
								"assetTagNames.raw",
								JSONUtil.put(
									"boost", boost
								).put(
									"value", "${keywords}"
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
				"Boost contents having an exact keyword match to a tag")
		).put(
			"enabled", true
		).put(
			"icon", "thumbs-up"
		).put(
			"title", JSONUtil.put("en_US", "Boost Tags Match")
		);
	}

	private String _getSelectedElementString(int boost) throws Exception {
		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/boost-tags-match-test.json");

		return JSONUtil.put(
			"query_configuration",
			createJSONArray().put(
				JSONUtil.put(
					"elementOutput",
					JSONUtil.put(
						"category", "boost"
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
											"assetTagNames.raw",
											JSONUtil.put(
												"boost", boost
											).put(
												"value", "${keywords}"
											)))
								).put(
									"type", "wrapper"
								)
							))
					).put(
						"conditions", createJSONArray()
					).put(
						"description",
						JSONUtil.put(
							"en_US",
							"Boost contents having an exact keyword match to " +
								"a tag")
					).put(
						"enabled", true
					).put(
						"icon", "thumbs-up"
					).put(
						"title", JSONUtil.put("en_US", "Boost Tags Match")
					)
				).put(
					"elementTemplateJSON",
					elementTemplateJSONObject.get("elementTemplateJSON")
				).put(
					"uiConfigurationJSON",
					elementTemplateJSONObject.get("uiConfigurationJSON")
				).put(
					"uiConfigurationValues", JSONUtil.put("boost", boost)
				))
		).toString();
	}

	private String _prefixRandomString;

}