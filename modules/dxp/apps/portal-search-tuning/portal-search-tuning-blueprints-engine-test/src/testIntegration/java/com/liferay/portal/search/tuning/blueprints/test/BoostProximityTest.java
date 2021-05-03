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
public class BoostProximityTest extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testExpandoKeywordCustomFieldsLocationGeolocation()
		throws Exception {

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
			getConfigurationString(_getQueryElementJSONObject(100)),
			_getSelectedElementString(100));

		assertSearch(blueprint, null, "[branch sf, branch la]", "branch", null);

		setupJsonDataProviderCache(
			"34.94.32.239", "Los Angeles CA", 24.03, -107.44);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				getIPStackConfigurationTemporarySwapper(
					"2345", "true", "34.94.32.239")) {

			assertSearch(
				blueprint, null, "[branch la, branch sf]", "34.94.32.239",
				"branch", null);
		}

		setupJsonDataProviderCache(
			"64.225.32.6", "Palo Alto, CA", 64.01, -117.42);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				getIPStackConfigurationTemporarySwapper(
					"2345", "true", "64.225.32.6")) {

			assertSearch(
				blueprint, null, "[branch sf, branch la]", "64.225.32.6",
				"branch", null);
		}
	}

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
							"function_score",
							JSONUtil.put(
								"boost", boost
							).put(
								"gauss",
								JSONUtil.put(
									"expando__custom_fields__location_" +
										"geolocation",
									JSONUtil.put(
										"decay", 0.3
									).put(
										"origin",
										JSONUtil.put(
											"lat", "${ipstack.latitude}"
										).put(
											"lon", "${ipstack.longitude}"
										)
									).put(
										"scale", "100km"
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
				"Boost contents tagged close to my location with a Gaussian " +
					"function")
		).put(
			"enabled", true
		).put(
			"icon", "thumbs-up"
		).put(
			"title", JSONUtil.put("en_US", "Boost Proximity")
		);
	}

	private String _getSelectedElementString(int boost) throws Exception {
		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/boost-proximity-test.json");

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
					"uiConfigurationValues",
					JSONUtil.put(
						"boost", boost
					).put(
						"decay", 0.3
					).put(
						"field", "expando__custom_fields__location_geolocation"
					).put(
						"scale", 100
					)
				))
		).toString();
	}

	private static final String _EXPANDO_COLUMN_GEOLOCATION = "location";

}