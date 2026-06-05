/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.context.Context;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
@FeatureFlags(featureFlags = @FeatureFlag("LPD-86384"))
@RunWith(Arquillian.class)
public class GetAudiencesServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());
	}

	@Test
	@TestInfo("LPD-91094")
	public void testGetAudiences() throws Exception {
		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			_createGroupJSONObject(
				"and", _createRuleJSONObject(Context.URL, "/pricing")),
			RandomTestUtil.randomString(),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONObject audienceJSONObject = _getAudienceJSONObject(
			_getAudiencesJSONArray(), segmentsEntry.getSegmentsEntryKey());

		Assert.assertEquals("AND", audienceJSONObject.getString("conjunction"));
		Assert.assertEquals(
			"BROWSER", audienceJSONObject.getString("retentionType"));

		JSONArray rulesJSONArray = audienceJSONObject.getJSONArray("rules");

		Assert.assertEquals(1, rulesJSONArray.length());

		JSONObject ruleJSONObject = rulesJSONArray.getJSONObject(0);

		Assert.assertEquals(Context.URL, ruleJSONObject.getString("attribute"));
		Assert.assertEquals("eq", ruleJSONObject.getString("operator"));
		Assert.assertEquals("/pricing", ruleJSONObject.getString("value"));
	}

	@Test
	@TestInfo("LPD-91094")
	public void testGetAudiencesMapsAttributeNames() throws Exception {
		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			_createGroupJSONObject(
				"and",
				_createRuleJSONObject(
					Context.BROWSER, RandomTestUtil.randomString()),
				_createRuleJSONObject(Context.BROWSER_VERSION, "124.0"),
				_createRuleJSONObject(
					Context.DEVICE_TYPE, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.GEOLOCATION, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.LANGUAGE_ID, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.LAST_SIGN_IN_DATE_TIME,
					RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.LOCAL_DATE, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.LOCAL_TIME, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.PATHNAME, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.REFERRER_URL, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.REQUEST_PARAMETERS, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.SIGNED_IN, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.TIME_ZONE, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.URL, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.USER_AGENT, RandomTestUtil.randomString()),
				_createRuleJSONObject(
					"customContext/ipGeocoderCountry",
					RandomTestUtil.randomString())),
			RandomTestUtil.randomString(),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONObject audienceJSONObject = _getAudienceJSONObject(
			_getAudiencesJSONArray(), segmentsEntry.getSegmentsEntryKey());

		JSONArray rulesJSONArray = audienceJSONObject.getJSONArray("rules");

		String[] attributeNames = new String[rulesJSONArray.length()];

		for (int i = 0; i < rulesJSONArray.length(); i++) {
			JSONObject ruleJSONObject = rulesJSONArray.getJSONObject(i);

			attributeNames[i] = ruleJSONObject.getString("attribute");
		}

		String[] expectedAttributeNames = {
			"browser_name", "browser_version", "device_type", "geolocation",
			"ip_geocoder_country", "language", "last_sign_in_date",
			"local_date", "local_time", "pathname", "referrer_url",
			"request_parameters", "signed_in", "time_zone", "user_agent", "url"
		};

		Assert.assertEquals(
			Arrays.toString(attributeNames), expectedAttributeNames.length,
			attributeNames.length);

		for (String expectedAttributeName : expectedAttributeNames) {
			Assert.assertTrue(
				ArrayUtil.contains(attributeNames, expectedAttributeName));
		}
	}

	@Test
	@TestInfo("LPD-93510")
	public void testGetAudiencesMapsCollectionOperators() throws Exception {
		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			_createGroupJSONObject(
				"and",
				_createRuleJSONObject(
					Context.COOKIES, "contains", RandomTestUtil.randomString()),
				_createRuleJSONObject(
					Context.COOKIES, "not-contains",
					RandomTestUtil.randomString())),
			RandomTestUtil.randomString(),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONObject audienceJSONObject = _getAudienceJSONObject(
			_getAudiencesJSONArray(), segmentsEntry.getSegmentsEntryKey());

		JSONArray rulesJSONArray = audienceJSONObject.getJSONArray("rules");

		JSONObject ruleJSONObject = rulesJSONArray.getJSONObject(0);

		Assert.assertEquals("includes", ruleJSONObject.getString("operator"));

		ruleJSONObject = rulesJSONArray.getJSONObject(1);

		Assert.assertEquals(
			"not_includes", ruleJSONObject.getString("operator"));
	}

	@Test
	@TestInfo("LPD-92872")
	public void testGetAudiencesMapsValues() throws Exception {
		LocalDate localDate = LocalDate.now();

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
			"MM/dd/yyyy");

		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			_createGroupJSONObject(
				"and",
				_createRuleJSONObject(
					Context.LANGUAGE_ID,
					LocaleUtil.toLanguageId(LocaleUtil.US)),
				_createRuleJSONObject(
					Context.LOCAL_DATE, localDate.format(dateTimeFormatter))),
			RandomTestUtil.randomString(),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONObject audienceJSONObject = _getAudienceJSONObject(
			_getAudiencesJSONArray(), segmentsEntry.getSegmentsEntryKey());

		JSONArray rulesJSONArray = audienceJSONObject.getJSONArray("rules");

		JSONObject ruleJSONObject = rulesJSONArray.getJSONObject(0);

		Assert.assertEquals(
			LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
			ruleJSONObject.get("value"));

		ruleJSONObject = rulesJSONArray.getJSONObject(1);

		Assert.assertEquals(
			localDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
			ruleJSONObject.get("value"));
	}

	@Test
	@TestInfo("LPD-91094")
	public void testGetAudiencesReturnsEmptyWhenNoAudienceEntriesExist()
		throws Exception {

		MockHttpServletResponse mockHttpServletResponse = _invokeServlet();

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON,
			mockHttpServletResponse.getContentType());

		JSONObject responseJSONObject = _jsonFactory.createJSONObject(
			mockHttpServletResponse.getContentAsString());

		Assert.assertTrue(responseJSONObject.has("audiences"));
		Assert.assertNotNull(responseJSONObject.getJSONArray("audiences"));
	}

	@Test
	@TestInfo("LPD-91094")
	public void testGetAudiencesReturnsOnlyAudienceSources() throws Exception {
		SegmentsEntry defaultSegmentsEntry = _addSegmentsEntry(
			_createGroupJSONObject(
				"and", _createRuleJSONObject(Context.URL, "/decoy")),
			RandomTestUtil.randomString(),
			SegmentsEntryConstants.SOURCE_DEFAULT);

		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			_createGroupJSONObject(
				"and", _createRuleJSONObject(Context.URL, "/pricing")),
			RandomTestUtil.randomString(),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONArray audiencesJSONArray = _getAudiencesJSONArray();

		Assert.assertNotNull(
			_getAudienceJSONObject(
				audiencesJSONArray, segmentsEntry.getSegmentsEntryKey()));
		Assert.assertNull(
			_getAudienceJSONObject(
				audiencesJSONArray,
				defaultSegmentsEntry.getSegmentsEntryKey()));
	}

	@Test
	@TestInfo("LPD-91094")
	public void testGetAudiencesWithNestedRules() throws Exception {
		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			_createGroupJSONObject(
				"and", _createRuleJSONObject(Context.URL, "/pricing"),
				_createGroupJSONObject(
					"or", _createRuleJSONObject(Context.URL, "/features"),
					_createRuleJSONObject(Context.URL, "/billing"))),
			RandomTestUtil.randomString(),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONObject audienceJSONObject = _getAudienceJSONObject(
			_getAudiencesJSONArray(), segmentsEntry.getSegmentsEntryKey());

		Assert.assertEquals("AND", audienceJSONObject.getString("conjunction"));

		JSONArray rulesJSONArray = audienceJSONObject.getJSONArray("rules");

		Assert.assertEquals(2, rulesJSONArray.length());

		JSONObject leafJSONObject = rulesJSONArray.getJSONObject(0);

		Assert.assertEquals(Context.URL, leafJSONObject.getString("attribute"));
		Assert.assertEquals("eq", leafJSONObject.getString("operator"));
		Assert.assertEquals("/pricing", leafJSONObject.getString("value"));

		JSONObject groupJSONObject = rulesJSONArray.getJSONObject(1);

		Assert.assertFalse(groupJSONObject.has("attribute"));
		Assert.assertEquals("OR", groupJSONObject.getString("conjunction"));

		JSONArray groupRulesJSONArray = groupJSONObject.getJSONArray("rules");

		Assert.assertEquals(2, groupRulesJSONArray.length());

		JSONObject rulesJSONObject1 = groupRulesJSONArray.getJSONObject(0);

		Assert.assertEquals("/features", rulesJSONObject1.getString("value"));

		JSONObject rulesJSONObject2 = groupRulesJSONArray.getJSONObject(1);

		Assert.assertEquals("/billing", rulesJSONObject2.getString("value"));
	}

	@Test
	@TestInfo("LPD-91094")
	public void testGetAudiencesWithOrRule() throws Exception {
		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			_createGroupJSONObject(
				"or", _createRuleJSONObject(Context.URL, "facebook.com"),
				_createRuleJSONObject(Context.URL, "twitter.com")),
			RandomTestUtil.randomString(),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONObject audienceJSONObject = _getAudienceJSONObject(
			_getAudiencesJSONArray(), segmentsEntry.getSegmentsEntryKey());

		Assert.assertEquals("OR", audienceJSONObject.getString("conjunction"));

		JSONArray rulesJSONArray = audienceJSONObject.getJSONArray("rules");

		Assert.assertEquals(2, rulesJSONArray.length());

		Map<String, JSONObject> rulesByValue = new HashMap<>();

		for (int i = 0; i < rulesJSONArray.length(); i++) {
			JSONObject ruleJSONObject = rulesJSONArray.getJSONObject(i);

			rulesByValue.put(ruleJSONObject.getString("value"), ruleJSONObject);
		}

		Assert.assertTrue(rulesByValue.containsKey("facebook.com"));
		Assert.assertTrue(rulesByValue.containsKey("twitter.com"));
	}

	private SegmentsEntry _addSegmentsEntry(
			JSONObject queryJSONObject, String segmentsEntryKey, String source)
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			segmentsEntryKey, segmentsEntryKey, segmentsEntryKey,
			queryJSONObject.toString(), source,
			ServiceContextTestUtil.getServiceContext(
				_companyGroup.getGroupId(), TestPropsValues.getUserId()));

		_segmentsEntries.add(segmentsEntry);

		return segmentsEntry;
	}

	private JSONObject _createGroupJSONObject(
		String conjunctionName, JSONObject... itemJSONObjects) {

		return JSONUtil.put(
			"conjunctionName", conjunctionName
		).put(
			"items", JSONUtil.putAll((Object[])itemJSONObjects)
		);
	}

	private JSONObject _createRuleJSONObject(
		String propertyName, String value) {

		return _createRuleJSONObject(propertyName, _TYPE_EQUALS, value);
	}

	private JSONObject _createRuleJSONObject(
		String propertyName, String operatorName, String value) {

		return JSONUtil.put(
			"operatorName", operatorName
		).put(
			"propertyName", propertyName
		).put(
			"value", value
		);
	}

	private JSONObject _getAudienceJSONObject(
		JSONArray audiencesJSONArray, String id) {

		for (int i = 0; i < audiencesJSONArray.length(); i++) {
			JSONObject audienceJSONObject = audiencesJSONArray.getJSONObject(i);

			if (Objects.equals(id, audienceJSONObject.getString("id"))) {
				return audienceJSONObject;
			}
		}

		return null;
	}

	private JSONArray _getAudiencesJSONArray() throws Exception {
		MockHttpServletResponse mockHttpServletResponse = _invokeServlet();

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		JSONObject responseJSONObject = _jsonFactory.createJSONObject(
			mockHttpServletResponse.getContentAsString());

		return responseJSONObject.getJSONArray("audiences");
	}

	private MockHttpServletResponse _invokeServlet() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest("GET", "/audiences/");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		return mockHttpServletResponse;
	}

	private static final String _TYPE_EQUALS = "eq";

	private Group _companyGroup;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@DeleteAfterTestRun
	private final List<SegmentsEntry> _segmentsEntries = new ArrayList<>();

	@Inject(
		filter = "osgi.http.whiteboard.servlet.name=com.liferay.audience.web.internal.servlet.GetAudiencesServlet"
	)
	private Servlet _servlet;

}