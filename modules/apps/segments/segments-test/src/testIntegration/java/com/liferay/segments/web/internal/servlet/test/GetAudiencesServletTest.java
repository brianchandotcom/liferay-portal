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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			RandomTestUtil.randomString(),
			_createGroupJSONObject(
				"and", _createRuleJSONObject("url", "/pricing")),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONArray audiencesJSONArray = _getAudiencesJSONArray();

		Assert.assertEquals(1, audiencesJSONArray.length());

		JSONObject audienceJSONObject = audiencesJSONArray.getJSONObject(0);

		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryKey(),
			audienceJSONObject.getString("id"));
		Assert.assertEquals("AND", audienceJSONObject.getString("conjunction"));
		Assert.assertEquals(
			"BROWSER", audienceJSONObject.getString("retentionType"));

		JSONArray rulesJSONArray = audienceJSONObject.getJSONArray("rules");

		Assert.assertEquals(1, rulesJSONArray.length());

		JSONObject ruleJSONObject = rulesJSONArray.getJSONObject(0);

		Assert.assertEquals("url", ruleJSONObject.getString("attribute"));
		Assert.assertEquals("eq", ruleJSONObject.getString("operation"));
		Assert.assertEquals("/pricing", ruleJSONObject.getString("value"));
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
		_addSegmentsEntry(
			RandomTestUtil.randomString(),
			_createGroupJSONObject(
				"and", _createRuleJSONObject("url", "/decoy")),
			SegmentsEntryConstants.SOURCE_DEFAULT);

		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			RandomTestUtil.randomString(),
			_createGroupJSONObject(
				"and", _createRuleJSONObject("url", "/pricing")),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONArray audiencesJSONArray = _getAudiencesJSONArray();

		Assert.assertEquals(
			audiencesJSONArray.toString(), 1, audiencesJSONArray.length());

		JSONObject jsonObject = audiencesJSONArray.getJSONObject(0);

		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryKey(), jsonObject.getString("id"));
	}

	@Test
	@TestInfo("LPD-91094")
	public void testGetAudiencesWithNestedRules() throws Exception {
		_addSegmentsEntry(
			RandomTestUtil.randomString(),
			_createGroupJSONObject(
				"and", _createRuleJSONObject("url", "/pricing"),
				_createGroupJSONObject(
					"or", _createRuleJSONObject("url", "/features"),
					_createRuleJSONObject("url", "/billing"))),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONArray audiencesJSONArray = _getAudiencesJSONArray();

		Assert.assertEquals(1, audiencesJSONArray.length());

		JSONObject audienceJSONObject = audiencesJSONArray.getJSONObject(0);

		Assert.assertEquals("AND", audienceJSONObject.getString("conjunction"));

		JSONArray rulesJSONArray = audienceJSONObject.getJSONArray("rules");

		Assert.assertEquals(2, rulesJSONArray.length());

		JSONObject leafJSONObject = rulesJSONArray.getJSONObject(0);

		Assert.assertEquals("url", leafJSONObject.getString("attribute"));
		Assert.assertEquals("eq", leafJSONObject.getString("operation"));
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
		_addSegmentsEntry(
			RandomTestUtil.randomString(),
			_createGroupJSONObject(
				"or", _createRuleJSONObject("url", "facebook.com"),
				_createRuleJSONObject("url", "twitter.com")),
			SegmentsEntryConstants.SOURCE_AUDIENCE);

		JSONArray audiencesJSONArray = _getAudiencesJSONArray();

		Assert.assertEquals(1, audiencesJSONArray.length());

		JSONObject audienceJSONObject = audiencesJSONArray.getJSONObject(0);

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
			String segmentsEntryKey, JSONObject queryJSONObject, String source)
		throws Exception {

		Criteria criteria = new Criteria();

		_contextSegmentsCriteriaContributor.contribute(
			criteria, queryJSONObject.toString(), Criteria.Conjunction.AND);

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			segmentsEntryKey, segmentsEntryKey, segmentsEntryKey,
			CriteriaSerializer.serialize(criteria), source,
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

		return JSONUtil.put(
			"operatorName", _TYPE_EQUALS
		).put(
			"propertyName", propertyName
		).put(
			"value", value
		);
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

	@Inject(
		filter = "segments.criteria.contributor.key=context",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _contextSegmentsCriteriaContributor;

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