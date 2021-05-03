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
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
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
public class BoostWithESFunctionScoreQueryTest
	extends BaseQueryElementsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchWithPasteESQueryMust() throws Exception {
		addExpandoColumn(
			ExpandoColumnConstants.GEOLOCATION, _EXPANDO_COLUMN_GEOLOCATION);

		serviceContext.setExpandoBridgeAttributes(
			Collections.singletonMap(
				_EXPANDO_COLUMN_GEOLOCATION,
				getGeolocationValue(64.01, -117.42)));

		addJournalArticle("Branch SF", "");

		serviceContext.setExpandoBridgeAttributes(
			Collections.singletonMap(
				_EXPANDO_COLUMN_GEOLOCATION,
				getGeolocationValue(24.03, -107.44)));

		addJournalArticle("Branch LA", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString(
				getFunctionalScoreQueryElementJSONObject("64.01", "-117.42")),
			getSelectedElementString(
				getPasteESQueryJSONObject(100, "must", "64.01", "-117.42")));

		setupJsonDataProviderCache(
			_IP_ADDRESS[1], "Palo Alto, CA", 64.01, -117.42);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				getIPStackConfigurationTemporarySwapper(
					"2345", "true", _IP_ADDRESS[1])) {

			assertSearch(
				blueprint, null, "[branch sf, branch la]", _IP_ADDRESS[1],
				"branch", null);
		}

		setupJsonDataProviderCache(
			_IP_ADDRESS[0], "Los Angeles CA", 24.03, -107.44);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				getIPStackConfigurationTemporarySwapper(
					"2345", "true", _IP_ADDRESS[0])) {

			assertSearch(
				blueprint,
				getConfigurationString(
					getFunctionalScoreQueryElementJSONObject(
						"24.03", "-107.44")),
				"[branch la, branch sf]", _IP_ADDRESS[0], "branch",
				getSelectedElementString(
					getPasteESQueryJSONObject(
						100, "must", "24.03", "-107.44")));
		}
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

	protected JSONObject getFunctionalScoreQueryElementJSONObject(
		String lat, String lon) {

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
							"function_score",
							JSONUtil.put(
								"boost", 100
							).put(
								"gauss",
								JSONUtil.put(
									"expando__custom_fields__" +
										"location_geolocation",
									JSONUtil.put(
										"decay", 0.3
									).put(
										"origin",
										JSONUtil.put(
											"lat", lat
										).put(
											"lon", lon
										)
									).put(
										"scale", "1000km"
									))
							)))
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

	@Override
	protected JSONObject getPasteESQueryUIConfigValuesJSONObject(
		int boost, String occur, String... queryValues) {

		return JSONUtil.put(
			"occur", occur
		).put(
			"query",
			JSONUtil.put(
				"function_score",
				JSONUtil.put(
					"",
					JSONUtil.put(
						"expando__custom_fields__location_geolocation",
						JSONUtil.put(
							"decay", 0.3
						).put(
							"origin",
							JSONUtil.put(
								"lat", queryValues[0]
							).put(
								"lon", queryValues[1]
							)
						).put(
							"scale", "1000km"
						))
				).put(
					"boost", boost
				))
		);
	}

	private static final String _EXPANDO_COLUMN_GEOLOCATION = "location";

	private static final String[] _IP_ADDRESS = {"34.94.32.240", "64.225.32.7"};

}